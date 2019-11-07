package com.example.country.service;

import com.example.country.domain.Country;
import com.example.country.exceptions.NoSuchCustomerException;
import com.example.country.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CountryService {

    private final CountryRepository countryRepository;

    public Country findCountryById(long id) {
        return countryRepository.findById(id).orElseThrow(()-> {
            log.error("Country with countryId {} not found", id);
            return new NoSuchCustomerException(id);
        });
    }
}
