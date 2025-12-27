package com.hw.hwjobbackend.service.recruiter.work;

import com.hw.hwjobbackend.model.dto.request.work.UpdateWorkStatusRequest;
import com.hw.hwjobbackend.model.dto.request.work.WorkCreateRequest;
import com.hw.hwjobbackend.model.dto.response.work.WorkCandidateResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface RecruiterWorkService {

    Page<WorkCandidateResponse> getCandidateWork(int page, int size, String jobPostId);

    List<WorkCandidateResponse> getAllCandidateWork(String jobPostId);
    void assignWork(WorkCreateRequest request);

    void updateCandidateWorkStatus(
            String jobPostId,
            String candidateId,
            UpdateWorkStatusRequest request
    );
    void deleteByJobPostIdAndCandidateId(String jobPostId, String candidateId);


}
