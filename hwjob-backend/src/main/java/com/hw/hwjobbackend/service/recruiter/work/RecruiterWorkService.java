package com.hw.hwjobbackend.service.recruiter.work;

import com.hw.hwjobbackend.model.dto.request.work.WorkCreateRequest;

public interface RecruiterWorkService {
    void assignWork(WorkCreateRequest request);

    void deleteByJobPostIdAndCandidateId(String jobPostId, String candidateId);
}
