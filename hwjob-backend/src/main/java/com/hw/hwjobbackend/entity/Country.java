package com.hw.hwjobbackend.entity;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;


//mappedBy: Chỉ định rằng quan hệ được quản lý bởi trường ở phía "many" (phía con)
//mappedBy = "country" trong Country.provinces: JPA sẽ sử dụng trường country trong entity Province để quản lý quan hệ
//mappedBy = "province" trong Province.wards: JPA sẽ sử dụng trường province trong entity Ward để quản lý quan hệ
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    String name;
    String code;

    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<Province> provinces;

}
