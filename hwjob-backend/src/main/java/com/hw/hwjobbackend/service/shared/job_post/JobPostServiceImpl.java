package com.hw.hwjobbackend.service.shared.job_post;

import com.hw.hwjobbackend.exception.AppException;
import com.hw.hwjobbackend.exception.ErrorCode;
import com.hw.hwjobbackend.model.dto.response.job_post.JobPostRecruiterProfileResponse;
import com.hw.hwjobbackend.model.entity.user.Recruiter;
import com.hw.hwjobbackend.repository.user.RecruiterRepository;
import com.hw.hwjobbackend.service.mapper.job_post.JobPostMapper;
import com.hw.hwjobbackend.model.dto.request.job_post.JobPostFilterRequest;
import com.hw.hwjobbackend.model.dto.response.job_post.JobPostDetailResponse;
import com.hw.hwjobbackend.model.dto.response.job_post.JobPostResponse;
import com.hw.hwjobbackend.model.entity.application.ApplicationId;
import com.hw.hwjobbackend.model.entity.candidate_save_job.CandidateSaveJobId;
import com.hw.hwjobbackend.model.entity.job_post.JobPost;
import com.hw.hwjobbackend.model.entity.user.Candidate;
import com.hw.hwjobbackend.model.enums.JobPostStatusEnum;
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

    CandidateSaveJobRepository candidateSaveJobRepository;
    CandidateRepository candidateRepository;
    ApplicationRepository applicationRepository;
    RecruiterRepository recruiterRepository;


    @Override
    public Page<JobPostResponse> getJobPosts(Integer page, Integer size, JobPostFilterRequest filter
    ) {
        page = Math.max(page, 0);
        size = size <= 0 ? 10 : size;
        Pageable pageable = PageRequest.of(page, size);

        Page<JobPost> jobPosts = jobPostRepository.getJobPosts(
                JobPostStatusEnum.PUBLIC,
                filter.getIndustryId(),
                filter.getLevelId(),
                filter.getJobTypeId(),
                filter.getRegionId(),
                pageable
        );
        return jobPosts.map(jobPostMapper::toJobPostResponse);
    }

    @Override
    public List<JobPostResponse> getAllJobPosts(JobPostFilterRequest filter) {
        List<JobPost> jobPosts = jobPostRepository.getAllJobPosts(
                JobPostStatusEnum.PUBLIC,
                filter.getIndustryId(),
                filter.getLevelId(),
                filter.getJobTypeId(),
                filter.getRegionId()
        );

        return jobPosts.stream()
                .map(jobPostMapper::toJobPostResponse)
                .toList();
    }

    @Override
    public JobPostDetailResponse getJobPostDetail(String id) {
        JobPost jobPost = jobPostRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.JOB_POST_NOT_EXISTED)
        );

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        RoleEnum userRole = authentication.getAuthorities()
                .stream()
                .map(a -> a.getAuthority().replace("ROLE_", ""))
                .map(RoleEnum::valueOf)
                .findAny()
                .orElseThrow(() -> new AppException(ErrorCode.UNAUTHORIZED));

        boolean canViewPrivate = false;

        if (jobPost.getStatus() == JobPostStatusEnum.PUBLIC) {
            canViewPrivate = true;
        } else {
            if (userRole == RoleEnum.ADMIN) {
                canViewPrivate = true;
            } else if (userRole == RoleEnum.RECRUITER) {
                Recruiter recruiter = recruiterRepository.findByUsername(username).orElseThrow(
                        () -> new AppException(ErrorCode.USER_NOT_EXISTED)
                );
                canViewPrivate = jobPost.getRecruiter() != null &&
                        jobPost.getRecruiter().getId().equals(recruiter.getId());
            }
        }
        if (!canViewPrivate) {
            throw new AppException(ErrorCode.JOB_POST_NOT_EXISTED);
        }
        JobPostDetailResponse response = jobPostMapper.toJobPostDetailResponse(jobPost);
        if (jobPost.getRecruiter() != null) {
            JobPostRecruiterProfileResponse recruiterResponse =
                    jobPostMapper.toJobPostRecruiterProfileResponse(jobPost.getRecruiter());
            response.setRecruiter(recruiterResponse);
        }
        if (userRole == RoleEnum.CANDIDATE) {
            Candidate candidate = candidateRepository.findByUsername(username).orElseThrow(
                    () -> new AppException(ErrorCode.USER_NOT_EXISTED)
            );

            ApplicationId applicationId = new ApplicationId(candidate.getId(), jobPost.getId());
            response.setIsApplied(applicationRepository.existsApplicationById(applicationId));

            CandidateSaveJobId saveId = new CandidateSaveJobId(candidate.getId(), jobPost.getId());
            response.setIsSaved(candidateSaveJobRepository.existsCandidateSaveJobById(saveId));

        } else {
            response.setIsApplied(false);
            response.setIsSaved(false);
        }

        return response;
    }
}
