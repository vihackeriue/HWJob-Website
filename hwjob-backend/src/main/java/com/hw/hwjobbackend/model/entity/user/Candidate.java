package com.hw.hwjobbackend.model.entity.user;


import com.hw.hwjobbackend.model.enums.CandidateGenderEnum;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "candidates")
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@PrimaryKeyJoinColumn(name = "id")
public class Candidate extends User {

    LocalDate dob;

    CandidateGenderEnum gender;

    String education;

    BigDecimal expectSalary;
}
