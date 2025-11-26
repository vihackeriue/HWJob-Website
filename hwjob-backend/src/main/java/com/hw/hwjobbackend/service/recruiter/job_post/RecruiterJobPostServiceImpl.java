package com.hw.hwjobbackend.service.recruiter.job_post;


import com.hw.hwjobbackend.exception.AppException;
import com.hw.hwjobbackend.exception.ErrorCode;
import com.hw.hwjobbackend.mapper.job_post.JobPostMapper;
import com.hw.hwjobbackend.model.dto.request.job_post.JobPostCreationRequest;
import com.hw.hwjobbackend.model.dto.response.job_post.JobPostResponse;
import com.hw.hwjobbackend.model.entity.job_post.JobPost;
import com.hw.hwjobbackend.model.entity.user.Recruiter;
import com.hw.hwjobbackend.model.enums.JobPostStatus;
import com.hw.hwjobbackend.repository.industry.IndustryRepository;
import com.hw.hwjobbackend.repository.job_post.JobPostRepository;
import com.hw.hwjobbackend.repository.job_type.JobTypeRepository;
import com.hw.hwjobbackend.repository.level.LevelRepository;
import com.hw.hwjobbackend.repository.user.RecruiterRepository;
import com.hw.hwjobbackend.service.shared.region.RegionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@PreAuthorize("hasRole('RECRUITER')")
public class RecruiterJobPostServiceImpl implements RecruiterJobPostService {


    JobPostRepository jobPostRepository;
    JobPostMapper jobPostMapper;
    RegionService regionService;

    RecruiterRepository recruiterRepository;
    LevelRepository levelRepository;
    JobTypeRepository jobTypeRepository;
    IndustryRepository industryRepository;

    @Override
    public JobPostResponse createJobPost(JobPostCreationRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Recruiter recruiter = recruiterRepository.findByUsername(username).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED)
        );

        JobPost jobPost = jobPostMapper.toJobPost(request);

        jobPost.setRecruiter(recruiter);

        jobPost.setLevel(
                request.getLevelId() != null
                        ? levelRepository.findById(request.getLevelId()).orElseThrow(
                        () -> new AppException(ErrorCode.LEVEL_NOT_EXISTED))
                        : null
        );

        jobPost.setJobType(
                request.getJobTypeId() != null ?
                        jobTypeRepository.findById(request.getJobTypeId()).orElseThrow(
                                () -> new AppException(ErrorCode.JOB_TYPE_NOT_EXISTED))
                        : null
        );

        jobPost.setIndustry(
                request.getIndustryId() != null
                        ? industryRepository.findById(request.getIndustryId()).orElseThrow(
                        () -> new AppException(ErrorCode.INDUSTRY_NOT_EXISTED))
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

}
