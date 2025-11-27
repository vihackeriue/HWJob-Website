package com.hw.hwjobbackend.service.recruiter.application;


import com.hw.hwjobbackend.exception.AppException;
import com.hw.hwjobbackend.exception.ErrorCode;
import com.hw.hwjobbackend.model.dto.response.application.ApplicationCandidateResponse;
import com.hw.hwjobbackend.model.entity.application.ApplicationId;
import com.hw.hwjobbackend.model.entity.user.Candidate;
import com.hw.hwjobbackend.repository.user.CandidateRepository;
import com.hw.hwjobbackend.service.mapper.application.ApplicationMapper;
import com.hw.hwjobbackend.model.dto.request.application.ApplicationRequest;
import com.hw.hwjobbackend.model.entity.application.Application;
import com.hw.hwjobbackend.model.entity.job_post.JobPost;
import com.hw.hwjobbackend.model.entity.user.Recruiter;
import com.hw.hwjobbackend.repository.application.ApplicationRepository;
import com.hw.hwjobbackend.repository.job_post.JobPostRepository;
import com.hw.hwjobbackend.repository.user.RecruiterRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RecruiterApplicationServiceImpl implements RecruiterApplicationService {

    ApplicationRepository applicationRepository;
    RecruiterRepository recruiterRepository;
    JobPostRepository jobPostRepository;
    CandidateRepository candidateRepository;

    ApplicationMapper applicationMapper;

    @Override
    public Page<ApplicationCandidateResponse> getCandidateApplications(int page, int size, String jobPostId) {

        JobPost jobPost = validateJobPost(jobPostId);

        int safePage = Math.max(page, 0);
        int safeSize = size <= 0 ? 10 : size;
        Pageable pageable = PageRequest.of(safePage, safeSize);

        Page<Application> applications = applicationRepository.findByJobPost(jobPost, pageable);
        return applications.map(applicationMapper::toCandidateApplicationResponse);
    }

    @Override
    public List<ApplicationCandidateResponse> getAllCandidateApplications(String jobPostId) {

        JobPost jobPost = validateJobPost(jobPostId);

        return applicationRepository.findAllByJobPost(jobPost).stream()
                .map(applicationMapper::toCandidateApplicationResponse).toList();
    }

    @Override
    public void updateCandidateApplication(ApplicationRequest request) {

        if (request.getStatus() == null) {
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }

        JobPost jobPost = validateJobPost(request.getJobPostId());

        Candidate candidate = candidateRepository.findById(request.getCandidateId()).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED)
        );

        ApplicationId applicationId = ApplicationId
                .builder()
                .candidateId(candidate.getId())
                .jobPostId(jobPost.getId())
                .build();

        Application application = applicationRepository.findById(applicationId).orElseThrow(
                () -> new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION)
        );

        application.setStatus(request.getStatus());

        applicationRepository.save(application);
    }

    private JobPost validateJobPost(String jobPostId) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Recruiter recruiter = recruiterRepository.findByUsername(username).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED));

        JobPost jobPost = jobPostRepository.findById(jobPostId).orElseThrow(
                () -> new AppException(ErrorCode.JOB_POST_NOT_EXISTED));

        if (!jobPost.getRecruiter().getId().equals(recruiter.getId())) {
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
        return jobPost;
    }
}
