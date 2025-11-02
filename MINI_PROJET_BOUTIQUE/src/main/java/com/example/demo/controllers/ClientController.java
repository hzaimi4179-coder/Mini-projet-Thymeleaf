package com.example.demo.controllers;

import com.example.demo.entities.Client;
import com.example.demo.repositories.ClientRepository;
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
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;

    @GetMapping("/clients")
    public String listClients(@RequestParam(required = false) String search,
                             Model model) {
        
        List<Client> clients;
        
        if (search != null && !search.trim().isEmpty()) {
            clients = clientRepository.findByNomContainingIgnoreCaseOrEmailContainingIgnoreCase(search, search);
        } else {
            clients = clientRepository.findAll();
        }
        
        model.addAttribute("clients", clients);
        model.addAttribute("search", search);
        
        return "clients/list";
    }

    @GetMapping("/clients/signup")
    public String showSignUpForm(Client client) {
        return "add-client";
    }

    @PostMapping("/clients/addclient")
    public String addClient(@Valid Client client, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-client";
        }

        clientRepository.save(client);
        return "redirect:/clients";
    }

    @GetMapping("/clients/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid client Id:" + id));
        model.addAttribute("client", client);
        return "update-client";
    }
    @PostMapping("/clients/update/{id}")
    public String updateClient(@PathVariable("id") long id, @Valid Client client,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            client.setId(id);
            return "update-client";
        }

        clientRepository.save(client);
        return "redirect:/clients";
    }

    @GetMapping("/clients/delete/{id}")
    public String deleteClient(@PathVariable("id") long id, Model model) {
        try {
            Client client = clientRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid client Id:" + id));

            // Vérifier si le client a des commandes associées
            long nombreCommandes = clientRepository.countCommandesByClientId(id);
            if (nombreCommandes > 0) {
                model.addAttribute("error", "Impossible de supprimer ce client car il est associé à " + 
                    nombreCommandes + " commande(s) existante(s). " +
                    "Veuillez d'abord supprimer ou modifier les commandes associées.");
                
                // Récupérer la liste des clients pour réafficher la page
                model.addAttribute("clients", clientRepository.findAll());
                return "clients/list";
            }

            clientRepository.delete(client);
            return "redirect:/clients";
        } catch (Exception e) {
            // Gérer les autres erreurs
            model.addAttribute("error", "Une erreur est survenue lors de la suppression du client : " + e.getMessage());
            
            // Récupérer la liste des clients pour réafficher la page
            model.addAttribute("clients", clientRepository.findAll());
            return "clients/list";
        }
    }

}
