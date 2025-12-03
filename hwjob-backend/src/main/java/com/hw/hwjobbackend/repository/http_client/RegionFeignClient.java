package com.hw.hwjobbackend.repository.http_client;

import com.hw.hwjobbackend.model.dto.api.ProvinceApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "province-api", url = "${api.api-province}")
public interface RegionFeignClient {

    @GetMapping
    List<ProvinceApiResponse> getAllProvinces();

}
