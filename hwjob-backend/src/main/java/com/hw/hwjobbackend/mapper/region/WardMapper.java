package com.hw.hwjobbackend.mapper.region;

import com.hw.hwjobbackend.dto.api_response.WardApiResponse;
import com.hw.hwjobbackend.dto.response.region.WardResponse;
import com.hw.hwjobbackend.entity.region.Ward;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface WardMapper {

    @Mapping(target = "province", ignore = true)
    Ward toWard(WardApiResponse response);

    WardResponse toWardResponse(Ward ward);
}
