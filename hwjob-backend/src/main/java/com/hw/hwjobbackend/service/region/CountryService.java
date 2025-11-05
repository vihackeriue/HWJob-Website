package com.hw.hwjobbackend.service.region;

import com.hw.hwjobbackend.entity.Country;


public interface CountryService {
    Country createCountry(String name, String code);

    Country getCountryByCode(String code);


}