package com.hw.hwjobbackend.service.candidate.application;


import com.hw.hwjobbackend.exception.AppException;
import com.hw.hwjobbackend.exception.ErrorCode;
import com.hw.hwjobbackend.mapper.application.ApplicationMapper;
import com.hw.hwjobbackend.model.dto.request.application.ApplicationRequest;
import com.hw.hwjobbackend.model.dto.response.application.ApplicationResponse;
import com.hw.hwjobbackend.model.entity.application.Application;
import com.hw.hwjobbackend.model.entity.application.ApplicationId;
import com.hw.hwjobbackend.model.entity.job_post.JobPost;
import com.hw.hwjobbackend.model.entity.user.Candidate;
import com.hw.hwjobbackend.model.enums.ApplicationStatus;
import com.hw.hwjobbackend.repository.application.ApplicationRepository;
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
public class CandidateApplicationServiceImpl implements CandidateApplicationService {

    ApplicationRepository applicationRepository;
    ApplicationMapper applicationMapper;

    CandidateRepository candidateRepository;
    JobPostRepository jobPostRepository;

    @Override
    @PreAuthorize("hasRole('CANDIDATE')")
    public ApplicationResponse applyJob(ApplicationRequest request) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Candidate candidate = candidateRepository.findByUsername(username).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED)
        );
        JobPost jobPost = jobPostRepository.findById(request.getJobPostId()).orElseThrow(
                () -> new AppException(ErrorCode.JOB_POST_NOT_EXISTED)
        );

        ApplicationId applicationId = ApplicationId.builder()
                .candidateId(candidate.getId())
                .jobPostId(jobPost.getId())
                .build();

        if (applicationRepository.existsApplicationById(applicationId)) {
            throw new AppException(ErrorCode.JOB_POST_ALREADY_APPLIED);
        }

        Application application = Application.builder()
                .id(applicationId)
                .candidate(candidate)
                .jobPost(jobPost)
                .status(ApplicationStatus.PENDING)
                .build();

        log.info("Applying job post {}", application.getCreatedAt());

        applicationRepository.save(application);
        return applicationMapper.toApplicationResponse(application);
    }

    @Override
    public boolean isCandidateApplied(String candidateId, String jobPostId) {
        Candidate candidate = candidateRepository.findById(candidateId).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED)
        );

        JobPost jobPost = jobPostRepository.findById(jobPostId).orElseThrow(
                () -> new AppException(ErrorCode.JOB_POST_NOT_EXISTED)
        );
        ApplicationId applicationId = ApplicationId.builder()
                .candidateId(candidate.getId())
                .jobPostId(jobPost.getId())
                .build();
        return applicationRepository.existsApplicationById(applicationId);
    }

}
