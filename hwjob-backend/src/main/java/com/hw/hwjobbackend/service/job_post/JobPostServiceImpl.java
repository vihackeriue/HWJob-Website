package com.hw.hwjobbackend.service.job_post;

import com.hw.hwjobbackend.model.dto.request.job_post.JobPostCreationRequest;
import com.hw.hwjobbackend.model.dto.request.job_post.JobPostFilterRequest;
import com.hw.hwjobbackend.model.dto.response.job_post.JobPostDetailResponse;
import com.hw.hwjobbackend.model.dto.response.job_post.JobPostResponse;
import com.hw.hwjobbackend.model.dto.response.job_post.SaveJobPostResponse;
import com.hw.hwjobbackend.model.dto.response.user.RecruiterProfileResponse;
import com.hw.hwjobbackend.model.entity.job_post.JobPost;
import com.hw.hwjobbackend.model.entity.application.ApplicationId;
import com.hw.hwjobbackend.model.entity.candidate_save_job.CandidateSaveJob;
import com.hw.hwjobbackend.model.entity.candidate_save_job.CandidateSaveJobId;
import com.hw.hwjobbackend.model.entity.user.Candidate;
import com.hw.hwjobbackend.model.entity.user.Recruiter;
import com.hw.hwjobbackend.model.enums.JobPostStatus;
import com.hw.hwjobbackend.model.enums.RoleEnum;
import com.hw.hwjobbackend.exception.AppException;
import com.hw.hwjobbackend.exception.ErrorCode;
import com.hw.hwjobbackend.mapper.job_post.JobPostMapper;
import com.hw.hwjobbackend.mapper.user.RecruiterMapper;
import com.hw.hwjobbackend.repository.application.ApplicationRepository;
import com.hw.hwjobbackend.repository.candidate_save_job.CandidateSaveJobRepository;
import com.hw.hwjobbackend.repository.job_post.JobPostRepository;
import com.hw.hwjobbackend.service.industry.IndustryService;
import com.hw.hwjobbackend.service.job_type.JobTypeService;
import com.hw.hwjobbackend.service.level.LevelService;
import com.hw.hwjobbackend.service.region.RegionService;
import com.hw.hwjobbackend.service.user.CandidateService;
import com.hw.hwjobbackend.service.user.RecruiterService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class JobPostServiceImpl implements JobPostService {

    JobPostRepository jobPostRepository;
    JobPostMapper jobPostMapper;
    RecruiterMapper recruiterMapper;

    LevelService levelService;
    JobTypeService jobTypeService;
    IndustryService industryService;
    RecruiterService recruiterService;
    RegionService regionService;
    CandidateSaveJobRepository candidateSaveJobRepository;

    CandidateService candidateService;


    ApplicationRepository applicationRepository;


    @Override
    public Page<JobPostResponse> getJobPosts(Integer page, Integer size, JobPostFilterRequest filter
    ) {
        page = Math.max(page, 0);
        size = size <= 0 ? 10 : size;
        Pageable pageable = PageRequest.of(page, size);

        Page<JobPost> jobPosts = jobPostRepository.getJobPosts(
                JobPostStatus.PUBLIC,
                filter.getIndustryId(),
                filter.getLevelId(),
                filter.getJobTypeId(),
                filter.getRegionId(),
                pageable
        );
        return jobPosts.map(jobPostMapper::toJobPostResponse);

    }

    @Override
    public List<JobPostResponse> getAllJobPosts() {
        return jobPostRepository.findAll().stream()
                .map(jobPostMapper::toJobPostResponse)
                .toList();
    }

    @Override
//    @PreAuthorize("hasRole('ADMIN') or hasRole('RECRUITER')")
    @PreAuthorize("hasAnyAuthority('ROLE_RECRUITER', 'ROLE_ADMIN')")
    public JobPostResponse createJobPost(JobPostCreationRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Recruiter recruiter = recruiterService.getRecruiterEntityByName(username);

        JobPost jobPost = jobPostMapper.toJobPost(request);

        jobPost.setRecruiter(recruiter);

        jobPost.setLevel(
                request.getLevelId() != null ?
                        levelService.getLevelEntityById(request.getLevelId())
                        : null
        );

        jobPost.setJobType(
                request.getJobTypeId() != null
                        ? jobTypeService.getJobTypeEntityById(request.getJobTypeId())
                        : null
        );

        jobPost.setIndustry(
                request.getIndustryId() != null
                        ? industryService.getIndustryEntityById(request.getIndustryId())
                        : null
        );

        jobPost.setProvince(
                request.getRegionId() != null
                        ? regionService.getProvinceByCode(request.getRegionId())
                        : null
        );
        jobPost.setSalaryType(request.getSalaryType());

        jobPost.setStatus(JobPostStatus.PUBLIC);

        jobPost = jobPostRepository.save(jobPost);

        return jobPostMapper.toJobPostResponse(jobPost);
    }

    @Override
    public JobPost getJobPostEntityById(String id) {
        return jobPostRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.JOB_POST_NOT_EXISTED)
        );
    }

    @Override
    public JobPostDetailResponse getJobPostDetail(String id) {

        JobPost jobPost = getJobPostEntityById(id);
        JobPostDetailResponse response = jobPostMapper.toJobPostDetailResponse(jobPost);

        if (jobPost.getRecruiter() != null) {
            RecruiterProfileResponse recruiterResponse = recruiterMapper.toRecruiterProfileResponse(jobPost.getRecruiter());
            recruiterResponse.setRegion(jobPost.getRecruiter().getProvince() != null ? jobPost.getRecruiter().getProvince().getName() : null);
            response.setRecruiter(recruiterResponse);
        } else {
            response.setRecruiter(null);
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String role = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElseThrow(() -> new AppException(ErrorCode.UNAUTHORIZED));
        String roleName = role.replace("ROLE_", "");
        RoleEnum userRole;
        try {
            userRole = RoleEnum.valueOf(roleName);
        } catch (IllegalArgumentException e) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        String username = authentication.getName();

        switch (userRole) {
            case ADMIN:
            case RECRUITER:
                response.setIsApplied(false);
                response.setIsSaved(false);
                break;
            case CANDIDATE:
                Candidate candidate = candidateService.getCandidateEntityByName(username);
                ApplicationId applicationId = ApplicationId.builder()
                        .candidateId(candidate.getId())
                        .jobPostId(jobPost.getId())
                        .build();
                response.setIsApplied(
                        applicationRepository.existsApplicationById(applicationId)
                );
                CandidateSaveJobId candidateSaveJobId = CandidateSaveJobId.builder()
                        .candidateId(candidate.getId())
                        .jobPostId(jobPost.getId())
                        .build();

                response.setIsSaved(
                        candidateSaveJobRepository.existsCandidateSaveJobById(candidateSaveJobId)
                );
                break;
            default:
                throw new AppException(ErrorCode.UNAUTHORIZED);
        }
        return response;
    }

    @Override
    @PreAuthorize("hasAnyAuthority('ROLE_RECRUITER', 'ROLE_ADMIN')")
    public SaveJobPostResponse saveJobPost(String id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Candidate candidate = candidateService.getCandidateEntityByName(username);
        JobPost jobPost = getJobPostEntityById(id);

        CandidateSaveJobId candidateSaveJobId = CandidateSaveJobId.builder()
                .candidateId(candidate.getId())
                .jobPostId(jobPost.getId())
                .build();

        if (candidateSaveJobRepository.existsCandidateSaveJobById(candidateSaveJobId)) {
            candidateSaveJobRepository.deleteById(candidateSaveJobId);
            return SaveJobPostResponse.builder()
                    .id(candidateSaveJobId.getJobPostId())
                    .isSaved(false)
                    .build();
        }

        CandidateSaveJob candidateSaveJob = CandidateSaveJob.builder()
                .id(candidateSaveJobId)
                .candidate(candidate)
                .jobPost(jobPost)
                .build();

        candidateSaveJobRepository.save(candidateSaveJob);

        return SaveJobPostResponse.builder()
                .id(candidateSaveJob.getJobPost().getId())
                .isSaved(true)
                .build();
    }
}
