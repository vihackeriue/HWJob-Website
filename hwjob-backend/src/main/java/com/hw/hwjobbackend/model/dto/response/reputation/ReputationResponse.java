package com.hw.hwjobbackend.model.dto.response.reputation;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigInteger;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReputationResponse {
    BigInteger reputation;
}
