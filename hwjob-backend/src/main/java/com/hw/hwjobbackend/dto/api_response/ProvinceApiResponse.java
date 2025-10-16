package com.hw.hwjobbackend.dto.api_response;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProvinceApiResponse {
    String name;
    Integer code;
    @JsonProperty("codename")
    String codeName;
    @JsonProperty("division_type")
    String divisionType;
    @JsonProperty("phone_code")
    Integer phoneCode;
    List<WardApiResponse> wards;
}
