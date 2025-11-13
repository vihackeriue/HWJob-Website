package com.hw.hwjobbackend.service.user;

import com.hw.hwjobbackend.dto.request.user.CandidateUpdateRequest;
import com.hw.hwjobbackend.dto.response.user.CandidateResponse;

public interface CandidateService {
    CandidateResponse updateCandidateInfo(CandidateUpdateRequest request);
}
