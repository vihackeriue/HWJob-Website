package com.hw.hwjobbackend.service.recruiter.job_post;

import com.hw.hwjobbackend.exception.AppException;
import com.hw.hwjobbackend.exception.ErrorCode;
import com.hw.hwjobbackend.model.dto.request.job_post.BoostJobPostRequest;
import com.hw.hwjobbackend.model.dto.request.job_post.JobPostRequest;
import com.hw.hwjobbackend.model.dto.response.application.projection.ApplyGoldenHourResponse;
import com.hw.hwjobbackend.model.dto.response.job_post.*;
import com.hw.hwjobbackend.model.dto.response.job_post.projection.RecruiterPostingFrequencyResponse;
import com.hw.hwjobbackend.model.dto.response.job_post.projection.RecruiterWorkSalaryStatsResponse;
import com.hw.hwjobbackend.model.dto.response.loyalty_point.LoyaltyPointResponse;
import com.hw.hwjobbackend.model.entity.job_post.JobPost;
import com.hw.hwjobbackend.model.entity.job_post.JobPostBoostHistory;
import com.hw.hwjobbackend.model.entity.skill.Skill;
import com.hw.hwjobbackend.model.enums.BoostPackageEnum;
import com.hw.hwjobbackend.model.enums.JobPostStatusEnum;
import com.hw.hwjobbackend.model.enums.WorkStatusEnum;
import com.hw.hwjobbackend.repository.application.ApplicationRepository;
import com.hw.hwjobbackend.repository.candidate_save_job.CandidateSaveJobRepository;
import com.hw.hwjobbackend.repository.industry.IndustryRepository;
import com.hw.hwjobbackend.repository.job_post.JobPostBoostHistoryRepository;
import com.hw.hwjobbackend.repository.job_post.JobPostRepository;
import com.hw.hwjobbackend.repository.job_type.JobTypeRepository;
import com.hw.hwjobbackend.repository.level.LevelRepository;
import com.hw.hwjobbackend.repository.region.RegionRepository;
import com.hw.hwjobbackend.repository.user.RecruiterRepository;
import com.hw.hwjobbackend.repository.work.WorkRepository;

import com.hw.hwjobbackend.service.blockchain.BlockchainService;
import com.hw.hwjobbackend.service.mapper.job_post.JobPostMapper;
import com.hw.hwjobbackend.service.shared.indexing.IndexingService;
import com.hw.hwjobbackend.service.shared.job_post.JobPostViewService;
import com.hw.hwjobbackend.service.shared.skill.SkillService;
import com.hw.hwjobbackend.util.PaginationUtils;
import com.hw.hwjobbackend.util.SecurityUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RecruiterJobPostServiceImpl implements RecruiterJobPostService {

    JobPostRepository jobPostRepository;
    JobPostMapper jobPostMapper;
    RegionRepository regionRepository;
    RecruiterRepository recruiterRepository;
    LevelRepository levelRepository;
    JobTypeRepository jobTypeRepository;
    IndustryRepository industryRepository;

    SkillService skillService;

    JobPostViewService jobPostViewService;
    ApplicationRepository applicationRepository;
    CandidateSaveJobRepository candidateSaveJobRepository;
    WorkRepository workRepository;
    IndexingService indexingService;
    BlockchainService blockchainService;
    JobPostBoostHistoryRepository jobPostBoostHistoryRepository;


    @Override
    @Transactional
    public JobPostResponse createJobPost(JobPostRequest request) {
        String recruiterId = SecurityUtils.getCurrentUserId();

        JobPost jobPost = jobPostMapper.toJobPost(request);
        jobPost.setRecruiter(recruiterRepository.getReferenceById(recruiterId));

        setJobPostRelations(jobPost, request);
        jobPost = jobPostRepository.save(jobPost);

        indexingService.createJobPostIndexing(jobPost);

        return jobPostMapper.toJobPostResponse(jobPost);
    }
    private static final long COST_PER_DAY = 10_000L;
    private static final int PRIORITY_PER_DAY = 10;
    @Override
    @Transactional
    public void boostJobPost(String jobPostId, BoostJobPostRequest request) {

        String recruiterId = SecurityUtils.getCurrentUserId();

        JobPost jobPost = jobPostRepository
                .findByIdAndRecruiterId(jobPostId, recruiterId)
                .orElseThrow(() -> new AppException(ErrorCode.UNAUTHORIZED));

        String wallet = jobPost.getRecruiter().getWalletAddress();

        LoyaltyPointResponse loyaltyPoint =
                blockchainService.getPointOfUser(wallet);

        BigInteger currentPoint = loyaltyPoint.getPoints();

        /* ================= 1. DETERMINE DAYS & COST ================= */
        int days;
        long finalCost;

        BoostPackageEnum boostPackage = null;

        if (request.getPackageKey() != null) {
            boostPackage = BoostPackageEnum.fromKey(request.getPackageKey())
                    .orElseThrow(() -> new AppException(ErrorCode.INVALID_PACKAGE));

            days = boostPackage.getDays();

            long original = days * COST_PER_DAY;
            long discount = original * boostPackage.getDiscountPercent() / 100;
            finalCost = original - discount;

        } else {
            if (request.getDays() <= 0) {
                throw new AppException(ErrorCode.INVALID_AMOUNT);
            }

            days = request.getDays();
            finalCost = days * COST_PER_DAY;
        }

        BigInteger cost = BigInteger.valueOf(finalCost);

        /* ================= 2. CHECK POINT ================= */
        if (currentPoint.compareTo(cost) < 0) {
            throw new AppException(ErrorCode.NOT_ENOUGH_POINT);
        }

        /* ================= 3. BURN POINT ================= */
        blockchainService.burnPoint(wallet, cost);

        /* ================= 4. BOOST LOGIC ================= */
        LocalDateTime now = LocalDateTime.now();

        LocalDateTime baseTime =
                jobPost.getBoostExpiredAt() == null ||
                        jobPost.getBoostExpiredAt().isBefore(now)
                        ? now
                        : jobPost.getBoostExpiredAt();

        LocalDateTime newExpiredAt = baseTime.plusDays(days);

        long remainingDays = Duration.between(now, newExpiredAt).toDays();
        if (Duration.between(now, newExpiredAt).toHours() % 24 != 0) {
            remainingDays++;
        }

        int priority = (int) remainingDays * PRIORITY_PER_DAY;

        jobPost.setBoostExpiredAt(newExpiredAt);
        jobPost.setBoostPriority(priority);
        jobPost.setIsBoosted(true);

        /* ================= 5. SAVE HISTORY ================= */
        jobPostBoostHistoryRepository.save(
                JobPostBoostHistory.builder()
                        .jobPost(jobPost)
                        .recruiter(jobPost.getRecruiter())
                        .boostDays(days)
                        .cost(finalCost)
                        .build()
        );
    }
    @Scheduled(cron = "0 */5 * * * *")
    @Transactional
    public void disableExpiredBoost() {
        List<JobPost> expired =
                jobPostRepository.findByIsBoostedTrueAndBoostExpiredAtBefore(LocalDateTime.now());

        expired.forEach(jp -> {
            jp.setIsBoosted(false);
            jp.setBoostPriority(0);
        });
    }
    @Override
    @Transactional(readOnly = true)
    public Page<JobPostResponse> getPostedJobPosts(int page, int size, JobPostStatusEnum status, String keyword) {
        String recruiterId = SecurityUtils.getCurrentUserId();

        Pageable pageable = PaginationUtils.buildPageable(page, size);
        String statusName = (status != null) ? status.name() : null;
        Page<JobPost> jobPosts = jobPostRepository
                .findByRecruiterAndStatusCustom(recruiterId, statusName, keyword,pageable);

        return jobPosts.map(jobPostMapper::toJobPostResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<JobPostResponse> getAllPostedJobPosts(JobPostStatusEnum status,String keyword) {
        String recruiterId = SecurityUtils.getCurrentUserId();
        String statusName = (status != null) ? status.name() : null;
        return jobPostRepository
                .findAllByRecruiterAndStatusCustom(recruiterId, statusName, keyword)
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

        indexingService.createJobPostIndexing(jobPost);

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

    @Override
    public RecruiterJobPostStatsResponse getRecruiterJobPostStats() {
        String recruiterId = SecurityUtils.getCurrentUserId();
        long totalJobs = jobPostRepository.countByRecruiterId(recruiterId);
        long openingJobs = jobPostRepository.countOpeningJobs(recruiterId);
        long hiddenJobs = jobPostRepository.countHiddenJobs(recruiterId);
        long expiredJobs = jobPostRepository.countExpiredJobs(recruiterId);

        JobPostStatsResponse jobPostStatsResponse = JobPostStatsResponse.builder()
                .totalJobPosts(totalJobs)
                .openingJobPosts(openingJobs)
                .hiddenJobPosts(hiddenJobs)
                .expiredJobPosts(expiredJobs)
                .build();


        List<ApplyGoldenHourResponse> applyGoldenHourSystem = applicationRepository.getApplyGoldenHour();
        List<ApplyGoldenHourResponse> applyGoldenHourOfRecruiter = applicationRepository.getApplyGoldenHourByRecruiter(recruiterId);

        LocalDateTime fromDate = LocalDateTime
                .now()
                .minusMonths(12)
                .withDayOfMonth(1)
                .withHour(0).withMinute(0).withSecond(0);
        List<RecruiterPostingFrequencyResponse> recruiterPostingFrequency = jobPostRepository
                .getPostingFrequencyOfRecruiter(recruiterId, fromDate);

        RecruiterWorkSalaryStatsResponse recruiterWorkSalaryStats = workRepository.getRecruiterWorkSalaryStats(recruiterId);

        return RecruiterJobPostStatsResponse.builder()
                .jobPostStats(jobPostStatsResponse)
                .systemApplyGoldenHour(applyGoldenHourSystem)
                .recruiterApplyGoldenHour(applyGoldenHourOfRecruiter)
                .postingFrequency(recruiterPostingFrequency)
                .workSalaryStats(recruiterWorkSalaryStats)
                .build();
    }

    @Override
    public JobPostDetailStatsResponse getJobPostDetailStats(String jobPostId) {
        JobPost jobPost = jobPostRepository.findById(jobPostId)
                .orElseThrow(() -> new AppException(ErrorCode.JOB_POST_NOT_EXISTED));

        // ===== Views =====
        long totalView = Optional.ofNullable(jobPost.getViewCount()).orElse(0L)
                + jobPostViewService.getRedisView(jobPostId);

        long applyCount = applicationRepository.countByJobPostId(jobPostId);
        long saveCount = candidateSaveJobRepository.countByJobPostId(jobPostId);


        long viewHourly = jobPostViewService.getRedisView(jobPostId); // view giờ gần nhất

        // ===== Employee / Work stats =====
        long totalStaffCount = workRepository.countByJobPostId(jobPostId);
        long staffCompletedCount = workRepository.countByJobPostIdAndStatus(jobPostId, WorkStatusEnum.SUBMITTED);

        // ===== Payment stats =====
        BigInteger totalSalary = workRepository.sumAgreedSalaryByJobPost(jobPostId);
        BigInteger  paidSalary = workRepository.sumAgreedSalaryByJobPostAndStatus(jobPostId, WorkStatusEnum.PAID);
        BigInteger  pendingSalary  = workRepository.sumAgreedSalaryByJobPostAndStatus(jobPostId, WorkStatusEnum.SUBMITTED);

        // ===== Build response =====
        return JobPostDetailStatsResponse.builder()
                .jobPostId(jobPostId)
                .totalView(totalView)
                .applyCount(applyCount)
                .saveCount(saveCount)
                .viewHourly(viewHourly)
                .totalStaffCount(totalStaffCount)
                .staffCompletedCount(staffCompletedCount)
                .totalSalary(totalSalary)
                .paidSalary(paidSalary)
                .pendingSalary(pendingSalary)
                .build();
    }
}