package com.jmteamconsulting.sgps.model.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity(name = "Contact")
@Table(name = "contact")
public class Contact {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @OneToOne(optional = false, mappedBy = "contact", fetch = FetchType.LAZY)
    @JsonBackReference
    User user;

    @OneToMany(mappedBy = "contact", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    List<PhoneNumber> phoneNumbers;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JsonManagedReference
    Country country;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JsonManagedReference
    Department department;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JsonManagedReference
    City city;

    @Column(name = "address_1", nullable = false)
    String address1;

    @Column(name = "address_2", nullable = true)
    String address2;

    @Column(name = "zip_code", nullable = true)
    Integer zipCode;

    public Integer getZipCode() {
        return zipCode;
    }

    public void setZipCode(Integer zipCode) {
        this.zipCode = zipCode;
    }

    public List<PhoneNumber> getPhoneNumbers() {
        return phoneNumbers;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public Contact(User user, Country country, Department department, City city, String address1, String address2, Integer zipCode) {
        this.user = user;
        this.country = country;
        this.department = department;
        this.city = city;
        this.address1 = address1;
        this.address2 = address2;
        this.zipCode = zipCode;
    }

    public Contact() {

    }
}
