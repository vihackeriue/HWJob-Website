package com.hw.hwjobbackend.service.candidate.application;

import com.hw.hwjobbackend.model.dto.request.application.ApplicationRequest;
import com.hw.hwjobbackend.model.dto.response.application.ApplicationResponse;

public interface CandidateApplicationService {
    ApplicationResponse applyJob(ApplicationRequest request);

    boolean isCandidateApplied(String candidateId, String jobPostId);
}
