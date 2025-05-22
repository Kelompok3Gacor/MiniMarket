package com.example.MiniMarket.service;
import com.example.MiniMarket.model.Barang;
import java.util.List;
public interface BarangService {
    List<Barang> getAllBarang();
    Barang getBarangById(Long id);
    void addBarang(Barang barang);
    void updateBarang(Barang barang);
    void deleteBarang(Long id);
    List<Barang> searchBarang(String keyword, String sortBy, String sortDir);
}
