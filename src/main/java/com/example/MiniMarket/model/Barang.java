package com.example.MiniMarket.model;

import jakarta.persistence.*;

@Entity
@Table(name = "barang")
public class Barang {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nama;

    @ManyToOne
    @JoinColumn(name = "merk_id") // nama kolom foreign key
    private Merk merk;

    private Integer harga;

    private Integer stok;

    private String foto;

    // Constructor
    public Barang() {}

    public Barang(Long id, String nama, Merk merk, Integer harga, Integer stok, String foto) {
        this.id = id;
        this.nama = nama;
        this.merk = merk;
        this.harga = harga;
        this.stok = stok;
        this.foto = foto;
    }

    // Getter & Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public Merk getMerk() {
        return merk;
    }

    public void setMerk(Merk merk) {
        this.merk = merk;
    }

    public Integer getHarga() {
        return harga;
    }

    public void setHarga(Integer harga) {
        this.harga = harga;
    }

    public Integer getStok() {
        return stok;
    }

    public void setStok(Integer stok) {
        this.stok = stok;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
