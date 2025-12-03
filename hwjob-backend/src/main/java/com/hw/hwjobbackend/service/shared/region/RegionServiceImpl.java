package com.hw.hwjobbackend.service.shared.region;

import com.hw.hwjobbackend.model.dto.api.ProvinceApiResponse;
import com.hw.hwjobbackend.model.dto.response.region.RegionResponse;
import com.hw.hwjobbackend.model.entity.region.Region;
import com.hw.hwjobbackend.exception.AppException;
import com.hw.hwjobbackend.exception.ErrorCode;
import com.hw.hwjobbackend.repository.region.RegionRepository;
import com.hw.hwjobbackend.service.mapper.region.RegionMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RegionServiceImpl implements RegionService {

    RegionRepository regionRepository;
    RegionMapper regionMapper;

    @Override
    public RegionResponse getRegionById(int id) {
        Region region = regionRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.REGION_NOT_EXISTED));
        return regionMapper.toRegionResponse(region);
    }

    @Override
    public void createRegion(ProvinceApiResponse apiResponse) {
        Region region = regionMapper.toRegion(apiResponse);
        log.debug("Created region: {} (id: {})", region.getName(), region.getId());
        regionRepository.save(region);
    }

    @Override
    public Page<RegionResponse> getAllRegion(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return regionRepository.findAll(pageable).map(regionMapper::toRegionResponse);
    }

    @Override
    public List<RegionResponse> getAllRegion() {
        return regionRepository.findAll().stream()
                .map(regionMapper::toRegionResponse)
                .toList();
    }

}