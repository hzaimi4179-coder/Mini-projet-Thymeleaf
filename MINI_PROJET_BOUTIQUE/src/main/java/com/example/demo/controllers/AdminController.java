package com.example.demo.controllers;

import com.example.demo.services.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @Autowired
    private StatisticsService statisticsService;

    @GetMapping("/admin/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("totalClients", statisticsService.getTotalClients());
        model.addAttribute("totalProduits", statisticsService.getTotalProduits());
        model.addAttribute("totalCommandes", statisticsService.getTotalCommandes());
        model.addAttribute("totalRevenue", statisticsService.getTotalRevenue());
        model.addAttribute("produitsEnStock", statisticsService.getProduitsEnStock());
        model.addAttribute("produitsEnRupture", statisticsService.getProduitsEnRupture());
        model.addAttribute("commandesEnAttente", statisticsService.getCommandesEnAttente());
        model.addAttribute("recentCommandes", statisticsService.getRecentCommandes());
        model.addAttribute("topProduits", statisticsService.getTopProduits());
        
        return "admin/dashboard-simple";
    }

    @GetMapping("/admin")
    public String adminHome() {
        return "redirect:/admin/dashboard";
    }
}
