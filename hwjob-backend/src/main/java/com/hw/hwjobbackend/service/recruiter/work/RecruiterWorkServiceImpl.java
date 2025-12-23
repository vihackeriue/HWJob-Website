package com.hw.hwjobbackend.service.recruiter.work;

import com.hw.hwjobbackend.exception.AppException;
import com.hw.hwjobbackend.exception.ErrorCode;
import com.hw.hwjobbackend.model.dto.request.application.ApplicationStatusRequest;
import com.hw.hwjobbackend.model.dto.request.work.UpdateWorkStatusRequest;
import com.hw.hwjobbackend.model.dto.request.work.WorkCreateRequest;
import com.hw.hwjobbackend.model.dto.response.application.ApplicationCandidateResponse;
import com.hw.hwjobbackend.model.dto.response.work.WorkCandidateResponse;
import com.hw.hwjobbackend.model.dto.response.work.WorkOverviewResponse;
import com.hw.hwjobbackend.model.entity.application.Application;
import com.hw.hwjobbackend.model.entity.application.ApplicationId;
import com.hw.hwjobbackend.model.entity.job_post.JobPost;
import com.hw.hwjobbackend.model.entity.works.Work;
import com.hw.hwjobbackend.model.enums.ApplicationStatusEnum;
import com.hw.hwjobbackend.model.enums.WorkStatusEnum;
import com.hw.hwjobbackend.repository.application.ApplicationRepository;
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
public class RecruiterWorkServiceImpl implements RecruiterWorkService {

    ApplicationRepository applicationRepository;
    WorkRepository workRepository;
    WorkMapper workMapper;

    @Override
    public Page<WorkCandidateResponse> getCandidateWork(int page, int size, String jobPostId) {
        String recruiterId = SecurityUtils.getCurrentUserId();

        Pageable pageable = PaginationUtils.buildPageable(page, size);

        Page<Work> works = workRepository
                .findByJobPostIdAndRecruiterIdOrderByCreatedAtDesc(jobPostId, recruiterId, pageable);

        return works.map(workMapper::toWorkCandidateResponse);
    }
    @Override
    public List<WorkCandidateResponse> getAllCandidateWork(String jobPostId) {
        String recruiterId = SecurityUtils.getCurrentUserId();

        List<Work> works = workRepository
                .findByJobPostIdAndRecruiterIdOrderByCreatedAtDesc(jobPostId, recruiterId);

        return works.stream().map(workMapper::toWorkCandidateResponse).toList();
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

        Work work = workMapper.toWorkCreate(request);

        work.setCandidate(application.getCandidate());
        work.setRecruiter(jobPost.getRecruiter());
        work.setJobPost(jobPost);
        work.setApplication(application);
        workRepository.save(work);

    }
    @Override
    @Transactional
    public void updateCandidateApplicationStatus(
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
//        if (work.getStatus() == WorkStatusEnum.SUBMITTED &&
//                newStatus == WorkStatusEnum.REJECTED) {
//
//            // (Optional) clear submission nếu muốn
//            // work.setSubmission(null);
//        }

        // SUBMITTED -> PAID
        if (newStatus == WorkStatusEnum.PAID) {

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
