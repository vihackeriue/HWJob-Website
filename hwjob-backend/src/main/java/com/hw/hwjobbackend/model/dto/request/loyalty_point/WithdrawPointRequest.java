package com.hw.hwjobbackend.model.dto.request.loyalty_point;


import lombok.*;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WithdrawPointRequest {

    @NotNull
    BigInteger amount;

//    @NotNull
//    BigInteger points;
//
//    @NotBlank
//    String momoPhone;
//
//    String momoName;
}
