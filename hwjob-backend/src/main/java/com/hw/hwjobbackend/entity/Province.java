package com.hw.hwjobbackend.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Province {

    @Id
    Integer code;

    String name;
    String codeName;
    String divisionType;
    Integer phoneCode;

    @ManyToOne(fetch = FetchType.LAZY)
    Country country;

    @OneToMany(mappedBy = "province", fetch = FetchType.LAZY)
    List<Ward> wards;
}
