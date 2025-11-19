package com.hw.hwjobbackend.mapper.job_type;

import com.hw.hwjobbackend.dto.request.job_type.JobTypeRequest;
import com.hw.hwjobbackend.dto.response.job_type.JobTypeResponse;
import com.hw.hwjobbackend.entity.JobType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface JobTypeMapper {
    JobType toJobType(JobTypeRequest request);

    JobTypeResponse toJobTypeResponse(JobType jobType);

}
