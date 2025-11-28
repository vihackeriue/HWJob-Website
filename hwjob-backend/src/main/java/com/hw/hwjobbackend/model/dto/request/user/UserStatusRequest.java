package com.hw.hwjobbackend.model.dto.request.user;


import com.hw.hwjobbackend.model.enums.UserStatusEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserStatusRequest {

    @NotNull(message = "USER_STATUS_REQUIRED")
    UserStatusEnum status;
}
