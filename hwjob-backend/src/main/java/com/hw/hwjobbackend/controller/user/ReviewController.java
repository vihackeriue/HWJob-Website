package com.hw.hwjobbackend.controller.user;

import com.hw.hwjobbackend.model.dto.request.review.ReviewCreateRequest;
import com.hw.hwjobbackend.model.dto.response.ApiResponse;
import com.hw.hwjobbackend.model.dto.response.review.AverageRatingResponse;
import com.hw.hwjobbackend.model.dto.response.review.ReviewResponse;
import com.hw.hwjobbackend.service.shared.review.ReviewService;
import com.hw.hwjobbackend.util.PaginationUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/reviews")
public class ReviewController {

    ReviewService reviewService;

    @PostMapping
    public ApiResponse<Void> createReview(@RequestBody ReviewCreateRequest request) {

        reviewService.createReview(request);

        return ApiResponse.<Void>builder()
                .message("Đánh giá thành công")
                .build();
    }

    @GetMapping("/me")
    public ApiResponse<List<ReviewResponse>> getMyReviews(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size
    ) {

        if (page != null && size != null) {
            int zeroBasedPage = PaginationUtils.toZeroBasedPage(page);
            Page<ReviewResponse> responses = reviewService.getReviewsOfUser(zeroBasedPage, size);

            return ApiResponse.<List<ReviewResponse>>builder()
                    .page(PaginationUtils.toOneBasedPage(responses.getNumber()))
                    .totalPages(responses.getTotalPages())
                    .result(responses.getContent())
                    .build();
        }

        return  ApiResponse.<List<ReviewResponse>>builder()
                .result(reviewService.getReviewsOfUser())
                .build();
    }
    @GetMapping("/average-rating")
    public ApiResponse<AverageRatingResponse> getAverageRating() {

        return ApiResponse.<AverageRatingResponse>builder()
                .result(reviewService.getAverageRating())
                .build();
    }

}
