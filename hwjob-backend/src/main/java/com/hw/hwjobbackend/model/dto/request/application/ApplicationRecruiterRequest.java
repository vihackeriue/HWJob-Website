package com.hw.hwjobbackend.model.dto.request.application;

import com.hw.hwjobbackend.model.enums.ApplicationStatusEnum;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ApplicationRecruiterRequest {
    String jobPostId;
    String candidateId;
    ApplicationStatusEnum status;
}
