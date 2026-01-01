package com.hw.hwjobbackend.model.dto.response.job_post;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RecruiterJobPostStatsResponse {

    long totalJobPosts;     // Tổng bài đã đăng
    long openingJobPosts;   // Đang mở tuyển
    long hiddenJobPosts;    // Bị ẩn
    long expiredJobPosts;   // Hết hạn
}
