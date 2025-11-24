package com.jmteamconsulting.sgps.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jmteamconsulting.sgps.model.entity.Document;
import com.jmteamconsulting.sgps.repository.DocumentRepository;

/**
 * Servicio de la entidad Document.
 * Define implementaciones de métodos que usan funcionalidad del DocumentRepository para realizar
 * operaciones sobre registros Document.
 */
@Service
@SuppressWarnings("null")
public class DocumentServiceImplementation implements DocumentService {
    @Autowired
    DocumentRepository documentRepository;

    /**
     * Condicionalmente obtiene un registro Document de la base de datos cuyo ID es id.
     * 
     * @param id El ID del Document que se busca obtener.
     * @return Opcionalmente y condicionalmente el Document que se está buscando. Puede o no existir.
     */
    @Override
    public Optional<Document> findById(Long id) {
        return documentRepository.findById(id);
    }


    /**
     * Guarda un nuevo Document o actualiza un Document existente en la base de datos.
     * 
     * @param document El Document que se busca crear o actualizar. Si document contiene un ID, entonces es actualizado; de lo contraro, es creado.
     * @return El Document actualizado en caso de  existir; o, si no existe, entonces un nuevo Docoument acutlizado en la base de datos.
     */
    @Override
    public Document save(Document document) {
        return documentRepository.save(document);
    }
}
