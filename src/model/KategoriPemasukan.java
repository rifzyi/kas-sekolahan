// File: model/KategoriPemasukan.java
package model;

public class KategoriPemasukan {
    private int idKategoriPemasukan;
    private String namaKategori;
    private boolean spp;

    public KategoriPemasukan() {}
    public KategoriPemasukan(int idKategoriPemasukan, String namaKategori, boolean spp) { this.idKategoriPemasukan = idKategoriPemasukan; this.namaKategori = namaKategori; this.spp = spp; }
    public int getIdKategoriPemasukan() { return idKategoriPemasukan; }
    public void setIdKategoriPemasukan(int idKategoriPemasukan) { this.idKategoriPemasukan = idKategoriPemasukan; }
    public String getNamaKategori() { return namaKategori; }
    public void setNamaKategori(String namaKategori) { this.namaKategori = namaKategori; }
    public boolean isSpp() { return spp; }
    public void setSpp(boolean spp) { this.spp = spp; }
}
