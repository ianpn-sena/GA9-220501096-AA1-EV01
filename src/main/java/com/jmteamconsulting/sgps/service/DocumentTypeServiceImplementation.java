package com.jmteamconsulting.sgps.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jmteamconsulting.sgps.model.entity.DocumentType;
import com.jmteamconsulting.sgps.repository.DocumentTypeRepository;

/**
 * Servicio de la entidad DocumentType.
 * Define implementaciones de métodos que usan funcionalidad del DocumentTypeRepository para realizar
 * operaciones sobre registros DocumentType.
 */
@Service
public class DocumentTypeServiceImplementation implements DocumentTypeService {
    @Autowired
    DocumentTypeRepository documentTypeRepository;

   /**
     * Condicionalmente obtiene un registro DocumentType de la base de datos cuyo ID es id.
     * 
     * @param id El ID del DocumentType que se busca obtener.
     * @return Opcionalmente y condicionalmente el DocumentType que se está buscando. Puede o no existir.
     */
    @Override
    public Optional<DocumentType> findById(Long id) {
        return documentTypeRepository.findById(id);
    }
}
