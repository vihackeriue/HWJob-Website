package com.hw.hwjobbackend.service.implement;

import com.hw.hwjobbackend.dto.api_response.ProvinceApiResponse;
import com.hw.hwjobbackend.dto.api_response.WardApiResponse;
import com.hw.hwjobbackend.entity.Country;
import com.hw.hwjobbackend.entity.Ward;
import com.hw.hwjobbackend.entity.Province;
import com.hw.hwjobbackend.exception.AppException;
import com.hw.hwjobbackend.exception.ErrorCode;
import com.hw.hwjobbackend.service.*;
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

    CountryService countryService;
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
            Country vietnam = countryService.createCountry("Việt Nam", "VN"); // Thay đổi lại sau
            countryService.createCountry("Nước ngoài", "FOREIGN");

            List<ProvinceApiResponse> provinceApiResponses = apiClientService.get(
                    PROVINCE_API_URL,
                    new ParameterizedTypeReference<>() {
                    }
            );
            if (provinceApiResponses != null && !provinceApiResponses.isEmpty()) {
                processProvinceData(provinceApiResponses, vietnam);
            }
        } catch (Exception e) {
            log.error("Error initializing location data: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to initialize location data from API", e);
        }
    }

    @Override
    public Country getCountryByCode(String code) {
        return countryService.getCountryByCode(code);
    }

    @Override
    public Province getProvinceByCodeAndCountry(int provinceCode, Country country) {
        Province province = provinceService.getProvince(provinceCode);
        if (!Objects.equals(province.getCountry().getCode(), country.getCode())) {
            throw new AppException(ErrorCode.PROVINCE_NOT_EXISTED);
        }
        return province;
    }

    @Override
    public Ward getWardByCodeAndProvince(int wardCode, Province province) {
        Ward ward = wardService.getWard(wardCode);
        if (!Objects.equals(ward.getProvince().getCode(), province.getCode())) {
            throw new AppException(ErrorCode.WARD_NOT_EXISTED);
        }
        return ward;
    }

    private void processProvinceData(List<ProvinceApiResponse> provinceApiResponses, Country vietnam) {

        for (ProvinceApiResponse provinceApiResponse : provinceApiResponses) {
            Province province = provinceService.createProvinceFromApi(provinceApiResponse, vietnam);
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