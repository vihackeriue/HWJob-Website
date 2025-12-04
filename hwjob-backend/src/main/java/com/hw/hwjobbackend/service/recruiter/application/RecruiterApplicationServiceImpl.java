package com.hw.hwjobbackend.service.recruiter.application;

import com.hw.hwjobbackend.exception.AppException;
import com.hw.hwjobbackend.exception.ErrorCode;
import com.hw.hwjobbackend.model.dto.request.application.ApplicationRecruiterRequest;
import com.hw.hwjobbackend.model.dto.request.application.ApplicationStatusRequest;
import com.hw.hwjobbackend.model.dto.response.application.ApplicationCandidateResponse;
import com.hw.hwjobbackend.model.entity.application.Application;
import com.hw.hwjobbackend.model.entity.application.ApplicationId;
import com.hw.hwjobbackend.repository.application.ApplicationRepository;
import com.hw.hwjobbackend.repository.job_post.JobPostRepository;
import com.hw.hwjobbackend.repository.user.CandidateRepository;
import com.hw.hwjobbackend.service.mapper.application.ApplicationMapper;
import com.hw.hwjobbackend.util.PaginationUtils;
import com.hw.hwjobbackend.util.SecurityUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RecruiterApplicationServiceImpl implements RecruiterApplicationService {

    ApplicationRepository applicationRepository;
    JobPostRepository jobPostRepository;
    CandidateRepository candidateRepository;
    ApplicationMapper applicationMapper;

    @Override
    public Page<ApplicationCandidateResponse> getCandidateApplications(int page, int size, String jobPostId) {
        String recruiterId = SecurityUtils.getCurrentUserId();

        Pageable pageable = PaginationUtils.buildPageable(page, size);

        Page<Application> applications = applicationRepository
                .findByJobPostIdAndRecruiterIdOrderByCreatedAtDesc(jobPostId, recruiterId, pageable);

        return applications.map(applicationMapper::toCandidateApplicationResponse);
    }

    @Override
    public List<ApplicationCandidateResponse> getAllCandidateApplications(String jobPostId) {
        String recruiterId = SecurityUtils.getCurrentUserId();

        List<Application> applications = applicationRepository
                .findAllByJobPostIdAndRecruiterIdOrderByCreatedAtDesc(jobPostId, recruiterId);

        return applications.stream()
                .map(applicationMapper::toCandidateApplicationResponse)
                .toList();
    }

    @Override
    public void updateCandidateApplication(String jobPostId, String candidateId, ApplicationStatusRequest request) {
        if (!jobPostRepository.existsById(jobPostId)) {
            throw new AppException(ErrorCode.JOB_POST_NOT_EXISTED);
        }
        if (!candidateRepository.existsById(candidateId)) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }

        String recruiterId = SecurityUtils.getCurrentUserId();

        ApplicationId applicationId = ApplicationId.builder()
                .candidateId(jobPostId)
                .jobPostId(candidateId)
                .build();

        Application application = applicationRepository
                .findByIdAndRecruiterId(applicationId, recruiterId)
                .orElseThrow(() -> new AppException(ErrorCode.UNAUTHORIZED));

        if (application.getStatus() != request.getStatus()) {
            application.setStatus(request.getStatus());
        }
    }
}