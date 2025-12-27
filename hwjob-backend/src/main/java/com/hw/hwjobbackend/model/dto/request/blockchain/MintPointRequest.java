package com.hw.hwjobbackend.model.dto.request.blockchain;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigInteger;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MintPointRequest {
    private String userId;
    private BigInteger amount;
}
