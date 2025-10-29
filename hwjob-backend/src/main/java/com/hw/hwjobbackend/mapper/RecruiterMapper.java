package com.hw.hwjobbackend.mapper;

import com.hw.hwjobbackend.dto.request.RecruiterUpdateRequest;
import com.hw.hwjobbackend.dto.response.RecruiterResponse;
import com.hw.hwjobbackend.entity.Recruiter;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {CountryMapper.class, ProvinceMapper.class, WardMapper.class})
public interface RecruiterMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "phone", target = "phone")
    @Mapping(source = "imageUrl", target = "imageUrl")
    @Mapping(source = "userStatus", target = "userStatus")
    @Mapping(source = "roles", target = "roles")
    @Mapping(source = "country", target = "country", qualifiedByName = "toCountryResponseWithoutProvinces")
    @Mapping(source = "province", target = "province", qualifiedByName = "toProvinceResponseWithoutWards")
    @Mapping(source = "ward", target = "ward")
    RecruiterResponse toRecruiterResponse(Recruiter recruiter);

    @Mapping(target = "password", ignore = true)
    void updateRecruiter(@MappingTarget Recruiter recruiter, RecruiterUpdateRequest request);
}
