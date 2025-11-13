package com.hw.hwjobbackend.mapper;

import com.hw.hwjobbackend.dto.request.user.CandidateUpdateRequest;
import com.hw.hwjobbackend.dto.response.user.CandidateResponse;
import com.hw.hwjobbackend.entity.Candidate;
import org.mapstruct.*;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {ProvinceMapper.class, WardMapper.class})
public interface CandidateMapper {

    @Mapping(source = "province", target = "province", qualifiedByName = "toProvinceResponseWithoutWards")
    CandidateResponse toCandidateResponse(Candidate candidate);

    @Mapping(target = "password", ignore = true)
    void updateCandidate(@MappingTarget Candidate candidate, CandidateUpdateRequest request);

}
