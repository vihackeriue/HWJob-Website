package com.hw.hwjobbackend.service.blockchain;


import com.hw.hwjobbackend.blockchain.ContractFactory;
import com.hw.hwjobbackend.blockchain.HWJob;
import com.hw.hwjobbackend.exception.AppException;
import com.hw.hwjobbackend.exception.ErrorCode;
import com.hw.hwjobbackend.model.dto.response.loyalty_point.LoyaltyPointResponse;
import com.hw.hwjobbackend.model.dto.response.reputation.ReputationResponse;
import com.hw.hwjobbackend.model.entity.user.User;
import com.hw.hwjobbackend.repository.user.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;

import java.math.BigInteger;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BlockchainServiceImpl implements BlockchainService {
    ContractFactory contractFactory;
    UserRepository userRepository;
    Credentials ownerCredentials;

    @Override
    public String registerWalletForUser(String walletAddress) throws Exception {
        // Kiểm tra địa chỉ ví hợp lệ
        if (walletAddress == null || walletAddress.isEmpty()) {
            throw new AppException(ErrorCode.USER_WALLET_NOT_EXISTED);
        }
        // Load contract bằng quyền OWNER (chỉ backend)
        HWJob contract = contractFactory.loadWithCredentials(ownerCredentials);

        // Gọi registerUser trên smart contract
        return contract
                .registerUser(walletAddress)
                .send()
                .getTransactionHash();
    }
    @Override
    public String lockForJob(String recruiterId, BigInteger amount) throws Exception {

        // 1. Lấy recruiter
        User recruiter = userRepository.findById(recruiterId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        if (recruiter.getWalletAddress() == null) {
            throw new AppException(ErrorCode.USER_WALLET_NOT_EXISTED);
        }

//        // 2. OWNER credentials (backend ký + trả gas)
//        Credentials ownerCredentials =
//                Credentials.create(ownerPrivateKey);

        // 3. Load contract với quyền OWNER
        HWJob contract =
                contractFactory.loadWithCredentials(ownerCredentials);

        // 4. Gọi hàm lock cho recruiter
        return contract
                .lockForJobForRecruiter(
                        recruiter.getWalletAddress(),
                        amount
                )
                .send()
                .getTransactionHash();
    }
    @Override
    public String completeJobForUser(String recruiterUserId,
                                     String freelancerUserId,
                                     BigInteger amount,
                                     boolean goodPerformance) throws Exception {

        // 1. Lấy recruiter và freelancer từ DB
        User recruiter = userRepository.findById(recruiterUserId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        User freelancer = userRepository.findById(freelancerUserId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        // 2. Lấy địa chỉ ví blockchain
        String recruiterWallet = recruiter.getWalletAddress();
        String freelancerWallet = freelancer.getWalletAddress();

        if (recruiterWallet == null) {
            throw new AppException(ErrorCode.USER_WALLET_NOT_EXISTED);
        }
        if (freelancerWallet == null) {
            throw new AppException(ErrorCode.USER_WALLET_NOT_EXISTED);
        }

        // 3. Load contract bằng quyền OWNER
        HWJob contract = contractFactory.loadWithCredentials(ownerCredentials);

        // 4. Gọi completeJob
        return contract
                .completeJob(recruiterWallet, freelancerWallet, amount, goodPerformance)
                .send()
                .getTransactionHash();
    }

    public String failJobForUser(String recruiterWallet,
                                 String freelancerWallet,
                                 BigInteger amount) throws Exception {

        if (recruiterWallet == null) {
            throw new AppException(ErrorCode.USER_WALLET_NOT_EXISTED);
        }
        if (freelancerWallet == null) {
            throw new AppException(ErrorCode.USER_WALLET_NOT_EXISTED);
        }

        // 3. Load contract bằng quyền OWNER
        HWJob contract = contractFactory.loadWithCredentials(ownerCredentials);

        // 4. Gọi failJob
        return contract
                .failJob(recruiterWallet, freelancerWallet, amount)
                .send()
                .getTransactionHash();
    }

    public String refundPointToRecruiter(String walletAddress, BigInteger amount
    ) throws Exception {
        if (walletAddress == null) {
            throw new AppException(ErrorCode.USER_WALLET_NOT_EXISTED);
        }

        // Validate amount
        if (amount == null || amount.compareTo(BigInteger.ZERO) <= 0) {
            throw new AppException(ErrorCode.INVALID_AMOUNT);
        }

        // 4. Load contract bằng quyền OWNER
        HWJob contract = contractFactory.loadWithCredentials(ownerCredentials);

        // 5. Gọi refundToRecruiter
        return contract
                .refundToRecruiter(walletAddress, amount)
                .send()
                .getTransactionHash();
    }

    @Override
    public String mintPointToUser(String userId, BigInteger amount) throws Exception {

        // 1. Lấy user từ DB
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        // 2. Lấy địa chỉ ví blockchain của user
        String userWalletAddress = user.getWalletAddress();
        if (userWalletAddress == null) {
            throw new AppException(ErrorCode.USER_WALLET_NOT_EXISTED);
        }

//        // 3. Credentials BACKEND (OWNER)
//        Credentials ownerCredentials =
//                Credentials.create(ownerPrivateKey);

        // 4. Load contract bằng quyền OWNER
        HWJob contract =
                contractFactory.loadWithCredentials(ownerCredentials);

        // 5. Gọi mintPoint
        return contract
                .mintPoint(userWalletAddress, amount)
                .send()
                .getTransactionHash();
    }
    @Override
    public LoyaltyPointResponse getPointOfUser(String walletAddress) throws Exception {
        if (walletAddress == null) {
            throw new AppException(ErrorCode.USER_WALLET_NOT_EXISTED);
        }
        // Load contract (chỉ đọc, dùng ownerCredentials hoặc credentials bất kỳ)
        HWJob contract = contractFactory.loadWithCredentials(ownerCredentials);
        // Gọi balanceOf trên blockchain
        BigInteger balance = contract.balanceOf(walletAddress).send();
        return new LoyaltyPointResponse(balance);
    }
    @Override
    public ReputationResponse getReputationOfUser(String walletAddress) throws Exception {
        if (walletAddress == null) {
            throw new AppException(ErrorCode.USER_WALLET_NOT_EXISTED);
        }
        // 3. Load contract (chỉ đọc, dùng ownerCredentials hoặc credentials bất kỳ)
        HWJob contract = contractFactory.loadWithCredentials(ownerCredentials);
        // 4. Gọi getReputation (CALL, không SEND)
        BigInteger reputation = contract.getReputation(walletAddress).send();
        return new ReputationResponse(reputation);
    }
    @Override
    public LoyaltyPointResponse getLockedBalance(String walletAddress) throws Exception {

        if (walletAddress == null) {
            throw new AppException(ErrorCode.USER_WALLET_NOT_EXISTED);
        }

        HWJob contract = contractFactory.loadWithCredentials(ownerCredentials);

        BigInteger lockedBalance = contract.getLockedBalance(walletAddress).send();

        return new LoyaltyPointResponse(lockedBalance);
    }
    @Override
    public String penalizeReputationForUser(String walletAddress, BigInteger penalty
    ) throws Exception {

        if (walletAddress == null || walletAddress.isBlank()) {
            throw new AppException(ErrorCode.USER_WALLET_NOT_EXISTED);
        }
        // 3. Validate penalty
        if (penalty == null || penalty.compareTo(BigInteger.ZERO) <= 0) {
            throw new AppException(ErrorCode.INVALID_REPUTATION);
        }

        // 4. Load contract bằng OWNER
        HWJob contract = contractFactory.loadWithCredentials(ownerCredentials);

        // 5. Gọi smart contract
        return contract
                .penalizeReputation(walletAddress, penalty)
                .send()
                .getTransactionHash();
    }

}