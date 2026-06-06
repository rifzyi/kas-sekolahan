package model;

import java.time.LocalDate;

public class Pemasukan {
  private int idPemasukan;
  private LocalDate tanggal;

  private int idKategoriPemasukan;
  private String kategori;

  private String sumberDana;

  private double nominal;
  private String keterangan;

  public Pemasukan() {}

  public Pemasukan(int idPemasukan, LocalDate tanggal, int idKategoriPemasukan, String kategori, double nominal, String keterangan) {
    this.idPemasukan = idPemasukan; this.tanggal = tanggal; this.idKategoriPemasukan = idKategoriPemasukan; this.kategori = kategori; this.nominal = nominal; this.keterangan = keterangan;
  }
  public int getIdPemasukan() { return idPemasukan; }
  public void setIdPemasukan(int idPemasukan) { this.idPemasukan = idPemasukan; }
  public LocalDate getTanggal() { return tanggal; }
  public void setTanggal(LocalDate tanggal) { this.tanggal = tanggal; }
  public int getIdKategoriPemasukan() { return idKategoriPemasukan; }
  public void setIdKategoriPemasukan(int idKategoriPemasukan) { this.idKategoriPemasukan = idKategoriPemasukan; }
  public String getKategori() { return kategori; }
  public void setKategori(String kategori) { this.kategori = kategori; }


  public Pemasukan(int idPemasukan, LocalDate tanggal, String sumberDana,
                   double nominal, String keterangan) {
    this.idPemasukan = idPemasukan;
    this.tanggal = tanggal;
    this.sumberDana = sumberDana;
    this.nominal = nominal;
    this.keterangan = keterangan;
  }

  public int getIdPemasukan() { return idPemasukan; }
  public void setIdPemasukan(int idPemasukan) {
    this.idPemasukan = idPemasukan;
  }
  public LocalDate getTanggal() { return tanggal; }
  public void setTanggal(LocalDate tanggal) { this.tanggal = tanggal; }
  public String getSumberDana() { return sumberDana; }
  public void setSumberDana(String sumberDana) { this.sumberDana = sumberDana; }

  public double getNominal() { return nominal; }
  public void setNominal(double nominal) { this.nominal = nominal; }
  public String getKeterangan() { return keterangan; }
  public void setKeterangan(String keterangan) { this.keterangan = keterangan; }
}
