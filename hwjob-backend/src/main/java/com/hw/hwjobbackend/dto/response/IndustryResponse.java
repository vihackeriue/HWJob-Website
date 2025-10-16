package com.hw.hwjobbackend.dto.response;

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
    List<IndustryResponse> children;
}
