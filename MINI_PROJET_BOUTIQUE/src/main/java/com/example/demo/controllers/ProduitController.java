package com.example.demo.controllers;

import com.example.demo.entities.Produit;
import com.example.demo.repositories.ProduitRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import java.util.List;


@Controller
public class ProduitController {

    @Autowired
    private ProduitRepository produitRepository;

    @GetMapping("/produits")
    public String listProduits(@RequestParam(required = false) String categorie,
                              @RequestParam(required = false) Long prixMin,
                              @RequestParam(required = false) Long prixMax,
                              @RequestParam(required = false) Boolean actif,
                              Model model) {
        
        List<Produit> produits;
        
        if (categorie != null || prixMin != null || prixMax != null || actif != null) {
            produits = produitRepository.findByFilters(categorie, prixMin, prixMax, actif);
        } else {
            produits = produitRepository.findAll();
        }
        
        model.addAttribute("produits", produits);
        model.addAttribute("categories", produitRepository.findAll().stream()
                .map(Produit::getCategorie)
                .distinct()
                .toList());
        model.addAttribute("categorie", categorie);
        model.addAttribute("prixMin", prixMin);
        model.addAttribute("prixMax", prixMax);
        model.addAttribute("actif", actif);
        
        return "produits/list";
    }

    @GetMapping("/produits/signup")
    public String showSignUpForm(Produit produit) {
        return "add-produit";
    }

    @PostMapping("/produits/addproduit")
    public String addProduit(@Valid Produit produit, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-produit";
        }

        produit.updateStatut();
        produitRepository.save(produit);
        return "redirect:/produits";
    }

    @GetMapping("/produits/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Produit produit = produitRepository.findById((long) id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid produit Id:" + id));
        model.addAttribute("produit", produit);
        return "update-produit";
    }
    @PostMapping("/produits/update/{id}")
    public String updateProduit(@PathVariable("id") long id, @Valid Produit produit,
                                 BindingResult result, Model model) {
        if (result.hasErrors()) {
            produit.setId((Long) id);
            return "update-produit";
        }

        produit.updateStatut();
        produitRepository.save(produit);
        return "redirect:/produits";
    }

    @GetMapping("/produits/delete/{id}")
    public String deleteProduit(@PathVariable("id") long id, Model model) {
        Produit produit = produitRepository.findById((long) id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid produit Id:" + id));

        produitRepository.delete(produit);
        return "redirect:/produits";
    }

}
