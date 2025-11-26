package com.hw.hwjobbackend.service.shared.job_post;

import com.hw.hwjobbackend.exception.AppException;
import com.hw.hwjobbackend.exception.ErrorCode;
import com.hw.hwjobbackend.mapper.job_post.JobPostMapper;
import com.hw.hwjobbackend.mapper.user.RecruiterMapper;
import com.hw.hwjobbackend.model.dto.request.job_post.JobPostFilterRequest;
import com.hw.hwjobbackend.model.dto.response.job_post.JobPostDetailResponse;
import com.hw.hwjobbackend.model.dto.response.job_post.JobPostResponse;
import com.hw.hwjobbackend.model.dto.response.user.RecruiterProfileResponse;
import com.hw.hwjobbackend.model.entity.application.ApplicationId;
import com.hw.hwjobbackend.model.entity.candidate_save_job.CandidateSaveJobId;
import com.hw.hwjobbackend.model.entity.job_post.JobPost;
import com.hw.hwjobbackend.model.entity.user.Candidate;
import com.hw.hwjobbackend.model.enums.JobPostStatus;
import com.hw.hwjobbackend.model.enums.RoleEnum;
import com.hw.hwjobbackend.repository.application.ApplicationRepository;
import com.hw.hwjobbackend.repository.candidate_save_job.CandidateSaveJobRepository;
import com.hw.hwjobbackend.repository.job_post.JobPostRepository;
import com.hw.hwjobbackend.repository.user.CandidateRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    CandidateSaveJobRepository candidateSaveJobRepository;
    CandidateRepository candidateRepository;

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
    public JobPostDetailResponse getJobPostDetail(String id) {

        JobPost jobPost = jobPostRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.JOB_POST_NOT_EXISTED)
        );

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
                Candidate candidate = candidateRepository.findByUsername(username).orElseThrow(
                        () -> new AppException(ErrorCode.USER_NOT_EXISTED)
                );
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
}
