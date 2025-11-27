package com.hw.hwjobbackend.service.mapper.application;

import com.hw.hwjobbackend.model.dto.response.application.ApplicationResponse;
import com.hw.hwjobbackend.model.dto.response.application.ApplicationCandidateResponse;
import com.hw.hwjobbackend.model.entity.application.Application;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ApplicationMapper {

    @Mapping(source = "jobPost.id", target = "jobPostId")
    ApplicationResponse toApplicationResponse(Application application);

    @Mapping(source = "candidate.id", target = "id")
    @Mapping(source = "candidate.fullName", target = "fullName")
    @Mapping(source = "candidate.imageUrl", target = "imageUrl")
    ApplicationCandidateResponse toCandidateApplicationResponse(Application application);

}
