package com.hw.hwjobbackend.dto.request.user;

import com.hw.hwjobbackend.validator.phone_validator.PhoneConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {

    @NotBlank(message = "USERNAME_INVALID")
    @Size(min = 6, message = "USERNAME_INVALID")
    String name;
    @Email(message = "EMAIL_INVALID")
    String email;
    @Size(min = 8, message = "PASSWORD_INVALID")
    String password;
    @PhoneConstraint(message = "PHONE_INVALID")
    String phone;
    String imageUrl;
    String userStatus;

    String countryCode;
    int provinceCode;
    int wardCode;
}
