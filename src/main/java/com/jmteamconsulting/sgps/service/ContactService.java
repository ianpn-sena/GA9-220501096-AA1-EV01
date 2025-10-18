package com.jmteamconsulting.sgps.service;

import java.util.Optional;

import com.jmteamconsulting.sgps.model.entity.Contact;

public interface ContactService {
    public Optional<Contact> findById(Long id);
    public Contact save(Contact document);
}
