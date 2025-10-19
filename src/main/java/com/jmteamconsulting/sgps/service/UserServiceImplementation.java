package com.jmteamconsulting.sgps.service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jmteamconsulting.sgps.model.entity.City;
import com.jmteamconsulting.sgps.model.entity.Contact;
import com.jmteamconsulting.sgps.model.entity.Country;
import com.jmteamconsulting.sgps.model.entity.Department;
import com.jmteamconsulting.sgps.model.entity.Document;
import com.jmteamconsulting.sgps.model.entity.DocumentType;
import com.jmteamconsulting.sgps.model.entity.PhoneNumber;
import com.jmteamconsulting.sgps.model.entity.User;
import com.jmteamconsulting.sgps.repository.UserRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

/**
 * Servicio de la entidad User.
 * Define implementaciones de métodos que usan funcionalidad del UserRepository para realizar
 * operaciones sobre registros User.
 */
@Service
public class UserServiceImplementation implements UserService {

    /**
     * Manager de Entity usado para la entidad User.
     * Realiza operaciones sobre User en la base de datos.
     */
    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CountryService countryService;

    @Autowired
    DepartmentService departmentService;

    @Autowired
    CityService cityService;

    @Autowired
    PhoneNumberService phoneNumberService;

    @Autowired
    DocumentTypeService documentTypeService;

    @Autowired
    DocumentService documentService;

    @Autowired
    ContactService contactService;

    // Usados para la generación de tokens de sesión.
    private static SecureRandom secureRandom = new SecureRandom();
    private static Encoder base64Encoder = Base64.getUrlEncoder().withoutPadding();

    /**
     * Genera un token de sesión aleatorio de autenticación, criptográficamente "seguro".
     * 
     * @return Token de sesión.
     */
    private static String generateRandomAuthToken() {
        byte randomBytes[] = new byte[32];
        secureRandom.nextBytes(randomBytes);

        String token = base64Encoder.encodeToString(randomBytes);

        return token;
    }

    /**
     * Intenta identificar un User con el sistema.
     * 
     * @param email El email del User que se busca autenticar con el sistema.
     * @param password El password del User que se busca autenticat con el sistema.
     * @return Si el User existe, y si el email y password coinciden, entonces se regresa un token de sesión. De lo contrario, se regresa null, 
     */
    @Override
    public String authenticateUser(String email, String password) {
        // Intenta obtener el User cuyo email es email. Si no existe, se regresa null.
        User user = this.findByEmail(email).orElse(null);

        if (user == null) {
            return null;
        }

        // Comprueba si el email y password del user que se busca autenticar coinciden.
        // Si coinciden, se regresa un nuevo token elatorio seguro. De lo contrario, se regresa null.
        if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
            return generateRandomAuthToken();
        }

        return null;
    }

    /**
     * Comprueba si existe un User en la base de datos cuyo ID (llave primaria) es id.
     * 
     * @param id El ID del usuario a commprobar.
     * @return true si el User existe. De lo contrario, false.
     */
    @Override
    public boolean existsById(Long id) {
        return userRepository.existsById(id);
    }

    /**
     * Obtiene un User por su campo email.
     * Implementado por JPA en tiempo de ejecución.
     * 
     * @param email email del usuario a obtener.
     * @return El usuario cuyo email es email, opcionalmente, dependiendo de si existe o no.
     **/
    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Obtiene un User por su campo id, su llave primaria.
     * 
     * @param id el ID del usuario a obtener.
     * @return El usuario cuyo ID es id, opcionalmente, dependiendo de si existe o no.
     */
    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    /**
     * Obtiene un listado de todos los User registrados en el sistema.
     * 
     * @return Listado de Users en el sistema. Estará en blanco si no existen Users.
     */
    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    /**
     * Actualiza un User que existe en la base de datos con los datos suministrados.
     * 
     * Note que las operaciones save de este método funcionan como una sola transacción.
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
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public User save(User user, String names, String surname, Long documentTypeId, String documentNumber, String email, String password, Long countryId, Long departmentId, Long cityId, String phoneNumber, String address1, String address2, Integer zipCode) {
        // Obtiene referencia al DocumentType cuyo ID fue suministrado por argumentos.
        DocumentType documentType = documentTypeService.findById(documentTypeId).get();

        // Actualiza el Document existente del User que se busca actualizar.
        Document document = user.getDocument();
        document.setDocumentType(documentType);
        document.setNumber(documentNumber);
        documentService.save(document);

        // Obtiene referencia a objetos exisentes de City, Department, y Country.
        Country country = countryService.findById(countryId).get();
        Department department = departmentService.findById(departmentId).get();
        City city = cityService.findById(cityId).get();

        // Crea un nuevo registro Contact con información de contacto del User que se busca actualizar.
        Contact contact = user.getContact();
        contact.setCountry(country);
        contact.setDepartment(department);
        contact.setCity(city);
        contact.setAddress1(address1);
        contact.setAddress2(address2);
        contact.setZipCode(zipCode);
        contactService.save(contact);

        // Se crea y guarda un objeto PhoneNumber en la base de datos, asociado al User que se está p
        PhoneNumber phoneNumberObject = new PhoneNumber(country, contact, phoneNumber);
        phoneNumberService.save(phoneNumberObject);
        
        // Se actualizan los campos de User con los valores suministrados, y se guarda el objeto Usre.
        user.setNames(names);
        user.setSurname(surname);
        user.setEmail(email);
        user.setPassword(password);
        userRepository.save(user);

        // Importante: se refresca la instancia antes de regresarla. Es necesario para incluir algunos campos actualizados de sus relaciones.
        entityManager.refresh(user);

        return userRepository.save(user);
    }

    /**
     * Crea un nuevo User en la base de datos.
     * 
     * Note que las operaciones save de este método funcionan como una sola transacción.
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
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public User save(String names, String surname, Long documentTypeId, String documentNumber, String email, String password, Long countryId, Long departmentId, Long cityId, String phoneNumber, String address1, String address2, Integer zipCode) {
        // Obtiene referencias a objetos estáticos de la base de datos de los cuales algunas relaciones de User dependen.
        Country country = countryService.findById(countryId).get();
        Department department = departmentService.findById(departmentId).get();
        City city = cityService.findById(cityId).get();
        DocumentType documentType = documentTypeService.findById(documentTypeId).get();
        
        // Crea un nuevo objeto Contact con la información suministrada.
        Contact contact = new Contact();
        contact.setCountry(country);
        contact.setDepartment(department);
        contact.setCity(city);
        contact.setAddress1(address1);
        contact.setAddress2(address2);
        contact.setZipCode(zipCode);
        contactService.save(contact);

        // Crea un nuevo objeto Document con la información suministrada.
        Document document = new Document();
        document.setDocumentType(documentType);
        document.setNumber(documentNumber);

        // Crea un uevo objeto PhoneNumber con la información suministrada.
        PhoneNumber phoneNumberObject = new PhoneNumber();
        phoneNumberObject.setCountry(country);
        phoneNumberObject.setContact(contact);
        phoneNumberObject.setNumber(phoneNumber);

        // Crea un nuevo User en la base de datos con la información suministrada.
        User user = new User(document, contact, email, names, surname, password);
        contact.setUser(user);
        document.setUser(user);

        // Crea el nuevo User en la base dedatos y guarda su PhoneNumber
        userRepository.save(user);
        phoneNumberService.save(phoneNumberObject);

        // Importante: se refresca la instancia antes de regresarla. Es necesario para incluir algunos campos actualizados de sus relaciones.
        entityManager.refresh(user);
        
        return user;
    }

    /**
     * Elimina el User de la base de datoa cuyo ID es id, junto a todos sus registros relacionados.
     * 
     * @param id El ID del User que se busca eliminar.
     */
    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}
