package com.jmteamconsulting.sgps.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jmteamconsulting.sgps.model.entity.Document;

/**
 * Interfaz repositorio JPA de la entidad Document.
 * 
 * Hereda varios métodos JPA de JpaRepository.
 * La mayoría de sus implementaciones están definidas por JPA.
 */
@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    
}