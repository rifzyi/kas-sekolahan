// File: model/KategoriPemasukan.java
package model;

public class KategoriPemasukan {
    private int idKategoriPemasukan;
    private String namaKategori;

    public KategoriPemasukan() {
    }

    public KategoriPemasukan(int idKategoriPemasukan, String namaKategori) {
        this.idKategoriPemasukan = idKategoriPemasukan;
        this.namaKategori = namaKategori;
    }

    public int getIdKategoriPemasukan() { return idKategoriPemasukan; }
    public void setIdKategoriPemasukan(int idKategoriPemasukan) { this.idKategoriPemasukan = idKategoriPemasukan; }
    public String getNamaKategori() { return namaKategori; }
    public void setNamaKategori(String namaKategori) { this.namaKategori = namaKategori; }
}
