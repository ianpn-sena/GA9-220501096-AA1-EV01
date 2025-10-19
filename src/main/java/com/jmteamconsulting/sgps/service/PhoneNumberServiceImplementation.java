package com.jmteamconsulting.sgps.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jmteamconsulting.sgps.model.entity.PhoneNumber;
import com.jmteamconsulting.sgps.repository.PhoneNumberRepository;

/**
 * Servicio de la entidad PhoneNumber.
 * Define implementaciones de métodos que usan funcionalidad del PhoneNumberRepository para realizar
 * operaciones sobre registros PhoneNumber.
 */
@Service
public class PhoneNumberServiceImplementation implements PhoneNumberService {
    @Autowired
    PhoneNumberRepository phoneNumberRepository;

   /**
     * Condicionalmente obtiene un registro PhoneNumber de la base de datos cuyo ID es id.
     * 
     * @param id El ID del PhoneNumber que se busca obtener.
     * @return Opcionalmente y condicionalmente el PhoneNumber que se está buscando. Puede o no existir.
     */
    @Override
    public Optional<PhoneNumber> findById(Long id) {
        return phoneNumberRepository.findById(id);
    }

    /**
     * Guarda un nuevo PhoneNumber o actualiza un PhoneNumber existente en la base de datos.
     * 
     * @param phoneNumber El PhoneNumber que se busca crear o actualizar. Si document contiene un ID,entonces es actualizado; de lo contraro, es creado.
     * @return El PhoneNumber actualizado en caso de  existir; o, si no existe, entonces un nuevo PhoneNumber acutlizado en la base de datos.
     */
    @Override
    public PhoneNumber save(PhoneNumber phoneNumber) {
        return phoneNumberRepository.save(phoneNumber);
    }
}
