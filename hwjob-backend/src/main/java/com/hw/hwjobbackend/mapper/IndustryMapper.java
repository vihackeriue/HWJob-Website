package com.hw.hwjobbackend.mapper;


import com.hw.hwjobbackend.dto.request.industry.IndustryRequest;
import com.hw.hwjobbackend.dto.response.industry.IndustryResponse;
import com.hw.hwjobbackend.entity.Industry;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IndustryMapper {
    Industry toIndustry(IndustryRequest request);

    IndustryResponse toIndustryResponse(Industry industry);
}
