package com.jmteamconsulting.sgps.service;

import java.util.Optional;

import com.jmteamconsulting.sgps.model.entity.City;

public interface CityService {
    public Optional<City> findById(Long id);
}
