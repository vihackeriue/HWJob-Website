package com.hw.hwjobbackend.dto.response.user;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;


@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CandidateResponse extends UserResponse {
    LocalDate dob;
    String summary;
    String gender;
    String address;
    String education;
    BigDecimal expectSalary;
}
