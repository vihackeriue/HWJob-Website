package com.hw.hwjobbackend.model.entity.region;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "wards")
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
