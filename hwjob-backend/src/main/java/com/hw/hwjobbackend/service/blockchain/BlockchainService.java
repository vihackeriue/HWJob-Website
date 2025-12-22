package com.hw.hwjobbackend.service.blockchain;


import com.hw.hwjobbackend.blockchain.ContractFactory;
import com.hw.hwjobbackend.blockchain.HWJob;
import com.hw.hwjobbackend.exception.AppException;
import com.hw.hwjobbackend.exception.ErrorCode;
import com.hw.hwjobbackend.model.dto.response.loyalty_point.LoyaltyPointResponse;
import com.hw.hwjobbackend.model.entity.user.User;
import com.hw.hwjobbackend.repository.user.UserRepository;
import com.hw.hwjobbackend.util.SecurityUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;

import java.math.BigInteger;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BlockchainService {
    ContractFactory contractFactory;
    UserRepository userRepository;

    Credentials ownerCredentials;

    public String registerWalletForUser(String walletAddress) throws Exception {
        // Kiểm tra địa chỉ ví hợp lệ
        if (walletAddress == null || walletAddress.isEmpty()) {
            throw new RuntimeException("Địa chỉ ví không hợp lệ");
        }
        // Load contract bằng quyền OWNER (chỉ backend)
        HWJob contract = contractFactory.loadWithCredentials(ownerCredentials);

        // Gọi registerUser trên smart contract
        return contract
                .registerUser(walletAddress)
                .send()
                .getTransactionHash();
    }
    public String lockForJob(String recruiterId, BigInteger amount) throws Exception {

        // 1. Lấy recruiter
        User recruiter = userRepository.findById(recruiterId)
                .orElseThrow(() -> new RuntimeException("Recruiter không tồn tại"));

        if (recruiter.getWalletAddress() == null) {
            throw new RuntimeException("Recruiter chưa có ví blockchain");
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
    public String completeJobForUser(String recruiterUserId,
                                     String freelancerUserId,
                                     BigInteger amount,
                                     boolean goodPerformance) throws Exception {

        // 1. Lấy recruiter và freelancer từ DB
        User recruiter = userRepository.findById(recruiterUserId)
                .orElseThrow(() -> new RuntimeException("Recruiter không tồn tại"));

        User freelancer = userRepository.findById(freelancerUserId)
                .orElseThrow(() -> new RuntimeException("Freelancer không tồn tại"));

        // 2. Lấy địa chỉ ví blockchain
        String recruiterWallet = recruiter.getWalletAddress();
        String freelancerWallet = freelancer.getWalletAddress();

        if (recruiterWallet == null) {
            throw new RuntimeException("Recruiter chưa có ví blockchain");
        }
        if (freelancerWallet == null) {
            throw new RuntimeException("Freelancer chưa có ví blockchain");
        }

        // 3. Load contract bằng quyền OWNER
        HWJob contract = contractFactory.loadWithCredentials(ownerCredentials);

        // 4. Gọi completeJob
        return contract
                .completeJob(recruiterWallet, freelancerWallet, amount, goodPerformance)
                .send()
                .getTransactionHash();
    }

    public String failJobForUser(String recruiterUserId,
                                 String freelancerUserId,
                                 BigInteger amount) throws Exception {

        // 1. Lấy recruiter và freelancer từ DB
        User recruiter = userRepository.findById(recruiterUserId)
                .orElseThrow(() -> new RuntimeException("Recruiter không tồn tại"));

        User freelancer = userRepository.findById(freelancerUserId)
                .orElseThrow(() -> new RuntimeException("Freelancer không tồn tại"));

        // 2. Lấy địa chỉ ví blockchain
        String recruiterWallet = recruiter.getWalletAddress();
        String freelancerWallet = freelancer.getWalletAddress();

        if (recruiterWallet == null) {
            throw new RuntimeException("Recruiter chưa có ví blockchain");
        }
        if (freelancerWallet == null) {
            throw new RuntimeException("Freelancer chưa có ví blockchain");
        }

        // 3. Load contract bằng quyền OWNER
        HWJob contract = contractFactory.loadWithCredentials(ownerCredentials);

        // 4. Gọi failJob
        return contract
                .failJob(recruiterWallet, freelancerWallet, amount)
                .send()
                .getTransactionHash();
    }

    public String mintPointToUser(String userId, BigInteger amount) throws Exception {

        // 1. Lấy user từ DB
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User không tồn tại"));

        // 2. Lấy địa chỉ ví blockchain của user
        String userWalletAddress = user.getWalletAddress();
        if (userWalletAddress == null) {
            throw new RuntimeException("User chưa có ví blockchain");
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

    public BigInteger getReputationOfUser(String userId) throws Exception {
        // 1. Lấy user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User không tồn tại"));
        // 2. Lấy địa chỉ ví
        String walletAddress = user.getWalletAddress();
        if (walletAddress == null) {
            throw new RuntimeException("User chưa có ví blockchain");
        }
        // 3. Load contract (chỉ đọc, dùng ownerCredentials hoặc credentials bất kỳ)
        HWJob contract = contractFactory.loadWithCredentials(ownerCredentials);
        // 4. Gọi getReputation (CALL, không SEND)
        return contract
                .getReputation(walletAddress)
                .send();
    }
//    public BigInteger getLockedBalance(String recruiterUserId) throws Exception {
//        User recruiter = userRepository.findById(recruiterUserId)
//                .orElseThrow(() -> new RuntimeException("Recruiter không tồn tại"));
//
//        String recruiterWallet = recruiter.getWalletAddress();
//        if (recruiterWallet == null) {
//            throw new RuntimeException("Recruiter chưa có ví blockchain");
//        }
//
//        HWJob contract = contractFactory.loadWithCredentials(ownerCredentials);
//
//        return contract.getLockedBalance(recruiterWallet).send();
//    }

}