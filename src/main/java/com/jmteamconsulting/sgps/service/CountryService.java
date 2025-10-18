package com.jmteamconsulting.sgps.service;

import java.util.Optional;

import com.jmteamconsulting.sgps.model.entity.Country;

public interface CountryService {
    public Optional<Country> findById(Long id);
}
