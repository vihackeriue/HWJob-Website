package com.hw.hwjobbackend.service.candidate.job_post;


import com.hw.hwjobbackend.exception.AppException;
import com.hw.hwjobbackend.exception.ErrorCode;
import com.hw.hwjobbackend.model.dto.response.job_post.SaveJobPostResponse;
import com.hw.hwjobbackend.model.entity.candidate_save_job.CandidateSaveJob;
import com.hw.hwjobbackend.model.entity.candidate_save_job.CandidateSaveJobId;
import com.hw.hwjobbackend.model.entity.job_post.JobPost;
import com.hw.hwjobbackend.model.entity.user.Candidate;
import com.hw.hwjobbackend.repository.candidate_save_job.CandidateSaveJobRepository;
import com.hw.hwjobbackend.repository.job_post.JobPostRepository;
import com.hw.hwjobbackend.repository.user.CandidateRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@PreAuthorize("hasRole('CANDIDATE')")
public class CandidateJobPostServiceImpl implements CandidateJobPostService {


    CandidateSaveJobRepository candidateSaveJobRepository;
    JobPostRepository jobPostRepository;
    CandidateRepository candidateRepository;


    @Override
    public SaveJobPostResponse saveJobPost(String id) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Candidate candidate = candidateRepository.findByUsername(username).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED)
        );

        JobPost jobPost = jobPostRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.JOB_POST_NOT_EXISTED)
        );

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
