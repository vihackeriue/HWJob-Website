package com.hw.hwjobbackend.mapper;

import com.hw.hwjobbackend.dto.response.CountryResponse;
import com.hw.hwjobbackend.entity.Country;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = {ProvinceMapper.class})
public interface CountryMapper {

    CountryResponse toCountryResponse(Country country);

    @Named("toCountryResponseWithoutProvinces")
    CountryResponse toCountryResponseWithoutProvinces(Country country);
}
