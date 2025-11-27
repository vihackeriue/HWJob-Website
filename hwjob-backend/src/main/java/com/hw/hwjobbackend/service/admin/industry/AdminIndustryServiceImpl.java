package com.hw.hwjobbackend.service.admin.industry;


import com.hw.hwjobbackend.exception.AppException;
import com.hw.hwjobbackend.exception.ErrorCode;
import com.hw.hwjobbackend.service.mapper.industry.IndustryMapper;
import com.hw.hwjobbackend.model.dto.request.industry.IndustryRequest;
import com.hw.hwjobbackend.model.dto.response.industry.IndustryResponse;
import com.hw.hwjobbackend.model.entity.industry.Industry;
import com.hw.hwjobbackend.repository.industry.IndustryRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@PreAuthorize("hasRole('ADMIN')")
public class AdminIndustryServiceImpl implements AdminIndustryService {

    IndustryRepository industryRepository;
    IndustryMapper industryMapper;

    @Override
    public IndustryResponse createIndustry(IndustryRequest request) {
        if (industryRepository.existsByName((request.getName()))) {
            throw new AppException(ErrorCode.INDUSTRY_EXISTED);
        }
        Industry industry = industryMapper.toIndustry(request);
        industry = industryRepository.save(industry);
        return industryMapper.toIndustryResponse(industry);
    }

    @Override
    public IndustryResponse updateIndustry(long id, IndustryRequest request) {
        Industry industry = industryRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.INDUSTRY_NOT_EXISTED));
        industry.setName(request.getName());
        industry.setDescription(request.getDescription());
        industry = industryRepository.save(industry);
        return industryMapper.toIndustryResponse(industry);
    }

    @Override
    public void deleteIndustry(long id) {
        Industry industry = industryRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.INDUSTRY_NOT_EXISTED));
        industryRepository.delete(industry);
    }
}
