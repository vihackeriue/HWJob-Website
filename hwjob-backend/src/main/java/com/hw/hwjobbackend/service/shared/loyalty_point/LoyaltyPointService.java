package com.hw.hwjobbackend.service.shared.loyalty_point;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hw.hwjobbackend.exception.AppException;
import com.hw.hwjobbackend.exception.ErrorCode;
import com.hw.hwjobbackend.model.dto.request.loyalty_point.LoyaltyPointPaymentGatewayRequest;
import com.hw.hwjobbackend.model.dto.request.loyalty_point.LoyaltyPointTopUpRequest;
import com.hw.hwjobbackend.model.dto.response.loyalty_point.LoyaltyPointResponse;
import com.hw.hwjobbackend.model.dto.response.loyalty_point.LoyaltyPointTopUpResponse;
import com.hw.hwjobbackend.model.entity.loyalty_point.LoyaltyPointPayment;
import com.hw.hwjobbackend.model.entity.user.User;
import com.hw.hwjobbackend.model.enums.PaymentMethodEnum;
import com.hw.hwjobbackend.model.enums.PaymentStatusEnum;
import com.hw.hwjobbackend.repository.loyalty_point.LoyaltyPointPaymentRepository;
import com.hw.hwjobbackend.repository.user.UserRepository;
import com.hw.hwjobbackend.service.blockchain.BlockchainService;
import com.hw.hwjobbackend.service.mapper.loyalty_point.LoyaltyPointPaymentMapper;
import com.hw.hwjobbackend.service.payment.MomoService;
import com.hw.hwjobbackend.service.payment.VnPayService;
import com.hw.hwjobbackend.util.SecurityUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LoyaltyPointService {

    UserRepository userRepository;
    LoyaltyPointPaymentRepository loyaltyPointPaymentRepository;
    LoyaltyPointPaymentMapper loyaltyPointPaymentMapper;
    BlockchainService blockchainService;
    MomoService momoService;
    VnPayService vnPayService;

    public LoyaltyPointResponse getUserPoints() throws Exception {
        String userId = SecurityUtils.getCurrentUserId();
        // 1. Lấy user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        // Lấy địa chỉ ví
        String walletAddress = user.getWalletAddress();

        return blockchainService.getPointOfUser(walletAddress);
    }

    @Transactional
    public LoyaltyPointTopUpResponse createTopUp(
            LoyaltyPointTopUpRequest req,
            HttpServletRequest httpRequest
    ) {
        String userId = SecurityUtils.getCurrentUserId();

        LoyaltyPointPayment payment =
                loyaltyPointPaymentMapper.toEntity(req);
        payment.setUserId(userId);

        loyaltyPointPaymentRepository.save(payment);

        LoyaltyPointPaymentGatewayRequest gatewayReq =
                loyaltyPointPaymentMapper.toGatewayRequest(payment);

        String payUrl;

        if (payment.getPaymentMethod() == PaymentMethodEnum.MOMO) {
            payUrl = momoService.createPaymentUrl(gatewayReq, httpRequest);
        } else if(payment.getPaymentMethod() == PaymentMethodEnum.VNPAY) {
            payUrl = vnPayService.createPaymentUrl(gatewayReq, httpRequest);
        }else {
            throw new IllegalArgumentException("Unsupported payment method");
        }

        return LoyaltyPointTopUpResponse.builder()
                .topUpId(payment.getId())
                .payUrl(payUrl)
                .build();
    }
    @Transactional
    public void handleMomoIpn(Map<String, String> payload) throws Exception {

        momoService.verifySignature(payload);

        processIpnResult(
                payload.get("orderId"),
                "0".equals(payload.get("resultCode")),
                payload.get("transId"),
                payload
        );
    }
    @Transactional
    public void processVnPayIpn(HttpServletRequest request) throws Exception {

        Map<String, String> params = new HashMap<>();
        request.getParameterMap()
                .forEach((k, v) -> params.put(k, v[0]));

        vnPayService.verifySignature(new HashMap<>(params));

        processIpnResult(
                params.get("vnp_TxnRef"),
                "00".equals(params.get("vnp_ResponseCode")),
                params.get("vnp_TransactionNo"),
                params
        );
    }


//    @Transactional
    public void processIpnResult(
            String paymentId,
            boolean success,
            String gatewayTransactionId,
            Map<String, String> rawPayload
    ) throws Exception {

        LoyaltyPointPayment payment =
                loyaltyPointPaymentRepository.findById(paymentId)
                        .orElseThrow(() -> new RuntimeException("Payment not found"));

        // Chống IPN gửi lại
        if (payment.getStatus() != PaymentStatusEnum.PENDING) {
            return;
        }

        // Lưu payload audit
        payment.setRawIpnPayload(
                new ObjectMapper().writeValueAsString(rawPayload)
        );

        if (success) {
            payment.setStatus(PaymentStatusEnum.PAID);
            payment.setGatewayTransactionId(gatewayTransactionId);

            String txHash = blockchainService.mintPointToUser(
                    payment.getUserId(),
                    payment.getPoints()
            );
            payment.setBlockchainTxHash(txHash);
        } else {
            payment.setStatus(PaymentStatusEnum.FAILED);
        }

        loyaltyPointPaymentRepository.save(payment);
    }

}
