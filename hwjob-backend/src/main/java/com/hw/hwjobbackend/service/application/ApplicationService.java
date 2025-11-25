package com.hw.hwjobbackend.service.application;

import com.hw.hwjobbackend.model.dto.request.application.ApplicationRequest;
import com.hw.hwjobbackend.model.dto.response.application.ApplicationResponse;


public interface ApplicationService {

    ApplicationResponse applyJob(ApplicationRequest request);

    boolean isCandidateApplied(String candidateId, String jobPostId);

}
