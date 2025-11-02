package com.example.demo.controllers;

import com.example.demo.entities.Commande;
import com.example.demo.entities.CommandePk;
import com.example.demo.repositories.CommandeRepository;
import com.example.demo.repositories.ClientRepository;
import com.example.demo.repositories.ProduitRepository;
import com.example.demo.entities.Client;
import com.example.demo.entities.Produit;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/commandes")
public class CommandeController {

    @Autowired
    private CommandeRepository commandeRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ProduitRepository produitRepository;

    // Lister toutes les commandes avec filtrage
    @GetMapping("")
    public String listCommandes(@RequestParam(required = false) String statut,
                               @RequestParam(required = false) String dateDebut,
                               @RequestParam(required = false) String dateFin,
                               Model model) {
        
        List<Commande> commandes;
        
        if (statut != null || dateDebut != null || dateFin != null) {
            LocalDate debut = dateDebut != null ? LocalDate.parse(dateDebut) : null;
            LocalDate fin = dateFin != null ? LocalDate.parse(dateFin) : null;
            commandes = commandeRepository.findByFilters(statut, debut, fin);
        } else {
            commandes = commandeRepository.findAll();
        }
        
        model.addAttribute("commandes", commandes);
        model.addAttribute("statuts", List.of("EN_ATTENTE", "CONFIRMEE", "EXPEDIEE", "LIVREE", "ANNULEE"));
        model.addAttribute("statut", statut);
        model.addAttribute("dateDebut", dateDebut);
        model.addAttribute("dateFin", dateFin);
        
        return "commandes/list";
    }

    // Afficher le formulaire d'ajout
    @GetMapping("/signup")
    public String showSignUpForm(Model model) {
        model.addAttribute("commande", new Commande());
        model.addAttribute("clients", clientRepository.findAll());
        model.addAttribute("produits", produitRepository.findAll());
        return "commandes/add-commande";
    }

    // Ajouter une commande
    @PostMapping("/add")
    public String addCommande(@RequestParam Long clientId, 
                             @RequestParam Long produitId,
                             @RequestParam String statut,
                             @RequestParam Double total,
                             Model model) {
        
        try {
            Client client = clientRepository.findById(clientId)
                    .orElseThrow(() -> new IllegalArgumentException("Client invalide: " + clientId));
            Produit produit = produitRepository.findById(produitId)
                    .orElseThrow(() -> new IllegalArgumentException("Produit invalide: " + produitId));

            Commande commande = new Commande();
            
            // Créer l'ID composite
            CommandePk id = new CommandePk();
            id.setClientId(clientId);
            id.setProduitId(produitId);
            id.setDateCreation(LocalDate.now());
            commande.setId(id);
            
            commande.setClient(client);
            commande.setProduit(produit);
            commande.setStatut(statut);
            commande.setTotal(total);

            commandeRepository.save(commande);
            return "redirect:/commandes";
            
        } catch (Exception e) {
            model.addAttribute("error", "Erreur lors de la création de la commande: " + e.getMessage());
            model.addAttribute("clients", clientRepository.findAll());
            model.addAttribute("produits", produitRepository.findAll());
            return "commandes/add-commande";
        }
    }

    // Afficher le formulaire de mise à jour
    @GetMapping("/edit/{clientId}/{produitId}")
    public String showUpdateForm(@PathVariable("clientId") Long clientId,
                                 @PathVariable("produitId") Long produitId,
                                 Model model) {
        try {
            // Trouver la commande par client et produit
            List<Commande> commandes = commandeRepository.findAll();
            Commande commande = commandes.stream()
                    .filter(c -> c.getId().getClientId().equals(clientId) && 
                                 c.getId().getProduitId().equals(produitId))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Commande non trouvée"));
            
            model.addAttribute("commande", commande);
            model.addAttribute("clients", clientRepository.findAll());
            model.addAttribute("produits", produitRepository.findAll());
            return "commandes/edit-commande";
        } catch (Exception e) {
            model.addAttribute("error", "Commande non trouvée");
            return "redirect:/commandes";
        }
    }

    // Mettre à jour la commande
    @PostMapping("/update/{clientId}/{produitId}")
    public String updateCommande(@PathVariable("clientId") Long clientId,
                                 @PathVariable("produitId") Long produitId,
                                 @RequestParam String statut,
                                 @RequestParam Double total,
                                 Model model) {
        try {
            List<Commande> commandes = commandeRepository.findAll();
            Commande commande = commandes.stream()
                    .filter(c -> c.getId().getClientId().equals(clientId) && 
                                 c.getId().getProduitId().equals(produitId))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Commande non trouvée"));

            commande.setStatut(statut);
            commande.setTotal(total);
            
            commandeRepository.save(commande);
            return "redirect:/commandes";
            
        } catch (Exception e) {
            model.addAttribute("error", "Erreur lors de la mise à jour: " + e.getMessage());
            return "redirect:/commandes";
        }
    }

    // Supprimer une commande
    @GetMapping("/delete/{clientId}/{produitId}")
    public String deleteCommande(@PathVariable("clientId") Long clientId,
                                 @PathVariable("produitId") Long produitId,
                                 Model model) {
        try {
            List<Commande> commandes = commandeRepository.findAll();
            Commande commande = commandes.stream()
                    .filter(c -> c.getId().getClientId().equals(clientId) && 
                                 c.getId().getProduitId().equals(produitId))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Commande non trouvée"));

            commandeRepository.delete(commande);
            return "redirect:/commandes";
            
        } catch (Exception e) {
            model.addAttribute("error", "Erreur lors de la suppression: " + e.getMessage());
            return "redirect:/commandes";
        }
    }

    // Voir les détails d'une commande
    @GetMapping("/view/{clientId}/{produitId}")
    public String viewCommande(@PathVariable("clientId") Long clientId,
                              @PathVariable("produitId") Long produitId,
                              Model model) {
        try {
            List<Commande> commandes = commandeRepository.findAll();
            Commande commande = commandes.stream()
                    .filter(c -> c.getId().getClientId().equals(clientId) && 
                                 c.getId().getProduitId().equals(produitId))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Commande non trouvée"));
            
            model.addAttribute("commande", commande);
            return "commandes/view-commande";
        } catch (Exception e) {
            model.addAttribute("error", "Commande non trouvée");
            return "redirect:/commandes";
        }
    }
}
