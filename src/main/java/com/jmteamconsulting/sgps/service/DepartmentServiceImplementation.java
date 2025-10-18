package com.jmteamconsulting.sgps.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jmteamconsulting.sgps.model.entity.Department;
import com.jmteamconsulting.sgps.repository.DepartmentRepository;

@Service
public class DepartmentServiceImplementation implements DepartmentService {
    @Autowired
    DepartmentRepository departmentRepository;

    @Override
    public Optional<Department> findById(Long id) {
        return departmentRepository.findById(id);
    }
}
