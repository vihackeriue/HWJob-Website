package com.hw.hwjobbackend.configuration.blockchain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.crypto.Credentials;
@Configuration
public class WalletConfig {
    @Value("${blockchain.private-key}")
    private String privateKey;

    @Bean
    public Credentials ownerCredentials() {
        return Credentials.create(privateKey);
    }
}
