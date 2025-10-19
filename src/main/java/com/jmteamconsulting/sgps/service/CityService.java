package com.jmteamconsulting.sgps.service;

import java.util.Optional;

import com.jmteamconsulting.sgps.model.entity.City;

/**
 * Servicio de la entidad User.
 * Define métodos que usan funcionalidad del CityRepository JPA que se deben implementar.
 */
public interface CityService {
    /**
     * Condicionalmente obtiene un registro City de la base de datos cuyo ID es id.
     * 
     * @param id El ID de la City que se busca obtener.
     * @return Opcionalmente y condicionalmente la City que se está buscando. Puede o no existir.
     */
    public Optional<City> findById(Long id);
}
