package com.example.MiniMarket.service;
import com.example.MiniMarket.model.Merk;
import com.example.MiniMarket.service.MerkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.Model;
import com.example.MiniMarket.repository.MerkRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/merk")
@Service
public class MerkService {
    @Autowired
    private MerkRepository merkRepository;

    public List<Merk> getAllMerk() {
        return merkRepository.findAll();
    }

    public List<Merk> searchMerk(String keyword, String sortBy, String sortDir) {
        List<Merk> result;

        if (keyword != null && !keyword.isEmpty()) {
            result = merkRepository.findByNamaMerkContainingIgnoreCase(keyword);
        } else {
            result = merkRepository.findAll();
        }

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

        if ("desc".equals(sortDir)) {
            comparator = comparator.reversed();
        }

        return result.stream().sorted(comparator).collect(Collectors.toList());
    }

    public Merk saveMerk(Merk merk) {
        return merkRepository.save(merk);
    }

    public Merk getMerkById(Long id) {
        return merkRepository.findById(id).orElse(null);
    }

    public void updateMerk(Merk merk) {
        merkRepository.save(merk);
    }

    public void deleteMerk(Long id) {
        merkRepository.deleteById(id);
    }
}
