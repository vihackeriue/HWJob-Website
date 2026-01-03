package com.hw.hwjobbackend.model.dto.response.job_post.projection;

import java.math.BigInteger;

public interface RecruiterWorkSalaryStatsResponse {
    BigInteger getPaidSalary();       // đã thanh toán

    BigInteger getUnpaidSalary();     // chưa thanh toán

    BigInteger getTotalSalary();      // tổng
}
