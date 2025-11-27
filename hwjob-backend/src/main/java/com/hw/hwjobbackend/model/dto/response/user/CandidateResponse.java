package com.hw.hwjobbackend.model.dto.response.user;

import com.hw.hwjobbackend.model.enums.CandidateGenderEnum;
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
    CandidateGenderEnum gender;
    String education;
    BigDecimal expectSalary;
}
