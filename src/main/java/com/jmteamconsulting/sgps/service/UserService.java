package com.jmteamconsulting.sgps.service;

import java.util.List;
import java.util.Optional;

import com.jmteamconsulting.sgps.model.entity.User;

public interface UserService {
    public String authenticateUser(String email, String password);
    public Optional<User> findByEmail(String email);
    public Optional<User> findById(Long id);
    public List<User> findAll();
    public User save(User user, String names, String surname, Long documentTypeId, String documentNumber, String email, String password, Long countryId, Long departmentId, Long cityId, String phoneNumber, String address1, String address2, Integer zipCode);
    public User save(String names, String surname, Long documentTypeId, String documentNumber, String email, String password, Long countryId, Long departmentId, Long cityId, String phoneNumber, String address1, String address2, Integer zipCode);
}
