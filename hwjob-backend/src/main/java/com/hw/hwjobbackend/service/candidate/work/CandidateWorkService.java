package com.hw.hwjobbackend.service.candidate.work;

import com.hw.hwjobbackend.model.dto.request.work.UpdateWorkStatusRequest;
import com.hw.hwjobbackend.model.dto.response.work.WorkOverviewResponse;
import com.hw.hwjobbackend.model.dto.response.work.WorkResponse;
import org.springframework.data.domain.Page;

import java.math.BigInteger;
import java.util.List;

public interface CandidateWorkService {
    void decideWork(String jobPostId, String candidateId, boolean accepted);
    WorkOverviewResponse getWorkOverviewForCandidate(String jobPostId);

    Page<WorkResponse> getAllWorksOfCandidate(int page, int size);
    List<WorkResponse> getAllWorksOfCandidate();

    void updateWorkStatus(String jobPostId, UpdateWorkStatusRequest request);

}
