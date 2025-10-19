package com.jmteamconsulting.sgps.service;

import java.util.List;
import java.util.Optional;

import com.jmteamconsulting.sgps.model.entity.User;

/**
 * Servicio de la entidad User.
 * Define métodos que usan funcionalidad del UserRepository JPA que se deben implementar.
 */
public interface UserService {
    /**
     * Intenta identificar un User con el sistema.
     * 
     * @param email El email del User que se busca autenticar con el sistema.
     * @param email El password del User que se busca autenticat con el sistema.
     * @return Si el User existe, y si el email y password coinciden, entonces se regresa un token de sesión. De lo contrario, se regresa null, 
     */
    public String authenticateUser(String email, String password);

    /**
     * Comprueba si existe un User en la base de datos cuyo ID (llave primaria) es id.
     * 
     * @param id El ID del usuario a commprobar.
     * @return true si el User existe. De lo contrario, false.
     */
    public boolean existsById(Long id);

    /**
     * Obtiene un User por su campo email.
     * Implementado por JPA en tiempo de ejecución.
     * 
     * @param email email del usuario a obtener.
     * @return El usuario cuyo email es email, opcionalmente, dependiendo de si existe o no.
     **/
    public Optional<User> findByEmail(String email);

    /**
     * Obtiene un User por su campo id, su llave primaria.
     * 
     * @param id el ID del usuario a obtener.
     * @return El usuario cuyo ID es id, opcionalmente, dependiendo de si existe o no.
     */
    public Optional<User> findById(Long id);

    /**
     * Obtiene un listado de todos los User registrados en el sistema.
     * 
     * @return Listado de Users en el sistema. Estará en blanco si no existen Users.
     */
    public List<User> findAll();

    /**
     * Actualiza un User que existe en la base de datos con los datos suministrados.
     * 
     * @param user Objeto User que ya existe en la base de datos. Debe tener un id que no es null.
     * @param names Los nombres de User que se buscan sobreescribir.
     * @param surname Los apellidos del User que se buscan sobreescribir.
     * @param documentTypeId El ID del objeto DocumentType del Document del User que se busca sobreescribir.
     * @param documentNumber El número de documento del Document del User que se busca sobreescribir.
     * @param email El email del User que se busca sobreescribir.
     * @param password El password del User que se susca sobreescribir.
     * @param countryId El ID del Country del Contact, PhoneNumber, y DocumentType del Document del User que se busca sobreescribir.
     * @param departmentId El ID del Department del Contact y City del User que se busca sobreescribir.
     * @param cityId El ID de la City del Contact del User que se busca sobreescribir.
     * @param phoneNumber El número de teléfono en el PhoneNumber del Contact del User que se busca sobreescriir.
     * @param address1 La dirección física del User que se busca sobreescribir. Primera línea.
     * @param address2 La dirección física del User que se busca sobreescribir. Segunda línea.
     * @param zipCode Código ZIP del User que se busca sobreescribir.
     * @return El User que se suministró como entrada, pero con sus datos y los datos de sus objetos relacionados condicionalmente sobreescritos por los datos suministrados en los demás parámetros.
     */
    public User save(User user, String names, String surname, Long documentTypeId, String documentNumber, String email, String password, Long countryId, Long departmentId, Long cityId, String phoneNumber, String address1, String address2, Integer zipCode);

    /**
     * Crea un nuevo User en la base de datos.
     * 
     * @param names Los nombres de User que se busca crear.
     * @param surname Los apellidos del User que se busca crear.
     * @param documentTypeId El ID del objeto DocumentType del Document del User que se busca crear.
     * @param documentNumber El número de documento del Document del User que se busca crear.
     * @param email El email del User que se busca crear.
     * @param password El password del User que se susca crear.
     * @param countryId El ID del Country del Contact, PhoneNumber, y DocumentType del Document del User que se busca crear.
     * @param departmentId El ID del Department del Contact y City del User que se busca crear.
     * @param cityId El ID de la City del Contact del User que se busca crear.
     * @param phoneNumber El número de teléfono en el PhoneNumber del Contact del User que se busca crear.
     * @param address1 La dirección física del User que se busca crear. Primera línea.
     * @param address2 La dirección física del User que se busca crear. Segunda línea.
     * @param zipCode Código ZIP del User que se busca crear.
     * @return El User que se creó usando los datos de los parámetros de este método.
     */
    public User save(String names, String surname, Long documentTypeId, String documentNumber, String email, String password, Long countryId, Long departmentId, Long cityId, String phoneNumber, String address1, String address2, Integer zipCode);

    /**
     * Elimina el User de la base de datoa cuyo ID es id, junto a todos sus registros relacionados.
     * 
     * @param id El ID del User que se busca eliminar.
     */
    public void deleteById(Long id);
}
