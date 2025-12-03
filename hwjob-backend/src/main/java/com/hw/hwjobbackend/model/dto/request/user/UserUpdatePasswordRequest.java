package com.hw.hwjobbackend.model.dto.request.user;


import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdatePasswordRequest {

    @Size(min = 8, message = "PASSWORD_INVALID")
    String oldPassword;

    @Size(min = 8, message = "PASSWORD_INVALID")
    String newPassword;
}
