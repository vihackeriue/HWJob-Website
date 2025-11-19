package com.hw.hwjobbackend.dto.request.user;


import com.hw.hwjobbackend.enums.UserStatusEnum;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserStatusRequest {
    @NotBlank
    String status;
}
