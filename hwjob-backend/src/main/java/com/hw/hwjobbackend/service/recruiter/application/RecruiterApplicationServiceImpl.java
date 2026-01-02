package com.hw.hwjobbackend.service.recruiter.application;

import com.hw.hwjobbackend.exception.AppException;
import com.hw.hwjobbackend.exception.ErrorCode;
import com.hw.hwjobbackend.model.dto.api.request.RankCandidateRequest;
import com.hw.hwjobbackend.model.dto.api.response.RecommendationResponse;

import com.hw.hwjobbackend.model.dto.request.application.ApplicationStatusRequest;
import com.hw.hwjobbackend.model.dto.request.work.WorkCreateRequest;
import com.hw.hwjobbackend.model.dto.response.application.ApplicationAllCandidateResponse;
import com.hw.hwjobbackend.model.dto.response.application.ApplicationCandidateResponse;
import com.hw.hwjobbackend.model.entity.application.Application;
import com.hw.hwjobbackend.model.entity.application.ApplicationId;
import com.hw.hwjobbackend.model.enums.ApplicationStatusEnum;
import com.hw.hwjobbackend.repository.application.ApplicationRepository;
import com.hw.hwjobbackend.repository.http_client.ServerAIFeignClient;
import com.hw.hwjobbackend.repository.user.CandidateRepository;

import com.hw.hwjobbackend.service.mapper.application.ApplicationMapper;
import com.hw.hwjobbackend.service.recruiter.work.RecruiterWorkService;
import com.hw.hwjobbackend.service.shared.loyalty_point.LoyaltyPointService;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RecruiterApplicationServiceImpl implements RecruiterApplicationService {

    ApplicationRepository applicationRepository;
    ApplicationMapper applicationMapper;
    RecruiterWorkService workService;
    LoyaltyPointService loyaltyPointService;
    ServerAIFeignClient serverAIFeignClient;
    CandidateRepository candidateRepository;

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

        List<String> candidateAppliedId = applicationRepository
                .findAllPendingCandidateIdAndJobPostIdAndRecruiterByCreatedAtDesc(jobPostId, recruiterId);

        RankCandidateRequest request = RankCandidateRequest.builder()
                .jobPostId(jobPostId)
                .pendingCandidateIds(candidateAppliedId)
                .build();

        RecommendationResponse rankedCandidateAppliedId = serverAIFeignClient.rankCandidates(request);

        log.info(rankedCandidateAppliedId.toString());

        return rankedCandidateAppliedId.getResults().stream()
                .map(rankedItem -> {
                    Application application = applicationRepository.findByJobPostIdAndCandidateId(jobPostId, rankedItem.getId())
                            .orElseThrow(() -> new AppException(ErrorCode.APPLICATION_NOT_FOUND));
                    return applicationMapper.toCandidateApplicationResponse(application);
                })
                .collect(Collectors.toList());

    }

    @Override
    @Transactional(readOnly = true)
    public Page<ApplicationAllCandidateResponse> getAllCandidateApplicationsOfRecruiter(
            int page,
            int size
    ) {
        String recruiterId = SecurityUtils.getCurrentUserId();
        Pageable pageable = PaginationUtils.buildPageable(page, size);

        Page<Application> applications =
                applicationRepository.findAllByRecruiterId(recruiterId, pageable);

        return applications.map(applicationMapper::toApplicationAllCandidateResponse);
    }



    @Override
    @Transactional(readOnly = true)
    public List<ApplicationAllCandidateResponse> getAllCandidateApplicationsOfRecruiter() {
        String recruiterId = SecurityUtils.getCurrentUserId();

        return applicationRepository.findAllByRecruiterId(recruiterId)
                .stream()
                .map(applicationMapper::toApplicationAllCandidateResponse)
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

            loyaltyPointService.refundPointToRecruiter(jobPostId, candidateId);

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