package com.hw.hwjobbackend.service.recruiter.application;

import com.hw.hwjobbackend.event.ApplicationStatusChangedEvent;
import com.hw.hwjobbackend.exception.AppException;
import com.hw.hwjobbackend.exception.ErrorCode;
import com.hw.hwjobbackend.model.dto.api.request.RankCandidateRequest;
import com.hw.hwjobbackend.model.dto.api.response.RankedItemResponse;
import com.hw.hwjobbackend.model.dto.api.response.RecommendationResponse;
import com.hw.hwjobbackend.model.dto.request.application.ApplicationStatusRequest;
import com.hw.hwjobbackend.model.dto.request.work.WorkCreateRequest;
import com.hw.hwjobbackend.model.dto.response.application.ApplicationAllCandidateResponse;
import com.hw.hwjobbackend.model.dto.response.application.ApplicationCandidateResponse;
import com.hw.hwjobbackend.model.entity.application.Application;
import com.hw.hwjobbackend.model.entity.application.ApplicationId;
import com.hw.hwjobbackend.model.entity.application.RankedCandidateCache;
import com.hw.hwjobbackend.model.enums.ApplicationStatusEnum;
import com.hw.hwjobbackend.repository.application.ApplicationRepository;
import com.hw.hwjobbackend.repository.application.RankedCandidateCacheRepository;
import com.hw.hwjobbackend.repository.http_client.ServerAIFeignClient;
import com.hw.hwjobbackend.repository.user.CandidateRepository;
import com.hw.hwjobbackend.service.mapper.application.ApplicationMapper;
import com.hw.hwjobbackend.service.recruiter.work.RecruiterWorkService;
import com.hw.hwjobbackend.service.shared.loyalty_point.LoyaltyPointService;
import com.hw.hwjobbackend.service.shared.reputation.ReputationService;
import com.hw.hwjobbackend.service.shared.review.ReviewService;
import com.hw.hwjobbackend.util.PaginationUtils;
import com.hw.hwjobbackend.util.SecurityUtils;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
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
public class RecruiterApplicationServiceImpl implements RecruiterApplicationService {

    ApplicationRepository applicationRepository;
    ApplicationMapper applicationMapper;
    RecruiterWorkService workService;
    LoyaltyPointService loyaltyPointService;
    ServerAIFeignClient serverAIFeignClient;
    RankedCandidateCacheRepository rankedCandidateCacheRepository;
    ApplicationEventPublisher eventPublisher;
    ReputationService reputationService;
    ReviewService reviewService;

    @Override
    public Page<ApplicationCandidateResponse> getRankedCandidateApplication(Integer page, Integer size, String jobPostId) {

        // 1. Kiểm tra Cache
        RankedCandidateCache cache = rankedCandidateCacheRepository.findById(jobPostId).orElse(null);
        List<String> rankedCandidateIds;

        if (cache != null && cache.getCandidateIds() != null && !cache.getCandidateIds().isEmpty()) {
            rankedCandidateIds = cache.getCandidateIds();
        } else {
            // 2. Nếu Cache miss, lấy từ DB và gọi AI
            String recruiterId = SecurityUtils.getCurrentUserId();
            List<String> pendingCandidateIds = applicationRepository
                    .findAllPendingCandidateIdAndJobPostIdAndRecruiterByCreatedAtDesc(jobPostId, recruiterId);

            if (pendingCandidateIds.isEmpty()) {
                return new PageImpl<>(Collections.emptyList(), PaginationUtils.buildPageable(page, size), 0);
            }

            RankCandidateRequest request = RankCandidateRequest.builder()
                    .jobPostId(jobPostId)
                    .pendingCandidateIds(pendingCandidateIds)
                    .build();

            RecommendationResponse rankedResponse = serverAIFeignClient.rankCandidates(request);
            rankedCandidateIds = rankedResponse.getResults().stream()
                    .map(RankedItemResponse::getId)
                    .collect(Collectors.toList());

            // 3. Lưu vào Cache
            cache = RankedCandidateCache.builder()
                    .jobPostId(jobPostId)
                    .candidateIds(rankedCandidateIds)
                    .build();
            rankedCandidateCacheRepository.save(cache);
        }

        // 4. Phân trang trên danh sách ID
        int start = page * size;
        int end = Math.min(start + size, rankedCandidateIds.size());

        if (start >= rankedCandidateIds.size()) {
            return new PageImpl<>(Collections.emptyList(), PaginationUtils.buildPageable(page, size), rankedCandidateIds.size());
        }

        List<String> pageCandidateIds = rankedCandidateIds.subList(start, end);

        // 5. Query DB lấy chi tiết Application
        List<Application> applications = applicationRepository.findByJobPostIdAndCandidateIdIn(jobPostId, pageCandidateIds);

        // Lấy danh sách candidateId
        List<String> candidateIds = applications.stream()
                .map(app -> app.getCandidate().getId())
                .distinct()
                .toList();

        // 5.1 Lấy reputation
        Map<String, BigInteger> reputationMap =
                reputationService.getReputationByUserIds(candidateIds);
//        get rating
        Map<String, Double> reviewMap =
                reviewService.getAverageRatingByUserIds(candidateIds);

        // 6. Sắp xếp lại kết quả DB theo thứ tự của pageCandidateIds
        Map<String, Application> applicationMap = applications.stream()
                .collect(Collectors.toMap(app -> app.getCandidate().getId(), Function.identity()));

        List<ApplicationCandidateResponse> responses = new ArrayList<>();
        for (String candidateId : pageCandidateIds) {
            Application app = applicationMap.get(candidateId);
            if (app != null) {
                ApplicationCandidateResponse response =
                        applicationMapper.toCandidateApplicationResponse(app);

                response.setReputation(
                        reputationMap.getOrDefault(candidateId, BigInteger.ZERO)
                );
                response.setAverageRating(
                        reviewMap.getOrDefault(candidateId, 0.0)
                );

                responses.add(response);
            }
        }

        return new PageImpl<>(responses, PaginationUtils.buildPageable(page, size), rankedCandidateIds.size());
    }

    @Override
    public Page<ApplicationCandidateResponse> getCandidateApplications(
            int page, int size, String jobPostId) {

        String recruiterId = SecurityUtils.getCurrentUserId();
        Pageable pageable = PaginationUtils.buildPageable(page, size);

        Page<Application> applicationsPage =
                applicationRepository.findByJobPostIdAndRecruiterIdOrderByCreatedAtDesc(
                        jobPostId, recruiterId, pageable
                );

        List<Application> applications = applicationsPage.getContent();

        if (applications.isEmpty()) {
            return new PageImpl<>(Collections.emptyList(), pageable, applicationsPage.getTotalElements());
        }

        // 1. Lấy candidateIds
        List<String> candidateIds = applications.stream()
                .map(app -> app.getCandidate().getId())
                .distinct()
                .toList();

        // 2. Lấy reputation + rating theo batch
        Map<String, BigInteger> reputationMap =
                reputationService.getReputationByUserIds(candidateIds);

        Map<String, Double> ratingMap =
                reviewService.getAverageRatingByUserIds(candidateIds);

        // 3. Map response
        List<ApplicationCandidateResponse> responses = applications.stream()
                .map(app -> {
                    String candidateId = app.getCandidate().getId();

                    ApplicationCandidateResponse response =
                            applicationMapper.toCandidateApplicationResponse(app);

                    response.setReputation(
                            reputationMap.getOrDefault(candidateId, BigInteger.ZERO)
                    );

                    response.setAverageRating(
                            ratingMap.getOrDefault(candidateId, 0.0)
                    );

                    return response;
                })
                .toList();

        return new PageImpl<>(responses, pageable, applicationsPage.getTotalElements());
    }

    @Override
    public List<ApplicationCandidateResponse> getCandidateApplications(String jobPostId) {

        String recruiterId = SecurityUtils.getCurrentUserId();

        // 1. Lấy toàn bộ application
        List<Application> applications =
                applicationRepository.findAllByJobPostIdAndRecruiterIdOrderByCreatedAtDesc(
                        jobPostId, recruiterId
                );

        if (applications.isEmpty()) {
            return Collections.emptyList();
        }

        // 2. Lấy candidateIds
        List<String> candidateIds = applications.stream()
                .map(app -> app.getCandidate().getId())
                .distinct()
                .toList();

        // 3. Lấy reputation + averageRating theo batch
        Map<String, BigInteger> reputationMap =
                reputationService.getReputationByUserIds(candidateIds);

        Map<String, Double> ratingMap =
                reviewService.getAverageRatingByUserIds(candidateIds);

        // 4. Sort applications theo reputation DESC
        List<Application> sortedApplications = applications.stream()
                .sorted((a, b) -> {
                    BigInteger r1 = reputationMap.getOrDefault(
                            a.getCandidate().getId(), BigInteger.ZERO
                    );
                    BigInteger r2 = reputationMap.getOrDefault(
                            b.getCandidate().getId(), BigInteger.ZERO
                    );
                    return r2.compareTo(r1);
                })
                .toList();

        // 5. Map response
        return sortedApplications.stream()
                .map(app -> {
                    String candidateId = app.getCandidate().getId();

                    ApplicationCandidateResponse response =
                            applicationMapper.toCandidateApplicationResponse(app);

                    response.setReputation(
                            reputationMap.getOrDefault(candidateId, BigInteger.ZERO)
                    );

                    response.setAverageRating(
                            ratingMap.getOrDefault(candidateId, 0.0)
                    );

                    return response;
                })
                .toList();
    }



    @Override
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

        // --- Gửi Event cho listener mail ---
        // Chỉ gửi mail khi trạng thái là APPROVED hoặc REJECTED
        if (newStatus == ApplicationStatusEnum.APPROVED || newStatus == ApplicationStatusEnum.REJECTED) {
            ApplicationStatusChangedEvent event = new ApplicationStatusChangedEvent(
                    application.getCandidate().getEmail(),
                    application.getJobPost().getTitle(),  // job title
                    newStatus                  // dùng tên enum làm status
            );
            eventPublisher.publishEvent(event);
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