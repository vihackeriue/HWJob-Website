package com.hw.hwjobbackend.mapper;

import com.hw.hwjobbackend.dto.api_response.WardApiResponse;
import com.hw.hwjobbackend.entity.Ward;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.hw.hwjobbackend.dto.response.WardResponse;


@Mapper(componentModel = "spring")
public interface WardMapper {

    @Mapping(target = "province", ignore = true)
    Ward toWard(WardApiResponse response);

    @Mapping(target = "provinceCode", source = "province.code")
    @Mapping(target = "provinceName", source = "province.name")
    WardResponse toWardResponse(Ward ward);
}
