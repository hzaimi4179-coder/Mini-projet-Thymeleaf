package com.example.demo.repositories;

import com.example.demo.entities.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProduitRepository extends JpaRepository<Produit,Long> {
    
    @Query("SELECT p FROM Produit p WHERE " +
           "(:categorie IS NULL OR p.categorie = :categorie) AND " +
           "(:prixMin IS NULL OR p.prix >= :prixMin) AND " +
           "(:prixMax IS NULL OR p.prix <= :prixMax) AND " +
           "(:actif IS NULL OR p.statut = :actif)")
    List<Produit> findByFilters(@Param("categorie") String categorie,
                                @Param("prixMin") Long prixMin,
                                @Param("prixMax") Long prixMax,
                                @Param("actif") Boolean actif);
}
