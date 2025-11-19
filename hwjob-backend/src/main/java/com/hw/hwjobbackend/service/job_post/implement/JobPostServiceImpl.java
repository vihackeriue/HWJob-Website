package com.hw.hwjobbackend.service.job_post.implement;

import com.hw.hwjobbackend.dto.request.job_post.JobPostCreationRequest;
import com.hw.hwjobbackend.dto.response.job_post.JobPostDetailResponse;
import com.hw.hwjobbackend.dto.response.job_post.JobPostResponse;
import com.hw.hwjobbackend.entity.*;
import com.hw.hwjobbackend.entity.user.Recruiter;
import com.hw.hwjobbackend.enums.JobPostStatus;
import com.hw.hwjobbackend.exception.AppException;
import com.hw.hwjobbackend.exception.ErrorCode;
import com.hw.hwjobbackend.mapper.job_post.JobPostMapper;
import com.hw.hwjobbackend.repository.job_post.JobPostRepository;
import com.hw.hwjobbackend.service.industry.IndustryService;
import com.hw.hwjobbackend.service.job_post.JobPostService;
import com.hw.hwjobbackend.service.job_type.JobTypeService;
import com.hw.hwjobbackend.service.level.LevelService;
import com.hw.hwjobbackend.service.region.RegionService;
import com.hw.hwjobbackend.service.user.RecruiterService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class JobPostServiceImpl implements JobPostService {

    JobPostRepository jobPostRepository;
    JobPostMapper jobPostMapper;

    LevelService levelService;
    JobTypeService jobTypeService;
    IndustryService industryService;
    RecruiterService recruiterService;
    RegionService regionService;


    @Override
    public Page<JobPostResponse> getAllJobPosts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return jobPostRepository.findAll(pageable)
                .map(jobPostMapper::toJobPostResponse);

    }

    @Override
    public List<JobPostResponse> getAllJobPosts() {
        return jobPostRepository.findAll().stream()
                .map(jobPostMapper::toJobPostResponse)
                .toList();
    }

    @Override
    @PreAuthorize("hasRole('ADMIN') or hasRole('RECRUITER')")
    public JobPostResponse createJobPost(JobPostCreationRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Recruiter recruiter = recruiterService.getRecruiterEntityByName(username);

        JobPost jobPost = jobPostMapper.toJobPost(request);

        jobPost.setRecruiter(recruiter);

        jobPost.setLevel(
                request.getLevelId() != null ?
                        levelService.getLevelEntityById(request.getLevelId())
                        : null
        );

        jobPost.setJobType(
                request.getJobTypeId() != null
                        ? jobTypeService.getJobTypeEntityById(request.getJobTypeId())
                        : null
        );

        jobPost.setIndustry(
                request.getIndustryId() != null
                        ? industryService.getIndustryEntityById(request.getIndustryId())
                        : null
        );

        jobPost.setProvince(
                request.getRegionId() != null
                        ? regionService.getProvinceByCode(request.getRegionId())
                        : null
        );
        jobPost.setSalaryType(request.getSalaryType());

        jobPost.setStatus(JobPostStatus.PUBLIC);

        jobPost = jobPostRepository.save(jobPost);

        return jobPostMapper.toJobPostResponse(jobPost);
    }

    @Override
    public JobPost getJobPostEntityById(String id) {
        return jobPostRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.JOB_POST_NOT_EXISTED)
        );
    }

    @Override
    public JobPostDetailResponse getJobPostDetail(String id) {

        JobPost jobPost = jobPostRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.JOB_POST_NOT_EXISTED)
        );

        return null;
    }


}
