package com.jmteamconsulting.sgps.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jmteamconsulting.sgps.model.entity.User;

/**
 * Interfaz repositorio JPA de la entidad User.
 * 
 * Hereda varios métodos JPA de JpaRepository.
 * La mayoría de sus implementaciones están definidas por JPA.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Obtiene un User por su campo email.
     * Implementado por JPA en tiempo de ejecución.
     * 
     * @param email email del usuario a obtener.
     * @return El usuario cuyo email es email, opcionalmente, dependiendo de si existe o no.
     */
    Optional<User> findByEmail(String email);
}