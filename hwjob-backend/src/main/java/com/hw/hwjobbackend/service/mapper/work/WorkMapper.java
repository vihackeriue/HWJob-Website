package com.hw.hwjobbackend.service.mapper.work;

import com.hw.hwjobbackend.model.dto.request.work.WorkCreateRequest;
import com.hw.hwjobbackend.model.dto.response.application.ApplicationCandidateResponse;
import com.hw.hwjobbackend.model.dto.response.work.WorkCandidateResponse;
import com.hw.hwjobbackend.model.dto.response.work.WorkOverviewResponse;
import com.hw.hwjobbackend.model.dto.response.work.WorkResponse;
import com.hw.hwjobbackend.model.entity.application.Application;
import com.hw.hwjobbackend.model.entity.works.Work;
import com.hw.hwjobbackend.model.enums.WorkStatusEnum;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WorkMapper {

    @Mapping(target = "id", ignore = true)

    @Mapping(target = "status", constant = "PENDING")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "candidate", ignore = true)
    @Mapping(target = "recruiter", ignore = true)
    @Mapping(target = "jobPost", ignore = true)
    @Mapping(target = "application", ignore = true)
    Work toWorkCreate(WorkCreateRequest workCreateRequest);

    @Mapping(target = "workId", source = "id")
    @Mapping(target = "jobPostId", source = "jobPost.id")
    @Mapping(target = "jobTitle", source = "jobPost.title")
    @Mapping(target = "recruiterName", source = "recruiter.fullName")
    @Mapping(target = "recruiterId", source = "recruiter.id")

    WorkOverviewResponse toWorkOverviewResponse(Work work);


    @Mapping(target = "workId", source = "id")
    @Mapping(target = "jobPostId", source = "jobPost.id")
    @Mapping(target = "title", source = "jobPost.title")
    @Mapping(target = "quantity", source = "jobPost.quantity")
    @Mapping(target = "industry", source = "jobPost.industry.name")
    @Mapping(target = "level", source = "jobPost.level.name")
    @Mapping(target = "jobType", source = "jobPost.jobType.name")
    @Mapping(target = "region", source = "jobPost.region.name")
    @Mapping(target = "workStatus", source = "status")
    @Mapping(
            target = "recruiter",
            source = "jobPost.recruiter"
    )
    WorkResponse toWorkResponse(Work work);


    @Mapping(target = "workId", source = "id")
    @Mapping(target = "candidateId",source = "candidate.id")
    @Mapping( target = "fullName",source = "candidate.fullName")
    @Mapping(target = "imageUrl",source = "candidate.imageUrl" )
    WorkCandidateResponse toWorkCandidateResponse(Work work);
}
