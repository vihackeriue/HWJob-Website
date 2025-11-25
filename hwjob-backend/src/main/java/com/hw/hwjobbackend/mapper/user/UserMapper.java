package com.hw.hwjobbackend.mapper.user;


import com.hw.hwjobbackend.model.dto.request.user.UserCreationRequest;
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

    @Mapping(target = "password", ignore = true)
    @Mapping(target = "roles", ignore = true)
    User toUser(UserCreationRequest request);

    @SubclassMapping(source = Candidate.class, target = CandidateResponse.class)
    @SubclassMapping(source = Recruiter.class, target = RecruiterResponse.class)
    @Mapping(source = "province.name", target = "region")
    UserResponse toUserResponse(User user);


    UserLoginResponse toUserLoginResponse(User user);

}
