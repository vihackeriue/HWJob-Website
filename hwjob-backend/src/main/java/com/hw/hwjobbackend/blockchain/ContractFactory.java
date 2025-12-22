package com.hw.hwjobbackend.blockchain;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.tx.gas.DefaultGasProvider;

@Component
@RequiredArgsConstructor
public class ContractFactory {

    private final Web3j web3j;

    @Value("${blockchain.contract-address}")
    private String contractAddress;

    public HWJob loadWithCredentials(Credentials credentials) {
        return HWJob.load(
                contractAddress,
                web3j,
                credentials,
                new CustomGasProvider()
        );
    }
}
