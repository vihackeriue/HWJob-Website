package com.hw.hwjobbackend.service.shared.job_post;

import com.hw.hwjobbackend.exception.AppException;
import com.hw.hwjobbackend.exception.ErrorCode;
import com.hw.hwjobbackend.model.dto.response.job_post.JobPostRecruiterProfileResponse;
import com.hw.hwjobbackend.service.mapper.job_post.JobPostMapper;
import com.hw.hwjobbackend.model.dto.request.job_post.JobPostFilterRequest;
import com.hw.hwjobbackend.model.dto.response.job_post.JobPostDetailResponse;
import com.hw.hwjobbackend.model.dto.response.job_post.JobPostResponse;
import com.hw.hwjobbackend.model.entity.job_post.JobPost;
import com.hw.hwjobbackend.model.enums.JobPostStatusEnum;
import com.hw.hwjobbackend.model.enums.RoleEnum;
import com.hw.hwjobbackend.repository.application.ApplicationRepository;
import com.hw.hwjobbackend.repository.candidate_save_job.CandidateSaveJobRepository;
import com.hw.hwjobbackend.repository.job_post.JobPostRepository;
import com.hw.hwjobbackend.util.PaginationUtils;
import com.hw.hwjobbackend.util.SecurityUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class JobPostServiceImpl implements JobPostService {

    JobPostRepository jobPostRepository;
    JobPostMapper jobPostMapper;
    CandidateSaveJobRepository candidateSaveJobRepository;
    ApplicationRepository applicationRepository;

    @Override
    public Page<JobPostResponse> getAllJobPosts(Integer page, Integer size, JobPostFilterRequest filter) {
        Pageable pageable = PaginationUtils.buildPageable(page, size);

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


        JobPost jobPost = jobPostRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.JOB_POST_NOT_EXISTED));

        JobPostDetailResponse response = jobPostMapper.toJobPostDetailResponse(jobPost);

        JobPostRecruiterProfileResponse recruiterResponse =
                jobPostMapper.toJobPostRecruiterProfileResponse(jobPost.getRecruiter());
        response.setRecruiter(recruiterResponse);

        if (SecurityUtils.isAuthenticated()) {
//            Optional<String> userIdOpt = SecurityUtils.getCurrentUserIdOptional();
//            Optional<RoleEnum> userRoleOpt = SecurityUtils.getCurrentUserRoleOptional();
            String userId = SecurityUtils.getCurrentUserId();
            RoleEnum userRole = SecurityUtils.getCurrentUserRole();
            if (userRole == RoleEnum.CANDIDATE) {
                setCandidateSpecificInfo(response, userId, jobPost.getId());
            } else {
                // Recruiter or Admin
                response.setIsApplied(false);
                response.setIsSaved(false);
            }
        } else {
            response.setIsApplied(false);
            response.setIsSaved(false);
        }

        return response;
    }

    private void setCandidateSpecificInfo(JobPostDetailResponse response, String candidateId, String jobPostId) {
        boolean isApplied = applicationRepository.existsByCandidateIdAndJobPostId(
                candidateId, jobPostId);
        boolean isSaved = candidateSaveJobRepository.existsByCandidateIdAndJobPostId(
                candidateId, jobPostId);

        response.setIsApplied(isApplied);
        response.setIsSaved(isSaved);
    }
}