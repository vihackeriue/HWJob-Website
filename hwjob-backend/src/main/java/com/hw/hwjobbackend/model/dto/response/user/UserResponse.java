package com.hw.hwjobbackend.model.dto.response.user;

import com.hw.hwjobbackend.model.dto.response.role.RoleResponse;
import com.hw.hwjobbackend.model.enums.UserStatusEnum;
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
    String username;
    String email;
    String phone;
    String fullName;
    String summary;
    String imageUrl;
    Set<RoleResponse> roles;
    String region;
}
