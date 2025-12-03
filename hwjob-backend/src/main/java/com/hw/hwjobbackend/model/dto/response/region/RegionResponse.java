package com.hw.hwjobbackend.model.dto.response.region;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegionResponse {
    Integer id;
    String name;
}