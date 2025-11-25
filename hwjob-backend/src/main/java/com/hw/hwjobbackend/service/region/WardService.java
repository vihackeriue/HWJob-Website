package com.hw.hwjobbackend.service.region;

import com.hw.hwjobbackend.model.dto.api_response.WardApiResponse;
import com.hw.hwjobbackend.model.entity.region.Province;
import com.hw.hwjobbackend.model.entity.region.Ward;

public interface WardService {
    void createWardFromApi(WardApiResponse apiResponse, Province province);

    Ward getWard(int code);

}
