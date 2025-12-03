package com.hw.hwjobbackend.model.dto.response.authentication;

import com.hw.hwjobbackend.model.dto.response.user.UserLoginResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;


@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationResponse {
    String accessToken;
    String refreshToken;
    UserLoginResponse user;
}
