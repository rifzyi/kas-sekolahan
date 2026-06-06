// File: model/Pengeluaran.java
package model;

import java.time.LocalDate;

public class Pengeluaran {
    private int idPengeluaran;
    private LocalDate tanggal;
    private int idKategoriPengeluaran;
    private String namaKategori;
    private double nominal;
    private String keterangan;

    public Pengeluaran() {
    }

    public Pengeluaran(int idPengeluaran, LocalDate tanggal, int idKategoriPengeluaran, String namaKategori, double nominal, String keterangan) {
        this.idPengeluaran = idPengeluaran;
        this.tanggal = tanggal;
        this.idKategoriPengeluaran = idKategoriPengeluaran;
        this.namaKategori = namaKategori;
        this.nominal = nominal;
        this.keterangan = keterangan;
    }

    public int getIdPengeluaran() { return idPengeluaran; }
    public void setIdPengeluaran(int idPengeluaran) { this.idPengeluaran = idPengeluaran; }
    public LocalDate getTanggal() { return tanggal; }
    public void setTanggal(LocalDate tanggal) { this.tanggal = tanggal; }
    public int getIdKategoriPengeluaran() { return idKategoriPengeluaran; }
    public void setIdKategoriPengeluaran(int idKategoriPengeluaran) { this.idKategoriPengeluaran = idKategoriPengeluaran; }
    public String getNamaKategori() { return namaKategori; }
    public void setNamaKategori(String namaKategori) { this.namaKategori = namaKategori; }
    public String getKategori() { return namaKategori; }
    public void setKategori(String kategori) { this.namaKategori = kategori; }
    public double getNominal() { return nominal; }
    public void setNominal(double nominal) { this.nominal = nominal; }
    public String getKeterangan() { return keterangan; }
    public void setKeterangan(String keterangan) { this.keterangan = keterangan; }
}
