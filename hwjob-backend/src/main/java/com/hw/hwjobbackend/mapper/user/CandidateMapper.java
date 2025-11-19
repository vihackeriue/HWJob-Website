package com.hw.hwjobbackend.mapper.user;

import com.hw.hwjobbackend.dto.request.user.CandidateUpdateRequest;
import com.hw.hwjobbackend.dto.response.user.CandidateResponse;
import com.hw.hwjobbackend.entity.user.Candidate;
import com.hw.hwjobbackend.mapper.region.ProvinceMapper;
import org.mapstruct.*;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {ProvinceMapper.class})
public interface CandidateMapper {

    @Mapping(source = "province.name", target = "region")
    CandidateResponse toCandidateResponse(Candidate candidate);

    @Mapping(target = "password", ignore = true)
    void updateCandidate(@MappingTarget Candidate candidate, CandidateUpdateRequest request);

}
