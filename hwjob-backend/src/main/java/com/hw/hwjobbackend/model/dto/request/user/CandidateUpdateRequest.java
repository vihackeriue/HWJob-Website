package com.hw.hwjobbackend.model.dto.request.user;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CandidateUpdateRequest extends UserUpdateRequest {
    LocalDate dob;
    String summary;
    String gender;
    String address;
    String education;
    BigDecimal expectSalary;
}
