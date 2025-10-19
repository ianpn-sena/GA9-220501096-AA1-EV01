package com.jmteamconsulting.sgps.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jmteamconsulting.sgps.model.entity.Contact;
import com.jmteamconsulting.sgps.repository.ContactRepository;

/**
 * Servicio de la entidad Contact.
 * Define implementaciones de métodos que usan funcionalidad del ContactRepository para realizar
 * operaciones sobre registros Contact.
 */
@Service
public class ContactServiceImplementation implements ContactService {
    @Autowired
    ContactRepository contactRepository;

    /**
     * Condicionalmente obtiene un registro Contact de la base de datos cuyo ID es id.
     * 
     * @param id El ID del Contact que se busca obtener.
     * @return Opcionalmente y condicionalmente el Contact que se está buscando. Puede o no existir.
     */
    @Override
    public Optional<Contact> findById(Long id) {
        return contactRepository.findById(id);
    }

    /**
     * Crea o actualiza un registro Contact en la base de datos.
     * 
     * @param document El objeto Contact que se gusta guardar o actualizar. Si contiene un ID, se inserta; de lo contrario, se actualiza.
     * @returnEl Contact que se acaba de crear, o que se acaba de actualizar.
     */
    @Override
    public Contact save(Contact contact) {
        return contactRepository.save(contact);
    }
}
