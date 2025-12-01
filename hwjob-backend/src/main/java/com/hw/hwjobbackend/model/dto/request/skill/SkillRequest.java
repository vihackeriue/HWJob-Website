package com.hw.hwjobbackend.model.dto.request.skill;


import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SkillRequest {
    String name;
    String description;
}
