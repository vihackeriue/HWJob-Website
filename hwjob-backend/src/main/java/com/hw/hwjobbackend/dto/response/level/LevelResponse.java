package com.hw.hwjobbackend.dto.response.level;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LevelResponse {
    long id;
    String name;
}
