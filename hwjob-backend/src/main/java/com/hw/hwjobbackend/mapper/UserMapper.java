package com.hw.hwjobbackend.mapper;


import com.hw.hwjobbackend.dto.request.UserCreationRequest;
import com.hw.hwjobbackend.dto.response.CandidateResponse;
import com.hw.hwjobbackend.dto.response.RecruiterResponse;
import com.hw.hwjobbackend.dto.response.UserCreationResponse;
import com.hw.hwjobbackend.dto.response.UserResponse;
import com.hw.hwjobbackend.entity.Candidate;
import com.hw.hwjobbackend.entity.Recruiter;
import com.hw.hwjobbackend.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.SubclassMapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    //    User toUser(UserCreationRequest request);
    UserCreationResponse toUserCreationResponse(User user);

    @SubclassMapping(source = Candidate.class, target = CandidateResponse.class)
    @SubclassMapping(source = Recruiter.class, target = RecruiterResponse.class)
    UserResponse toUserResponse(User user);

}
