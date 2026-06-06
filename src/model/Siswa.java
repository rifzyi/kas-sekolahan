package model;

public class Siswa {
  private int idSiswa;
  private String nis;
  private String namaSiswa;

  private int idKelas;
  private String kodeKelas;
  private String namaKelas;

  private String kelas;

  private String jenisKelamin;
  private String alamat;

  public Siswa() {}

  public Siswa(int idSiswa, String nis, String namaSiswa, int idKelas, String kodeKelas, String namaKelas, String jenisKelamin, String alamat) {
    this.idSiswa = idSiswa; this.nis = nis; this.namaSiswa = namaSiswa; this.idKelas = idKelas; this.kodeKelas = kodeKelas; this.namaKelas = namaKelas; this.jenisKelamin = jenisKelamin; this.alamat = alamat;
  }


  public Siswa(int idSiswa, String nis, String namaSiswa, String kelas,
               String jenisKelamin, String alamat) {
    this.idSiswa = idSiswa;
    this.nis = nis;
    this.namaSiswa = namaSiswa;
    this.kelas = kelas;
    this.jenisKelamin = jenisKelamin;
    this.alamat = alamat;
  }


  public int getIdSiswa() { return idSiswa; }
  public void setIdSiswa(int idSiswa) { this.idSiswa = idSiswa; }
  public String getNis() { return nis; }
  public void setNis(String nis) { this.nis = nis; }
  public String getNamaSiswa() { return namaSiswa; }
  public void setNamaSiswa(String namaSiswa) { this.namaSiswa = namaSiswa; }
  public int getIdKelas() { return idKelas; }
  public void setIdKelas(int idKelas) { this.idKelas = idKelas; }
  public String getKodeKelas() { return kodeKelas; }
  public void setKodeKelas(String kodeKelas) { this.kodeKelas = kodeKelas; }
  public String getNamaKelas() { return namaKelas; }
  public void setNamaKelas(String namaKelas) { this.namaKelas = namaKelas; }
  public String getJenisKelamin() { return jenisKelamin; }
  public void setJenisKelamin(String jenisKelamin) { this.jenisKelamin = jenisKelamin; }
  public String getAlamat() { return alamat; }
  public void setAlamat(String alamat) { this.alamat = alamat; }
}
