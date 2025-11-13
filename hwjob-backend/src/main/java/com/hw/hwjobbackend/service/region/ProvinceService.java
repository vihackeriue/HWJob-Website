package com.hw.hwjobbackend.service.region;

import com.hw.hwjobbackend.dto.api_response.ProvinceApiResponse;
import com.hw.hwjobbackend.dto.response.region.ProvinceResponse;
import com.hw.hwjobbackend.entity.Province;
import org.springframework.data.domain.Page;

import java.util.List;


public interface ProvinceService {
    Province createProvinceFromApi(ProvinceApiResponse apiResponse);

    ProvinceResponse getProvinceByCode(int code);

    Province getProvince(int code);

    Page<ProvinceResponse> getAllProvince(int page, int size);

}