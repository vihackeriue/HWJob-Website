package com.hw.hwjobbackend.dto.response.user;

import com.hw.hwjobbackend.dto.response.role.RoleResponse;
import com.hw.hwjobbackend.enums.UserStatusEnum;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    String id;
    String name;
    String username;
    String email;
    String phone;
    String imageUrl;
    UserStatusEnum userStatus;
    Set<RoleResponse> roles;
    String region;
    String description;
    String website;
    String specificAddress;
}
