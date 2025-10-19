package com.jmteamconsulting.sgps.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jmteamconsulting.sgps.model.entity.Department;
import com.jmteamconsulting.sgps.repository.DepartmentRepository;

/**
 * Servicio de la entidad Department.
 * Define implementaciones de métodos que usan funcionalidad del DepartmentRepository para realizar
 * operaciones sobre registros Department.
 */
@Service
public class DepartmentServiceImplementation implements DepartmentService {
    @Autowired
    DepartmentRepository departmentRepository;

    /**
     * Condicionalmente obtiene un registro Department de la base de datos cuyo ID es id.
     * 
     * @param id El ID del Department que se busca obtener.
     * @return Opcionalmente y condicionalmente el Department que se está buscando. Puede o no existir.
     */
    @Override
    public Optional<Department> findById(Long id) {
        return departmentRepository.findById(id);
    }
}
