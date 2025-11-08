package com.hw.hwjobbackend.mapper;

import com.hw.hwjobbackend.dto.request.user.RecruiterUpdateRequest;
import com.hw.hwjobbackend.dto.response.user.RecruiterResponse;
import com.hw.hwjobbackend.entity.Recruiter;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {ProvinceMapper.class, WardMapper.class})
public interface RecruiterMapper {

    @Mapping(source = "province", target = "province", qualifiedByName = "toProvinceResponseWithoutWards")
    RecruiterResponse toRecruiterResponse(Recruiter recruiter);

    @Mapping(target = "password", ignore = true)
    void updateRecruiter(@MappingTarget Recruiter recruiter, RecruiterUpdateRequest request);
}
