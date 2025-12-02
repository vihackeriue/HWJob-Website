package com.hw.hwjobbackend.service.mapper.region;


import com.hw.hwjobbackend.model.dto.api.ProvinceApiResponse;
import com.hw.hwjobbackend.model.dto.response.region.RegionResponse;
import com.hw.hwjobbackend.model.entity.region.Region;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RegionMapper {

    @Mapping(source = "code", target = "id")
    Region toRegion(ProvinceApiResponse response);

    RegionResponse toRegionResponse(Region region);
}
