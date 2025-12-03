package com.hw.hwjobbackend.model.dto.response.industry;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class IndustryResponse {
    Long id;
    String name;
    String description;
}
