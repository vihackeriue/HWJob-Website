package com.hw.hwjobbackend.dto.api_response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WardApiResponse {
    String name;
    Integer code;
    @JsonProperty("codename")
    String codeName;
    @JsonProperty("division_type")
    String divisionType;
    @JsonProperty("short_codename")
    String shortCodeName;
}
