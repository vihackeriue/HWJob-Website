package com.hw.hwjobbackend.service.recruiter.job_post;


import com.hw.hwjobbackend.exception.AppException;
import com.hw.hwjobbackend.exception.ErrorCode;
import com.hw.hwjobbackend.service.mapper.job_post.JobPostMapper;
import com.hw.hwjobbackend.model.dto.request.job_post.JobPostRequest;
import com.hw.hwjobbackend.model.dto.response.job_post.JobPostDetailResponse;
import com.hw.hwjobbackend.model.dto.response.job_post.JobPostResponse;
import com.hw.hwjobbackend.model.entity.industry.Industry;
import com.hw.hwjobbackend.model.entity.job_post.JobPost;
import com.hw.hwjobbackend.model.entity.job_type.JobType;
import com.hw.hwjobbackend.model.entity.level.Level;
import com.hw.hwjobbackend.model.entity.region.Province;
import com.hw.hwjobbackend.model.entity.user.Recruiter;
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
    public JobPostResponse createJobPost(JobPostRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Recruiter recruiter = recruiterRepository.findByUsername(username).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED));

        JobPost jobPost = jobPostMapper.toJobPost(request);
        jobPost.setRecruiter(recruiter);

        jobPost.setLevel(request.getLevelId() != null
                ? levelRepository.findById(request.getLevelId()).orElseThrow(
                () -> new AppException(ErrorCode.LEVEL_NOT_EXISTED))
                : null);
        jobPost.setJobType(request.getJobTypeId() != null ?
                jobTypeRepository.findById(request.getJobTypeId()).orElseThrow(
                        () -> new AppException(ErrorCode.JOB_TYPE_NOT_EXISTED))
                : null);
        jobPost.setIndustry(request.getIndustryId() != null
                ? industryRepository.findById(request.getIndustryId()).orElseThrow(
                () -> new AppException(ErrorCode.INDUSTRY_NOT_EXISTED))
                : null);
        jobPost.setProvince(request.getRegionId() != null
                ? regionService.getProvinceByCode(request.getRegionId())
                : null);

        jobPost = jobPostRepository.save(jobPost);
        return jobPostMapper.toJobPostResponse(jobPost);
    }

    @Override
    public Page<JobPostResponse> getPostedJobPosts(int page, int size) {

        page = Math.max(page, 0);
        size = size <= 0 ? 10 : size;

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Recruiter recruiter = recruiterRepository.findByUsername(username).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED)
        );

        Pageable pageable = PageRequest.of(page, size);

        Page<JobPost> jobPosts = jobPostRepository.findAllByRecruiter(recruiter, pageable);

        return jobPosts.map(jobPostMapper::toJobPostResponse);
    }

    @Override
    public List<JobPostResponse> getAllPostedJobPosts() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Recruiter recruiter = recruiterRepository.findByUsername(username).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED)
        );
        return jobPostRepository.findAllByRecruiter(recruiter)
                .stream()
                .map(jobPostMapper::toJobPostResponse)
                .toList();
    }

    @Override
    public JobPostDetailResponse editJobPost(String id, JobPostRequest request) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Recruiter recruiter = recruiterRepository.findByUsername(username).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED)
        );

        JobPost jobPost = jobPostRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.JOB_POST_NOT_EXISTED)
        );

        if (!jobPost.getRecruiter().getId().equals(recruiter.getId())) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        jobPostMapper.updateJobPost(request, jobPost);

        if (request.getJobTypeId() != null) {
            JobType jobType = jobTypeRepository.findById(request.getJobTypeId())
                    .orElseThrow(() -> new AppException(ErrorCode.JOB_TYPE_NOT_EXISTED));
            jobPost.setJobType(jobType);
        }
        if (request.getLevelId() != null) {
            Level level = levelRepository.findById(request.getLevelId())
                    .orElseThrow(() -> new AppException(ErrorCode.LEVEL_NOT_EXISTED));
            jobPost.setLevel(level);
        }
        if (request.getIndustryId() != null) {
            Industry industry = industryRepository.findById(request.getIndustryId())
                    .orElseThrow(() -> new AppException(ErrorCode.INDUSTRY_NOT_EXISTED));
            jobPost.setIndustry(industry);
        }
        if (request.getRegionId() != null) {
            Province province = regionService.getProvinceByCode(request.getRegionId());
            jobPost.setProvince(province);
        }
        jobPost = jobPostRepository.save(jobPost);

        return jobPostMapper.toJobPostDetailResponse(jobPost);
    }
}
