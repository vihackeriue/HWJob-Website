package com.hw.hwjobbackend.entity.user;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity(name = "candidates")
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@PrimaryKeyJoinColumn(name = "id")
public class Candidate extends User {
    LocalDate dob;
    @Column(columnDefinition = "TEXT")
    String summary;
    String gender;
    String address;
    String education;
    BigDecimal expectSalary;
}
