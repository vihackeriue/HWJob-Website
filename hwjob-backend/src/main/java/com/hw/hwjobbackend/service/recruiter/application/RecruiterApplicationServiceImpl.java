package com.hw.hwjobbackend.service.recruiter.application;

import com.hw.hwjobbackend.exception.AppException;
import com.hw.hwjobbackend.exception.ErrorCode;
import com.hw.hwjobbackend.model.dto.request.application.ApplicationRecruiterRequest;
import com.hw.hwjobbackend.model.dto.request.application.ApplicationStatusRequest;
import com.hw.hwjobbackend.model.dto.response.application.ApplicationCandidateResponse;
import com.hw.hwjobbackend.model.entity.application.Application;
import com.hw.hwjobbackend.model.entity.application.ApplicationId;
import com.hw.hwjobbackend.model.enums.ApplicationStatusEnum;
import com.hw.hwjobbackend.repository.application.ApplicationRepository;
import com.hw.hwjobbackend.repository.job_post.JobPostRepository;
import com.hw.hwjobbackend.repository.user.CandidateRepository;
import com.hw.hwjobbackend.service.mapper.application.ApplicationMapper;
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
            ApplicationStatusEnum newStatus
    ) {
        // Validate job post & candidate
        if (!jobPostRepository.existsById(jobPostId)) {
            throw new AppException(ErrorCode.JOB_POST_NOT_EXISTED);
        }
        if (!candidateRepository.existsById(candidateId)) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }

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

        // Update
        application.setStatus(newStatus);
    }
    private void validateStatusTransition(
            ApplicationStatusEnum current,
            ApplicationStatusEnum next
    ) {

        // Không cho đổi trạng thái khi đã kết thúc
        if (current == ApplicationStatusEnum.ACCEPTED ||
                current == ApplicationStatusEnum.CANCELLED) {
            throw new AppException(ErrorCode.INVALID_APPLICATION_STATUS);
        }

        // Recruiter có thể REJECTED ở mọi trạng thái còn lại
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

            case ASSIGNED -> {
                if (next != ApplicationStatusEnum.ACCEPTED &&
                        next != ApplicationStatusEnum.CANCELLED) {
                    throw new AppException(ErrorCode.INVALID_APPLICATION_STATUS);
                }
            }

            default -> throw new AppException(ErrorCode.INVALID_APPLICATION_STATUS);
        }
    }

}