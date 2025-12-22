package com.hw.hwjobbackend.controller.payment;

import com.hw.hwjobbackend.service.shared.loyalty_point.LoyaltyPointService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/public/vnpay")
public class VNPayController {

    LoyaltyPointService loyaltyPointService;

    @GetMapping("/ipn")
    public ResponseEntity<Map<String, String>> handleVnPayIpn(HttpServletRequest request) throws Exception {
        // Chỉ truyền request vào service
        loyaltyPointService.processVnPayIpn(request);

        // VNPay yêu cầu trả về đúng format này để dừng gọi IPN
        Map<String, String> response = new HashMap<>();
        response.put("RspCode", "00");
        response.put("Message", "Confirm Success");
        return ResponseEntity.ok(response);
    }
}
