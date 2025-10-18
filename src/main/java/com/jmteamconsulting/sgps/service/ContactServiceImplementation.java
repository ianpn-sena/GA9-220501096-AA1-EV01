package com.jmteamconsulting.sgps.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jmteamconsulting.sgps.model.entity.Contact;
import com.jmteamconsulting.sgps.repository.ContactRepository;

@Service
public class ContactServiceImplementation implements ContactService {
    @Autowired
    ContactRepository contactRepository;

    @Override
    public Optional<Contact> findById(Long id) {
        return contactRepository.findById(id);
    }

    @Override
    public Contact save(Contact contact) {
        return contactRepository.save(contact);
    }
}
