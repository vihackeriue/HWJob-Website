package com.hw.hwjobbackend.service.shared.review;

import com.hw.hwjobbackend.model.dto.request.review.ReviewCreateRequest;
import com.hw.hwjobbackend.model.dto.response.review.AverageRatingResponse;
import com.hw.hwjobbackend.model.dto.response.review.ReviewResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ReviewService {
    void createReview(ReviewCreateRequest request);
    Page<ReviewResponse> getReviewsOfUser(Integer page, Integer size);
    List<ReviewResponse> getReviewsOfUser();
    AverageRatingResponse getAverageRating();


}
