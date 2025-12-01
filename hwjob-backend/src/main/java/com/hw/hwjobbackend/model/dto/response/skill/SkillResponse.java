package com.hw.hwjobbackend.model.dto.response.skill;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SkillResponse {
    Long id;
    String name;
    String description;
}
