package com.hw.hwjobbackend.service.job_type;

import com.hw.hwjobbackend.model.dto.request.job_type.JobTypeRequest;
import com.hw.hwjobbackend.model.dto.response.job_type.JobTypeResponse;
import com.hw.hwjobbackend.model.entity.job_type.JobType;
import org.springframework.data.domain.Page;

public interface JobTypeService {

    JobTypeResponse createJobType(JobTypeRequest request);

    JobTypeResponse getJobTypeById(Long id);

    Page<JobTypeResponse> getAllJobTypes(int page, int size);

    JobTypeResponse updateJobType(Long id, JobTypeRequest request);

    void deleteJobType(Long id);

    JobType getJobTypeEntityById(Long id);
}
