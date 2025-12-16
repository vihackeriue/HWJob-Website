package com.hw.hwjobbackend.service.candidate.application;

import com.hw.hwjobbackend.exception.AppException;
import com.hw.hwjobbackend.exception.ErrorCode;
import com.hw.hwjobbackend.model.dto.request.application.ApplicationCandidateRequest;
import com.hw.hwjobbackend.model.dto.response.job_post.JobPostResponse;
import com.hw.hwjobbackend.model.enums.JobPostStatusEnum;
import com.hw.hwjobbackend.service.mapper.application.ApplicationMapper;
import com.hw.hwjobbackend.service.mapper.job_post.JobPostMapper;
import com.hw.hwjobbackend.model.dto.request.application.ApplicationRecruiterRequest;
import com.hw.hwjobbackend.model.dto.response.application.ApplicationResponse;
import com.hw.hwjobbackend.model.entity.application.Application;
import com.hw.hwjobbackend.model.entity.application.ApplicationId;
import com.hw.hwjobbackend.model.entity.job_post.JobPost;
import com.hw.hwjobbackend.model.entity.user.Candidate;
import com.hw.hwjobbackend.model.enums.ApplicationStatusEnum;
import com.hw.hwjobbackend.repository.application.ApplicationRepository;
import com.hw.hwjobbackend.repository.job_post.JobPostRepository;
import com.hw.hwjobbackend.repository.user.CandidateRepository;
import com.hw.hwjobbackend.util.PaginationUtils;
import com.hw.hwjobbackend.util.SecurityUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CandidateApplicationServiceImpl implements CandidateApplicationService {

    ApplicationRepository applicationRepository;
    ApplicationMapper applicationMapper;
    JobPostMapper jobPostMapper;
    CandidateRepository candidateRepository;
    JobPostRepository jobPostRepository;

    @Override
    public ApplicationResponse applyJob(ApplicationCandidateRequest request) {

        String candidateId = SecurityUtils.getCurrentUserId();

        ApplicationId applicationId = ApplicationId.builder()
                .candidateId(candidateId)
                .jobPostId(request.getJobPostId())
                .build();

        if (applicationRepository.existsById(applicationId)) {
            throw new AppException(ErrorCode.JOB_POST_ALREADY_APPLIED);
        }

        String jobPostId = request.getJobPostId();

        if (!jobPostRepository.existsValidJobPost(jobPostId)) {
            throw new AppException(ErrorCode.JOB_POST_NOT_EXISTED);
        }

        JobPost jobPost = jobPostRepository.getReferenceById(jobPostId);

        Candidate candidate = candidateRepository.getReferenceById(candidateId);

        Application application = Application.builder()
                .id(applicationId)
                .candidate(candidate)
                .jobPost(jobPost)
                .status(ApplicationStatusEnum.PENDING)
                .build();

        application = applicationRepository.save(application);

        return applicationMapper.toApplicationResponse(application);
    }

    @Override
    public Page<JobPostResponse> getAllJobPostsApplied(int page, int size) {
        String candidateId = SecurityUtils.getCurrentUserId();

        Pageable pageable = PaginationUtils.buildPageable(page, size);

        Page<Application> applications = applicationRepository
                .findByCandidateIdOrderByCreatedAtDesc(candidateId, pageable);

        return applications.map(this::buildJobPostResponse);
    }

    private JobPostResponse buildJobPostResponse(Application application) {
        JobPostResponse response =
                jobPostMapper.toJobPostResponse(application.getJobPost());


        response.setApplicationStatus(application.getStatus());

        return response;
    }

    @Override
    public List<JobPostResponse> getAllJobPostsApplied() {
        String candidateId = SecurityUtils.getCurrentUserId();

        List<Application> applications = applicationRepository
                .findAllByCandidateIdOrderByCreatedAtDesc(candidateId);

        return applications.stream()
                .map(application -> jobPostMapper.toJobPostResponse(application.getJobPost()))
                .toList();
    }
}