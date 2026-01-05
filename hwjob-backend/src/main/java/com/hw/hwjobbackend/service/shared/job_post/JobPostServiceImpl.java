package com.hw.hwjobbackend.service.shared.job_post;

import com.hw.hwjobbackend.exception.AppException;
import com.hw.hwjobbackend.exception.ErrorCode;
import com.hw.hwjobbackend.model.dto.response.application.ApplicationResponse;
import com.hw.hwjobbackend.model.dto.response.job_post.JobPostDetailStatsResponse;
import com.hw.hwjobbackend.model.dto.response.job_post.JobPostRecruiterProfileResponse;
import com.hw.hwjobbackend.model.dto.response.work.WorkOverviewResponse;
import com.hw.hwjobbackend.model.enums.WorkStatusEnum;
import com.hw.hwjobbackend.model.entity.skill.Skill;
import com.hw.hwjobbackend.model.entity.user.Candidate;
import com.hw.hwjobbackend.repository.http_client.ServerAIFeignClient;
import com.hw.hwjobbackend.repository.review.ReviewRepository;
import com.hw.hwjobbackend.repository.user.CandidateRepository;
import com.hw.hwjobbackend.repository.work.WorkRepository;
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
import com.hw.hwjobbackend.service.mapper.work.WorkMapper;
import com.hw.hwjobbackend.util.PaginationUtils;
import com.hw.hwjobbackend.util.SecurityUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class JobPostServiceImpl implements JobPostService {

    JobPostRepository jobPostRepository;
    JobPostMapper jobPostMapper;
    CandidateSaveJobRepository candidateSaveJobRepository;
    ApplicationRepository applicationRepository;
    WorkRepository workRepository;
    WorkMapper workMapper;
    ReviewRepository reviewRepository;


    JobPostViewService jobPostViewService;


    @Override
    public Page<JobPostResponse> getAllJobPosts(Integer page, Integer size, JobPostFilterRequest filter) {
        Pageable pageable = PaginationUtils.buildPageable(page, size);

        Page<JobPost> jobPosts = jobPostRepository.getAllJobPosts(
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
    public List<JobPostResponse> getTop12BoostedJobPosts() {
        List<JobPost> jobPosts = jobPostRepository.findTop12BoostedJobPosts(
                JobPostStatusEnum.PUBLIC);

        return jobPosts.stream().map(jobPostMapper::toJobPostResponse).toList();
    }

    @Override
    public Page<JobPostResponse> getAllJobPostsByRecruiterId(Integer page, Integer size, String recruiterId) {

        Pageable pageable = PaginationUtils.buildPageable(page, size);
        Page<JobPost> jobPosts = jobPostRepository
                .getAllJobPostsByRecruiterId(JobPostStatusEnum.PUBLIC, recruiterId, pageable);

        return jobPosts.map(jobPostMapper::toJobPostResponse);
    }


    @Override
    public JobPostDetailResponse getJobPostDetail(String id, HttpServletRequest request) {
        JobPost jobPost = jobPostRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.JOB_POST_NOT_EXISTED));

        // INCREASE VIEW
        String viewerKey = jobPostViewService.getViewerKey(request);
        jobPostViewService.increaseView(
                jobPost.getId(),
                viewerKey
        );

        // TOTAL VIEW
        Long totalView = Optional.ofNullable(jobPost.getViewCount()).orElse(0L)
                + jobPostViewService.getRedisView(jobPost.getId());

        JobPostDetailResponse response = jobPostMapper.toJobPostDetailResponse(jobPost);

        JobPostRecruiterProfileResponse recruiterResponse =
                jobPostMapper.toJobPostRecruiterProfileResponse(jobPost.getRecruiter());
        response.setRecruiter(recruiterResponse);
        response.setViewCount(totalView);

        // ===== GUEST =====
        if (!SecurityUtils.isAuthenticated()) {
            response.setApplication(null);
            response.setIsSaved(false);
            return response;
        }
        String userId = SecurityUtils.getCurrentUserId();
        RoleEnum role = SecurityUtils.getCurrentUserRole();

        // ===== CANDIDATE =====
        if (role == RoleEnum.CANDIDATE) {
            applicationRepository
                    .findByJobPostIdAndCandidateId(jobPost.getId(), userId)
                    .ifPresent(application ->
                            response.setApplication(
                                    ApplicationResponse.builder()
                                            .jobPostId(jobPost.getId())
                                            .status(application.getStatus())
                                            .build()
                            )
                    );

            workRepository
                    .findByCandidateIdAndJobPostId(userId, jobPost.getId())
                    .ifPresent(work -> {

                        WorkOverviewResponse workResponse =
                                workMapper.toWorkOverviewResponse(work);

                        // thêm rating của candidate cho recruiter
                        Double myRating = reviewRepository.findMyRating(
                                work.getId(),
                                userId
                        );

                        workResponse.setMyReviewRating(myRating);

                        response.setWork(workResponse);
                    });

            response.setIsSaved(
                    candidateSaveJobRepository.existsByCandidateIdAndJobPostId(
                            userId, jobPost.getId()
                    )
            );

            return response;

        }

        // ===== RECRUITER / ADMIN =====
        response.setApplication(null);
        response.setIsSaved(false);
        response.setWork(null);
        return response;

    }

    @Override
    public void increaseViewCount(String jobPostId, Long viewCount) {
        jobPostRepository.increaseViewCount(jobPostId, viewCount);
    }

}