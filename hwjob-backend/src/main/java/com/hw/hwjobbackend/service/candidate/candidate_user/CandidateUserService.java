package com.hw.hwjobbackend.service.candidate.candidate_user;

import com.hw.hwjobbackend.model.dto.request.user.CandidateUpdateRequest;
import com.hw.hwjobbackend.model.dto.response.user.CandidateResponse;

public interface CandidateUserService {
    CandidateResponse updateCandidateInfo(CandidateUpdateRequest request);
}
