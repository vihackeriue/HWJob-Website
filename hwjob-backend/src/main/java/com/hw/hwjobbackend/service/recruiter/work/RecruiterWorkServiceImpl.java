package com.hw.hwjobbackend.service.recruiter.work;

import com.hw.hwjobbackend.exception.AppException;
import com.hw.hwjobbackend.exception.ErrorCode;
import com.hw.hwjobbackend.model.dto.request.work.UpdateWorkStatusRequest;
import com.hw.hwjobbackend.model.dto.request.work.WorkCreateRequest;
import com.hw.hwjobbackend.model.dto.response.work.AllWorkCandidateOfRecruiterResponse;
import com.hw.hwjobbackend.model.dto.response.work.WorkCandidateResponse;
import com.hw.hwjobbackend.model.entity.application.Application;
import com.hw.hwjobbackend.model.entity.application.ApplicationId;
import com.hw.hwjobbackend.model.entity.job_post.JobPost;
import com.hw.hwjobbackend.model.entity.works.Work;
import com.hw.hwjobbackend.model.enums.ApplicationStatusEnum;
import com.hw.hwjobbackend.model.enums.WorkStatusEnum;
import com.hw.hwjobbackend.repository.application.ApplicationRepository;
import com.hw.hwjobbackend.repository.review.ReviewRepository;
import com.hw.hwjobbackend.repository.user.UserRepository;
import com.hw.hwjobbackend.repository.work.WorkRepository;
import com.hw.hwjobbackend.service.blockchain.BlockchainService;
import com.hw.hwjobbackend.service.mapper.work.WorkMapper;
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

import java.math.BigInteger;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RecruiterWorkServiceImpl implements RecruiterWorkService {

    ApplicationRepository applicationRepository;
    WorkRepository workRepository;
    WorkMapper workMapper;

    BlockchainService blockchainService;
    ReviewRepository reviewRepository;
    LoyaltyPointService loyaltyPointService;


    @Override
    public Page<WorkCandidateResponse> getCandidateWork(int page, int size, String jobPostId) {
        String recruiterId = SecurityUtils.getCurrentUserId();

        Pageable pageable = PaginationUtils.buildPageable(page, size);

        Page<Work> works = workRepository
                .findByJobPostIdAndRecruiterIdOrderByCreatedAtDesc(jobPostId, recruiterId, pageable);



        return works.map(work -> {
            WorkCandidateResponse res = workMapper.toWorkCandidateResponse(work);

            Double myRating = reviewRepository.findMyRating(
                    work.getId(),
                    recruiterId
            );

            res.setMyReviewRating(myRating); // null nếu chưa review
            return res;
        });
    }
    @Override
    public List<WorkCandidateResponse> getCandidateWork(String jobPostId) {
        String recruiterId = SecurityUtils.getCurrentUserId();

        List<Work> works = workRepository
                .findByJobPostIdAndRecruiterIdOrderByCreatedAtDesc(jobPostId, recruiterId);

        return works.stream().map(work -> {
            WorkCandidateResponse res = workMapper.toWorkCandidateResponse(work);

            Double myRating = reviewRepository.findMyRating(
                    work.getId(),
                    recruiterId
            );

            res.setMyReviewRating(myRating);
            return res;
        }).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AllWorkCandidateOfRecruiterResponse> getAllCandidateWorkOfRecruiter(
            int page,
            int size
    ) {
        String recruiterId = SecurityUtils.getCurrentUserId();
        Pageable pageable = PaginationUtils.buildPageable(page, size);

        Page<Work> works =
                workRepository.findAllByRecruiterId(recruiterId, pageable);

        return works.map(work -> {
            AllWorkCandidateOfRecruiterResponse res =
                    workMapper.toAllWorkCandidateOfRecruiterResponse(work);

            Double myRating = reviewRepository.findMyRating(
                    work.getId(),
                    recruiterId
            );

            res.setMyReviewRating(myRating); // null nếu chưa review
            return res;
        });
    }
    @Override
    @Transactional(readOnly = true)
    public List<AllWorkCandidateOfRecruiterResponse> getAllCandidateWorkOfRecruiter() {
        String recruiterId = SecurityUtils.getCurrentUserId();

        return workRepository.findAllByRecruiterId(recruiterId)
                .stream()
                .map(work -> {
                    AllWorkCandidateOfRecruiterResponse res =
                            workMapper.toAllWorkCandidateOfRecruiterResponse(work);

                    Double myRating = reviewRepository.findMyRating(
                            work.getId(),
                            recruiterId
                    );

                    res.setMyReviewRating(myRating);
                    return res;
                })
                .toList();
    }
    @Override
    public void assignWork(WorkCreateRequest request) {
        ApplicationId applicationId = ApplicationId.builder()
                .candidateId(request.getCandidateId())
                .jobPostId(request.getJobPostId())
                .build();

        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new AppException(ErrorCode.APPLICATION_NOT_FOUND));

        if (application.getStatus() != ApplicationStatusEnum.ASSIGNED) {
            throw new AppException(ErrorCode.INVALID_APPLICATION_STATUS);
        }

        if (workRepository.existsByCandidateIdAndJobPostId(
                request.getCandidateId(), request.getJobPostId())) {
            throw new AppException(ErrorCode.WORK_EXISTED);
        }

        JobPost jobPost = application.getJobPost();

        BigInteger lockAmount = BigInteger.valueOf(request.getAgreedSalary());
        String recruiterId = SecurityUtils.getCurrentUserId();

        String txHash;
        try {
            txHash = blockchainService.lockForJob(recruiterId, lockAmount);
        } catch (Exception e) {
            // LOCK FAIL → KHÔNG ĐƯỢC KÝ
            throw new AppException(ErrorCode.NOT_ENOUGH_REWARD_POINT);
        }

        Work work = workMapper.toWorkCreate(request);

        work.setCandidate(application.getCandidate());
        work.setRecruiter(jobPost.getRecruiter());
        work.setJobPost(jobPost);
        work.setApplication(application);
        work.setLockTxHash(txHash);

        workRepository.save(work);

    }
    @Override
    @Transactional
    public void updateCandidateWorkStatus(
            String jobPostId,
            String candidateId,
            UpdateWorkStatusRequest request
    ) {
        WorkStatusEnum newStatus = request.getStatus();

        String recruiterId = SecurityUtils.getCurrentUserId();

        Work work = workRepository
                .findByJobPostIdAndCandidateIdAndRecruiterId(
                        jobPostId, candidateId, recruiterId
                )
                .orElseThrow(() -> new AppException(ErrorCode.UNAUTHORIZED));

        // VALIDATE CHUYỂN TRẠNG THÁI
        validateRecruiterWorkTransition(work.getStatus(), newStatus);

        // SUBMITTED -> REJECTED
        if (work.getStatus() == WorkStatusEnum.SUBMITTED &&
                newStatus == WorkStatusEnum.REJECTED) {
            loyaltyPointService.refundPointToRecruiterAndDeductReputation(
                    recruiterId, candidateId, work.getAgreedSalary());
        }

        // SUBMITTED -> PAID
        if (newStatus == WorkStatusEnum.PAID) {
            boolean goodPerformance = true;
            try {
                blockchainService.completeJobForUser(recruiterId, candidateId, work.getAgreedSalary(), goodPerformance);
            } catch (Exception e) {
                throw new AppException(ErrorCode.FAIL_PROCESS_BLOCKCHAIN);
            }

            // - update wallet / transaction
        }
        // Update status
        work.setStatus(newStatus);

    }


    private void validateRecruiterWorkTransition(
            WorkStatusEnum current,
            WorkStatusEnum next
    ) {
        // Không cho đổi khi đã kết thúc
        if (current == WorkStatusEnum.PAID ||
                current == WorkStatusEnum.CANCELLED) {
            throw new AppException(ErrorCode.INVALID_WORK_STATUS);
        }

        switch (current) {

            case SUBMITTED -> {
                if (next != WorkStatusEnum.PAID &&
                        next != WorkStatusEnum.REJECTED) {
                    throw new AppException(ErrorCode.INVALID_WORK_STATUS);
                }
            }

            default -> throw new AppException(ErrorCode.INVALID_WORK_STATUS);
        }
    }


    @Override
    public void deleteByJobPostIdAndCandidateId(String jobPostId, String candidateId) {
        workRepository.deleteByJobPostIdAndCandidateId(jobPostId, candidateId);
    }
}
