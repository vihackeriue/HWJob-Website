package com.hw.hwjobbackend.mapper.application;

import com.hw.hwjobbackend.dto.response.application.ApplicationResponse;
import com.hw.hwjobbackend.entity.application.Application;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ApplicationMapper {

    @Mapping(source = "jobPost.id", target = "jobPostId")
    ApplicationResponse toApplicationResponse(Application application);

}
