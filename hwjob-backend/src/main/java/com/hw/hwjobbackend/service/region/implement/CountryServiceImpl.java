package com.hw.hwjobbackend.service.region.implement;

import com.hw.hwjobbackend.entity.Country;
import com.hw.hwjobbackend.exception.AppException;
import com.hw.hwjobbackend.exception.ErrorCode;
import com.hw.hwjobbackend.repository.CountryRepository;
import com.hw.hwjobbackend.service.region.CountryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CountryServiceImpl implements CountryService {

    CountryRepository countryRepository;

    @Override
    @Transactional
    public Country createCountry(String name, String code) {
        Country country = Country.builder()
                .name(name)
                .code(code)
                .provinces(new ArrayList<>())
                .build();

        country = countryRepository.save(country);
        log.info("Created country: {} with code: {}", name, code);
        return country;
    }

    @Override
    public Country getCountryByCode(String code) {
        return countryRepository.findByCode(code)
                .orElseThrow(() -> new AppException(ErrorCode.COUNTRY_NOT_EXISTED));
    }

}
