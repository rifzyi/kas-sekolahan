package model;

import java.time.LocalDate;

public class Pemasukan {
  private int idPemasukan;
  private LocalDate tanggal;
  private String sumberDana;
  private double nominal;
  private String keterangan;

  public Pemasukan() {}

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
