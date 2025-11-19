package com.hw.hwjobbackend.dto.response.application;

import com.hw.hwjobbackend.enums.ApplicationStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ApplicationResponse {
    String jobPostId;
    ApplicationStatus status;
    Date createdAt;
}
