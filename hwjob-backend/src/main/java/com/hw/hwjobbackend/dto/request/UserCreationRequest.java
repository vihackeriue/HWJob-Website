package com.hw.hwjobbackend.dto.request;


import com.hw.hwjobbackend.validator.phone_validator.PhoneConstraint;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;


@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {

    @Size(min = 6, message = "USERNAME_INVALID")
    String username;

    @Size(min = 8, message = "PASSWORD_INVALID")
    String password;

    String name;

    @PhoneConstraint(message = "PHONE_INVALID")
    String phone;
}
