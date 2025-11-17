package com.hw.hwjobbackend.service.region.implement;

import com.hw.hwjobbackend.dto.api_response.WardApiResponse;
import com.hw.hwjobbackend.entity.Province;
import com.hw.hwjobbackend.entity.Ward;
import com.hw.hwjobbackend.exception.AppException;
import com.hw.hwjobbackend.exception.ErrorCode;
import com.hw.hwjobbackend.mapper.region.WardMapper;
import com.hw.hwjobbackend.repository.region.WardRepository;
import com.hw.hwjobbackend.service.region.WardService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class WardServiceImpl implements WardService {

    WardRepository wardRepository;
    WardMapper wardMapper;

    @Override
    @Transactional
    public void createWardFromApi(WardApiResponse apiResponse, Province province) {
        Ward ward = wardMapper.toWard(apiResponse);
        ward.setProvince(province);

        ward = wardRepository.save(ward);
        log.debug("Created ward: {} (code: {})", ward.getName(), ward.getCode());
    }

    @Override
    public Ward getWard(int code) {
        return wardRepository.findById(code)
                .orElseThrow(() -> new AppException(ErrorCode.WARD_NOT_EXISTED));
    }
}