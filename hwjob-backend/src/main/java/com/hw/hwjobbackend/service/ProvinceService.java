package com.hw.hwjobbackend.service;

import com.hw.hwjobbackend.dto.api_response.ProvinceApiResponse;
import com.hw.hwjobbackend.dto.response.ProvinceResponse;
import com.hw.hwjobbackend.entity.Country;
import com.hw.hwjobbackend.entity.Province;

import java.util.List;


public interface ProvinceService {
    Province createProvinceFromApi(ProvinceApiResponse apiResponse, Country country);

    ProvinceResponse getProvinceByCode(int code);

    List<ProvinceResponse> getAllProvince();

}