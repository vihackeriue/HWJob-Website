package com.hw.hwjobbackend.service.industry;

import com.hw.hwjobbackend.model.dto.request.industry.IndustryRequest;
import com.hw.hwjobbackend.model.dto.response.industry.IndustryResponse;
import com.hw.hwjobbackend.model.entity.industry.Industry;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IndustryService {

    Page<IndustryResponse> getIndustries(int page, int size);

    List<IndustryResponse> getAllIndustries();

    IndustryResponse getIndustryById(long id);

    IndustryResponse createIndustry(IndustryRequest request);

    IndustryResponse updateIndustry(long id, IndustryRequest request);

    void deleteIndustry(long id);

    Industry getIndustryEntityById(Long id);
}
