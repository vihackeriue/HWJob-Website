package com.hw.hwjobbackend.controller.user;

import com.hw.hwjobbackend.model.dto.request.loyalty_point.LoyaltyPointTopUpRequest;
import com.hw.hwjobbackend.model.dto.response.ApiResponse;
import com.hw.hwjobbackend.model.dto.response.loyalty_point.LoyaltyPointPaymentHistoryResponse;
import com.hw.hwjobbackend.model.dto.response.loyalty_point.LoyaltyPointResponse;
import com.hw.hwjobbackend.model.dto.response.loyalty_point.LoyaltyPointTopUpResponse;
import com.hw.hwjobbackend.service.shared.loyalty_point.LoyaltyPointService;
import com.hw.hwjobbackend.service.shared.loyalty_point.LoyaltyPointServiceImpl;
import com.hw.hwjobbackend.util.PaginationUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/loyalty-points")
public class LoyaltyPointController {
    LoyaltyPointService loyaltyPointService;

    @GetMapping("/me")
    ApiResponse<LoyaltyPointResponse> getPoints() {

        return ApiResponse.<LoyaltyPointResponse>builder()
                .result(loyaltyPointService.getUserPoints())
                .build();
    }
    @GetMapping("/locked/me")
    ApiResponse<LoyaltyPointResponse> getLockedPoints()  {

        return ApiResponse.<LoyaltyPointResponse>builder()
                .result(loyaltyPointService.getUserLockedPoints())
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

    @GetMapping("/payment-history")
    public ApiResponse<List<LoyaltyPointPaymentHistoryResponse>> getPaymentHistory(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size) {
        if (page != null && size != null) {
            int zeroBasedPage = PaginationUtils.toZeroBasedPage(page);
            Page<LoyaltyPointPaymentHistoryResponse> responses = loyaltyPointService.getUserPaymentHistory(zeroBasedPage, size);

            return ApiResponse.<List<LoyaltyPointPaymentHistoryResponse>>builder()
                    .page(PaginationUtils.toOneBasedPage(responses.getNumber()))
                    .totalPages(responses.getTotalPages())
                    .result(loyaltyPointService.getUserPaymentHistory())
                    .build();
        }

        return ApiResponse.<List<LoyaltyPointPaymentHistoryResponse>>builder()
                .result(loyaltyPointService.getUserPaymentHistory())
                .build();
    }

}
