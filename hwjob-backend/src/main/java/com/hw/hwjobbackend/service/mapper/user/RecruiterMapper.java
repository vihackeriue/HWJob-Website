package com.hw.hwjobbackend.service.mapper.user;

import com.hw.hwjobbackend.model.dto.request.user.RecruiterUpdateRequest;
import com.hw.hwjobbackend.model.dto.response.job_post.JobPostRecruiterProfileResponse;
import com.hw.hwjobbackend.model.dto.response.profile.RecruiterProfileResponse;
import com.hw.hwjobbackend.model.dto.response.user.RecruiterResponse;
import com.hw.hwjobbackend.model.entity.user.Recruiter;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface RecruiterMapper {

    @Mapping(source = "region.name", target = "region")
    RecruiterResponse toRecruiterResponse(Recruiter recruiter);

    @Mapping(target = "password", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateRecruiter(@MappingTarget Recruiter recruiter, RecruiterUpdateRequest request);

    @Mapping(source = "region.name", target = "region")
    RecruiterProfileResponse toRecruiterProfileResponse(Recruiter recruiter);

}
