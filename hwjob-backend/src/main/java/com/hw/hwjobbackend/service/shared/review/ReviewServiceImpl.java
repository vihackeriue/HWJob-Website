package com.hw.hwjobbackend.service.shared.review;

import com.hw.hwjobbackend.exception.AppException;
import com.hw.hwjobbackend.exception.ErrorCode;
import com.hw.hwjobbackend.model.dto.request.review.ReviewCreateRequest;
import com.hw.hwjobbackend.model.dto.response.review.AverageRatingResponse;
import com.hw.hwjobbackend.model.dto.response.review.ReviewResponse;
import com.hw.hwjobbackend.model.entity.review.Review;
import com.hw.hwjobbackend.model.entity.user.User;
import com.hw.hwjobbackend.model.entity.works.Work;
import com.hw.hwjobbackend.model.enums.WorkStatusEnum;
import com.hw.hwjobbackend.repository.review.ReviewRepository;
import com.hw.hwjobbackend.repository.user.UserRepository;
import com.hw.hwjobbackend.repository.work.WorkRepository;
import com.hw.hwjobbackend.service.mapper.review.ReviewMapper;
import com.hw.hwjobbackend.service.shared.reputation.ReputationService;
import com.hw.hwjobbackend.util.PaginationUtils;
import com.hw.hwjobbackend.util.SecurityUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReviewServiceImpl implements ReviewService {

    WorkRepository workRepository;
    ReviewRepository reviewRepository;
    UserRepository userRepository;
    ReviewMapper reviewMapper;
    ReputationService reputationService;
    @Override
    public void createReview(ReviewCreateRequest request) {
        String reviewerId = SecurityUtils.getCurrentUserId();

        User reviewer = userRepository.findById(reviewerId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        Work work = workRepository.findById(request.getWorkId())
                .orElseThrow(() -> new AppException(ErrorCode.WORK_NOT_FOUND));


        // Check work PAID
        if (work.getStatus() != WorkStatusEnum.PAID) {
            throw new AppException(ErrorCode.WORK_NOT_PAID);
        }
        User candidate = work.getCandidate();
        User recruiter = work.getRecruiter();

        // Reviewer phải là recruiter hoặc candidate
        if (!reviewerId.equals(candidate.getId())
                && !reviewerId.equals(recruiter.getId())) {
            throw new AppException(ErrorCode.NOT_ALLOWED_RATING);
        }
        User reviewee = userRepository.findById(request.getRevieweeId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        String revieweeId = request.getRevieweeId();
        boolean validPair =
                (reviewerId.equals(candidate.getId()) && revieweeId.equals(recruiter.getId()))
                        || (reviewerId.equals(recruiter.getId()) && revieweeId.equals(candidate.getId()));


        if (!validPair) {
            throw new AppException(ErrorCode.INVALID_REVIEW_TARGET);
        }

        // Check đã review chưa
        if (reviewRepository.existsByWork_IdAndReviewer_Id(request.getWorkId(), reviewerId)) {
            throw new AppException(ErrorCode.REVIEW_ALREADY_EXISTS);
        }
        // Validate rating
        if (request.getRating() < 1 || request.getRating() > 5) {
            throw new AppException(ErrorCode.INVALID_RATING);
        }
        if(request.getRating() == 1) {
            reputationService.deductReputation(revieweeId, BigInteger.valueOf(3));
        }
//        if(request.getRating() == 5) {
//            reputationService.deductReputation(revieweeId, BigInteger.valueOf(5));
//        }

        Review review = Review.builder()
                .work(work)
                .reviewer(reviewer)
                .reviewee(reviewee)
                .rating(request.getRating())
                .build();
        reviewRepository.save(review);
    }
    @Override
    public Page<ReviewResponse> getReviewsOfUser(Integer page, Integer size) {

        String revieweeId = SecurityUtils.getCurrentUserId();

        // validate user tồn tại (giữ giống logic cũ)
        userRepository.findById(revieweeId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        Pageable pageable = PaginationUtils.buildPageable(page, size);

        Page<Review> reviews = reviewRepository.findByRevieweeId(revieweeId, pageable);

        return reviews.map(reviewMapper::toReviewResponse);
    }
    @Override
    public List<ReviewResponse> getReviewsOfUser() {
        String revieweeId = SecurityUtils.getCurrentUserId();

        User user = userRepository.findById(revieweeId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));



        return reviewRepository.findByRevieweeId(revieweeId).stream()
                .map(reviewMapper::toReviewResponse)
                .toList();
    }
    @Override
    public AverageRatingResponse getAverageRating() {
        String userId = SecurityUtils.getCurrentUserId();

        Double avg = reviewRepository.getAverageRating(userId);
        AverageRatingResponse averageRatingResponse = new AverageRatingResponse();
        averageRatingResponse.setAverageRating(avg);
        return averageRatingResponse;
    }
}
