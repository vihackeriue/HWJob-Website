package com.hw.hwjobbackend.entity;


import com.hw.hwjobbackend.enums.UserStatusEnum;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Inheritance(strategy = InheritanceType.JOINED)
@EntityListeners(AuditingEntityListener.class)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    String name;
    String username;
    String email;
    String password;
    String phone;
    String imageUrl;

    UserStatusEnum userStatus;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    Date createdAt;

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    Date updatedAt;

    @ManyToMany(fetch = FetchType.EAGER)
    Set<Role> roles;

    @ManyToOne(fetch = FetchType.LAZY)
    Country country;

    @ManyToOne(fetch = FetchType.LAZY)
    Province province;

    @ManyToOne(fetch = FetchType.LAZY)
    Ward ward;

}
