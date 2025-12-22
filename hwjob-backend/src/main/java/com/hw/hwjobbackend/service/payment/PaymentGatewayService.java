package com.hw.hwjobbackend.service.payment;

import com.hw.hwjobbackend.model.dto.request.loyalty_point.LoyaltyPointPaymentGatewayRequest;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

public interface PaymentGatewayService {

    // Tạo URL thanh toán
    String createPaymentUrl(LoyaltyPointPaymentGatewayRequest paymentReq, HttpServletRequest request);

    // Xác thực chữ ký từ nhà cung cấp (IPN/Return)
    void verifySignature(Map<String, String> params);
}
