package com.hw.hwjobbackend.model.dto.request.user;

import com.hw.hwjobbackend.model.enums.CandidateGenderEnum;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CandidateUpdateRequest extends UserUpdateRequest {

    LocalDate dob;

    CandidateGenderEnum gender;

    String education;

    BigDecimal expectSalary;
}
