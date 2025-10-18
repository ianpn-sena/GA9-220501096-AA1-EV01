package com.jmteamconsulting.sgps.service;

import java.util.Optional;

import com.jmteamconsulting.sgps.model.entity.DocumentType;

public interface DocumentTypeService {
    public Optional<DocumentType> findById(Long id);
}
