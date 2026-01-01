package com.hw.hwjobbackend.service.blockchain;

import com.hw.hwjobbackend.exception.AppException;
import com.hw.hwjobbackend.exception.ErrorCode;
import com.hw.hwjobbackend.model.dto.response.wallet.WalletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WalletService {

    BlockchainService blockchainService;

    public WalletResponse createWallet() {
        try {
            ECKeyPair keyPair = Keys.createEcKeyPair();

            String privateKey = keyPair.getPrivateKey().toString(16);
            String address = "0x" + Keys.getAddress(keyPair.getPublicKey());
            blockchainService.registerWalletForUser(address);
            return new WalletResponse(address, privateKey);
        } catch (Exception e) {
            throw new AppException(ErrorCode.FAIL_PROCESS_BLOCKCHAIN);
        }
    }
}
