package com.hw.hwjobbackend.service.job_type;

import com.hw.hwjobbackend.dto.request.job_type.JobTypeRequest;
import com.hw.hwjobbackend.dto.response.job_type.JobTypeResponse;

import java.util.List;

public interface JobTypeService {

    JobTypeResponse createJobType(JobTypeRequest request);

    JobTypeResponse getJobTypeById(Long id);

    List<JobTypeResponse> getAllJobTypes();

    JobTypeResponse updateJobType(Long id, JobTypeRequest request);

    void deleteJobType(Long id);
}
