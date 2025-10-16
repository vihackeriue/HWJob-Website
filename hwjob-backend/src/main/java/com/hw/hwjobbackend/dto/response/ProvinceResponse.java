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
public class ProvinceResponse {
    Integer code;
    String name;
    String codeName;
    String divisionType;
    Integer phoneCode;
    List<WardResponse> wards;
}