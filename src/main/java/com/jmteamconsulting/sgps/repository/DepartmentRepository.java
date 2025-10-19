package com.jmteamconsulting.sgps.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jmteamconsulting.sgps.model.entity.Department;

/**
 * Interfaz repositorio JPA de la entidad Department.
 * 
 * Hereda varios métodos JPA de JpaRepository.
 * La mayoría de sus implementaciones están definidas por JPA.
 */
@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    
}
