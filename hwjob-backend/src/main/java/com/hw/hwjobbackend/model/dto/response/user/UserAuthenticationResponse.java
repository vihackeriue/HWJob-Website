package com.hw.hwjobbackend.model.dto.response.user;


import com.hw.hwjobbackend.model.dto.response.role.RoleResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserAuthenticationResponse {
    String id;
    String username;
    String email;
    String phone;
    String imageUrl;
    Set<RoleResponse> roles;
}
