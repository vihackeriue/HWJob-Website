package com.hw.hwjobbackend.controller.payment;

import com.hw.hwjobbackend.service.shared.loyalty_point.LoyaltyPointServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/public/vnpay")
@Log4j2
public class VNPayController {

    LoyaltyPointServiceImpl loyaltyPointServiceImpl;

    @GetMapping("/ipn")
    public ResponseEntity<Map<String, String>> handleVnPayIpn(HttpServletRequest request) throws Exception {
        log.error("🔥 VNPay IPN HIT 🔥");
        // Chỉ truyền request vào service
        loyaltyPointServiceImpl.processVnPayIpn(request);

        // VNPay yêu cầu trả về đúng format này để dừng gọi IPN
        Map<String, String> response = new HashMap<>();
        response.put("RspCode", "00");
        response.put("Message", "Confirm Success");
        return ResponseEntity.ok(response);
    }
}
