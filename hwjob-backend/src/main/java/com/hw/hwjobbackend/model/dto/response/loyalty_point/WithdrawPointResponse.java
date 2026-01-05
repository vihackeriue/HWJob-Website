package com.hw.hwjobbackend.model.dto.response.loyalty_point;


import com.hw.hwjobbackend.model.enums.PaymentStatusEnum;

import lombok.*;
import lombok.experimental.FieldDefaults;



@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WithdrawPointResponse {

    String paymentId;
    PaymentStatusEnum status;
    String blockchainTxHash;
}
