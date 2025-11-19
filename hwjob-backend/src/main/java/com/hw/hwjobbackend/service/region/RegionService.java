package com.hw.hwjobbackend.service.region;

import com.hw.hwjobbackend.entity.region.Province;
import com.hw.hwjobbackend.entity.region.Ward;

public interface RegionService {

    void initializeRegionData();

    Province getProvinceByCode(int provinceCode);

    Ward getWardByCodeAndProvince(int wardCode, Province province);
}
