package com.jmteamconsulting.sgps.service;

import java.util.Optional;

import com.jmteamconsulting.sgps.model.entity.Department;

public interface DepartmentService {
    public Optional<Department> findById(Long id);
}
