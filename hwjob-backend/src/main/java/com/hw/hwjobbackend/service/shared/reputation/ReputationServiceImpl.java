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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Override
    public ReputationResponse getUserReputation(String userId) {
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
    @Override
    public Map<String, BigInteger> getReputationByUserIds(List<String> userIds) {

        Map<String, BigInteger> result = new HashMap<>();

        List<User> users = userRepository.findAllById(userIds);

        for (User user : users) {
            try {
                if (user.getWalletAddress() == null) {
                    result.put(user.getId(), BigInteger.ZERO);
                    continue;
                }

                BigInteger reputation = blockchainService
                        .getReputationOfUser(user.getWalletAddress())
                        .getReputation();

                result.put(user.getId(), reputation);
            } catch (Exception e) {
                log.error("Fail get reputation for user {}", user.getId(), e);
                result.put(user.getId(), BigInteger.ZERO);
            }
        }
        return result;
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
