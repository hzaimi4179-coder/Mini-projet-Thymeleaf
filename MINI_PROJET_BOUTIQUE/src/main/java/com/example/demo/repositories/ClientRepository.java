package com.example.demo.repositories;

import com.example.demo.entities.Client;
import com.example.demo.entities.Commande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client,Long> {

    List<Client> findByNomContainingIgnoreCaseOrEmailContainingIgnoreCase(String nom, String email);

    @Query("SELECT COUNT(c) FROM Commande c WHERE c.client.id = :clientId")
    long countCommandesByClientId(@Param("clientId") Long clientId);

}
