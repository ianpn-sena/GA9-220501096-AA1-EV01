package com.jmteamconsulting.sgps.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jmteamconsulting.sgps.model.entity.Country;
import com.jmteamconsulting.sgps.repository.CountryRepository;

/**
 * Servicio de la entidad Country.
 * Define implementaciones de métodos que usan funcionalidad del CountryRepository para realizar
 * operaciones sobre registros Country.
 */
@Service
public class CountryServiceImplementation implements CountryService {
    @Autowired
    CountryRepository countryRepository;

    /**
     * Servicio de la entidad ountry.
     * Define implementaciones de métodos que usan funcionalidad del Cuntry Repository para realizar
     * operaciones sobre registros Country.
     *
     * @param id el ID del Country que se busca obtener.
     */
    @Override
    public Optional<Country> findById(Long id) {
        return countryRepository.findById(id);
    }
}
