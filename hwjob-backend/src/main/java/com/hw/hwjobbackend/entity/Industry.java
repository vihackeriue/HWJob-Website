package com.hw.hwjobbackend.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Industry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String name;
    String description;

    @ManyToOne(fetch = FetchType.LAZY)
    Industry parent;

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    List<Industry> children;
}
