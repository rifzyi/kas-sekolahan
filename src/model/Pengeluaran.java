package model;

import java.time.LocalDate;

public class Pengeluaran {
  private int idPengeluaran;
  private LocalDate tanggal;
  private int idKategoriPengeluaran;
  private String kategori;
  private double nominal;
  private String keterangan;

  public Pengeluaran() {}

  public Pengeluaran(int idPengeluaran, LocalDate tanggal, int idKategoriPengeluaran, String kategori, double nominal, String keterangan) {
    this.idPengeluaran = idPengeluaran;
    this.tanggal = tanggal;
    this.idKategoriPengeluaran = idKategoriPengeluaran;
    this.kategori = kategori;
    this.nominal = nominal;
    this.keterangan = keterangan;
  }

  public int getIdPengeluaran() { return idPengeluaran; }
  public void setIdPengeluaran(int idPengeluaran) { this.idPengeluaran = idPengeluaran; }
  public LocalDate getTanggal() { return tanggal; }
  public void setTanggal(LocalDate tanggal) { this.tanggal = tanggal; }
  public int getIdKategoriPengeluaran() { return idKategoriPengeluaran; }
  public void setIdKategoriPengeluaran(int idKategoriPengeluaran) { this.idKategoriPengeluaran = idKategoriPengeluaran; }
  public String getKategori() { return kategori; }
  public void setKategori(String kategori) { this.kategori = kategori; }
  public double getNominal() { return nominal; }
  public void setNominal(double nominal) { this.nominal = nominal; }
  public String getKeterangan() { return keterangan; }
  public void setKeterangan(String keterangan) { this.keterangan = keterangan; }
}
