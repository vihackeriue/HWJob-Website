package com.hw.hwjobbackend.mapper;


import com.hw.hwjobbackend.dto.api_response.ProvinceApiResponse;
import com.hw.hwjobbackend.dto.response.region.ProvinceResponse;
import com.hw.hwjobbackend.entity.Province;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = {WardMapper.class})
public interface ProvinceMapper {

    @Mapping(target = "wards", ignore = true)
    Province toProvince(ProvinceApiResponse response);

    @Named("toProvinceResponseWithWards")
    ProvinceResponse toProvinceResponse(Province province);

    @Named("toProvinceResponseWithoutWards")
    ProvinceResponse toProvinceResponseWithoutWard(Province province);
}
