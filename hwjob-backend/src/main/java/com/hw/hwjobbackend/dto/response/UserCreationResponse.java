package com.hw.hwjobbackend.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;


@Setter
@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationResponse {
    private String username;
}
