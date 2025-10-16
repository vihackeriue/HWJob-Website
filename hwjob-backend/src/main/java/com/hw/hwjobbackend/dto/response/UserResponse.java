package com.hw.hwjobbackend.dto.response;

import com.hw.hwjobbackend.enums.UserStatusEnum;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {

    String id;
    String name;
    String username;
    String email;
    String phone;
    String imageUrl;
    //    Region region;
    UserStatusEnum userStatus;
    Set<RoleResponse> roles;
}
