// File: model/Siswa.java
package model;

public class Siswa {
    private int idSiswa;
    private String nis;
    private String namaSiswa;
    private int idKelas;
    private String jenisKelamin;
    private String alamat;
    private String namaKelas;

    public Siswa() {}
    public Siswa(int idSiswa, String nis, String namaSiswa, int idKelas, String namaKelas, String jenisKelamin, String alamat) {
        this.idSiswa = idSiswa; this.nis = nis; this.namaSiswa = namaSiswa; this.idKelas = idKelas; this.namaKelas = namaKelas; this.jenisKelamin = jenisKelamin; this.alamat = alamat;
    }
    public int getIdSiswa() { return idSiswa; }
    public void setIdSiswa(int idSiswa) { this.idSiswa = idSiswa; }
    public String getNis() { return nis; }
    public void setNis(String nis) { this.nis = nis; }
    public String getNamaSiswa() { return namaSiswa; }
    public void setNamaSiswa(String namaSiswa) { this.namaSiswa = namaSiswa; }
    public int getIdKelas() { return idKelas; }
    public void setIdKelas(int idKelas) { this.idKelas = idKelas; }
    public String getJenisKelamin() { return jenisKelamin; }
    public void setJenisKelamin(String jenisKelamin) { this.jenisKelamin = jenisKelamin; }
    public String getAlamat() { return alamat; }
    public void setAlamat(String alamat) { this.alamat = alamat; }
    public String getNamaKelas() { return namaKelas; }
    public void setNamaKelas(String namaKelas) { this.namaKelas = namaKelas; }
    public String getKelas() { return namaKelas; }
    public void setKelas(String kelas) { this.namaKelas = kelas; }
}
