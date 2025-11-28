package com.hw.hwjobbackend.service.mapper.region;

import com.hw.hwjobbackend.model.dto.api_response.WardApiResponse;
import com.hw.hwjobbackend.model.dto.response.region.WardResponse;
import com.hw.hwjobbackend.model.entity.region.Ward;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface WardMapper {

    @Mapping(target = "province", ignore = true)
    Ward toWard(WardApiResponse response);

}
