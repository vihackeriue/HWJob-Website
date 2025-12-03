package com.hw.hwjobbackend.service.shared.industry;

import com.hw.hwjobbackend.model.dto.response.industry.IndustryResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IndustryService {

    Page<IndustryResponse> getIndustries(int page, int size);

    List<IndustryResponse> getAllIndustries();

    IndustryResponse getIndustryById(long id);

}
