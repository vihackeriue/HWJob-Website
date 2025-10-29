package com.hw.hwjobbackend.service;

import com.hw.hwjobbackend.entity.Country;
import com.hw.hwjobbackend.entity.Province;
import com.hw.hwjobbackend.entity.Ward;

public interface RegionService {
    void initializeRegionData();

    Country getCountryByCode(String code);
    Province getProvinceByCodeAndCountry(int provinceCode, Country country);
    Ward getWardByCodeAndProvince(int wardCode, Province province);
}
