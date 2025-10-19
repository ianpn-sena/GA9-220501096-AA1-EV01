package com.jmteamconsulting.sgps.service;

import java.util.Optional;

import com.jmteamconsulting.sgps.model.entity.Country;

/**
 * Servicio de la entidad Country.
 * Define métodos que usan funcionalidad del CountryRepository JPA que se deben implementar.
 */
public interface CountryService {
    /**
     * Condicionalmente obtiene un registro Country de la base de datos cuyo ID es id.
     * 
     * @param id El ID del Country que se busca obtener.
     * @return Opcionalmente y condicionalmente el Country que se está buscando. Puede o no existir.
     */
    public Optional<Country> findById(Long id);
}
