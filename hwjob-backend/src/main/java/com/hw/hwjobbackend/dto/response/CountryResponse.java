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
public class CountryResponse {
    String id;
    String name;
    String code;
    List<ProvinceResponse> provinces;
}
