package com.jmteamconsulting.sgps.model.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
import jakarta.persistence.Table;

/**
 * Modela la entiad DocumentType, tipos de documentos de identidad. Define también sus relaciones.
 */
@Entity(name = "DocumentType")
@Table(name = "document_types")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class DocumentType {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @OneToMany(mappedBy = "documentType", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    List<Document> documents;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JsonManagedReference
    Country country;

    @Column(name = "name", unique = true, nullable = false)
    String name;

    public DocumentType () {

    }

    public DocumentType(Country country, String name) {
        this.country = country;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Document> getDocuments() {
        return documents;
    }

    public String getName() {
        return name;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public void setName(String name) {
        this.name = name;
    }
}
