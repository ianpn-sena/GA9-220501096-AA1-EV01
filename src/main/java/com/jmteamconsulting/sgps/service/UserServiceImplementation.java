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

@Service
public class UserServiceImplementation implements UserService {

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

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public User save(User user, String names, String surname, Long documentTypeId, String documentNumber, String email, String password, Long countryId, Long departmentId, Long cityId, String phoneNumber, String address1, String address2, Integer zipCode) {
        DocumentType documentType = documentTypeService.findById(documentTypeId).get();

        Document document = user.getDocument();
        document.setDocumentType(documentType);
        document.setNumber(documentNumber);
        documentService.save(document);

        Country country = countryService.findById(countryId).get();
        Department department = departmentService.findById(departmentId).get();
        City city = cityService.findById(cityId).get();

        Contact contact = user.getContact();
        contact.setCountry(country);
        contact.setDepartment(department);
        contact.setCity(city);
        contact.setAddress1(address1);
        contact.setAddress2(address2);
        contact.setZipCode(zipCode);
        contactService.save(contact);

        PhoneNumber phoneNumberObject = new PhoneNumber(country, contact, phoneNumber);
        phoneNumberService.save(phoneNumberObject);
        
        user.setNames(names);
        user.setSurname(surname);
        user.setEmail(email);
        user.setPassword(password);
        userRepository.save(user);

        entityManager.refresh(user);

        return userRepository.save(user);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public User save(String names, String surname, Long documentTypeId, String documentNumber, String email, String password, Long countryId, Long departmentId, Long cityId, String phoneNumber, String address1, String address2, Integer zipCode) {
        Country country = countryService.findById(countryId).get();
        Department department = departmentService.findById(departmentId).get();
        City city = cityService.findById(cityId).get();
        DocumentType documentType = documentTypeService.findById(documentTypeId).get();
        
        Contact contact = new Contact();
        contact.setCountry(country);
        contact.setDepartment(department);
        contact.setCity(city);
        contact.setAddress1(address1);
        contact.setAddress2(address2);
        contact.setZipCode(zipCode);
        contactService.save(contact);

        Document document = new Document();
        document.setDocumentType(documentType);
        document.setNumber(documentNumber);

        PhoneNumber phoneNumberObject = new PhoneNumber();
        phoneNumberObject.setCountry(country);
        phoneNumberObject.setContact(contact);
        phoneNumberObject.setNumber(phoneNumber);

        User user = new User(document, contact, email, names, surname, password);
        contact.setUser(user);
        document.setUser(user);

        userRepository.save(user);
        phoneNumberService.save(phoneNumberObject);

        entityManager.refresh(user);
        
        return user;
    }
}
