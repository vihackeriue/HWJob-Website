package com.hw.hwjobbackend.service.mapper.application;

import com.hw.hwjobbackend.model.dto.response.application.ApplicationAllCandidateResponse;
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
    @Mapping(source = "candidate.email", target = "email")
    ApplicationCandidateResponse toCandidateApplicationResponse(Application application);

    @Mapping(source = "candidate.id", target = "candidateId")
    @Mapping(source = "candidate.fullName", target = "fullName")
    @Mapping(source = "candidate.imageUrl", target = "imageUrl")
    @Mapping(source = "candidate.email", target = "email")
    @Mapping(source = "jobPost.id", target = "jobPostId")
    @Mapping(source = "jobPost.title", target = "jobPostTitle")
    ApplicationAllCandidateResponse toApplicationAllCandidateResponse(Application application);

}
