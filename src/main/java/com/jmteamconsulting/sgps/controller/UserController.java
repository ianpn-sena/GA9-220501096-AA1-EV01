package com.jmteamconsulting.sgps.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jmteamconsulting.sgps.model.entity.User;
import com.jmteamconsulting.sgps.service.UserService;
import com.jmteamconsulting.sgps.service.ContactService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    ContactService contactService;

    @PostMapping(path = "login")
    public ResponseEntity<String> login(HttpServletResponse response, @RequestParam(name = "email") String email, @RequestParam(name = "password") String password) {
        String token = userService.authenticateUser(email, password);

        if (token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Long userId = userService.findByEmail(email).get().getId();

        Cookie tokenCookie = new Cookie("SGPS_SESSION_TOKEN", token);
        tokenCookie.setMaxAge(3600); // Una hora en segundos
        tokenCookie.setSecure(true);
        tokenCookie.setHttpOnly(true);
        tokenCookie.setPath("/");

        Cookie userIdCookie = new Cookie("SGPS_SESSION_USER", String.valueOf(userId));
        userIdCookie.setMaxAge(3600); // Una hora en segundos
        userIdCookie.setSecure(true);
        userIdCookie.setHttpOnly(true);
        userIdCookie.setPath("/");

        response.addCookie(tokenCookie);
        response.addCookie(userIdCookie);

        return ResponseEntity.noContent().build();
    }

    @GetMapping(path = "logout")
    public ResponseEntity<String> logout(HttpServletResponse response) {
        Cookie tokenCookie = new Cookie("SGPS_SESSION_TOKEN", "");
        tokenCookie.setMaxAge(0);
        tokenCookie.setPath("/");

        Cookie userIdCookie = new Cookie("SGPS_SESSION_USER", "");
        userIdCookie.setMaxAge(0);
        userIdCookie.setPath("/");

        response.addCookie(tokenCookie);
        response.addCookie(userIdCookie);

        return ResponseEntity.noContent().build();
    }

    @GetMapping(path = "user/{id}")
    public ResponseEntity<User> getUserById(@CookieValue(value = "SGPS_SESSION_TOKEN", required = false) String token, @PathVariable(name = "id") Long id) {
        if (token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok(userService.findById(id).orElse(null));
    }

    @GetMapping(path = "user")
    public ResponseEntity<List<User>> getAllUsers(@CookieValue(value = "SGPS_SESSION_TOKEN", required = false) String token) {
        if (token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok(userService.findAll());
    }

    @PostMapping(path = "user")
    public ResponseEntity<User> createUser(@CookieValue(value = "SGPS_SESSION_TOKEN", required = false) String token, @RequestBody @Valid UserControllerDTO userData) {
        if (token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        User user = userService.save(
            userData.names,
            userData.surname,
            userData.document_type_id,
            userData.document_number,
            userData.email,
            userData.password,
            userData.country_id,
            userData.department_id,
            userData.city_id,
            userData.phone_number,
            userData.address_1,
            userData.address_2,
            userData.zip_code
        );
        
        return ResponseEntity.ok(user);
    }

    @PutMapping(path = "user/{id}")
    public ResponseEntity<User> updateUser(@CookieValue(value = "SGPS_SESSION_TOKEN", required = false) String token, @PathVariable(name = "id") Long id, @RequestBody @Valid UserControllerDTO userData) {
        if (token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        User user = userService.findById(id).orElse(null);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        user = userService.save(
            user,
            userData.names,
            userData.surname,
            userData.document_type_id,
            userData.document_number,
            userData.email,
            userData.password,
            userData.country_id,
            userData.department_id,
            userData.city_id,
            userData.phone_number,
            userData.address_1,
            userData.address_2,
            userData.zip_code
        );

        return ResponseEntity.ok(user);
    }
}
