// File: model/Pengeluaran.java
package model;

import java.sql.Date;

public class Pengeluaran {
    private int idPengeluaran;
    private Date tanggal;
    private int idKategoriPengeluaran;
    private double nominal;
    private String keterangan;
    private int idUserInput;
    private String namaKategori;

    public Pengeluaran() {}
    public Pengeluaran(int idPengeluaran, Date tanggal, int idKategoriPengeluaran, double nominal, String keterangan, int idUserInput, String namaKategori) {
        this.idPengeluaran = idPengeluaran; this.tanggal = tanggal; this.idKategoriPengeluaran = idKategoriPengeluaran; this.nominal = nominal; this.keterangan = keterangan; this.idUserInput = idUserInput; this.namaKategori = namaKategori;
    }
    public int getIdPengeluaran() { return idPengeluaran; }
    public void setIdPengeluaran(int idPengeluaran) { this.idPengeluaran = idPengeluaran; }
    public Date getTanggal() { return tanggal; }
    public void setTanggal(Date tanggal) { this.tanggal = tanggal; }
    public int getIdKategoriPengeluaran() { return idKategoriPengeluaran; }
    public void setIdKategoriPengeluaran(int idKategoriPengeluaran) { this.idKategoriPengeluaran = idKategoriPengeluaran; }
    public double getNominal() { return nominal; }
    public void setNominal(double nominal) { this.nominal = nominal; }
    public String getKeterangan() { return keterangan; }
    public void setKeterangan(String keterangan) { this.keterangan = keterangan; }
    public int getIdUserInput() { return idUserInput; }
    public void setIdUserInput(int idUserInput) { this.idUserInput = idUserInput; }
    public String getNamaKategori() { return namaKategori; }
    public void setNamaKategori(String namaKategori) { this.namaKategori = namaKategori; }
}
