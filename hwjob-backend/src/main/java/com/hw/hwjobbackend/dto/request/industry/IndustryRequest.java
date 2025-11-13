package com.hw.hwjobbackend.dto.request.industry;


import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class IndustryRequest {
    @NotBlank(message = " INDUSTRY_NOT_BLANK")
    String name;
    String description;
}
