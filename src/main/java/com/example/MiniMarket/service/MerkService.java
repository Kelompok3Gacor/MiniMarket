package com.example.MiniMarket.service;
import org.springframework.stereotype.Service;
import com.example.MiniMarket.model.Merk;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MerkService {
    private final List<Merk> merkList = new ArrayList<>();
    private Long idCounter = 3L;

    public MerkService() {
        // Data awal
        Merk merkMie = new Merk(1L, "Mie Sedap");
        Merk merkCoffe = new Merk(2L, "Harum Alam");
        merkList.add(merkMie);
        merkList.add(merkCoffe);

    }

    public List<Merk> getAllMerk() {
        return merkList;
    }
    
    // Search and Sort method
    public List<Merk> searchMerk(String keyword, String sortBy, String sortDir) {
        List<Merk> result = merkList;
        
        // Apply search if keyword provided
        if (keyword != null && !keyword.isEmpty()) {
            final String lowerKeyword = keyword.toLowerCase();
            result = result.stream()
                .filter(j -> j.getNamaMerk().toLowerCase().contains(lowerKeyword))
                .collect(Collectors.toList());
        }
        
        // Apply sorting
        if (sortBy != null && !sortBy.isEmpty()) {
            Comparator<Merk> comparator;
            
            switch (sortBy) {
                case "namaMerk":
                    comparator = Comparator.comparing(Merk::getNamaMerk);
                    break;
                case "id":
                default:
                    comparator = Comparator.comparing(Merk::getId);
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

    public Merk saveMerk(Merk merk) {
        if (merk.getId() == null) {
            merk.setId(idCounter++);
            merkList.add(merk);
        }
        return merk;
    }

    public Merk getMerkByID(Long id) {
        return merkList.stream().filter(m -> m.getId().equals(id)).findFirst().orElse(null);
    }
    
    public void updateMerk(Merk merk) {
        merkList.replaceAll(m -> m.getId().equals(merk.getId()) ? merk : m);
    }
    
    public void deleteMerk(Long id) {
        merkList.removeIf(j -> j.getId().equals(id));
    }
    public Merk getMerkById(Long id) {
        return merkList.stream()
            .filter(j -> j.getId().equals(id))
            .findFirst()
            .orElse(null);
    }
}
