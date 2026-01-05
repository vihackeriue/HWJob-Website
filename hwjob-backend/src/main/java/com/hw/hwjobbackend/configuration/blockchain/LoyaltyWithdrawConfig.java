package com.hw.hwjobbackend.configuration.blockchain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.math.BigInteger;

@Configuration
@ConfigurationProperties(prefix = "loyalty.withdraw")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoyaltyWithdrawConfig {
    BigDecimal pointPerEth;      // 96,000,000 point = 1 ETH
    BigInteger minWithdrawPoint; // tối thiểu được rút
    BigInteger withdrawFeePoint; // phí rút
}
