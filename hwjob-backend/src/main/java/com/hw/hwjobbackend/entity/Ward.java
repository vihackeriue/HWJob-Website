package com.hw.hwjobbackend.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Ward {
    @Id
    Integer code;
    String name;
    String codeName;
    String divisionType;
    String shortCodeName;

    @ManyToOne(fetch = FetchType.LAZY)
    Province province;
}
