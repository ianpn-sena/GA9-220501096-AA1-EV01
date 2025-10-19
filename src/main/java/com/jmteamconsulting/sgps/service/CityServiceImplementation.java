package com.jmteamconsulting.sgps.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jmteamconsulting.sgps.model.entity.City;
import com.jmteamconsulting.sgps.repository.CityRepository;

/**
 * Servicio de la entidad City.
 * Define implementaciones de métodos que usan funcionalidad del CityRepository para realizar
 * operaciones sobre registros City.
 */
@Service
public class CityServiceImplementation implements CityService {
    @Autowired
    CityRepository cityRepository;

    /**
     * Condicionalmente obtiene un registro City de la base de datos cuyo ID es id.
     * 
     * @param id El ID de la City que se busca obtener.
     * @return Opcionalmente y condicionalmente la City que se está buscando. Puede o no existir.
     */
    @Override
    public Optional<City> findById(Long id) {
        return cityRepository.findById(id);
    }
}
