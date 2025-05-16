package com.example.MiniMarket.service;

import com.example.MiniMarket.model.Merk;
import com.example.MiniMarket.model.Barang;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service

public class BarangService {
    private List<Barang> barangList = new ArrayList<>();
    private List<Merk> merkList = new ArrayList<>();

    public BarangService() {
        // Data awal
        Merk merkTI = new Merk(1L, "Teknik Informatika");
        Merk merkSI = new Merk(2L, "Sistem Informasi");
        merkList.add(merkTI);
        merkList.add(merkSI);

        barangList.add(new Barang(1L, "Wahyu", merkTI));
        barangList.add(new Barang(2L, "Andi", merkSI));
        barangList.add(new Barang(3L, "Budi", merkTI));
        barangList.add(new Barang(4L, "Cindy", merkSI));
    }

    // CRUD Barang
    public List<Barang> getAllBarang() {
        return barangList;
    }
    
    // Search and Sort methods
    public List<Barang> searchBarang(String keyword, String sortBy, String sortDir) {
        List<Barang> result = barangList;
        
        // Apply search if keyword provided
        if (keyword != null && !keyword.isEmpty()) {
            final String lowerKeyword = keyword.toLowerCase();
            result = result.stream()
                .filter(m -> m.getNama().toLowerCase().contains(lowerKeyword) || 
                             m.getMerk().getNamaMerk().toLowerCase().contains(lowerKeyword))
                .collect(Collectors.toList());
        }
        
        // Apply sorting
        if (sortBy != null && !sortBy.isEmpty()) {
            Comparator<Barang> comparator;
            
            switch (sortBy) {
                case "nama":
                    comparator = Comparator.comparing(Barang::getNama);
                    break;
                case "merk":
                    comparator = Comparator.comparing(m -> m.getMerk().getNamaMerk());
                    break;
                case "id":
                default:
                    comparator = Comparator.comparing(Barang::getId);
                    break;
            }
            
            // Apply sort direction
            if ("desc".equals(sortDir)) {
                comparator = comparator.reversed();
            }
            
            result = result.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
        }
        
        return result;
    }

    public Barang getBarangById(Long id) {
        return barangList.stream().filter(m -> m.getId().equals(id)).findFirst().orElse(null);
    }

    public void addBarang(Barang barang) {
        barangList.add(barang);
    }

    public void updateBarang(Barang barang) {
        barangList.replaceAll(m -> m.getId().equals(barang.getId()) ? barang : m);
    }

    public void deleteBarang(Long id) {
        barangList.removeIf(m -> m.getId().equals(id));
    }
}
