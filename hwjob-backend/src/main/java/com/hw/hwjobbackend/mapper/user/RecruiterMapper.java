package com.hw.hwjobbackend.mapper.user;

import com.hw.hwjobbackend.model.dto.request.user.RecruiterUpdateRequest;
import com.hw.hwjobbackend.model.dto.response.user.RecruiterProfileResponse;
import com.hw.hwjobbackend.model.dto.response.user.RecruiterResponse;
import com.hw.hwjobbackend.model.entity.user.Recruiter;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface RecruiterMapper {

    @Mapping(source = "province.name", target = "region")
    RecruiterResponse toRecruiterResponse(Recruiter recruiter);

    @Mapping(target = "password", ignore = true)
    void updateRecruiter(@MappingTarget Recruiter recruiter, RecruiterUpdateRequest request);

    @Mapping(target = "region", ignore = true)
    RecruiterProfileResponse toRecruiterProfileResponse(Recruiter recruiter);

}
