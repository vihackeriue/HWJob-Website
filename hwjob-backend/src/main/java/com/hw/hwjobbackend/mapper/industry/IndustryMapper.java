package com.hw.hwjobbackend.mapper.industry;


import com.hw.hwjobbackend.model.dto.request.industry.IndustryRequest;
import com.hw.hwjobbackend.model.dto.response.industry.IndustryResponse;
import com.hw.hwjobbackend.model.entity.Industry;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IndustryMapper {
    Industry toIndustry(IndustryRequest request);

    IndustryResponse toIndustryResponse(Industry industry);
}
