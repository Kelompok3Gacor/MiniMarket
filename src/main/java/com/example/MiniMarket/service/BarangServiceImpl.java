package com.example.MiniMarket.service;

import com.example.MiniMarket.model.Barang;
import com.example.MiniMarket.repository.BarangRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class BarangServiceImpl implements BarangService {

    @Autowired
    private BarangRepository barangRepository;

    @Override
    public List<Barang> getAllBarang() {
        return barangRepository.findAll();
    }

    @Override
    public Barang getBarangById(Long id) {
        return barangRepository.findById(id).orElse(null);
    }

    @Override
    public void addBarang(Barang barang) {
        barangRepository.save(barang);
    }

    @Override
    public void updateBarang(Barang barang) {
        barangRepository.save(barang); // save juga bisa update otomatis
    }

    @Override
    public void deleteBarang(Long id) {
        barangRepository.deleteById(id);
    }

    @Override
    public List<Barang> searchBarang(String keyword, String sortBy, String sortDir) {
    List<Barang> result;

    if (keyword != null && !keyword.isEmpty()) {
        result = barangRepository.findByNamaContainingIgnoreCase(keyword);
    } else {
        result = barangRepository.findAll();
    }

    Comparator<Barang> comparator;

    switch (sortBy) {
        case "nama":
            comparator = Comparator.comparing(Barang::getNama);
            break;
        case "merk":
            comparator = Comparator.comparing(b -> b.getMerk().getNamaMerk());
            break;
        case "harga":
            comparator = Comparator.comparing(Barang::getHarga);
            break;
        case "stok":
            comparator = Comparator.comparing(Barang::getStok);
            break;
        case "foto":
            comparator = Comparator.comparing(Barang::getFoto);
            break;
        default:
            comparator = Comparator.comparing(Barang::getId);
            break;
    }

    if ("desc".equals(sortDir)) {
        comparator = comparator.reversed();
    }

    return result.stream().sorted(comparator).toList();
}

}
