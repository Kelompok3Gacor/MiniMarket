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
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
    public String addBarang(@ModelAttribute Barang barang, @RequestParam("file") MultipartFile file, Model model, RedirectAttributes redirectAttributes) {

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
        if (barang.getStok() < 0) {
            model.addAttribute("error", "Stok tidak boleh negatif");
            model.addAttribute("merkList", merkService.getAllMerk());
            model.addAttribute("isEdit", false);
            return "barang/add";
        }
        // Cek jika file kosong
        if (file.isEmpty()) {
            model.addAttribute("error", "Foto tidak boleh kosong");
            model.addAttribute("merkList", merkService.getAllMerk());
            model.addAttribute("isEdit", false);
            return "barang/add";
        }

        try {
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            
            // Pastikan path uploadnya bener dan aman
            String uploadDir = System.getProperty("user.dir") + "/uploads/";
            Path uploadPath = Paths.get(uploadDir);
            Files.createDirectories(uploadPath); // Buat folder kalau belum ada

            Path filePath = uploadPath.resolve(fileName); // Lebih aman daripada + operator
            file.transferTo(filePath.toFile());

            barang.setFoto(fileName);
        } catch (IOException e) {
            model.addAttribute("error", "Gagal upload file: " + e.getMessage());
            model.addAttribute("merkList", merkService.getAllMerk());
            model.addAttribute("isEdit", true);
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
public String editBarang(@PathVariable Long id, @ModelAttribute Barang barangForm, Model model, RedirectAttributes redirectAttributes) {

    Barang barang = barangService.getBarangById(id);
    if (barang == null) {
        redirectAttributes.addFlashAttribute("error", "Barang tidak ditemukan");
        return "redirect:/barang";
    }

    // Validasi dari form
    if (barangForm.getNama() == null || barangForm.getNama().trim().isEmpty()) {
        model.addAttribute("error", "Nama barang tidak boleh kosong");
        model.addAttribute("merkList", merkService.getAllMerk());
        model.addAttribute("isEdit", true);
        model.addAttribute("barang", barangForm);
        return "barang/add";
    }

    if (barangForm.getMerk() == null || barangForm.getMerk().getId() == null) {
        model.addAttribute("error", "Pilih merk untuk barang");
        model.addAttribute("merkList", merkService.getAllMerk());
        model.addAttribute("isEdit", true);
        model.addAttribute("barang", barangForm);
        return "barang/add";
    }

    if (barangForm.getHarga() == null || barangForm.getHarga() <= 0) {
        model.addAttribute("error", "Harga harus lebih dari 0");
        model.addAttribute("merkList", merkService.getAllMerk());
        model.addAttribute("isEdit", true);
        model.addAttribute("barang", barangForm);
        return "barang/add";
    }

    if (barangForm.getStok() < 0) {
        model.addAttribute("error", "Stok tidak boleh negatif");
        model.addAttribute("merkList", merkService.getAllMerk());
        model.addAttribute("isEdit", true);
        model.addAttribute("barang", barangForm);
        return "barang/add";
    }

    // Ambil Merk dari DB
    Merk merk = merkService.getMerkById(barangForm.getMerk().getId());
    if (merk == null) {
        model.addAttribute("error", "Merk tidak valid");
        model.addAttribute("merkList", merkService.getAllMerk());
        model.addAttribute("isEdit", true);
        model.addAttribute("barang", barangForm);
        return "barang/add";
    }

    // Update nilai properti dari form
    barang.setNama(barangForm.getNama());
    barang.setHarga(barangForm.getHarga());
    barang.setStok(barangForm.getStok());
    barang.setMerk(merk);
    // Foto tetap yang lama (tidak perlu diubah)

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
