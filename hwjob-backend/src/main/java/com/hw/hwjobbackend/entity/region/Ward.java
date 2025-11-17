package com.hw.hwjobbackend.entity.region;


import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity(name = "wards")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Ward {
    @Id
    int code;
    String name;
    String codeName;
    String divisionType;
    String shortCodeName;

    @ManyToOne(fetch = FetchType.LAZY)
    Province province;
}
