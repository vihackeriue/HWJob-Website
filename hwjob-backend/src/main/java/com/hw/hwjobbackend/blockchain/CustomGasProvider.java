package com.hw.hwjobbackend.blockchain;

import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.tx.gas.ContractGasProvider;

import java.math.BigInteger;

public class CustomGasProvider implements ContractGasProvider {

    private static final BigInteger GAS_PRICE =
            BigInteger.valueOf(10_000_000_000L); // 10 Gwei

    private static final BigInteger GAS_LIMIT =
            BigInteger.valueOf(1_000_000L); // 1 triệu

    @Override
    public BigInteger getGasPrice() {
        return GAS_PRICE;
    }



    // ⚠️ METHOD BẮT BUỘC với web3j version của bạn
    @Override
    public BigInteger getGasLimit(Transaction transaction) {
        return GAS_LIMIT;
    }

    @Override
    public BigInteger getGasLimit() {
        return GAS_LIMIT;
    }
}
