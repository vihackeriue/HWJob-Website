package com.hw.hwjobbackend.service.shared.loyalty_point;

import com.hw.hwjobbackend.model.dto.request.loyalty_point.LoyaltyPointTopUpRequest;
import com.hw.hwjobbackend.model.dto.response.loyalty_point.LoyaltyPointPaymentHistoryResponse;
import com.hw.hwjobbackend.model.dto.response.loyalty_point.LoyaltyPointResponse;
import com.hw.hwjobbackend.model.dto.response.loyalty_point.LoyaltyPointTopUpResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;

import java.math.BigInteger;
import java.util.List;

public interface LoyaltyPointService {

    LoyaltyPointResponse getUserPoints() ;

    LoyaltyPointResponse getUserLockedPoints();

    List<LoyaltyPointPaymentHistoryResponse> getUserPaymentHistory();

    Page<LoyaltyPointPaymentHistoryResponse> getUserPaymentHistory(Integer page, Integer size);


    LoyaltyPointTopUpResponse createTopUp(
            LoyaltyPointTopUpRequest req,
            HttpServletRequest httpRequest
    );

    void refundPointToRecruiter(String jobPostId, String candidateId);

    void refundPointToRecruiterAndDeductReputation(String recruiterUserId, String freelancerUserId, BigInteger amount);
}
