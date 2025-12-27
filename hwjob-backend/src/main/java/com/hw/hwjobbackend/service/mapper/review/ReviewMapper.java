package com.hw.hwjobbackend.service.mapper.review;

import com.hw.hwjobbackend.model.dto.response.review.ReviewResponse;
import com.hw.hwjobbackend.model.entity.review.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    @Mapping(target = "reviewer.id", source = "reviewer.id")
    @Mapping(target = "reviewer.fullName", source = "reviewer.fullName")
    @Mapping(target = "reviewer.imageUrl", source = "reviewer.imageUrl")

    ReviewResponse toReviewResponse(Review review);
}
