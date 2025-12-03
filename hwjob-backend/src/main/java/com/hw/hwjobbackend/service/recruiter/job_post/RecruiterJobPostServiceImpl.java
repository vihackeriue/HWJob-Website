package com.hw.hwjobbackend.service.recruiter.job_post;

import com.hw.hwjobbackend.exception.AppException;
import com.hw.hwjobbackend.exception.ErrorCode;
import com.hw.hwjobbackend.model.dto.request.job_post.JobPostRequest;
import com.hw.hwjobbackend.model.dto.response.job_post.JobPostDetailResponse;
import com.hw.hwjobbackend.model.dto.response.job_post.JobPostResponse;
import com.hw.hwjobbackend.model.entity.job_post.JobPost;
import com.hw.hwjobbackend.model.entity.skill.Skill;
import com.hw.hwjobbackend.repository.industry.IndustryRepository;
import com.hw.hwjobbackend.repository.job_post.JobPostRepository;
import com.hw.hwjobbackend.repository.job_type.JobTypeRepository;
import com.hw.hwjobbackend.repository.level.LevelRepository;
import com.hw.hwjobbackend.repository.region.RegionRepository;
import com.hw.hwjobbackend.repository.user.RecruiterRepository;
import com.hw.hwjobbackend.service.mapper.job_post.JobPostMapper;
import com.hw.hwjobbackend.service.shared.skill.SkillService;
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

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@PreAuthorize("hasRole('RECRUITER')")
public class RecruiterJobPostServiceImpl implements RecruiterJobPostService {

    JobPostRepository jobPostRepository;
    JobPostMapper jobPostMapper;
    RegionRepository regionRepository;
    RecruiterRepository recruiterRepository;
    LevelRepository levelRepository;
    JobTypeRepository jobTypeRepository;
    IndustryRepository industryRepository;

    SkillService skillService;


    @Override
    @Transactional
    public JobPostResponse createJobPost(JobPostRequest request) {
        String recruiterId = SecurityUtils.getCurrentUserId();

        JobPost jobPost = jobPostMapper.toJobPost(request);
        jobPost.setRecruiter(recruiterRepository.getReferenceById(recruiterId));

        setJobPostRelations(jobPost, request);
        jobPost = jobPostRepository.save(jobPost);

        return jobPostMapper.toJobPostResponse(jobPost);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<JobPostResponse> getPostedJobPosts(int page, int size) {
        String recruiterId = SecurityUtils.getCurrentUserId();

        Pageable pageable = PaginationUtils.buildPageable(page, size);

        Page<JobPost> jobPosts = jobPostRepository
                .findAllByRecruiterIdOrderByCreatedAtDesc(recruiterId, pageable);

        return jobPosts.map(jobPostMapper::toJobPostResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<JobPostResponse> getAllPostedJobPosts() {
        String recruiterId = SecurityUtils.getCurrentUserId();

        return jobPostRepository
                .findAllByRecruiterIdOrderByCreatedAtDesc(recruiterId)
                .stream()
                .map(jobPostMapper::toJobPostResponse)
                .toList();
    }

    @Override
    @Transactional
    public JobPostDetailResponse editJobPost(String id, JobPostRequest request) {
        String recruiterId = SecurityUtils.getCurrentUserId();

        JobPost jobPost = jobPostRepository
                .findByIdAndRecruiterId(id, recruiterId)
                .orElseThrow(() -> new AppException(ErrorCode.UNAUTHORIZED));

        jobPostMapper.updateJobPost(request, jobPost);
        setJobPostRelations(jobPost, request);

        return jobPostMapper.toJobPostDetailResponse(jobPost);
    }

    private void setJobPostRelations(JobPost jobPost, JobPostRequest request) {
        if (request.getLevelId() != null) {
            if (!levelRepository.existsById(request.getLevelId())) {
                throw new AppException(ErrorCode.LEVEL_NOT_EXISTED);
            }
            jobPost.setLevel(levelRepository.getReferenceById(request.getLevelId()));
        } else {
            jobPost.setLevel(null);
        }

        if (request.getJobTypeId() != null) {
            if (!jobTypeRepository.existsById(request.getJobTypeId())) {
                throw new AppException(ErrorCode.JOB_TYPE_NOT_EXISTED);
            }
            jobPost.setJobType(jobTypeRepository.getReferenceById(request.getJobTypeId()));

        } else {
            jobPost.setJobType(null);
        }

        if (request.getIndustryId() != null) {
            if (!industryRepository.existsById(request.getIndustryId())) {
                throw new AppException(ErrorCode.INDUSTRY_NOT_EXISTED);
            }
            jobPost.setIndustry(industryRepository.getReferenceById(request.getIndustryId()));

        } else {
            jobPost.setIndustry(null);
        }

        if (request.getRegionId() != null) {
            if (!regionRepository.existsById(request.getRegionId())) {
                throw new AppException(ErrorCode.REGION_NOT_EXISTED);
            }
            jobPost.setRegion(regionRepository.getReferenceById(request.getRegionId()));

        } else {
            jobPost.setRegion(null);
        }
        if (request.getSkillIds() != null) {
            Set<Skill> skills = skillService.getSkillsByIds(request.getSkillIds());
            jobPost.setSkills(skills);
        } else {
            jobPost.setSkills(null);
        }
    }
}