package com.hw.hwjobbackend.mapper.job_post;


import com.hw.hwjobbackend.model.dto.request.job_post.JobPostCreationRequest;
import com.hw.hwjobbackend.model.dto.response.job_post.JobPostDetailResponse;
import com.hw.hwjobbackend.model.dto.response.job_post.JobPostResponse;
import com.hw.hwjobbackend.model.entity.job_post.JobPost;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface JobPostMapper {


    @Mapping(target = "jobType", ignore = true)
    @Mapping(target = "level", ignore = true)
    @Mapping(target = "industry", ignore = true)
    @Mapping(target = "province", ignore = true)
    @Mapping(target = "recruiter", ignore = true)
    JobPost toJobPost(JobPostCreationRequest request);


    @Mapping(source = "recruiter.username", target = "recruiterName")
    @Mapping(source = "recruiter.imageUrl", target = "imageUrl")
    @Mapping(source = "industry.name", target = "industry")
    @Mapping(source = "level.name", target = "level")
    @Mapping(source = "jobType.name", target = "jobType")
    @Mapping(source = "province.name", target = "region")
    JobPostResponse toJobPostResponse(JobPost jobPost);

    @Mapping(source = "industry.name", target = "industry")
    @Mapping(source = "level.name", target = "level")
    @Mapping(source = "jobType.name", target = "jobType")
    @Mapping(source = "province.name", target = "region")
    @Mapping(target = "recruiter", ignore = true)
    @Mapping(target = "isApplied", ignore = true)
    @Mapping(target = "isSaved", ignore = true)
    JobPostDetailResponse toJobPostDetailResponse(JobPost jobPost);

}
