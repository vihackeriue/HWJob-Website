package com.hw.hwjobbackend.service.mapper.user;


import com.hw.hwjobbackend.model.dto.response.user.*;
import com.hw.hwjobbackend.model.entity.user.Candidate;
import com.hw.hwjobbackend.model.entity.user.Recruiter;
import com.hw.hwjobbackend.model.entity.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.SubclassMapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserCreationResponse toUserCreationResponse(User user);

    @SubclassMapping(source = Candidate.class, target = CandidateResponse.class)
    @SubclassMapping(source = Recruiter.class, target = RecruiterResponse.class)
    @Mapping(source = "region.name", target = "region")
    UserResponse toUserResponse(User user);

    UserLoginResponse toUserLoginResponse(User user);

}
