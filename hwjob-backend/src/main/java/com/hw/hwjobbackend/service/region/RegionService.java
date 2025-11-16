package com.hw.hwjobbackend.service.region;

import com.hw.hwjobbackend.entity.Province;
import com.hw.hwjobbackend.entity.Ward;

public interface RegionService {

    void initializeRegionData();

    Province getProvinceByCode(int provinceCode);

    Ward getWardByCodeAndProvince(int wardCode, Province province);
}
