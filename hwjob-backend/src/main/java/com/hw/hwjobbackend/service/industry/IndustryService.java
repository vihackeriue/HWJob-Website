package com.hw.hwjobbackend.service.industry;

import com.hw.hwjobbackend.dto.request.industry.IndustryRequest;
import com.hw.hwjobbackend.dto.response.industry.IndustryResponse;
import com.hw.hwjobbackend.entity.Industry;
import org.springframework.data.domain.Page;

public interface IndustryService {

    Page<IndustryResponse> getAllIndustryNames(int page, int size);

    IndustryResponse getIndustryById(long id);

    IndustryResponse createIndustry(IndustryRequest request);

    IndustryResponse updateIndustry(long id, IndustryRequest request);

    void deleteIndustry(long id);

    Industry getIndustryEntityById(Long id);
}
