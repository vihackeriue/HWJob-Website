package com.hw.hwjobbackend.model.entity.region;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "provinces")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Region {
    @Id
    Integer id;

    String name;
    String codeName;
    String divisionType;
    Integer phoneCode;
}
