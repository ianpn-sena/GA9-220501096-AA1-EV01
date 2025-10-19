package com.jmteamconsulting.sgps.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jmteamconsulting.sgps.model.entity.PhoneNumber;

/**
 * Interfaz repositorio JPA de la entidad PhoneNumber.
 * 
 * Hereda varios métodos JPA de JpaRepository.
 * La mayoría de sus implementaciones están definidas por JPA.
 */
@Repository
public interface PhoneNumberRepository extends JpaRepository<PhoneNumber, Long> {
    
}