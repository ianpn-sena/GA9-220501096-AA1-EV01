package com.jmteamconsulting.sgps.service;

import java.util.Optional;

import com.jmteamconsulting.sgps.model.entity.User;

public interface UserService {
    public String authenticateUser(String email, String password);
    public Optional<User> findByEmail(String email);
    public Optional<User> findById(Long id);
}
