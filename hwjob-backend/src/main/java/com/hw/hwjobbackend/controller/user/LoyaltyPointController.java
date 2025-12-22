package com.hw.hwjobbackend.controller.user;

import com.hw.hwjobbackend.model.dto.request.loyalty_point.LoyaltyPointTopUpRequest;
import com.hw.hwjobbackend.model.dto.response.ApiResponse;
import com.hw.hwjobbackend.model.dto.response.loyalty_point.LoyaltyPointResponse;
import com.hw.hwjobbackend.model.dto.response.loyalty_point.LoyaltyPointTopUpResponse;
import com.hw.hwjobbackend.service.payment.MomoService;
import com.hw.hwjobbackend.service.shared.loyalty_point.LoyaltyPointService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/loyalty-points")
public class LoyaltyPointController {
    LoyaltyPointService loyaltyPointService;

    @GetMapping("/me")
    ApiResponse<LoyaltyPointResponse> getPoints() throws Exception {

        return ApiResponse.<LoyaltyPointResponse>builder()
                .result(loyaltyPointService.getUserPoints())
                .build();
    }
    @PostMapping("/top-up")
    public ResponseEntity<LoyaltyPointTopUpResponse> topUp(
            @RequestBody LoyaltyPointTopUpRequest request,  HttpServletRequest httpRequest
    ) {
        return ResponseEntity.ok(
                loyaltyPointService.createTopUp(request, httpRequest)
        );
    }


}
