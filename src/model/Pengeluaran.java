package model;

import java.time.LocalDate;

public class Pengeluaran {
  private int idPengeluaran;
  private LocalDate tanggal;
  private String keperluan;
  private double nominal;
  private String keterangan;

  public Pengeluaran() {}

  public Pengeluaran(int idPengeluaran, LocalDate tanggal, String keperluan,
                     double nominal, String keterangan) {
    this.idPengeluaran = idPengeluaran;
    this.tanggal = tanggal;
    this.keperluan = keperluan;
    this.nominal = nominal;
    this.keterangan = keterangan;
  }

  public int getIdPengeluaran() { return idPengeluaran; }
  public void setIdPengeluaran(int idPengeluaran) {
    this.idPengeluaran = idPengeluaran;
  }
  public LocalDate getTanggal() { return tanggal; }
  public void setTanggal(LocalDate tanggal) { this.tanggal = tanggal; }
  public String getKeperluan() { return keperluan; }
  public void setKeperluan(String keperluan) { this.keperluan = keperluan; }
  public double getNominal() { return nominal; }
  public void setNominal(double nominal) { this.nominal = nominal; }
  public String getKeterangan() { return keterangan; }
  public void setKeterangan(String keterangan) { this.keterangan = keterangan; }
}
