// File: model/Pemasukan.java
package model;

import java.time.LocalDate;

public class Pemasukan {
    private int idPemasukan;
    private LocalDate tanggal;
    private int idKategoriPemasukan;
    private String namaKategori;
    private double nominal;
    private String keterangan;

    public Pemasukan() {
    }

    public Pemasukan(int idPemasukan, LocalDate tanggal, int idKategoriPemasukan, String namaKategori, double nominal, String keterangan) {
        this.idPemasukan = idPemasukan;
        this.tanggal = tanggal;
        this.idKategoriPemasukan = idKategoriPemasukan;
        this.namaKategori = namaKategori;
        this.nominal = nominal;
        this.keterangan = keterangan;
    }

    public int getIdPemasukan() { return idPemasukan; }
    public void setIdPemasukan(int idPemasukan) { this.idPemasukan = idPemasukan; }
    public LocalDate getTanggal() { return tanggal; }
    public void setTanggal(LocalDate tanggal) { this.tanggal = tanggal; }
    public int getIdKategoriPemasukan() { return idKategoriPemasukan; }
    public void setIdKategoriPemasukan(int idKategoriPemasukan) { this.idKategoriPemasukan = idKategoriPemasukan; }
    public String getNamaKategori() { return namaKategori; }
    public void setNamaKategori(String namaKategori) { this.namaKategori = namaKategori; }
    public String getKategori() { return namaKategori; }
    public void setKategori(String kategori) { this.namaKategori = kategori; }
    public double getNominal() { return nominal; }
    public void setNominal(double nominal) { this.nominal = nominal; }
    public String getKeterangan() { return keterangan; }
    public void setKeterangan(String keterangan) { this.keterangan = keterangan; }
}
