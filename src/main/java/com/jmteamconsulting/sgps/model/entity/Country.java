package com.jmteamconsulting.sgps.model.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * Modela la entiad Country, país. Define también sus relaciones.
 */
@Entity(name = "Country")
@Table(name = "countries")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class Country {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    List<Department> departments;

    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    List<PhoneNumber> phoneNumbers;

    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    List<DocumentType> documentTypes;

    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    List<Contact> contacts;

    @Column(name = "name", nullable = false)
    String name;

    @Column(name = "country_code", nullable = false)
    String countryCode;

    @Column(name = "area_code", nullable = false)
    String areaCode;

    public Country() {
        /**
 * Modela la entiad City, ciudad. Define también sus relaciones.
 */
    }

    public Country(String countryCode, String areaCode) {
        this.countryCode = countryCode;
        this.areaCode = areaCode;
    }

    public Country(String name, String countryCode, String areaCode) {
        this.name = name;
        this.countryCode = countryCode;
        this.areaCode = areaCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Department> getDepartments() {
        return departments;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public List<PhoneNumber> getPhoneNumbers() {
        return phoneNumbers;
    }

    public List<DocumentType> getDocumentTypes() {
        return documentTypes;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
