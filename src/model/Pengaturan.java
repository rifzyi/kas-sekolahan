// File: model/Pengaturan.java
package model;

public class Pengaturan {
    private int idPengaturan;
    private String namaSekolah;
    private String alamat;
    private String telepon;
    private String kepalaSekolah;
    private String bendahara;

    public Pengaturan() {}
    public Pengaturan(int idPengaturan, String namaSekolah, String alamat, String telepon, String kepalaSekolah, String bendahara) {
        this.idPengaturan = idPengaturan; this.namaSekolah = namaSekolah; this.alamat = alamat; this.telepon = telepon; this.kepalaSekolah = kepalaSekolah; this.bendahara = bendahara;
    }
    public int getIdPengaturan() { return idPengaturan; }
    public void setIdPengaturan(int idPengaturan) { this.idPengaturan = idPengaturan; }
    public String getNamaSekolah() { return namaSekolah; }
    public void setNamaSekolah(String namaSekolah) { this.namaSekolah = namaSekolah; }
    public String getAlamat() { return alamat; }
    public void setAlamat(String alamat) { this.alamat = alamat; }
    public String getTelepon() { return telepon; }
    public void setTelepon(String telepon) { this.telepon = telepon; }
    public String getKepalaSekolah() { return kepalaSekolah; }
    public void setKepalaSekolah(String kepalaSekolah) { this.kepalaSekolah = kepalaSekolah; }
    public String getBendahara() { return bendahara; }
    public void setBendahara(String bendahara) { this.bendahara = bendahara; }
}
