package com.hw.hwjobbackend.service.user;

import com.hw.hwjobbackend.model.dto.request.user.CandidateUpdateRequest;
import com.hw.hwjobbackend.model.dto.response.user.CandidateResponse;
import com.hw.hwjobbackend.model.entity.user.Candidate;

public interface CandidateService {
    CandidateResponse updateCandidateInfo(CandidateUpdateRequest request);

    Candidate getCandidateEntityByName(String name);

    Candidate getCandidateEntityById(String id);
}
