package com.hw.hwjobbackend.service.region.implement;

import com.hw.hwjobbackend.dto.api_response.ProvinceApiResponse;
import com.hw.hwjobbackend.dto.api_response.WardApiResponse;
import com.hw.hwjobbackend.entity.region.Ward;
import com.hw.hwjobbackend.entity.region.Province;
import com.hw.hwjobbackend.exception.AppException;
import com.hw.hwjobbackend.exception.ErrorCode;
import com.hw.hwjobbackend.service.api.ApiClientService;
import com.hw.hwjobbackend.service.region.ProvinceService;
import com.hw.hwjobbackend.service.region.RegionService;
import com.hw.hwjobbackend.service.region.WardService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RegionServiceImpl implements RegionService {

    ProvinceService provinceService;
    WardService wardService;
    ApiClientService apiClientService;

    @NonFinal
    @Value("${api.api-province}")
    String PROVINCE_API_URL;

    @Override
    @Transactional
    public void initializeRegionData() {
        try {
            List<ProvinceApiResponse> provinceApiResponses = apiClientService.get(
                    PROVINCE_API_URL,
                    new ParameterizedTypeReference<>() {
                    }
            );
            if (provinceApiResponses != null && !provinceApiResponses.isEmpty()) {
                processProvinceData(provinceApiResponses);
            }
        } catch (Exception e) {
            log.error("Error initializing location data: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to initialize location data from API", e);
        }
    }

    @Override
    public Province getProvinceByCode(int provinceCode) {
        return provinceService.getProvince(provinceCode);
    }

    @Override
    public Ward getWardByCodeAndProvince(int wardCode, Province province) {
        Ward ward = wardService.getWard(wardCode);
        if (!Objects.equals(ward.getProvince().getCode(), province.getCode())) {
            throw new AppException(ErrorCode.WARD_NOT_EXISTED);
        }
        return ward;
    }

    private void processProvinceData(List<ProvinceApiResponse> provinceApiResponses) {
        for (ProvinceApiResponse provinceApiResponse : provinceApiResponses) {
            Province province = provinceService.createProvinceFromApi(provinceApiResponse);
            if (provinceApiResponse.getWards() != null && !provinceApiResponse.getWards().isEmpty()) {
                processWardData(provinceApiResponse.getWards(), province);
            }
        }
    }

    private void processWardData(List<WardApiResponse> wards, Province province) {
        for (WardApiResponse wardApiResponse : wards) {
            wardService.createWardFromApi(wardApiResponse, province);
        }
    }

}