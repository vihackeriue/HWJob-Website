package com.hw.hwjobbackend.model.dto.request.level;


import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LevelRequest {
    @NotBlank(message = "LEVEL_NOT_BLANK")
    String name;
}
