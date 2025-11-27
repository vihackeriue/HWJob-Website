package com.hw.hwjobbackend.service.mapper.industry;


import com.hw.hwjobbackend.model.dto.request.industry.IndustryRequest;
import com.hw.hwjobbackend.model.dto.response.industry.IndustryResponse;
import com.hw.hwjobbackend.model.entity.industry.Industry;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IndustryMapper {
    Industry toIndustry(IndustryRequest request);

    IndustryResponse toIndustryResponse(Industry industry);
}
