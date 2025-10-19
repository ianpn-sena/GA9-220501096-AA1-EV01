package com.jmteamconsulting.sgps.service;

import java.util.Optional;

import com.jmteamconsulting.sgps.model.entity.DocumentType;

/**
 * Servicio de la entidad DocumentType.
 * Define métodos que usan funcionalidad del DocumentTypeRepository JPA que se deben implementar.
 */
public interface DocumentTypeService {
   /**
     * Condicionalmente obtiene un registro DocumentType de la base de datos cuyo ID es id.
     * 
     * @param id El ID del DocumentType que se busca obtener.
     * @return Opcionalmente y condicionalmente el DocumentType que se está buscando. Puede o no existir.
     */
    public Optional<DocumentType> findById(Long id);
}
