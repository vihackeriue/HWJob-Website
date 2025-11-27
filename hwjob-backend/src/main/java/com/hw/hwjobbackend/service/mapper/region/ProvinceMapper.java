package com.hw.hwjobbackend.service.mapper.region;


import com.hw.hwjobbackend.model.dto.api_response.ProvinceApiResponse;
import com.hw.hwjobbackend.model.dto.response.region.ProvinceResponse;
import com.hw.hwjobbackend.model.entity.region.Province;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface ProvinceMapper {


    Province toProvince(ProvinceApiResponse response);

    @Named("toProvinceResponseWithWards")
    @Mapping(source = "province.code", target = "id")
    ProvinceResponse toProvinceResponse(Province province);
}
