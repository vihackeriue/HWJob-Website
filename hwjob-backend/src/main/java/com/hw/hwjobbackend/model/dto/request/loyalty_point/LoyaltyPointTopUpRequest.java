package com.hw.hwjobbackend.model.dto.request.loyalty_point;

import com.hw.hwjobbackend.model.enums.PaymentMethodEnum;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoyaltyPointTopUpRequest {
     Long amount;
     PaymentMethodEnum paymentMethod;
}
