package com.example.MiniMarket.controller;

import com.example.MiniMarket.model.Merk;
import com.example.MiniMarket.service.MerkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/merk")

public class MerkController {
    @Autowired
    private MerkService merkService;

    @GetMapping
    public String listMerk(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            Model model) {
        
        model.addAttribute("merkList", merkService.searchMerk(keyword, sortBy, sortDir));
        model.addAttribute("keyword", keyword);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", "asc".equals(sortDir) ? "desc" : "asc");
        
        return "merk/index";
    }

    @GetMapping("/add")
    public String addMerkForm(Model model) {
        model.addAttribute("merk", new Merk());
        model.addAttribute("isEdit", false);
        return "merk/add";
    }

    @PostMapping("/add")
    public String saveMerk(@ModelAttribute Merk merk, Model model, RedirectAttributes redirectAttributes) {
        // Validate input
        if (merk.getNamaMerk() == null || merk.getNamaMerk().trim().isEmpty()) {
            model.addAttribute("error", "Nama merk tidak boleh kosong");
            model.addAttribute("merk", merk);
            model.addAttribute("isEdit", false);
            return "merk/add";
        }
        
        // Save merk
        merkService.saveMerk(merk);
        redirectAttributes.addFlashAttribute("success", "Merk berhasil ditambahkan");
        return "redirect:/merk";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Merk merk = merkService.getMerkById(id);
        if (merk == null) {
            model.addAttribute("error", "Merk dengan ID " + id + " tidak ditemukan");
            return "redirect:/merk";
        }
        
        model.addAttribute("merk", merk);
        model.addAttribute("isEdit", true);
        return "merk/add";
    }

    @PostMapping("/edit/{id}")
    public String editMerk(@PathVariable Long id, @ModelAttribute Merk merk, 
                             Model model, RedirectAttributes redirectAttributes) {
        // Validate input
        if (merk.getNamaMerk() == null || merk.getNamaMerk().trim().isEmpty()) {
            model.addAttribute("error", "Nama merk tidak boleh kosong");
            model.addAttribute("merk", merk);
            model.addAttribute("isEdit", true);
            return "merk/add";
        }
        
        // Ensure the ID from path is set on the object
        merk.setId(id);
        merkService.updateMerk(merk);
        redirectAttributes.addFlashAttribute("success", "Merk berhasil diperbarui");
        return "redirect:/merk";
    }
    
    @GetMapping("/delete/{id}")
    public String deleteMerk(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Merk merk = merkService.getMerkById(id);
        if (merk != null) {
            merkService.deleteMerk(id);
            redirectAttributes.addFlashAttribute("success", "Merk '" + merk.getNamaMerk() + "' berhasil dihapus");
        } else {
            redirectAttributes.addFlashAttribute("error", "Merk dengan ID " + id + " tidak ditemukan");
        }
        return "redirect:/merk";
    }
}
