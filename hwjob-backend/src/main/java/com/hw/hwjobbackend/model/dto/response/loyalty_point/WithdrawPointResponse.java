package com.hw.hwjobbackend.model.dto.response.loyalty_point;

import com.hw.hwjobbackend.model.enums.PaymentMethodEnum;
import com.hw.hwjobbackend.model.enums.PaymentStatusEnum;
import com.hw.hwjobbackend.model.enums.PaymentTypeEnum;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WithdrawPointResponse {

    String id;
    Long amount;
    BigInteger points;

    PaymentTypeEnum paymentType;
    PaymentMethodEnum paymentMethod;
    PaymentStatusEnum status;

    String momoPhone;
    String gatewayTransactionId;

    LocalDateTime createdAt;
}
