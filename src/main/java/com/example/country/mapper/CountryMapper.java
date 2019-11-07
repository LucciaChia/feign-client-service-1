package com.example.country.mapper;

import com.example.country.domain.Country;
import com.example.country.dto.CountryDto;
import springfox.documentation.swagger2.mappers.LicenseMapper;
import org.mapstruct.Mapper;

@Mapper
public interface CountryMapper {

    CountryDto countryToCountryDto(Country country);
    Country countryDtoToCountry(CountryDto countryDto);
}
