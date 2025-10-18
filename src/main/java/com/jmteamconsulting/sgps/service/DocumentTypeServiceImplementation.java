package com.jmteamconsulting.sgps.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jmteamconsulting.sgps.model.entity.DocumentType;
import com.jmteamconsulting.sgps.repository.DocumentTypeRepository;

@Service
public class DocumentTypeServiceImplementation implements DocumentTypeService {
    @Autowired
    DocumentTypeRepository documentTypeRepository;

    @Override
    public Optional<DocumentType> findById(Long id) {
        return documentTypeRepository.findById(id);
    }
}
