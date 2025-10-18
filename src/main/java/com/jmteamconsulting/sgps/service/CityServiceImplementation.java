package com.jmteamconsulting.sgps.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jmteamconsulting.sgps.model.entity.City;
import com.jmteamconsulting.sgps.repository.CityRepository;

@Service
public class CityServiceImplementation implements CityService {
    @Autowired
    CityRepository cityRepository;

    @Override
    public Optional<City> findById(Long id) {
        return cityRepository.findById(id);
    }
}
