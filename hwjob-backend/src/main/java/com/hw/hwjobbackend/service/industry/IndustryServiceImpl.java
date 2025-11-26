package com.hw.hwjobbackend.service.industry;

import com.hw.hwjobbackend.model.dto.request.industry.IndustryRequest;
import com.hw.hwjobbackend.model.dto.response.industry.IndustryResponse;
import com.hw.hwjobbackend.model.entity.industry.Industry;
import com.hw.hwjobbackend.exception.AppException;
import com.hw.hwjobbackend.exception.ErrorCode;
import com.hw.hwjobbackend.mapper.industry.IndustryMapper;
import com.hw.hwjobbackend.repository.industry.IndustryRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class IndustryServiceImpl implements IndustryService {

    IndustryRepository industryRepository;
    IndustryMapper industryMapper;

    @Override
    public Page<IndustryResponse> getIndustries(int page, int size) {
        page = Math.max(page, 0);
        size = size <= 0 ? 10 : size;
        Pageable pageable = PageRequest.of(page, size);
        return industryRepository.findAll(pageable)
                .map(industryMapper::toIndustryResponse);
    }

    @Override
    public List<IndustryResponse> getAllIndustries() {
        return industryRepository.findAll().stream().map(industryMapper::toIndustryResponse).toList();
    }

    @Override
    public IndustryResponse getIndustryById(long id) {
        return industryRepository.findById(id)
                .map(industryMapper::toIndustryResponse)
                .orElseThrow(() -> new AppException(ErrorCode.INDUSTRY_NOT_EXISTED));
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public IndustryResponse createIndustry(IndustryRequest request) {
        if (industryRepository.existsByName((request.getName()))) {
            throw new AppException(ErrorCode.INDUSTRY_EXISTED);
        }
        Industry industry = industryMapper.toIndustry(request);
        industry = industryRepository.save(industry);
        return industryMapper.toIndustryResponse(industry);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public IndustryResponse updateIndustry(long id, IndustryRequest request) {
        Industry industry = industryRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.INDUSTRY_NOT_EXISTED));
        industry.setName(request.getName());
        industry.setDescription(request.getDescription());
        industry = industryRepository.save(industry);
        return industryMapper.toIndustryResponse(industry);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteIndustry(long id) {
        Industry industry = industryRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.INDUSTRY_NOT_EXISTED));
        industryRepository.delete(industry);
    }

    @Override
    public Industry getIndustryEntityById(Long id) {
        return industryRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.INDUSTRY_NOT_EXISTED)
        );
    }
}
