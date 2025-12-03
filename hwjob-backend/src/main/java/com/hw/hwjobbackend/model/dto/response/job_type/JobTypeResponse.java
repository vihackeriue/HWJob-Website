package com.hw.hwjobbackend.model.dto.response.job_type;


import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JobTypeResponse {
    Long id;
    String name;
    String code;
}
