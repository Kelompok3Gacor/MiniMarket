package com.example.MiniMarket.repository;

import com.example.MiniMarket.model.Merk;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MerkRepository extends JpaRepository<Merk, Long> {
    List<Merk> findByNamaMerkContainingIgnoreCase(String keyword);
}

