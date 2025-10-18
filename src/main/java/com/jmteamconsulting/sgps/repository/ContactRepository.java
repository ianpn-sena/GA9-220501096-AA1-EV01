package com.jmteamconsulting.sgps.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jmteamconsulting.sgps.model.entity.Contact;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
    
}
