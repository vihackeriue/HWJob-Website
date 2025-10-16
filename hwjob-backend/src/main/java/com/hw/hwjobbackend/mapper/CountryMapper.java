package com.hw.hwjobbackend.mapper;

import com.hw.hwjobbackend.dto.response.CountryResponse;
import com.hw.hwjobbackend.entity.Country;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ProvinceMapper.class})
public interface CountryMapper {

    CountryResponse toCountryResponse(Country country);
}
