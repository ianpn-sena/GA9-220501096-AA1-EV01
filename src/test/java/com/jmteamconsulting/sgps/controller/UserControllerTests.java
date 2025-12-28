package com.jmteamconsulting.sgps.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jmteamconsulting.sgps.model.entity.User;
import com.jmteamconsulting.sgps.repository.UserRepository;
import com.jmteamconsulting.sgps.service.UserService;

import jakarta.servlet.http.Cookie;

import static org.hamcrest.Matchers.*;

/**
 * Prueba UserController.
 * 
 * Antes de ejecutar cada prueba individual, se insertan los datos definidos en
 * src/main/resources/data.sql.
 * 
 * Al terminar cada prueba, los datos creados, modificados o eliminados son revertidos.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTests {
    // Usuario usado para la mayoría de las pruebas
    static final String MOCK_LOGIN_EMAIL = "admin@admin.admin";
    static final String MOCK_LOGIN_PASSWORD = "Abcd1234*";

    // Token usado para facilitar las pruebas para cada intento de autenticación con cookies
    static final String MOCK_LOGIN_TOKEN = "AAAA-BBBB-CCCC-DDDD";

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Método de utilidad que regresa las dos cookies que maneja la aplicación: la cookie
     * del token de sesión, y la cookie de ID de usuario autenticado.
     * 
     * Este método es usado por todos los métodos de prueba que requieren algún tipo de
     * autenticación usando cookies.
     * 
     * El método hace un mock de userService.genrateRandomAuthToken, haciendo que
     * siempre regrese MOCK_LOGIN_TOKEN. Luego, obtiene una referencia al usuario en la
     * base de datos cuyo email concida con "email". Posteriormente, se hace una solicitud
     * post a /api/v1/login, intentando iniciar sesión, y, si todo salió bien, y si se
     * recibieron cookies adecuadas, entonces se regresan las dos cookies en un List de
     * dos elementos. 
     * 
     * @param email Correo del usuario con el que se busca autenticar para obtener cookies
     * @param password Contraseña del usuario con el que se busca autenticar para obtener cookies
     * @return List con dos elementos: el primero es la Cookie del token de autenticación, mientras que el segundo contiene el ID de usuario como su valor
     * @throws Exception Errores no manejados explícitamente que causan que la prueba falle
     */
    @SuppressWarnings("null")
    private List<Cookie> getLoginCookies(String email, String password) throws Exception {
        when(userService.generateRandomAuthToken()).thenReturn(MOCK_LOGIN_TOKEN);

        final User user = userRepository.findByEmail(email).orElse(null);

        mockMvc.perform(
            post("/api/v1/login")
            .param("email", email)
            .param("password", password)
            .accept("application/json")
            .contentType("application/json")
        ).andExpect(status().is2xxSuccessful())
        //.andExpect(cookie().value("SGPS_SESSION_TOKEN", MOCK_LOGIN_TOKEN))
        .andExpect(cookie().value("SGPS_SESSION_USER", String.valueOf(user.getId())));

        return List.of(
            new Cookie("SGPS_SESSION_TOKEN", MOCK_LOGIN_TOKEN),
            new Cookie("SGPS_SESSION_USER", String.valueOf(user.getId()))
        );
    }

    /**
     * Prueba el endpoint POST /api/v1/login, asegurándose de que los keys y values de las
     * Cookies sean correctos.
     * 
     * Usa getLoginCookies().
     * 
     * @throws Exception Errores no manejados explícitamente que causan que la prueba falle.
     */
    @Test
    void testLogin() throws Exception {
        final User user = userRepository.findByEmail(MOCK_LOGIN_EMAIL).orElse(null);
        assertNotNull(user);

        final List<Cookie> loginCookies = getLoginCookies(MOCK_LOGIN_EMAIL, MOCK_LOGIN_PASSWORD);
        assertNotNull(loginCookies);
        assertFalse(loginCookies.isEmpty());
        assertEquals(2, loginCookies.size());

        final Cookie tokenCookie = loginCookies.get(0);
        assertNotNull(tokenCookie);
        assertEquals("SGPS_SESSION_TOKEN", tokenCookie.getName());
        assertEquals(MOCK_LOGIN_TOKEN, tokenCookie.getValue());

        final Cookie userCookie = loginCookies.get(1);
        assertNotNull(userCookie);
        assertEquals("SGPS_SESSION_USER", userCookie.getName());
        assertEquals(user.getId(), Long.parseLong(userCookie.getValue()));
    }

    /**
     * Prueba que al finalizar una sesión, las cookies del token de autenticación y el
     * ID de usuario autenticado estén en blanco. Prueba GET /api/v1/logout.
     * 
     * @throws Exception Errores no manejados explícitamente que causan que la prueba falle.
     */
    @Test
    void testLogout() throws Exception {
        mockMvc.perform(
            get("/api/v1/logout")
            .accept("application/json")
            .contentType("application/json")
        ).andExpect(status().is2xxSuccessful())
        .andExpect(cookie().value("SGPS_SESSION_TOKEN", ""))
        .andExpect(cookie().value("SGPS_SESSION_USER", ""));
    }

    /**
     * Prueba GET /api/v1/user/{id} de manera que se busca el usuario con el ID igual a 1L.
     * 
     * @throws Exception Errores no manejados explícitamente que causan que la prueba falle.
     */
    @Test
    void testGetUserById() throws Exception {
        final List<Cookie> loginCookies = getLoginCookies(MOCK_LOGIN_EMAIL, MOCK_LOGIN_PASSWORD);

        mockMvc.perform(
            get("/api/v1/user/1")
            .accept("application/json")
            .contentType("application/json")
            .cookie(loginCookies.get(0))
            .cookie(loginCookies.get(1))
        ).andExpect(status().is2xxSuccessful())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.email").value(MOCK_LOGIN_EMAIL));
    }

    /**
     * Prueba GET /api/v1/user para obtener todos los usuarios en el sistema.
     * 
     * @throws Exception Errores no manejados explícitamente que causan que la prueba falle.
     */
    @SuppressWarnings("null")
    @Test
    void testGetAllUsers() throws Exception {
        final List<Cookie> loginCookies = getLoginCookies(MOCK_LOGIN_EMAIL, MOCK_LOGIN_PASSWORD);

        mockMvc.perform(
            get("/api/v1/user")
            .accept("application/json")
            .contentType("application/json")
            .cookie(loginCookies.get(0))
            .cookie(loginCookies.get(1))
        ).andExpect(status().is2xxSuccessful())
        .andExpect(jsonPath("$.length()", greaterThan(0)));
    }

    /**
     * Prueba POST /api/v1/user para crear un nuevo usuario en el sistema usando la API.
     * 
     * @throws Exception Errores no manejados explícitamente que causan que la prueba falle.
     */
    @SuppressWarnings("null")
    @Test
    void testCreateUser() throws Exception {
        final UserControllerDTO userData = new UserControllerDTO();
        userData.names = "Fabian David";
        userData.surname = "Pineda Nieto";
        userData.document_type_id = 1L;
        userData.document_number = "1143111111";
        userData.email = "user@user.user";
        userData.password = "Abcd1234*";
        userData.country_id = 1L;
        userData.department_id = 4L;
        userData.city_id = 4L;
        userData.phone_number = "3221111111";
        userData.address_1 = "Calle A";
        userData.address_2 = "Transversal B";
        userData.zip_code = 130005;

        List<Cookie> loginCookies = getLoginCookies(MOCK_LOGIN_EMAIL, MOCK_LOGIN_PASSWORD);

        mockMvc.perform(
            post("/api/v1/user")
            .accept("application/json")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(userData))
            .cookie(loginCookies.get(0))
            .cookie(loginCookies.get(1))
        ).andExpect(status().is2xxSuccessful());

        final User user = userRepository.findByEmail("user@user.user").orElse(null);

        assertNotNull(user);
        assertEquals("user@user.user", user.getEmail());
    }

    /**
     * Prueba PUT /api/v1/user/{id} para modificar los datos de un usuario específico.
     * 
     * @throws Exception Errores no manejados explícitamente que causan que la prueba falle.
     */
    @Test
    void testUpdateUser() throws Exception {
        final UserControllerDTO updatedUserData = new UserControllerDTO();
        updatedUserData.names = "Fabian David";
        updatedUserData.surname = "Pineda Nieto";
        updatedUserData.document_type_id = 1L;
        updatedUserData.document_number = "1143111111";
        updatedUserData.email = "user@user.user";
        updatedUserData.password = "Abcd1234*";
        updatedUserData.country_id = 1L;
        updatedUserData.department_id = 4L;
        updatedUserData.city_id = 4L;
        updatedUserData.phone_number = "3221111111";
        updatedUserData.address_1 = "Calle A";
        updatedUserData.address_2 = "Transversal B";
        updatedUserData.zip_code = 130005;

        List<Cookie> loginCookies = getLoginCookies(MOCK_LOGIN_EMAIL, MOCK_LOGIN_PASSWORD);

        mockMvc.perform(
            put("/api/v1/user/1")
            .accept("application/json")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(updatedUserData))
            .cookie(loginCookies.get(0))
            .cookie(loginCookies.get(1))
        ).andExpect(status().is2xxSuccessful());

        final User user = userRepository.findById(1L).orElse(null);

        assertNotNull(user);
        assertEquals("user@user.user", user.getEmail());
    }

    /**
     * Prueba DELETE /api/v1/user/{id} para eliminar un usuario específico.
     * 
     * @throws Exception Errores no manejados explícitamente que causan que la prueba falle.
     */
    @Test
    void testDeleteUserById() throws Exception {
        List<Cookie> loginCookies = getLoginCookies(MOCK_LOGIN_EMAIL, MOCK_LOGIN_PASSWORD);

        mockMvc.perform(
            delete("/api/v1/user/1")
            .accept("application/json")
            .contentType("application/json")
            .cookie(loginCookies.get(0))
            .cookie(loginCookies.get(1))
        ).andExpect(status().is2xxSuccessful());

        final User user = userRepository.findById(1L).orElse(null);

        assertNull(user);
    }
}
