package com.hw.hwjobbackend.model.entity.user;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "recruiters")
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@PrimaryKeyJoinColumn(name = "id")
public class Recruiter extends User {
    @Column(columnDefinition = "TEXT")
    @Lob
    String description;
    String website;
    String specificAddress;
}
