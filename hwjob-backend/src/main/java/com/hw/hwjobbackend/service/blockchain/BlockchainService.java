package com.hw.hwjobbackend.service.blockchain;

import com.hw.hwjobbackend.model.dto.response.loyalty_point.LoyaltyPointResponse;
import com.hw.hwjobbackend.model.dto.response.reputation.ReputationResponse;

import java.math.BigDecimal;
import java.math.BigInteger;

public interface BlockchainService {
    String registerWalletForUser(String walletAddress) throws Exception;

    String lockForJob(String recruiterId, BigInteger amount) throws Exception;

    LoyaltyPointResponse getPointOfUser(String walletAddress);

    String mintPointToUser(String userId, BigInteger amount) throws Exception;
    String burnPoint(String walletAddress, BigInteger amount);

    String completeJobForUser(String recruiterUserId,
                              String freelancerUserId,
                              BigInteger amount,
                              boolean goodPerformance) throws Exception ;
    ReputationResponse getReputationOfUser(String userId) throws Exception;

    LoyaltyPointResponse getLockedBalance(String walletAddress)throws Exception;

    String refundPointToRecruiter(String walletAddress, BigInteger amount
    ) throws Exception;

    String failJobForUser(String recruiterUserId,
                          String freelancerUserId,
                          BigInteger amount) throws Exception;

    String penalizeReputationForUser(String walletAddress, BigInteger penalty
    ) throws Exception;

    String sendEth(String toWallet, BigDecimal ethAmount);

}
