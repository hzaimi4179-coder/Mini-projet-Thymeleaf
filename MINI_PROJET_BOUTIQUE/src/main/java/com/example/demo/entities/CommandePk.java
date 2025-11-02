package com.example.demo.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Embeddable
public class CommandePk implements Serializable {

    private Long clientId;

    private LocalDate dateCreation;

    private Long produitId;


    public CommandePk() {}

    public CommandePk(Long clientId, LocalDate dateCreation, Long produitId) {
        this.clientId = clientId;
        this.dateCreation = dateCreation;
        this.produitId = produitId;
    }

    public CommandePk(Long clientId, LocalDate date) {
    }

    // --- Getters & Setters ---
    public Long getClientId() { return clientId; }
    public void setClientId(Long clientId) { this.clientId = clientId; }

    public LocalDate getDateCreation() { return dateCreation; }
    public void setDateCreation(LocalDate dateCreation) { this.dateCreation = dateCreation; }

    public Long getProduitId() {
        return produitId;
    }

    public void setProduitId(Long produitId) {
        this.produitId = produitId;
    }
    
}
