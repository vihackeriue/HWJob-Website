package com.hw.hwjobbackend.dto.response;

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
    CountryResponse country;
    ProvinceResponse province;
    WardResponse ward;
}
