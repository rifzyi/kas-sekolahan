// File: model/KategoriPengeluaran.java
package model;

public class KategoriPengeluaran {
    private int idKategoriPengeluaran;
    private String namaKategori;

    public KategoriPengeluaran() {}
    public KategoriPengeluaran(int idKategoriPengeluaran, String namaKategori) { this.idKategoriPengeluaran = idKategoriPengeluaran; this.namaKategori = namaKategori; }
    public int getIdKategoriPengeluaran() { return idKategoriPengeluaran; }
    public void setIdKategoriPengeluaran(int idKategoriPengeluaran) { this.idKategoriPengeluaran = idKategoriPengeluaran; }
    public String getNamaKategori() { return namaKategori; }
    public void setNamaKategori(String namaKategori) { this.namaKategori = namaKategori; }
}
