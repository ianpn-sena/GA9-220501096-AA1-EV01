package com.jmteamconsulting.sgps.service;

import java.util.Optional;

import com.jmteamconsulting.sgps.model.entity.PhoneNumber;

/**
 * Servicio de la entidad PhoneNumber.
 * Define métodos que usan funcionalidad del PhoneNumberRepository JPA que se deben implementar.
 */
public interface PhoneNumberService {
   /**
     * Condicionalmente obtiene un registro PhoneNumber de la base de datos cuyo ID es id.
     * 
     * @param id El ID del PhoneNumber que se busca obtener.
     * @return Opcionalmente y condicionalmente el PhoneNumber que se está buscando. Puede o no existir.
     */
    public Optional<PhoneNumber> findById(Long id);

    /**
     * Guarda un nuevo PhoneNumber o actualiza un PhoneNumber existente en la base de datos.
     * 
     * @param phoneNumber El PhoneNumber que se busca crear o actualizar. Si document contiene un ID,entonces es actualizado; de lo contraro, es creado.
     * @return El PhoneNumber actualizado en caso de  existir; o, si no existe, entonces un nuevo PhoneNumber acutlizado en la base de datos.
     */
    public PhoneNumber save(PhoneNumber phoneNumber);
    
}
