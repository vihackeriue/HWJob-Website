package com.hw.hwjobbackend.service.shared.indexing;

import com.hw.hwjobbackend.model.dto.api.request.CandidateIndexingRequest;
import com.hw.hwjobbackend.model.dto.api.request.JobPostIndexingRequest;
import com.hw.hwjobbackend.model.dto.api.response.ServerAIMessageResponse;
import com.hw.hwjobbackend.model.entity.job_post.JobPost;
import com.hw.hwjobbackend.model.entity.skill.Skill;
import com.hw.hwjobbackend.model.entity.user.Candidate;
import com.hw.hwjobbackend.repository.http_client.ServerAIFeignClient;
import com.hw.hwjobbackend.repository.job_post.JobPostRepository;
import com.hw.hwjobbackend.repository.user.CandidateRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class IndexingServiceImpl implements IndexingService {

    ServerAIFeignClient serverAIFeignClient;
    CandidateRepository candidateRepository;
    JobPostRepository jobPostRepository;


    public void createCandidateIndexing(Candidate candidate) {

        List<String> candidateSkills = (candidate.getSkills() != null)
                ? candidate.getSkills().stream().map(Skill::getName).toList()
                : List.of();

        CandidateIndexingRequest request = CandidateIndexingRequest.builder()
                .candidateId(candidate.getId())
                .summary(candidate.getSummary())
                .education(candidate.getEducation())
                .skills(candidateSkills)
                .build();
        ServerAIMessageResponse response = serverAIFeignClient.indexCandidate(request);
        log.info(response.getMessage());
    }

    @Override
    public void createJobPostIndexing(JobPost jobPost) {

        List<String> jobPostSkills = (jobPost.getSkills() != null)
                ? jobPost.getSkills().stream().map(Skill::getName).toList()
                : List.of();

        JobPostIndexingRequest jobPostIndexingRequest = JobPostIndexingRequest.builder()
                .jobId(jobPost.getId())
                .title(jobPost.getTitle())
                .description(jobPost.getDescription())
                .level(jobPost.getLevel().getName())
                .skills(jobPostSkills)
                .endedTime(jobPost.getEndedTime())
                .status(jobPost.getStatus().name())
                .build();
        ServerAIMessageResponse response = serverAIFeignClient.indexJobPost(jobPostIndexingRequest);
        log.info(response.getMessage());
    }

    @Override
    @Scheduled(cron = "0 */30 * * * ?")
    @Transactional(readOnly = true)
    public void indexAllCandidates() {
        log.info("Starting scheduled indexing for all candidates...");
        List<Candidate> candidates = candidateRepository.findAll();
        for (Candidate candidate : candidates) {
            try {
                createCandidateIndexing(candidate);
            } catch (Exception e) {
                log.error("Failed to index candidate {}: {}", candidate.getId(), e.getMessage());
            }
        }
        log.info("Completed scheduled indexing for all candidates.");
    }

    @Override
    @Scheduled(cron = "0 */30 * * * ?")
    @Transactional(readOnly = true)
    public void indexAllJobPosts() {
        log.info("Starting scheduled indexing for all job posts...");
        List<JobPost> jobPosts = jobPostRepository.findAll();
        for (JobPost jobPost : jobPosts) {
            try {
                createJobPostIndexing(jobPost);
            } catch (Exception e) {
                log.error("Failed to index job post {}: {}", jobPost.getId(), e.getMessage());
            }
        }
        log.info("Completed scheduled indexing for all job posts.");
    }
}
