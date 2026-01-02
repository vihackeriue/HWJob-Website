package com.hw.hwjobbackend.service.candidate.job_post;

import com.hw.hwjobbackend.exception.AppException;
import com.hw.hwjobbackend.exception.ErrorCode;
import com.hw.hwjobbackend.model.dto.api.request.JobPostRecommendationRequest;
import com.hw.hwjobbackend.model.dto.api.response.RankedItemResponse;
import com.hw.hwjobbackend.model.dto.api.response.RecommendationResponse;
import com.hw.hwjobbackend.model.dto.response.job_post.JobPostResponse;
import com.hw.hwjobbackend.model.dto.response.job_post.SaveJobPostResponse;
import com.hw.hwjobbackend.model.entity.candidate_save_job.CandidateSaveJob;
import com.hw.hwjobbackend.model.entity.candidate_save_job.CandidateSaveJobId;
import com.hw.hwjobbackend.model.entity.job_post.JobPost;
import com.hw.hwjobbackend.model.entity.job_post.RecommendJobPostCache;
import com.hw.hwjobbackend.model.entity.user.Candidate;
import com.hw.hwjobbackend.repository.candidate_save_job.CandidateSaveJobRepository;
import com.hw.hwjobbackend.repository.http_client.ServerAIFeignClient;
import com.hw.hwjobbackend.repository.job_post.JobPostCacheRepository;
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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

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
    JobPostCacheRepository jobPostCacheRepository;

    static final int N_RESULTS = 30;

    @Override
    public Page<JobPostResponse> getRecommendJobPosts(int page, int size) {
        String candidateId = SecurityUtils.getCurrentUserId();

        RecommendJobPostCache cache = jobPostCacheRepository.findById(candidateId).orElse(null);

        if (cache == null) {
            List<String> jobIds = fetchJobPostIds(candidateId);
            log.debug("Get job post cache");
            cache = RecommendJobPostCache.builder()
                    .id(candidateId)
                    .jobPostIds(jobIds)
                    .build();
            jobPostCacheRepository.save(cache);
        }
        return getJobPostsPage(cache.getJobPostIds(), page, size);
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

    private List<String> fetchJobPostIds(String candidateId) {
        Candidate candidate = candidateRepository.findById(candidateId).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED)
        );

        JobPostRecommendationRequest request = JobPostRecommendationRequest.builder()
                .candidateId(candidate.getId())
                .nResults(N_RESULTS)
                .build();

        RecommendationResponse recommendationResponse = serverAIFeignClient.recommendJobs(request);

        return recommendationResponse.getResults().stream()
                .map(RankedItemResponse::getId)
                .collect(Collectors.toList());
    }

    private Page<JobPostResponse> getJobPostsPage(List<String> jobIds, int page, int size) {
        int start = page * size;
        int end = Math.min(start + size, jobIds.size());

        if (start >= jobIds.size()) {
            return new PageImpl<>(Collections.emptyList(), PaginationUtils.buildPageable(page, size), jobIds.size());
        }

        List<String> pageJobIds = jobIds.subList(start, end);

        // Fetch JobPosts from DB
        List<JobPost> jobPosts = jobPostRepository.findAllById(pageJobIds);

        // Sort JobPosts based on the order of pageJobIds
        Map<String, JobPost> jobPostMap = jobPosts.stream()
                .collect(Collectors.toMap(JobPost::getId, Function.identity()));

        List<JobPostResponse> jobPostResponses = new ArrayList<>();
        for (String id : pageJobIds) {
            if (jobPostMap.containsKey(id)) {
                jobPostResponses.add(jobPostMapper.toJobPostResponse(jobPostMap.get(id)));
            }
        }

        return new PageImpl<>(jobPostResponses, PaginationUtils.buildPageable(page, size), jobIds.size());
    }
}
