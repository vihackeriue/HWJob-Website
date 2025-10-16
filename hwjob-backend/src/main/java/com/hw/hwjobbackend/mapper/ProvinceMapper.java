package com.hw.hwjobbackend.mapper;


import com.hw.hwjobbackend.dto.api_response.ProvinceApiResponse;
import com.hw.hwjobbackend.dto.response.ProvinceResponse;
import com.hw.hwjobbackend.entity.Province;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {WardMapper.class})
public interface ProvinceMapper {

    @Mapping(target = "country", ignore = true)
    @Mapping(target = "wards", ignore = true)
    Province toProvince(ProvinceApiResponse response);

    //    @Mapping(target = "countryId", source = "country.id")
//    @Mapping(target = "countryName", source = "country.name")
    ProvinceResponse toProvinceResponse(Province province);
    
    @Mapping(target = "wards", ignore = true)
    ProvinceResponse toProvinceResponseWithoutWard(Province province);
}
