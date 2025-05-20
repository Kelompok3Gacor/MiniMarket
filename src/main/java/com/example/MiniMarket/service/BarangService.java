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
        Merk merkMie = new Merk(1L, "Mie Sedap");
        Merk merkCoffe = new Merk(2L, "Harum Alam");
        merkList.add(merkMie);
        merkList.add(merkCoffe);

        barangList.add(new Barang(1L, "Mie Kuah Rasa Soto", merkMie));
        barangList.add(new Barang(2L, "Mie Kuah Rasa Kari Ayam", merkCoffe));
        barangList.add(new Barang(3L, "Neo Coffe Tiramisu", merkMie));
        barangList.add(new Barang(4L, "Neo Coffe Moccachino", merkCoffe));
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
