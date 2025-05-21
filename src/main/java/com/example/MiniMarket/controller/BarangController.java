package com.example.MiniMarket.controller;
import com.example.MiniMarket.model.Barang;
import com.example.MiniMarket.model.Merk;
import com.example.MiniMarket.service.MerkService;
import com.example.MiniMarket.service.BarangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
@Controller
@RequestMapping("/barang")
public class BarangController {
    private final MerkService merkService;

    @Autowired
    private BarangService barangService;

    BarangController(MerkService merkService) {
        this.merkService = merkService;
    }

    @GetMapping
    public String listBarang(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            Model model) {
        
        model.addAttribute("barangList", barangService.searchBarang(keyword, sortBy, sortDir));
        model.addAttribute("keyword", keyword);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", "asc".equals(sortDir) ? "desc" : "asc");
        
        return "barang/index";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("barang", new Barang());
        model.addAttribute("merkList", merkService.getAllMerk());
        model.addAttribute("isEdit", false);
        return "barang/add";
    }

    @PostMapping("/add")
    public String addBarang(@ModelAttribute Barang barang, Model model, RedirectAttributes redirectAttributes) {
        // Validate input
        if (barang.getNama() == null || barang.getNama().trim().isEmpty()) {
            model.addAttribute("error", "Nama barang tidak boleh kosong");
            model.addAttribute("merkList", merkService.getAllMerk());
            model.addAttribute("isEdit", false);
            return "barang/add";
        }
        
        if (barang.getMerk() == null || barang.getMerk().getId() == null) {
            model.addAttribute("error", "Pilih merk untuk barang");
            model.addAttribute("merkList", merkService.getAllMerk());
            model.addAttribute("isEdit", false);
            return "barang/add";
        }
        
        if (barang.getHarga() == null || barang.getHarga() <= 0) {
        model.addAttribute("error", "Harga harus lebih dari 0");
        model.addAttribute("merkList", merkService.getAllMerk());
        model.addAttribute("isEdit", false);
        return "barang/add";
    }
        // Get the full Merk object from the ID
        Long merkId = barang.getMerk().getId();
        Merk merk = merkService.getMerkById(merkId);
        if (merk == null) {
            model.addAttribute("error", "Merk tidak valid");
            model.addAttribute("merkList", merkService.getAllMerk());
            model.addAttribute("isEdit", false);
            return "barang/add";
        }
        
        barang.setMerk(merk);
        barang.setId(System.currentTimeMillis()); // Generate ID
        barangService.addBarang(barang);
        
        redirectAttributes.addFlashAttribute("success", "Data barang berhasil ditambahkan");
        return "redirect:/barang";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Barang barang = barangService.getBarangById(id);
        if (barang == null) {
            redirectAttributes.addFlashAttribute("error", "Barang dengan ID " + id + " tidak ditemukan");
            return "redirect:/barang";
        }
        
        model.addAttribute("barang", barang);
        model.addAttribute("merkList", merkService.getAllMerk());
        model.addAttribute("isEdit", true);
        return "barang/add";
    }

    @PostMapping("/edit/{id}")
    public String editBarang(@PathVariable Long id, @ModelAttribute Barang barang, 
                              Model model, RedirectAttributes redirectAttributes) {
        // Validate input
        if (barang.getNama() == null || barang.getNama().trim().isEmpty()) {
            model.addAttribute("error", "Nama barang tidak boleh kosong");
            model.addAttribute("merkList", merkService.getAllMerk());
            model.addAttribute("isEdit", true);
            return "barang/add";
        }
        
        if (barang.getMerk() == null || barang.getMerk().getId() == null) {
            model.addAttribute("error", "Pilih merk untuk barang");
            model.addAttribute("merkList", merkService.getAllMerk());
            model.addAttribute("isEdit", true);
            return "barang/add";
        }

         if (barang.getHarga() == null || barang.getHarga() <= 0) {
            model.addAttribute("error", "Harga harus lebih dari 0");
            model.addAttribute("merkList", merkService.getAllMerk());
            model.addAttribute("isEdit", true);
        return "barang/add";
        }
        
        // Ensure the ID from path is set on the object
        barang.setId(id);
        
        // Get the full Merk object from the ID
        Long merkId = barang.getMerk().getId();
        Merk merk = merkService.getMerkById(merkId);
        if (merk == null) {
            model.addAttribute("error", "Merk tidak valid");
            model.addAttribute("merkList", merkService.getAllMerk());
            model.addAttribute("isEdit", true);
            return "barang/add";
        }
        
        barang.setMerk(merk);
        barangService.updateBarang(barang);
        
        redirectAttributes.addFlashAttribute("success", "Data barang berhasil diperbarui");
        return "redirect:/barang";
    }

    @GetMapping("/delete/{id}")
    public String deleteBarang(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Barang barang = barangService.getBarangById(id);
        if (barang != null) {
            barangService.deleteBarang(id);
            redirectAttributes.addFlashAttribute("success", "Barang '" + barang.getNama() + "' berhasil dihapus");
        } else {
            redirectAttributes.addFlashAttribute("error", "Barang dengan ID " + id + " tidak ditemukan");
        }
        return "redirect:/barang";
    }
}
