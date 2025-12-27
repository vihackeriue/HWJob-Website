package com.hw.hwjobbackend.controller.user;

import com.hw.hwjobbackend.model.dto.response.ApiResponse;
import com.hw.hwjobbackend.model.dto.response.loyalty_point.LoyaltyPointResponse;
import com.hw.hwjobbackend.model.dto.response.reputation.ReputationResponse;
import com.hw.hwjobbackend.service.shared.reputation.ReputationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/reputation")
public class ReputationController {
    ReputationService reputationService;
    @GetMapping("/me")
    ApiResponse<ReputationResponse> getReputation() throws Exception {

        return ApiResponse.<ReputationResponse>builder()
                .result(reputationService.getUserReputation())
                .build();
    }
}
