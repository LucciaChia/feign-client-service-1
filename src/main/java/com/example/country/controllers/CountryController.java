package com.example.country.controllers;

import com.example.country.domain.Country;
import com.example.country.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/countries")
public class CountryController {

    private final CountryRepository countryRepository;

    @GetMapping("/country/{id}")
    public Country findCountry(@PathVariable Long id) {
        return countryRepository.findById(id).get();
    }


}
