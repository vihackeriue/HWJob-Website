package com.hw.hwjobbackend.entity;


import com.hw.hwjobbackend.enums.UserStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
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
    //    Region region;
    UserStatus userStatus;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;

    @ManyToMany(fetch = FetchType.EAGER)
    Set<Role> roles;

}
