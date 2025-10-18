package com.jmteamconsulting.sgps.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jmteamconsulting.sgps.model.entity.Document;
import com.jmteamconsulting.sgps.repository.DocumentRepository;

@Service
public class DocumentServiceImplementation implements DocumentService {
    @Autowired
    DocumentRepository documentRepository;

    @Override
    public Optional<Document> findById(Long id) {
        return documentRepository.findById(id);
    }

    @Override
    public Document save(Document document) {
        return documentRepository.save(document);
    }
}
