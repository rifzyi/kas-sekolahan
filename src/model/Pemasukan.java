// File: model/Pemasukan.java
package model;

import java.sql.Date;

public class Pemasukan {
    private int idPemasukan;
    private Date tanggal;
    private Integer idSiswa;
    private int idKategoriPemasukan;
    private double nominal;
    private String keterangan;
    private int idUserInput;
    private String namaSiswa;
    private String namaKategori;

    public Pemasukan() {}
    public Pemasukan(int idPemasukan, Date tanggal, Integer idSiswa, int idKategoriPemasukan, double nominal, String keterangan, int idUserInput, String namaSiswa, String namaKategori) {
        this.idPemasukan = idPemasukan; this.tanggal = tanggal; this.idSiswa = idSiswa; this.idKategoriPemasukan = idKategoriPemasukan; this.nominal = nominal; this.keterangan = keterangan; this.idUserInput = idUserInput; this.namaSiswa = namaSiswa; this.namaKategori = namaKategori;
    }
    public int getIdPemasukan() { return idPemasukan; }
    public void setIdPemasukan(int idPemasukan) { this.idPemasukan = idPemasukan; }
    public Date getTanggal() { return tanggal; }
    public void setTanggal(Date tanggal) { this.tanggal = tanggal; }
    public Integer getIdSiswa() { return idSiswa; }
    public void setIdSiswa(Integer idSiswa) { this.idSiswa = idSiswa; }
    public int getIdKategoriPemasukan() { return idKategoriPemasukan; }
    public void setIdKategoriPemasukan(int idKategoriPemasukan) { this.idKategoriPemasukan = idKategoriPemasukan; }
    public double getNominal() { return nominal; }
    public void setNominal(double nominal) { this.nominal = nominal; }
    public String getKeterangan() { return keterangan; }
    public void setKeterangan(String keterangan) { this.keterangan = keterangan; }
    public int getIdUserInput() { return idUserInput; }
    public void setIdUserInput(int idUserInput) { this.idUserInput = idUserInput; }
    public String getNamaSiswa() { return namaSiswa; }
    public void setNamaSiswa(String namaSiswa) { this.namaSiswa = namaSiswa; }
    public String getNamaKategori() { return namaKategori; }
    public void setNamaKategori(String namaKategori) { this.namaKategori = namaKategori; }
}
