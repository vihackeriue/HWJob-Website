package com.hw.hwjobbackend.mapper;

import com.hw.hwjobbackend.dto.api_response.WardApiResponse;
import com.hw.hwjobbackend.dto.response.WardResponse;
import com.hw.hwjobbackend.entity.Ward;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface WardMapper {

    @Mapping(target = "province", ignore = true)
    Ward toWard(WardApiResponse response);

    WardResponse toWardResponse(Ward ward);
}
