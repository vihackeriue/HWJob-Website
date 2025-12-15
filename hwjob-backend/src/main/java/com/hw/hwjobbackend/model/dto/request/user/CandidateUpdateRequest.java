package com.hw.hwjobbackend.model.dto.request.user;

import com.hw.hwjobbackend.model.enums.CandidateGenderEnum;
import jakarta.annotation.Nullable;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CandidateUpdateRequest extends UserUpdateRequest {

    Set<Long> skillIds;

    LocalDate dob;

    CandidateGenderEnum gender;

    String education;

    BigDecimal expectSalary;
}
