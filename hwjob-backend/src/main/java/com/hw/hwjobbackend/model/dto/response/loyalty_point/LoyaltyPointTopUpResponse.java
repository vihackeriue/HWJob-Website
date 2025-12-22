package com.hw.hwjobbackend.model.dto.response.loyalty_point;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoyaltyPointTopUpResponse {
    private String topUpId;
    private String payUrl;
}
