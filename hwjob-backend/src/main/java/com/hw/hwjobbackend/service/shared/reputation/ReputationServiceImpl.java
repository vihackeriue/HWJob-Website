package com.hw.hwjobbackend.service.shared.reputation;

import com.hw.hwjobbackend.exception.AppException;
import com.hw.hwjobbackend.exception.ErrorCode;
import com.hw.hwjobbackend.model.dto.response.loyalty_point.LoyaltyPointResponse;
import com.hw.hwjobbackend.model.dto.response.reputation.ReputationResponse;
import com.hw.hwjobbackend.model.entity.user.User;
import com.hw.hwjobbackend.repository.user.UserRepository;
import com.hw.hwjobbackend.service.blockchain.BlockchainService;
import com.hw.hwjobbackend.util.SecurityUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReputationServiceImpl implements ReputationService {

    UserRepository userRepository;
    BlockchainService blockchainService;

    @Override
    public ReputationResponse getUserReputation() {
        String userId = SecurityUtils.getCurrentUserId();
        // 1. Lấy user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        // Lấy địa chỉ ví
        String walletAddress = user.getWalletAddress();

        try {
            return blockchainService.getReputationOfUser(walletAddress);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new AppException(ErrorCode.FAIL_PROCESS_BLOCKCHAIN);
        }
    }

    public void deductReputation(String userId, BigInteger penalty){
        // 1. Lấy user từ DB
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        String walletAddress = user.getWalletAddress();
        try {
            blockchainService.penalizeReputationForUser(walletAddress, penalty);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new AppException(ErrorCode.FAIL_PROCESS_BLOCKCHAIN);
        }
    }


}
