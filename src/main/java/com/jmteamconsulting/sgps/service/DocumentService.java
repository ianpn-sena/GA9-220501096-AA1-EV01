package com.jmteamconsulting.sgps.service;

import java.util.Optional;

import com.jmteamconsulting.sgps.model.entity.Document;

/**
 * Servicio de la entidad Document.
 * Define métodos que usan funcionalidad del DocumentRepository JPA que se deben implementar.
 */
public interface DocumentService {
   /**
     * Condicionalmente obtiene un registro Document de la base de datos cuyo ID es id.
     * 
     * @param id El ID del Document que se busca obtener.
     * @return Opcionalmente y condicionalmente el Document que se está buscando. Puede o no existir.
     */
    public Optional<Document> findById(Long id);

    /**
     * Guarda un nuevo Document o actualiza un Document existente en la base de datos.
     * 
     * @param document El Document que se busca crear o actualizar. Si document contiene un ID, entonces es actualizado; de lo contraro, es creado.
     * @return El Document actualizado en caso de  existir; o, si no existe, entonces un nuevo Docoument acutlizado en la base de datos.
     */
    public Document save(Document document);
}
