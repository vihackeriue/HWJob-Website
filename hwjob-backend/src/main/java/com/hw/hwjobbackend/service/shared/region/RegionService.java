package com.hw.hwjobbackend.service.shared.region;

import com.hw.hwjobbackend.model.dto.api.response.ProvinceApiResponse;
import com.hw.hwjobbackend.model.dto.response.region.RegionResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface RegionService {

    RegionResponse getRegionById(int id);

    void createRegion(ProvinceApiResponse apiResponse);

    Page<RegionResponse> getAllRegion(int page, int size);

    List<RegionResponse> getAllRegion();

}
