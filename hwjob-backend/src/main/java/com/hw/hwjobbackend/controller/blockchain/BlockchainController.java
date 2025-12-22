package com.hw.hwjobbackend.controller.blockchain;

import com.hw.hwjobbackend.model.dto.request.blockchain.LockPointRequest;
import com.hw.hwjobbackend.model.dto.request.blockchain.MintPointRequest;
import com.hw.hwjobbackend.service.blockchain.BlockchainService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/public/blockchain")
public class BlockchainController {
    @Autowired
    BlockchainService blockchainService;

    @PostMapping("/mint-point")
    public ResponseEntity<?> mintPoint(
            @RequestBody MintPointRequest request
    ) throws Exception {

        String txHash = blockchainService.mintPointToUser(
                request.getUserId(),
                request.getAmount()
        );

        return ResponseEntity.ok(
                Map.of(
                        "message", "Mint point thành công",
                        "txHash", txHash
                )
        );
    }

    @GetMapping("/point/{userId}")
    public ResponseEntity<?> getUserPoint(@PathVariable String userId) throws Exception {



        return null;
    }
    @GetMapping("/reputation/{userId}")
    public ResponseEntity<?> getUserReputation(@PathVariable String userId) throws Exception {

        BigInteger point = blockchainService.getReputationOfUser(userId);

        return ResponseEntity.ok(
                Map.of(
                        "userId", userId,
                        "Reputation", point
                )
        );
    }


    @PostMapping("/lock")
    public ResponseEntity<?> lockForJob(
            @RequestBody LockPointRequest request
    ) throws Exception {

        blockchainService.lockForJob(
                request.getRecruiterId(),
                request.getAmount()
        );

        return ResponseEntity.ok(
                Map.of(
                        "status", "SUCCESS",
                        "message", "Recruiter đã lock điểm thành công"
                )
        );
    }
    // 1. API Nạp điểm thưởng thẳng vào ví User
    @PostMapping("/reward-user")
    public ResponseEntity<String> rewardUser(@RequestParam String userAddress, @RequestParam Long amount) {
        return null;
    }

    // 2. API Xem số dư hiện tại của một ví
    @GetMapping("/balance")
    public ResponseEntity<String> getBalance(@RequestParam String address) {
        return null;
    }
}
