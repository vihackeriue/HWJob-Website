package com.hw.hwjobbackend.service.user;

import com.hw.hwjobbackend.dto.request.user.CandidateUpdateRequest;
import com.hw.hwjobbackend.dto.response.user.CandidateResponse;
import com.hw.hwjobbackend.entity.Candidate;

public interface CandidateService {
    CandidateResponse updateCandidateInfo(CandidateUpdateRequest request);

    Candidate getCandidateEntityByName(String name);
}
