package com.hw.hwjobbackend.mapper.region;


import com.hw.hwjobbackend.dto.api_response.ProvinceApiResponse;
import com.hw.hwjobbackend.dto.response.region.ProvinceResponse;
import com.hw.hwjobbackend.entity.region.Province;
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
