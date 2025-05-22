package com.example.MiniMarket.repository;

import com.example.MiniMarket.model.Barang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BarangRepository extends JpaRepository<Barang, Long> {
    List<Barang> findByNamaContainingIgnoreCase(String keyword);
}
