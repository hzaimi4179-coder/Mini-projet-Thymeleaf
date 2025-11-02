package com.example.demo.services;

import com.example.demo.repositories.ClientRepository;
import com.example.demo.repositories.ProduitRepository;
import com.example.demo.repositories.CommandeRepository;
import com.example.demo.entities.Commande;
import com.example.demo.entities.Produit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StatisticsService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ProduitRepository produitRepository;

    @Autowired
    private CommandeRepository commandeRepository;

    public long getTotalClients() {
        return clientRepository.count();
    }

    public long getTotalProduits() {
        return produitRepository.count();
    }

    public long getTotalCommandes() {
        return commandeRepository.count();
    }

    public double getTotalRevenue() {
        return commandeRepository.findAll().stream()
                .mapToDouble(commande -> commande.getTotal() != null ? commande.getTotal() : 0.0)
                .sum();
    }

    public long getProduitsEnStock() {
        return produitRepository.findAll().stream()
                .filter(p -> p.getStock() > 0)
                .count();
    }

    public long getProduitsEnRupture() {
        return produitRepository.findAll().stream()
                .filter(p -> p.getStock() <= 0)
                .count();
    }

    public long getCommandesEnAttente() {
        return commandeRepository.findAll().stream()
                .filter(commande -> "EN_ATTENTE".equals(commande.getStatut()))
                .count();
    }

    public List<Commande> getRecentCommandes() {
        return commandeRepository.findAll().stream()
                .sorted((c1, c2) -> c2.getId().getDateCreation().compareTo(c1.getId().getDateCreation()))
                .limit(5)
                .collect(Collectors.toList());
    }

    public Map<String, Long> getTopProduits() {
        return commandeRepository.findAll().stream()
                .collect(Collectors.groupingBy(
                        commande -> commande.getProduit().getNom(),
                        Collectors.counting()
                ))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(5)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        java.util.LinkedHashMap::new
                ));
    }
}
