package com.hw.hwjobbackend.entity.region;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity(name = "provinces")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Province {

    @Id
    int code;

    String name;
    String codeName;
    String divisionType;
    Integer phoneCode;

    @OneToMany(mappedBy = "province", fetch = FetchType.LAZY)
    List<Ward> wards;
}
