package com.hw.hwjobbackend.dto.request.job_type;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JobTypeRequest {
    @NotBlank(message = "JOB_TYPE_NOT_BLANK")
    @Size(min = 1)
    String name;
    @NotBlank(message = "JOB_TYPE_CODE_NOT_BLANK")
    String code;
}
