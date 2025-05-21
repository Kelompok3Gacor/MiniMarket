package com.example.MiniMarket.model;

public class Barang {
    private Long id;
    private String nama;
    private Merk merk;
    private int harga;


    // Constructor
    public Barang() {
    }

    public Barang(Long id, String nama, Merk merk, int harga) {
        this.id = id;
        this.nama = nama;
        this.merk = merk;
        this.harga = harga;
    }

    // Getter dan Setter
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
}
