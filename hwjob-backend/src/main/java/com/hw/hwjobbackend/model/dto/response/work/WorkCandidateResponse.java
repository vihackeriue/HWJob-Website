package com.hw.hwjobbackend.model.dto.response.work;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.hw.hwjobbackend.model.enums.SalaryTypeEnum;
import com.hw.hwjobbackend.model.enums.WorkStatusEnum;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WorkCandidateResponse {
    String workId;
    String candidateId;
    String fullName;
    String email;
    String imageUrl;
    Long agreedSalary;
    SalaryTypeEnum salaryType;
    LocalDateTime startTime;
    LocalDateTime endTime;
    WorkStatusEnum status;
    Double myReviewRating ;
}
