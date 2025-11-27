package com.hw.hwjobbackend.model.dto.request.user;

import com.hw.hwjobbackend.validation.groups.OnUpdate;
import com.hw.hwjobbackend.validation.phone_validator.PhoneConstraint;
import jakarta.validation.constraints.Email;
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

    @Size(min = 6, message = "USERNAME_INVALID", groups = OnUpdate.class)
    String fullName;

    @Email(message = "EMAIL_INVALID", groups = OnUpdate.class)
    String email;

    @PhoneConstraint(message = "PHONE_INVALID", groups = OnUpdate.class)
    String phone;

    String summary;

    Integer regionId;
}
