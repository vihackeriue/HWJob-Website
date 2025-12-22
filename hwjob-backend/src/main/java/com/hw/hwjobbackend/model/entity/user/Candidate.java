package com.hw.hwjobbackend.model.entity.user;


import com.hw.hwjobbackend.model.entity.skill.Skill;
import com.hw.hwjobbackend.model.enums.CandidateGenderEnum;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

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

    @ManyToMany(fetch = FetchType.LAZY)
    Set<Skill> skills;

    LocalDate dob;

    CandidateGenderEnum gender;

    String education;

    BigDecimal expectSalary;

}
