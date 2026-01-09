package com.hw.hwjobbackend.service.shared.reputation;

import com.hw.hwjobbackend.model.dto.response.reputation.ReputationResponse;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

public interface ReputationService {
    ReputationResponse getUserReputation();

    void deductReputation(String userId, BigInteger penalty);
    ReputationResponse getUserReputation(String userId);

    Map<String, BigInteger> getReputationByUserIds(List<String> userIds);
}
