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
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoyaltyPointPaymentHistoryResponse {

     String paymentId;

     Long amount;

     BigInteger points;

     PaymentMethodEnum paymentMethod;

     PaymentTypeEnum paymentType;

     PaymentStatusEnum status;

     String blockchainTxHash;

     LocalDateTime createdAt;
}
