package com.hw.hwjobbackend.dto.response;

import com.hw.hwjobbackend.enums.UserStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {

    String id;
    String name;
    String username;
    String email;
    String phone;
    String imageUrl;
    //    Region region;
    UserStatus userStatus;
    Set<RoleResponse> roles;
}
