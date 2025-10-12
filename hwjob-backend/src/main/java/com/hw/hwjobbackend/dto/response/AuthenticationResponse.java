package com.hw.hwjobbackend.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;


@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationResponse {
    String token;
    boolean authenticated;
}
