package com.hw.hwjobbackend.service.candidate.job_post;

import com.hw.hwjobbackend.exception.AppException;
import com.hw.hwjobbackend.exception.ErrorCode;
import com.hw.hwjobbackend.model.dto.api.request.CandidateIndexingRequest;
import com.hw.hwjobbackend.model.dto.api.request.JobPostRecommendationRequest;
import com.hw.hwjobbackend.model.dto.api.response.RecommendationResponse;
import com.hw.hwjobbackend.model.dto.response.job_post.JobPostResponse;
import com.hw.hwjobbackend.model.dto.response.job_post.SaveJobPostResponse;
import com.hw.hwjobbackend.model.entity.candidate_save_job.CandidateSaveJob;
import com.hw.hwjobbackend.model.entity.candidate_save_job.CandidateSaveJobId;
import com.hw.hwjobbackend.model.entity.job_post.JobPost;
import com.hw.hwjobbackend.model.entity.skill.Skill;
import com.hw.hwjobbackend.model.entity.user.Candidate;
import com.hw.hwjobbackend.repository.candidate_save_job.CandidateSaveJobRepository;
import com.hw.hwjobbackend.repository.http_client.ServerAIFeignClient;
import com.hw.hwjobbackend.repository.job_post.JobPostRepository;
import com.hw.hwjobbackend.repository.user.CandidateRepository;
import com.hw.hwjobbackend.service.mapper.job_post.JobPostMapper;
import com.hw.hwjobbackend.util.PaginationUtils;
import com.hw.hwjobbackend.util.SecurityUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CandidateJobPostServiceImpl implements CandidateJobPostService {

    CandidateSaveJobRepository candidateSaveJobRepository;
    JobPostRepository jobPostRepository;
    CandidateRepository candidateRepository;
    JobPostMapper jobPostMapper;
    ServerAIFeignClient serverAIFeignClient;

    @Override
    public List<JobPostResponse> getRecommendJobPosts(String userId) {

        Candidate candidate = candidateRepository.findById(userId).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED)
        );

        JobPostRecommendationRequest request = JobPostRecommendationRequest.builder()
                .candidateId(candidate.getId())
                .build();

        RecommendationResponse recommendationResponse = serverAIFeignClient.recommendJobs(request);

        return recommendationResponse
                .getResults().stream()
                .map(rankedItemResponse -> jobPostRepository.findById(rankedItemResponse.getId())
                        .orElseThrow(() -> new AppException(ErrorCode.JOB_POST_NOT_EXISTED)))
                .map(jobPostMapper::toJobPostResponse)
                .toList();
    }

    @Override
    public SaveJobPostResponse saveJobPost(String jobPostId) {
        String candidateId = SecurityUtils.getCurrentUserId();

        if (!jobPostRepository.existsById(jobPostId)) {
            throw new AppException(ErrorCode.JOB_POST_NOT_EXISTED);
        }

        CandidateSaveJobId candidateSaveJobId = CandidateSaveJobId.builder()
                .candidateId(candidateId)
                .jobPostId(jobPostId)
                .build();


        if (candidateSaveJobRepository.existsById(candidateSaveJobId)) {

            candidateSaveJobRepository.deleteById(candidateSaveJobId);

            return SaveJobPostResponse.builder()
                    .id(jobPostId)
                    .isSaved(false)
                    .build();
        }


        Candidate candidate = candidateRepository.getReferenceById(candidateId);

        JobPost jobPost = jobPostRepository.getReferenceById(jobPostId);

        CandidateSaveJob candidateSaveJob = CandidateSaveJob.builder()
                .id(candidateSaveJobId)
                .candidate(candidate)
                .jobPost(jobPost)
                .build();

        candidateSaveJobRepository.save(candidateSaveJob);

        return SaveJobPostResponse.builder()
                .id(jobPostId)
                .isSaved(true)
                .build();
    }

    @Override
    public Page<JobPostResponse> getSavedJobPosts(int page, int size) {
        String candidateId = SecurityUtils.getCurrentUserId();
        Pageable pageable = PaginationUtils.buildPageable(page, size);

        Page<JobPost> jobPosts = candidateSaveJobRepository
                .findSavedJobPostsByCandidateId(candidateId, pageable);

        return jobPosts.map(jobPostMapper::toJobPostResponse);
    }

    @Override
    public List<JobPostResponse> getAllSavedJobPosts() {
        String candidateId = SecurityUtils.getCurrentUserId();

        List<JobPost> jobPosts = candidateSaveJobRepository
                .findAllSavedJobPostsByCandidateId(candidateId);

        return jobPosts.stream()
                .map(jobPostMapper::toJobPostResponse)
                .toList();
    }
}
