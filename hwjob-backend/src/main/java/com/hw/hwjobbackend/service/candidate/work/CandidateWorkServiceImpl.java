package com.hw.hwjobbackend.service.candidate.work;

import com.hw.hwjobbackend.exception.AppException;
import com.hw.hwjobbackend.exception.ErrorCode;
import com.hw.hwjobbackend.model.dto.request.work.UpdateWorkStatusRequest;
import com.hw.hwjobbackend.model.dto.response.work.WorkOverviewResponse;
import com.hw.hwjobbackend.model.dto.response.work.WorkResponse;
import com.hw.hwjobbackend.model.entity.works.Work;
import com.hw.hwjobbackend.model.enums.ApplicationStatusEnum;
import com.hw.hwjobbackend.model.enums.WorkStatusEnum;

import com.hw.hwjobbackend.repository.work.WorkRepository;

import com.hw.hwjobbackend.service.mapper.work.WorkMapper;
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
public class CandidateWorkServiceImpl implements CandidateWorkService {


    WorkRepository workRepository;
    WorkMapper workMapper;


    public void decideWork( String jobPostId, String candidateId, boolean accepted) {

        Work work = workRepository
                .findByCandidateIdAndJobPostId(candidateId, jobPostId)
                .orElseThrow(() -> new AppException(ErrorCode.WORK_NOT_FOUND));
//        if (!work.getCandidate().getId().equals(candidateId)) {
//            throw new AppException(ErrorCode.UNAUTHENTICATED);
//        }
        if (work.getStatus() != WorkStatusEnum.PENDING) {
            throw new AppException(ErrorCode.INVALID_WORK_STATUS);
        }
        if (accepted) {
            work.setStatus(WorkStatusEnum.IN_PROGRESS);
        } else {
            workRepository.delete(work);
        }
    }
    @Override
    @Transactional(readOnly = true)
    public WorkOverviewResponse getWorkOverviewForCandidate(String jobPostId) {

        String candidateId = SecurityUtils.getCurrentUserId();

        Work work = workRepository
                .findByCandidateIdAndJobPostId(candidateId, jobPostId)
                .orElseThrow(() -> new AppException(ErrorCode.WORK_NOT_FOUND));

//        if (work.getStatus() != WorkStatusEnum.PENDING) {
//            throw new AppException(ErrorCode.INVALID_WORK_STATUS);
//        }

        return workMapper.toWorkOverviewResponse(work);
    }

    @Override
    public Page<WorkResponse> getAllWorksOfCandidate(int page, int size) {

        String candidateId = SecurityUtils.getCurrentUserId();
        Pageable pageable = PaginationUtils.buildPageable(page, size);

        return workRepository
                .findByCandidateIdOrderByCreatedAtDesc(candidateId, pageable)
                .map(workMapper::toWorkResponse);
    }
    @Override
    public List<WorkResponse> getAllWorksOfCandidate() {

        String candidateId = SecurityUtils.getCurrentUserId();

        List<Work> works = workRepository
                .findAllByCandidateIdOrderByCreatedAtDesc(candidateId);

        return works.stream()
                .map(workMapper::toWorkResponse)
                .toList();
    }
    @Override
    @Transactional
    public void updateApplicationStatus(String jobPostId, UpdateWorkStatusRequest request) {
        String candidateId = SecurityUtils.getCurrentUserId();

        Work work = workRepository
                .findByCandidateIdAndJobPostId(candidateId, jobPostId)
                .orElseThrow(() -> new AppException(ErrorCode.WORK_NOT_FOUND));

        validateCandidateWorkTransition(work.getStatus(), request.getStatus());

        if (request.getStatus() == WorkStatusEnum.SUBMITTED) {
            if (request.getSubmission() == null || request.getSubmission().isBlank()) {
                throw new AppException(ErrorCode.SUBMISSION_REQUIRED);
            }
            work.setSubmission(request.getSubmission());
        }
        work.setStatus(request.getStatus());
    }
    private void validateCandidateWorkTransition(
            WorkStatusEnum current,
            WorkStatusEnum next
    ) {

        // Không cho đổi khi đã kết thúc hoàn toàn
        if (current == WorkStatusEnum.PAID ||
                current == WorkStatusEnum.CANCELLED) {
            throw new AppException(ErrorCode.INVALID_WORK_STATUS);
        }

        switch (current) {

            case IN_PROGRESS -> {
                if (next != WorkStatusEnum.SUBMITTED &&
                        next != WorkStatusEnum.CANCELLED) {
                    throw new AppException(ErrorCode.INVALID_WORK_STATUS);
                }
            }

            case SUBMITTED, REJECTED -> {
                if (next != WorkStatusEnum.DISPUTED) {
                    throw new AppException(ErrorCode.INVALID_WORK_STATUS);
                }
            }

            default -> throw new AppException(ErrorCode.INVALID_WORK_STATUS);
        }
    }

}
