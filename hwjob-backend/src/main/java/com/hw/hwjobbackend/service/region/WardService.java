package com.hw.hwjobbackend.service.region;

import com.hw.hwjobbackend.dto.api_response.WardApiResponse;
import com.hw.hwjobbackend.entity.Province;
import com.hw.hwjobbackend.entity.Ward;

public interface WardService {
    void createWardFromApi(WardApiResponse apiResponse, Province province);

    Ward getWard(int code);

}
