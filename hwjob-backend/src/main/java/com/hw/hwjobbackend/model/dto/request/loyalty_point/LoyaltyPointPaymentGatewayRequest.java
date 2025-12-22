package com.hw.hwjobbackend.model.dto.request.loyalty_point;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoyaltyPointPaymentGatewayRequest  {
     String paymentId;
     String userId;
     Long amount;
}
