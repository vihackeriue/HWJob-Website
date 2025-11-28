package com.hw.hwjobbackend.service.recruiter.application;

import com.hw.hwjobbackend.exception.AppException;
import com.hw.hwjobbackend.exception.ErrorCode;
import com.hw.hwjobbackend.model.dto.request.application.ApplicationRequest;
import com.hw.hwjobbackend.model.dto.response.application.ApplicationCandidateResponse;
import com.hw.hwjobbackend.model.entity.application.Application;
import com.hw.hwjobbackend.model.entity.application.ApplicationId;
import com.hw.hwjobbackend.repository.application.ApplicationRepository;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RecruiterApplicationServiceImpl implements RecruiterApplicationService {

    ApplicationRepository applicationRepository;
    ApplicationMapper applicationMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<ApplicationCandidateResponse> getCandidateApplications(int page, int size, String jobPostId) {
        String recruiterId = SecurityUtils.getCurrentUserId();

        Pageable pageable = PaginationUtils.buildPageable(page, size);

        Page<Application> applications = applicationRepository
                .findByJobPostIdAndRecruiterIdOrderByCreatedAtDesc(jobPostId, recruiterId, pageable);

        return applications.map(applicationMapper::toCandidateApplicationResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ApplicationCandidateResponse> getAllCandidateApplications(String jobPostId) {
        String recruiterId = SecurityUtils.getCurrentUserId();

        List<Application> applications = applicationRepository
                .findAllByJobPostIdAndRecruiterIdOrderByCreatedAtDesc(jobPostId, recruiterId);

        return applications.stream()
                .map(applicationMapper::toCandidateApplicationResponse)
                .toList();
    }

    @Override
    @Transactional
    public void updateCandidateApplication(ApplicationRequest request) {
        validateApplicationRequest(request);

        String recruiterId = SecurityUtils.getCurrentUserId();

        ApplicationId applicationId = ApplicationId.builder()
                .candidateId(request.getCandidateId())
                .jobPostId(request.getJobPostId())
                .build();

        Application application = applicationRepository
                .findByIdAndRecruiterId(applicationId, recruiterId)
                .orElseThrow(() -> new AppException(ErrorCode.UNAUTHORIZED));

        if (application.getStatus() != request.getStatus()) {
            application.setStatus(request.getStatus());
        }
    }

    private void validateApplicationRequest(ApplicationRequest request) {
        if (request.getStatus() == null) {
            throw new AppException(ErrorCode.INVALID_KEY);
        }
        if (request.getJobPostId() == null || request.getJobPostId().isBlank()) {
            throw new AppException(ErrorCode.INVALID_KEY);
        }
        if (request.getCandidateId() == null || request.getCandidateId().isBlank()) {
            throw new AppException(ErrorCode.INVALID_KEY);
        }
    }
}