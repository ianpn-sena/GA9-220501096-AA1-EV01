package com.jmteamconsulting.sgps.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jmteamconsulting.sgps.model.entity.PhoneNumber;
import com.jmteamconsulting.sgps.repository.PhoneNumberRepository;

@Service
public class PhoneNumberServiceImplementation implements PhoneNumberService {
    @Autowired
    PhoneNumberRepository phoneNumberRepository;

    @Override
    public Optional<PhoneNumber> findById(Long id) {
        return phoneNumberRepository.findById(id);
    }

    @Override
    public PhoneNumber save(PhoneNumber phoneNumber) {
        return phoneNumberRepository.save(phoneNumber);
    }
}
