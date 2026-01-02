package com.hw.hwjobbackend.model.dto.response.job_post;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigInteger;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JobPostDetailStatsResponse {

    String jobPostId;

    @Builder.Default
    long totalView = 0L;

    @Builder.Default
    long applyCount = 0L;

    @Builder.Default
    long saveCount = 0L;

    @Builder.Default
    long viewHourly = 0L;

    @Builder.Default
    long totalStaffCount = 0L;

    @Builder.Default
    long staffCompletedCount = 0L;

    @Builder.Default
    BigInteger totalSalary = BigInteger.ZERO;

    @Builder.Default
    BigInteger paidSalary = BigInteger.ZERO;

    @Builder.Default
    BigInteger pendingSalary = BigInteger.ZERO;
}
