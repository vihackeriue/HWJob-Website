package com.hw.hwjobbackend.service.shared.reputation;

import com.hw.hwjobbackend.model.dto.response.reputation.ReputationResponse;

import java.math.BigInteger;

public interface ReputationService {
    ReputationResponse getUserReputation();

    void deductReputation(String userId, BigInteger penalty);
}
