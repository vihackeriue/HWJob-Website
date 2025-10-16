package com.hw.hwjobbackend.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@PrimaryKeyJoinColumn(name = "id")
public class Candidate extends User {
    LocalDate dob;
    String summary;
    String gender;
    String address;
    String education;
    Double expectSalary;

    @ManyToMany(fetch = FetchType.EAGER)
    Set<Skill> skills;
}
