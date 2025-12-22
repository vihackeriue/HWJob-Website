package com.hw.hwjobbackend.controller.payment;

import com.hw.hwjobbackend.service.shared.loyalty_point.LoyaltyPointService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/public/momo")
public class MomoController {

    LoyaltyPointService loyaltyPointService;

    @PostMapping("/ipn")
    public ResponseEntity<Void> momoIpn(
            @RequestBody Map<String, String> payload
    ) throws Exception {
        loyaltyPointService.handleMomoIpn(payload);
        return ResponseEntity.ok().build();
    }
}
