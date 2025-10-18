package com.jmteamconsulting.sgps.service;

import java.util.Optional;

import com.jmteamconsulting.sgps.model.entity.Document;

public interface DocumentService {
    public Optional<Document> findById(Long id);
    public Document save(Document document);
}
