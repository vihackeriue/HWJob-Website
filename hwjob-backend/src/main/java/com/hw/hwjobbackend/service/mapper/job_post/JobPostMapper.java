package com.hw.hwjobbackend.service.mapper.job_post;


import com.hw.hwjobbackend.model.dto.request.job_post.JobPostRequest;
import com.hw.hwjobbackend.model.dto.response.job_post.JobPostDetailResponse;
import com.hw.hwjobbackend.model.dto.response.job_post.JobPostRecruiterProfileResponse;
import com.hw.hwjobbackend.model.dto.response.job_post.JobPostResponse;
import com.hw.hwjobbackend.model.entity.job_post.JobPost;
import com.hw.hwjobbackend.model.entity.user.Recruiter;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface JobPostMapper {


    @Mapping(target = "jobType", ignore = true)
    @Mapping(target = "level", ignore = true)
    @Mapping(target = "industry", ignore = true)
    @Mapping(target = "region", ignore = true)
    @Mapping(target = "recruiter", ignore = true)
    JobPost toJobPost(JobPostRequest request);


    @Mapping(source = "industry.name", target = "industry")
    @Mapping(source = "level.name", target = "level")
    @Mapping(source = "jobType.name", target = "jobType")
    @Mapping(source = "region.name", target = "region")
    @Mapping(source = "recruiter", target = "recruiter")
    JobPostResponse toJobPostResponse(JobPost jobPost);

    @Mapping(source = "industry.name", target = "industry")
    @Mapping(source = "level.name", target = "level")
    @Mapping(source = "jobType.name", target = "jobType")
    @Mapping(source = "region.name", target = "region")
    @Mapping(target = "recruiter", ignore = true)
    @Mapping(target = "application", ignore = true)
    @Mapping(target = "viewCount", ignore = true)
    @Mapping(target = "isSaved", ignore = true)
    JobPostDetailResponse toJobPostDetailResponse(JobPost jobPost);


    @Mapping(target = "jobType", ignore = true)
    @Mapping(target = "level", ignore = true)
    @Mapping(target = "industry", ignore = true)
    @Mapping(target = "region", ignore = true)
    @Mapping(target = "recruiter", ignore = true)
    // Nếu request field null -> bỏ qua
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateJobPost(JobPostRequest request, @MappingTarget JobPost jobPost);

    JobPostRecruiterProfileResponse toJobPostRecruiterProfileResponse(Recruiter recruiter);
}
