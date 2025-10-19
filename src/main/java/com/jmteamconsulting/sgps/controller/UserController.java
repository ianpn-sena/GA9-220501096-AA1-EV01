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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Controlador REST principal de la aplicación. Trabaja sobre el modelo User, de manera CRUD,
 * 
 * Contiene métodos de inicio y finalización de sesión ya que las sesiones están relacionadas
 * a datos de Users, como email y password.
 * 
 * Salvo por los endpoints login y logout, todos los endpoints están protegidos por cookies.
 */
@RestController
@RequestMapping("/api/v1")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    ContactService contactService;

    /**
     * Intenta iniciar y guardar una nueva sesión.
     * 
     * Si email y password coinciden con un usuario en el sistema, entonces se regresa
     * una respuesta HTTP 204 no content y se guarda la cookie "SGPS_SESSION_TOKEN". Adicionalmente,
     * en este caso, se asigna el ID del dicho usuario en la cookie "SGPS_SESSION_USER".
     * 
     * Si no existe un usuario cuyo email coincide con el parámetro email, o si existe tal usuario
     * pero su contraseña no coincide con el parámetro password, entonces se regresa una respuesta
     * HTTP 401, no autorizado, y no se envían nuevas cookies.
     * 
     * @param response Servlet utilizado para construir respuestas. Suministrado por el entorno de ejecución. 
     * @param email Dirección email del User a autenticar.
     * @param password Contraseña del User a autenticar.
     * @return Una respuesta con código HTTP 204 o HTTP 401 dependiendo del resultado.
     */
    @PostMapping(path = "login")
    public ResponseEntity<String> login(HttpServletResponse response, @RequestParam(name = "email") String email, @RequestParam(name = "password") String password) {
        // Intenta autenticar el usuario, obteniendo de vuelta un token.
        String token = userService.authenticateUser(email, password);

        // No se pudo autenticar el usuario. Se envía error HTTP 401.
        if (token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Se obtiene el ID del User que existe en el sistema
        Long userId = userService.findByEmail(email).get().getId();

        // Se crean las cookies: una de sesión, y la otra de ID de User.
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

        // Se añaden las cookies a los headers de la respuesta
        response.addCookie(tokenCookie);
        response.addCookie(userIdCookie);

        // Se devuelve una respuesta HTTP 204.
        return ResponseEntity.noContent().build();
    }

    /**
     * Finaliza una sesión, eliminando las cookies usadas por la aplicación.
     * 
     * @param response Servlet utilizado para construir respuestas. Suministrado por el entorno de ejecución. 
     * @return Siempre regresa una respuesta en blanco HTTP 204.
     */
    @GetMapping(path = "logout")
    public ResponseEntity<String> logout(HttpServletResponse response) {
        // Vacía las dos cookies relevantes
        Cookie tokenCookie = new Cookie("SGPS_SESSION_TOKEN", "");
        tokenCookie.setMaxAge(0);
        tokenCookie.setPath("/");

        Cookie userIdCookie = new Cookie("SGPS_SESSION_USER", "");
        userIdCookie.setMaxAge(0);
        userIdCookie.setPath("/");

        // Añade las cookies vacías a los headers de la respuesta
        response.addCookie(tokenCookie);
        response.addCookie(userIdCookie);

        // Finalizado. Regresa HTTP 204.
        return ResponseEntity.noContent().build();
    }

    /**
     * Intenta otener un usuario de la bsse de datos por el parámetro "id".
     * 
     * Este método está protegido por cookies.
     * 
     * @param token El token en la cookie SGPS_SESSION_TOKEN.
     * @param id El ID del usuario a obtener.
     * @return Usuario serializado en JSON con un código de estado 200 en caso de existir. De lo contrario, HTTP 401.
     */
    @GetMapping(path = "user/{id}")
    public ResponseEntity<User> getUserById(@CookieValue(value = "SGPS_SESSION_TOKEN", required = false) String token, @PathVariable(name = "id") Long id) {
        if (token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        User user = userService.findById(id).orElse(null);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(user);
    }

    /**
     * Obtiene todos los User en la base de datos.
     * 
     * Este método está protegido por cookies.
     * 
     * @param token El token en la cookie SGPS_SESSION_TOKEN.
     * @return Respuesta con el User con código HTTP 200 en caso de obtener el User. De lo contrario, respuesta vacía con error HTTP 401 no autorizado.
     */
    @GetMapping(path = "user")
    public ResponseEntity<List<User>> getAllUsers(@CookieValue(value = "SGPS_SESSION_TOKEN", required = false) String token) {
        // Regresa error HTTP 401 no autorizado si el token no está presente.
        if (token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Regresa los usuarios con un código HTTP 200 en caso de tener un token válido. Puede ser un List vacío.
        return ResponseEntity.ok(userService.findAll());
    }

    /**
     * Crea un nuevo User en la base de datos, incluyendo todo registro relacionado que necesite ser creado.
     * 
     * Este método está protegido por cookies.
     * 
     * @param token El token en la cookie SGPS_SESSION_TOKEN.
     * @param userData Objeto POJO que defije todos los campos necesarios del cuerpo de la solicitud. Debe cumplir con las validaciones que define.
     * @return El User que se acaba de crear con un código HTTP 200 si el token es válido y si no hubo errores. De lo contrario
     */
    @PostMapping(path = "user")
    public ResponseEntity<User> createUser(@CookieValue(value = "SGPS_SESSION_TOKEN", required = false) String token, @RequestBody @Valid UserControllerDTO userData) {
        // Regresa error HTTP 401 no autorizado si el token no está presente.
        if (token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Intenta crear un nuevo usuario, obteniendo una nueva instancia de User con un ID asignado.
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
        
        // Regresa una respuesta de éxito HTTP 200 OK.
        return ResponseEntity.ok(user);
    }

    /**
     * Actualia un User existente con datos tomados del cuerpo de la solicitud.
     * Actualiza y crea registros relacionados a User como sea necesario.
     * 
     * Este método está protegido por cookies.
     * 
     * @param token El token en la cookie SGPS_SESSION_TOKEN.
     * @param id El ID del usuario a actualizar.
     * @param userData Objeto POJO que defije todos los campos necesarios del cuerpo de la solicitud. Debe cumplir con las validaciones que define.
     * @return Si no existe el token de sesión, se regresa HTTP 401 no autorizado. Si no existe el User a actualizar, se regresa HTTP 404 no encontrado. Si se actualizó el User exitosamente, se regresa el usuario con los nuevos datos con estado HTTP 200 OK.
     */
    @PutMapping(path = "user/{id}")
    public ResponseEntity<User> updateUser(@CookieValue(value = "SGPS_SESSION_TOKEN", required = false) String token, @PathVariable(name = "id") Long id, @RequestBody @Valid UserControllerDTO userData) {
        // Regresa error HTTP 401 no autorizado si el token no está presente.
        if (token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Se intenta obtener el User existinte con el ID de entrada de la solicitud.
        User user = userService.findById(id).orElse(null);

        // Si el usuario no existe, se regresa error HTTP 404 no encontrado.
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        // Se actualiza el User con los datos de entrada de la solicitud.
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

        // Se regresa el User actualizado con un código HTTP 200 OK.
        return ResponseEntity.ok(user);
    }

    /**
     * Elimina un User de la base de datos.
     * Elimina registros relacionados como sea necesario.
     * 
     * Este método está protegido por cookies.
     * 
     * @param token El token en la cookie SGPS_SESSION_TOKEN.
     * @param id El ID del usuario a eliminar.
     * @return Error HTTP 401 no autorizado si el token es inválido o no existe. Error 404 no encontrado si no existe un usuario con ese ID. De lo contrario, HTTP 204 no content si el registro fue eliminado exitosamente.
     */
    @DeleteMapping(path = "user/{id}")
    public ResponseEntity<User> deleteUserById(@CookieValue(value = "SGPS_SESSION_TOKEN", required = false) String token, @PathVariable(name = "id") Long id) {
        // Regresa error HTTP 401 no autorizado si el token no está presente.
        if (token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Si existe un User con el ID suministrado, se elimina, y se responde con un código de éxito HTTP 204 no content.
        if (userService.existsById(id)) {
            userService.deleteById(id);

            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        // Si el User no existía, entonces se respoonde con error HTTP 404 no encontrado.
        return ResponseEntity.notFound().build();
    }
}
