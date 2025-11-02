package com.example.demo.repositories;

import com.example.demo.entities.Commande;
import com.example.demo.entities.CommandePk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CommandeRepository extends JpaRepository<Commande, CommandePk> {

    @Query("SELECT c FROM Commande c WHERE " +
           "(:statut IS NULL OR c.statut = :statut) AND " +
           "(:dateDebut IS NULL OR c.id.dateCreation >= :dateDebut) AND " +
           "(:dateFin IS NULL OR c.id.dateCreation <= :dateFin)")
    List<Commande> findByFilters(@Param("statut") String statut,
                                @Param("dateDebut") LocalDate dateDebut,
                                @Param("dateFin") LocalDate dateFin);

}
