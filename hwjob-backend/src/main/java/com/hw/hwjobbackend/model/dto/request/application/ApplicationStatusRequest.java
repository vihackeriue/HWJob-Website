package com.hw.hwjobbackend.model.dto.request.application;


import com.hw.hwjobbackend.model.enums.ApplicationStatusEnum;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationStatusRequest {
    ApplicationStatusEnum status;
}
