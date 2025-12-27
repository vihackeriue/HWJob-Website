package com.hw.hwjobbackend.service.mapper.loyalty_point;


import com.hw.hwjobbackend.model.dto.request.loyalty_point.LoyaltyPointPaymentGatewayRequest;
import com.hw.hwjobbackend.model.dto.request.loyalty_point.LoyaltyPointTopUpRequest;
import com.hw.hwjobbackend.model.dto.response.loyalty_point.LoyaltyPointPaymentHistoryResponse;
import com.hw.hwjobbackend.model.entity.loyalty_point.LoyaltyPointPayment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LoyaltyPointPaymentMapper {

    @Mapping(target = "grossAmount", source = "amount")
    @Mapping(target = "netAmount", source = "amount")
    @Mapping(target = "feeAmount", constant = "0L")
    @Mapping(target = "points",
            expression = "java(java.math.BigInteger.valueOf(req.getAmount()))")
    @Mapping(target = "status", constant = "PENDING")
    @Mapping(target = "paymentMethod", source  = "paymentMethod")
    @Mapping(target = "idempotentKey",
            expression = "java(java.util.UUID.randomUUID().toString())")

    LoyaltyPointPayment toEntity(LoyaltyPointTopUpRequest req);

    @Mapping(target = "paymentId", source = "id")
    @Mapping(target = "amount", source = "grossAmount")
    LoyaltyPointPaymentGatewayRequest toGatewayRequest(LoyaltyPointPayment entity);

    @Mapping(target = "paymentId", source = "id")
    @Mapping(target = "amount", source = "grossAmount")
    LoyaltyPointPaymentHistoryResponse toHistoryResponse(LoyaltyPointPayment entity);

    List<LoyaltyPointPaymentHistoryResponse> toHistoryResponse(
            List<LoyaltyPointPayment> entities
    );
}
