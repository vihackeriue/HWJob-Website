package com.hw.hwjobbackend.service.recruiter.work;

import com.hw.hwjobbackend.exception.AppException;
import com.hw.hwjobbackend.exception.ErrorCode;
import com.hw.hwjobbackend.model.dto.request.work.WorkCreateRequest;
import com.hw.hwjobbackend.model.dto.response.work.WorkOverviewResponse;
import com.hw.hwjobbackend.model.entity.application.Application;
import com.hw.hwjobbackend.model.entity.application.ApplicationId;
import com.hw.hwjobbackend.model.entity.job_post.JobPost;
import com.hw.hwjobbackend.model.entity.works.Work;
import com.hw.hwjobbackend.model.enums.ApplicationStatusEnum;
import com.hw.hwjobbackend.repository.application.ApplicationRepository;
import com.hw.hwjobbackend.repository.work.WorkRepository;
import com.hw.hwjobbackend.service.mapper.work.WorkMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RecruiterWorkServiceImpl implements RecruiterWorkService {

    ApplicationRepository applicationRepository;
    WorkRepository workRepository;
    WorkMapper workMapper;

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
    public void deleteByJobPostIdAndCandidateId(String jobPostId, String candidateId) {
        workRepository.deleteByJobPostIdAndCandidateId(jobPostId, candidateId);
    }
}
