package com.hw.hwjobbackend.service.shared.job_type;

import com.hw.hwjobbackend.model.dto.response.job_type.JobTypeResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface JobTypeService {

    JobTypeResponse getJobTypeById(Long id);

    Page<JobTypeResponse> getJobTypes(int page, int size);

    List<JobTypeResponse> getAllJobTypes();

}
