package com.example.demo.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

@Entity
public class Commande {

    @EmbeddedId
    private CommandePk id;

    @NotBlank(message = "Le statut est obligatoire")
    private String statut;

    private Double total;

    @MapsId("clientId")
    @ManyToOne
    @JoinColumn(name = "client-id")
    private Client client;

    @MapsId("produitId")
    @ManyToOne
    @JoinColumn(name = "produit-id")
    private Produit produit;

    public Commande() {
    }

    // Getters / Setters
    public CommandePk getId() {
        return id;
    }

    public void setId(CommandePk id) {
        this.id = id;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }
}
