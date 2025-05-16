package com.example.MiniMarket;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.MiniMarket.service.BarangService;
import com.example.MiniMarket.service.MerkService;

@Controller
public class MiniMarketController {
    @Autowired
    private BarangService barangService;
    
    @Autowired
    private MerkService merkService;
    
    @GetMapping("/")
    public String showDashboard(Model model) {
        model.addAttribute("totalBarang", barangService.getAllBarang().size());
        model.addAttribute("totalMerk", merkService.getAllMerk().size());
        return "dashboard";
    }
}
