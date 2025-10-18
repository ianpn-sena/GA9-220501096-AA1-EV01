package com.jmteamconsulting.sgps.service;

import java.util.Optional;

import com.jmteamconsulting.sgps.model.entity.PhoneNumber;

public interface PhoneNumberService {
    public Optional<PhoneNumber> findById(Long id);
    public PhoneNumber save(PhoneNumber phoneNumber);
}
