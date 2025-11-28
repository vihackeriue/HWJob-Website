package com.hw.hwjobbackend.service.shared.region;

import com.hw.hwjobbackend.model.entity.region.Province;
import com.hw.hwjobbackend.model.entity.region.Ward;

public interface RegionService {

    void initializeRegionData();

    Province getProvinceByCode(int provinceCode);

    Province getProvinceReferenceByCode(Integer code);

    Ward getWardByCodeAndProvince(int wardCode, Province province);
}
