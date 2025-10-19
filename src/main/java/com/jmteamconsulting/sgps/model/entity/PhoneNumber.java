package com.jmteamconsulting.sgps.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Modela la entiad PhoneNumber, número telefónic. Define también sus relaciones.
 */
@Entity(name = "PhoneNumber")
@Table(name = "phone_numbers")
public class PhoneNumber {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;
    
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JsonManagedReference
    Country country;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JsonBackReference
    Contact contact;

    @Column(nullable = false)
    String number;

    public PhoneNumber() {

    }

    public PhoneNumber(Country country, Contact contact, String number) {
        this.country = country;
        this.contact = contact;
        this.number = number;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }
}
