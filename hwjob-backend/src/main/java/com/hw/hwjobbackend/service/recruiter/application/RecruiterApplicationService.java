package com.hw.hwjobbackend.service.recruiter.application;

import com.hw.hwjobbackend.model.dto.request.application.ApplicationStatusRequest;
import com.hw.hwjobbackend.model.dto.response.application.ApplicationAllCandidateResponse;
import com.hw.hwjobbackend.model.dto.response.application.ApplicationCandidateResponse;
import com.hw.hwjobbackend.model.enums.ApplicationStatusEnum;
import org.springframework.data.domain.Page;

import java.util.List;

public interface RecruiterApplicationService {

    Page<ApplicationCandidateResponse> getCandidateApplications(int page, int size, String jobPostId);

    List<ApplicationCandidateResponse> getCandidateApplications(String jobPostId);

    void updateCandidateApplicationStatus(String jobPostId, String candidateId, ApplicationStatusRequest request);

    Page<ApplicationAllCandidateResponse> getAllCandidateApplicationsOfRecruiter(int page, int size);

    List<ApplicationAllCandidateResponse> getAllCandidateApplicationsOfRecruiter();
}
