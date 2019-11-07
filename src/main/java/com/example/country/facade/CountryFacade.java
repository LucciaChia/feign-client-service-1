package com.example.country.facade;

import com.example.country.domain.Country;
import com.example.country.dto.CountryDto;
import com.example.country.mapper.CountryMapper;
import com.example.country.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CountryFacade {

    private final CountryService countryService;
    private final CountryMapper countryMapper;

    public CountryDto findCountryById(long id) {
        Country country = countryService.findCountryById(id);
        return countryMapper.countryToCountryDto(country);
    }
}
