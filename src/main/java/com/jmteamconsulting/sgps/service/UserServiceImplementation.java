package com.jmteamconsulting.sgps.service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jmteamconsulting.sgps.model.entity.User;
import com.jmteamconsulting.sgps.repository.UserRepository;

@Service
public class UserServiceImplementation implements UserService {
    @Autowired
    UserRepository userRepository;

    private static SecureRandom secureRandom = new SecureRandom();
    private static Encoder base64Encoder = Base64.getUrlEncoder().withoutPadding();

    private static String generateRandomAuthToken() {
        byte randomBytes[] = new byte[32];
        secureRandom.nextBytes(randomBytes);

        String token = base64Encoder.encodeToString(randomBytes);

        return token;
    }

    @Override
    public String authenticateUser(String email, String password) {
        User user = this.findByEmail(email).orElse(null);

        if (user == null) {
            return null;
        }

        if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
            return generateRandomAuthToken();
        }

        return null;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }
}
