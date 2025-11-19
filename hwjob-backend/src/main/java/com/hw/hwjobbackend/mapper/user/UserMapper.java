package com.hw.hwjobbackend.mapper.user;


import com.hw.hwjobbackend.dto.request.user.UserCreationRequest;
import com.hw.hwjobbackend.dto.response.user.CandidateResponse;
import com.hw.hwjobbackend.dto.response.user.RecruiterResponse;
import com.hw.hwjobbackend.dto.response.user.UserCreationResponse;
import com.hw.hwjobbackend.dto.response.user.UserResponse;
import com.hw.hwjobbackend.entity.user.Candidate;
import com.hw.hwjobbackend.entity.user.Recruiter;
import com.hw.hwjobbackend.entity.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.SubclassMapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserCreationResponse toUserCreationResponse(User user);

    @Mapping(target = "password", ignore = true)
    @Mapping(target = "roles", ignore = true)
    User toUser(UserCreationRequest request);

    @SubclassMapping(source = Candidate.class, target = CandidateResponse.class)
    @SubclassMapping(source = Recruiter.class, target = RecruiterResponse.class)
    @Mapping(source = "province.name", target = "region")
    UserResponse toUserResponse(User user);

}
