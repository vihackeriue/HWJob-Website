package com.hw.hwjobbackend.model.dto.request.loyalty_point;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.math.BigInteger;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoyaltyPointPaidEvent {
    String paymentId;
    String userId;
    BigInteger points;
}
