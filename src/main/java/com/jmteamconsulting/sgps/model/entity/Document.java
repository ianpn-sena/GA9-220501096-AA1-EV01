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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity(name = "Document")
@Table(name = "documents")
public class Document {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @OneToOne(optional = false, mappedBy = "document", fetch = FetchType.LAZY)
    @JsonBackReference
    User user;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JsonManagedReference
    DocumentType documentType;

    @Column(name = "number", unique = true, nullable = false)
    String number;

    public Document () {

    }

    public Document(User user, DocumentType documentType, String number) {
        this.user = user;
        this.documentType = documentType;
        this.number = number;
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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public DocumentType getDocumentType() {
        return documentType;
    }

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }
}
