package com.hw.hwjobbackend.service.admin.job_type;

import com.hw.hwjobbackend.model.dto.request.job_type.JobTypeRequest;
import com.hw.hwjobbackend.model.dto.response.job_type.JobTypeResponse;

public interface AdminJobTypeService {

    JobTypeResponse createJobType(JobTypeRequest request);

    JobTypeResponse updateJobType(Long id, JobTypeRequest request);

    void deleteJobType(Long id);
}
