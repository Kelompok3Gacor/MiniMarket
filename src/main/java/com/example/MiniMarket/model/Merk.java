package com.example.MiniMarket.model;

import jakarta.persistence.*; 

@Entity
@Table(name = "merk")
public class Merk {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nama_merk", nullable = false)
    private String namaMerk;

    // Constructor
    public Merk() {
    }

    public Merk(Long id, String namaMerk) {
        this.id = id;
        this.namaMerk = namaMerk;
    }

    // Getter dan Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNamaMerk() {
        return namaMerk;
    }

    public void setNamaMerk(String namaMerk) {
        this.namaMerk = namaMerk;
    }
}
