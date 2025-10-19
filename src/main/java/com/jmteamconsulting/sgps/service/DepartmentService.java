package com.jmteamconsulting.sgps.service;

import java.util.Optional;

import com.jmteamconsulting.sgps.model.entity.Department;

/**
 * Servicio de la entidad Department.
 * Define métodos que usan funcionalidad del DepartmentRepository JPA que se deben implementar.
 */
public interface DepartmentService {
    /**
     * Condicionalmente obtiene un registro Department de la base de datos cuyo ID es id.
     * 
     * @param id El ID del Department que se busca obtener.
     * @return Opcionalmente y condicionalmente el Department que se está buscando. Puede o no existir.
     */
    public Optional<Department> findById(Long id);
}
