package com.hw.hwjobbackend.dto.response;


import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WardResponse {
    Integer code;
    String name;
    String codeName;
    String divisionType;
    String shortCodeName;
    Integer provinceCode;
    String provinceName;
}