package com.hw.hwjobbackend.service.mapper.user;

import com.hw.hwjobbackend.model.dto.request.user.CandidateUpdateRequest;
import com.hw.hwjobbackend.model.dto.response.profile.CandidateProfileResponse;
import com.hw.hwjobbackend.model.dto.response.user.CandidateResponse;
import com.hw.hwjobbackend.model.entity.user.Candidate;
import com.hw.hwjobbackend.service.mapper.region.ProvinceMapper;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CandidateMapper {

    @Mapping(source = "province.name", target = "region")
    CandidateResponse toCandidateResponse(Candidate candidate);

    @Mapping(target = "password", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCandidate(@MappingTarget Candidate candidate, CandidateUpdateRequest request);


    CandidateProfileResponse toCandidateProfileResponse(Candidate candidate);

}
