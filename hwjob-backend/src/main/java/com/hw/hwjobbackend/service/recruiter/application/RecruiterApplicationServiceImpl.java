package com.hw.hwjobbackend.service.recruiter.application;

import com.hw.hwjobbackend.exception.AppException;
import com.hw.hwjobbackend.exception.ErrorCode;
import com.hw.hwjobbackend.model.dto.request.application.ApplicationRecruiterRequest;
import com.hw.hwjobbackend.model.dto.request.application.ApplicationStatusRequest;
import com.hw.hwjobbackend.model.dto.request.work.WorkCreateRequest;
import com.hw.hwjobbackend.model.dto.response.application.ApplicationCandidateResponse;
import com.hw.hwjobbackend.model.entity.application.Application;
import com.hw.hwjobbackend.model.entity.application.ApplicationId;
import com.hw.hwjobbackend.model.enums.ApplicationStatusEnum;
import com.hw.hwjobbackend.repository.application.ApplicationRepository;
import com.hw.hwjobbackend.repository.job_post.JobPostRepository;
import com.hw.hwjobbackend.repository.user.CandidateRepository;
import com.hw.hwjobbackend.service.mapper.application.ApplicationMapper;
import com.hw.hwjobbackend.service.recruiter.work.RecruiterWorkService;
import com.hw.hwjobbackend.util.PaginationUtils;
import com.hw.hwjobbackend.util.SecurityUtils;
import jakarta.transaction.Transactional;
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
    RecruiterWorkService workService;

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
    @Transactional
    public void updateCandidateApplicationStatus(
            String jobPostId,
            String candidateId,
            ApplicationStatusRequest request
    ) {
        ApplicationStatusEnum newStatus = request.getStatus();


        //Lấy recruiter đang đăng nhập
        String recruiterId = SecurityUtils.getCurrentUserId();

        // Lấy application
        ApplicationId applicationId = ApplicationId.builder()
                .candidateId(candidateId)
                .jobPostId(jobPostId)
                .build();

        Application application = applicationRepository
                .findByIdAndRecruiterId(applicationId, recruiterId)
                .orElseThrow(() -> new AppException(ErrorCode.UNAUTHORIZED));

        //VALIDATE CHUYỂN TRẠNG THÁI
        validateStatusTransition(application.getStatus(), newStatus);

        // ASSIGNED -> REJECTED
        if (application.getStatus() == ApplicationStatusEnum.ASSIGNED &&
                newStatus == ApplicationStatusEnum.REJECTED) {

            // Xóa work đã giao
            workService.deleteByJobPostIdAndCandidateId(jobPostId, candidateId);
        }

        // Update
        application.setStatus(newStatus);

        if (newStatus == ApplicationStatusEnum.ASSIGNED) {
            if (request.getAgreedSalary() == null ||
                    request.getSalaryType() == null ||
                    request.getStartTime() == null ||
                    request.getEndTime() == null) {
                throw new AppException(ErrorCode.INVALID_REQUEST);
            }
            WorkCreateRequest workRequest = WorkCreateRequest.builder()
                    .candidateId(candidateId)
                    .jobPostId(jobPostId)
                    .agreedSalary(request.getAgreedSalary())
                    .salaryType(request.getSalaryType())
                    .startTime(request.getStartTime())
                    .endTime(request.getEndTime())
                    .build();

            workService.assignWork(workRequest);
        }


    }
    private void validateStatusTransition(
            ApplicationStatusEnum current,
            ApplicationStatusEnum next
    ) {
        // Không cho đổi khi đã kết thúc
        if (current == ApplicationStatusEnum.ACCEPTED ||
                current == ApplicationStatusEnum.CANCELLED) {
            throw new AppException(ErrorCode.INVALID_APPLICATION_STATUS);
        }

        // Recruiter được REJECTED ở mọi trạng thái chưa kết thúc
        if (next == ApplicationStatusEnum.REJECTED) {
            return;
        }

        switch (current) {
            case PENDING -> {
                if (next != ApplicationStatusEnum.APPROVED) {
                    throw new AppException(ErrorCode.INVALID_APPLICATION_STATUS);
                }
            }
            case APPROVED -> {
                if (next != ApplicationStatusEnum.ASSIGNED) {
                    throw new AppException(ErrorCode.INVALID_APPLICATION_STATUS);
                }
            }
            default -> throw new AppException(ErrorCode.INVALID_APPLICATION_STATUS);
        }
    }


}