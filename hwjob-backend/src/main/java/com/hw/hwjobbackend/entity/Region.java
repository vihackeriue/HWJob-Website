package com.hw.hwjobbackend.entity;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Region {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    String name;
    String code;

    @ManyToOne(fetch = FetchType.EAGER)
    Country country;
}
