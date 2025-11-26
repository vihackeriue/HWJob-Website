package com.hw.hwjobbackend.service.shared.region;

import com.hw.hwjobbackend.model.dto.api_response.ProvinceApiResponse;
import com.hw.hwjobbackend.model.dto.response.region.ProvinceResponse;
import com.hw.hwjobbackend.model.entity.region.Province;
import org.springframework.data.domain.Page;

import java.util.List;


public interface ProvinceService {
    Province createProvinceFromApi(ProvinceApiResponse apiResponse);

    ProvinceResponse getProvinceByCode(int code);

    Province getProvince(Integer code);

    Page<ProvinceResponse> getAllProvince(int page, int size);

    List<ProvinceResponse> getAllProvince();

}