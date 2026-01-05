package com.hw.hwjobbackend.model.dto.request.job_post;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BoostJobPostRequest {
    private int days; // số ngày muốn đẩy
    private String packageKey; // dùng khi mua gói
}
