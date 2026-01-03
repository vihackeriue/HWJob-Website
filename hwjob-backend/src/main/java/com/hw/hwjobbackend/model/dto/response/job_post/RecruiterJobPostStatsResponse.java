package com.hw.hwjobbackend.model.dto.response.job_post;

import com.hw.hwjobbackend.model.dto.response.application.projection.ApplyGoldenHourResponse;
import com.hw.hwjobbackend.model.dto.response.job_post.projection.RecruiterPostingFrequencyResponse;
import com.hw.hwjobbackend.model.dto.response.job_post.projection.RecruiterWorkSalaryStatsResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RecruiterJobPostStatsResponse {


    JobPostStatsResponse jobPostStats;

    List<ApplyGoldenHourResponse> systemApplyGoldenHour;
    List<ApplyGoldenHourResponse> recruiterApplyGoldenHour;
    List<RecruiterPostingFrequencyResponse> postingFrequency;

    RecruiterWorkSalaryStatsResponse workSalaryStats;
}
