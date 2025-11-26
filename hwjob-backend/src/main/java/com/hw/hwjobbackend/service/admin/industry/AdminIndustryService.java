package com.hw.hwjobbackend.service.admin.industry;

import com.hw.hwjobbackend.model.dto.request.industry.IndustryRequest;
import com.hw.hwjobbackend.model.dto.response.industry.IndustryResponse;

public interface AdminIndustryService {
    IndustryResponse createIndustry(IndustryRequest request);

    IndustryResponse updateIndustry(long id, IndustryRequest request);

    void deleteIndustry(long id);
}
