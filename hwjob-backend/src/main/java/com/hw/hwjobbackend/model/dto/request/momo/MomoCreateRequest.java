package com.hw.hwjobbackend.model.dto.request.momo;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MomoCreateRequest {
     String partnerCode;
     String accessKey;
     String requestId;
     Long amount;
     String orderId;
     String orderInfo;
     String redirectUrl;
     String ipnUrl;
     String extraData;
     String requestType;
     String signature;
     String lang = "en";
}
