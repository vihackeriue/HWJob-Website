package com.hw.hwjobbackend.service;

import com.hw.hwjobbackend.dto.api_response.WardApiResponse;
import com.hw.hwjobbackend.entity.Province;

public interface WardService {
    void createWardFromApi(WardApiResponse apiResponse, Province province);

}
