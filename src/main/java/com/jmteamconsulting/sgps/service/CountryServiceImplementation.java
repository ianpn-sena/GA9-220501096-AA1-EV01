package com.jmteamconsulting.sgps.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jmteamconsulting.sgps.model.entity.Country;
import com.jmteamconsulting.sgps.repository.CountryRepository;

@Service
public class CountryServiceImplementation implements CountryService {
    @Autowired
    CountryRepository countryRepository;

    @Override
    public Optional<Country> findById(Long id) {
        return countryRepository.findById(id);
    }
}
