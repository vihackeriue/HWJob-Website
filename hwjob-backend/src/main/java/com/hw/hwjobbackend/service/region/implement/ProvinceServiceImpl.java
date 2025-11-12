package com.hw.hwjobbackend.service.region.implement;

import com.hw.hwjobbackend.dto.api_response.ProvinceApiResponse;
import com.hw.hwjobbackend.dto.response.region.ProvinceResponse;
import com.hw.hwjobbackend.entity.Province;
import com.hw.hwjobbackend.exception.AppException;
import com.hw.hwjobbackend.exception.ErrorCode;
import com.hw.hwjobbackend.mapper.ProvinceMapper;
import com.hw.hwjobbackend.repository.ProvinceRepository;
import com.hw.hwjobbackend.service.region.ProvinceService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ProvinceServiceImpl implements ProvinceService {

    ProvinceRepository provinceRepository;
    ProvinceMapper provinceMapper;

    @Override
    @Transactional
    public Province createProvinceFromApi(ProvinceApiResponse apiResponse) {
        Province province = provinceMapper.toProvince(apiResponse);
        province.setWards(new ArrayList<>());

        province = provinceRepository.save(province);
        log.debug("Created province: {} (code: {})", province.getName(), province.getCode());
        return province;
    }

    @Override
    public List<ProvinceResponse> getAllProvince() {
        return provinceRepository.findAll()
                .stream()
                .map(provinceMapper::toProvinceResponseWithoutWard)
                .toList();
    }

    @Override
    public ProvinceResponse getProvinceByCode(int code) {
        Province province = provinceRepository.findById(code)
                .orElseThrow(() -> new AppException(ErrorCode.PROVINCE_NOT_EXISTED));
        return provinceMapper.toProvinceResponse(province);
    }

    @Override
    public Province getProvince(int code) {
        return provinceRepository.findById(code)
                .orElseThrow(() -> new AppException(ErrorCode.PROVINCE_NOT_EXISTED));
    }
}
