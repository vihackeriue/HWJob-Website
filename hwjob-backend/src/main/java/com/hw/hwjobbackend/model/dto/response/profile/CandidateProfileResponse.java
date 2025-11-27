package com.hw.hwjobbackend.model.dto.response.profile;


import com.hw.hwjobbackend.model.enums.CandidateGenderEnum;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CandidateProfileResponse extends UserProfileResponse {
    LocalDate dob;
    CandidateGenderEnum gender;
    String education;
    BigDecimal expectSalary;
}
