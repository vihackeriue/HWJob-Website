package com.hw.hwjobbackend.service;

import com.hw.hwjobbackend.dto.request.CandidateUpdateRequest;
import com.hw.hwjobbackend.dto.response.CandidateResponse;

public interface CandidateService {
    CandidateResponse updateCandidateInfo(String candidateId, CandidateUpdateRequest request);
}
