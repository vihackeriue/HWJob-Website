package com.hw.hwjobbackend.service.shared.loyalty_point;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hw.hwjobbackend.exception.AppException;
import com.hw.hwjobbackend.exception.ErrorCode;
import com.hw.hwjobbackend.model.dto.request.loyalty_point.LoyaltyPointPaymentGatewayRequest;
import com.hw.hwjobbackend.model.dto.request.loyalty_point.LoyaltyPointTopUpRequest;
import com.hw.hwjobbackend.model.dto.response.loyalty_point.LoyaltyPointPaymentHistoryResponse;
import com.hw.hwjobbackend.model.dto.response.loyalty_point.LoyaltyPointResponse;
import com.hw.hwjobbackend.model.dto.response.loyalty_point.LoyaltyPointTopUpResponse;
import com.hw.hwjobbackend.model.entity.loyalty_point.LoyaltyPointPayment;
import com.hw.hwjobbackend.model.entity.user.User;
import com.hw.hwjobbackend.model.entity.works.Work;
import com.hw.hwjobbackend.model.enums.PaymentMethodEnum;
import com.hw.hwjobbackend.model.enums.PaymentStatusEnum;
import com.hw.hwjobbackend.repository.loyalty_point.LoyaltyPointPaymentRepository;
import com.hw.hwjobbackend.repository.user.UserRepository;
import com.hw.hwjobbackend.repository.work.WorkRepository;
import com.hw.hwjobbackend.service.blockchain.BlockchainService;
import com.hw.hwjobbackend.service.mapper.loyalty_point.LoyaltyPointPaymentMapper;
import com.hw.hwjobbackend.service.payment.MomoService;
import com.hw.hwjobbackend.service.payment.VnPayService;
import com.hw.hwjobbackend.util.PaginationUtils;
import com.hw.hwjobbackend.util.SecurityUtils;
import jakarta.servlet.http.HttpServletRequest;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LoyaltyPointServiceImpl implements LoyaltyPointService {

    UserRepository userRepository;
    LoyaltyPointPaymentRepository loyaltyPointPaymentRepository;
    LoyaltyPointPaymentMapper loyaltyPointPaymentMapper;
    BlockchainService blockchainService;
    MomoService momoService;
    VnPayService vnPayService;
    WorkRepository workRepository;


    @Override
    public LoyaltyPointResponse getUserPoints() {
        String userId = SecurityUtils.getCurrentUserId();
        // 1. Lấy user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        // Lấy địa chỉ ví
        String walletAddress = user.getWalletAddress();

        try {
            return blockchainService.getPointOfUser(walletAddress);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new AppException(ErrorCode.FAIL_PROCESS_BLOCKCHAIN);
        }
    }
    @Override
    public LoyaltyPointResponse getUserLockedPoints() {
        String userId = SecurityUtils.getCurrentUserId();
        // 1. Lấy user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        // Lấy địa chỉ ví
        String walletAddress = user.getWalletAddress();

        try {
            return blockchainService.getLockedBalance(walletAddress);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new AppException(ErrorCode.FAIL_PROCESS_BLOCKCHAIN);
        }
    }


    @Override
    public List<LoyaltyPointPaymentHistoryResponse> getUserPaymentHistory() {
        String userId = SecurityUtils.getCurrentUserId();

        List<LoyaltyPointPayment> payments =
                loyaltyPointPaymentRepository.findByUserIdAndStatusOrderByCreatedAtDesc(
                        userId,
                        PaymentStatusEnum.PAID
                );

        return loyaltyPointPaymentMapper.toHistoryResponse(payments);
    }

    public Page<LoyaltyPointPaymentHistoryResponse> getUserPaymentHistory(Integer page, Integer size){

        String userId = SecurityUtils.getCurrentUserId();
        Pageable pageable = PaginationUtils.buildPageable(page, size);
        Page<LoyaltyPointPayment> payments = loyaltyPointPaymentRepository.findByUserIdAndStatusOrderByCreatedAtDesc(
                userId,
                PaymentStatusEnum.PAID,
                pageable
        );
        return payments.map(loyaltyPointPaymentMapper::toHistoryResponse);
    }
    @Override
    public void refundPointToRecruiter(String jobPostId, String candidateId) {
        String recruiterId = SecurityUtils.getCurrentUserId();
        User user = userRepository.findById(recruiterId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        String userWalletAddress = user.getWalletAddress();

        Work work = workRepository.findByJobPostIdAndCandidateIdAndRecruiterId(jobPostId, candidateId, recruiterId).orElseThrow(() -> new AppException(ErrorCode.UNAUTHORIZED));

        try {
            String tx = blockchainService.refundPointToRecruiter(userWalletAddress,work.getAgreedSalary());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new AppException(ErrorCode.FAIL_PROCESS_BLOCKCHAIN);
        }
    }

    public void refundPointToRecruiterAndDeductReputation(String recruiterUserId, String freelancerUserId, BigInteger amount) {
        User recruiter = userRepository.findById(recruiterUserId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        User freelancer = userRepository.findById(freelancerUserId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        String recruiterWallet = recruiter.getWalletAddress();
        String freelancerWallet = freelancer.getWalletAddress();

        try {
            blockchainService.failJobForUser(recruiterWallet, freelancerWallet, amount);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
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
//    @Transactional
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


    @Transactional
    public void processIpnResult(
            String paymentId,
            boolean success,
            String gatewayTransactionId,
            Map<String, String> rawPayload
    ) {

        LoyaltyPointPayment payment =
                loyaltyPointPaymentRepository.findById(paymentId)
                        .orElseThrow(() -> new RuntimeException("Payment not found"));

        // Chống IPN gửi lại
        if (payment.getStatus() != PaymentStatusEnum.PENDING) {
            return;
        }

        // Lưu payload audit
        try {
            payment.setRawIpnPayload(
                    new ObjectMapper().writeValueAsString(rawPayload)
            );
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        if (success) {
            payment.setStatus(PaymentStatusEnum.PAID);
            payment.setGatewayTransactionId(gatewayTransactionId);

            String txHash = null;
            try {
                txHash = blockchainService.mintPointToUser(
                        payment.getUserId(),
                        payment.getPoints()
                );
            } catch (Exception e) {
                log.error("Mint blockchain failed", e);
            }
            payment.setBlockchainTxHash(txHash);
        } else {
            payment.setStatus(PaymentStatusEnum.FAILED);
        }

        loyaltyPointPaymentRepository.save(payment);
    }

}
