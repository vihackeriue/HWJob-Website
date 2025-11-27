package com.hw.hwjobbackend.model.dto.response.application;

import com.hw.hwjobbackend.model.enums.ApplicationStatusEnum;
import lombok.*;
import lombok.experimental.FieldDefaults;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ApplicationResponse {
    String jobPostId;
    ApplicationStatusEnum status;
}
