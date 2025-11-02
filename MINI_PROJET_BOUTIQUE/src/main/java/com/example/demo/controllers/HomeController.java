package com.example.demo.controllers;

import com.example.demo.entities.Client;
import com.example.demo.entities.Produit;
import com.example.demo.repositories.ClientRepository;
import com.example.demo.repositories.ProduitRepository;
import com.example.demo.repositories.CommandeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ProduitRepository produitRepository;

    @Autowired
    private CommandeRepository commandeRepository;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("clients", clientRepository.findAll());
        model.addAttribute("produits", produitRepository.findAll());
        model.addAttribute("commandes", commandeRepository.findAll());
        
        // Statistiques
        model.addAttribute("totalClients", clientRepository.count());
        model.addAttribute("totalProduits", produitRepository.count());
        model.addAttribute("totalCommandes", commandeRepository.count());
        
        return "index";
    }
}
