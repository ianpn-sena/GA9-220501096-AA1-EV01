package com.jmteamconsulting.sgps.service;

import java.util.Optional;

import com.jmteamconsulting.sgps.model.entity.Contact;

/**
 * Servicio de la entidad Contact.
 * Define métodos que usan funcionalidad del ContactRepository JPA que se deben implementar.
 */
public interface ContactService {
    /**
     * Condicionalmente obtiene un registro Country de la base de datos cuyo ID es id.
     * 
     * @param id El ID del Contact que se busca obtener.
     * @return Opcionalmente y condicionalmente la Contact que se está buscando. Puede o no existir.
     */
    public Optional<Contact> findById(Long id);

    /**
     * Crea o actualiza un registro Contact en la base de datos.
     * 
     * @param document El objeto Contact que se gusta guardar o actualizar. Si contiene un ID, se inserta; de lo contrario, se actualiza.
     * @return El Contact que se acaba de crear, o que se acaba de actualizar.
     */
    public Contact save(Contact document);
}
