package com.example.country.controllers;

import com.example.country.domain.Country;
import com.example.country.dto.CountryDto;
import com.example.country.facade.CountryFacade;
import com.example.country.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/countries")
public class CountryController {

    private final CountryRepository countryRepository;
    private final CountryFacade countryFacade;

    @GetMapping("/country/{id}")
    public CountryDto findCountry(@PathVariable Long id) {
        return countryFacade.findCountryById(id);
    }

    @GetMapping("/")
    public List<Country> findCountries() {
        return countryRepository.findAll();
    }

}