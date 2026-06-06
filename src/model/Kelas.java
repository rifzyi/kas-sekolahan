// File: model/Kelas.java
package model;

public class Kelas {
    private int idKelas;
    private String kodeKelas;
    private String namaKelas;

    public Kelas() {
    }

    public Kelas(int idKelas, String kodeKelas, String namaKelas) {
        this.idKelas = idKelas;
        this.kodeKelas = kodeKelas;
        this.namaKelas = namaKelas;
    }

    public int getIdKelas() { return idKelas; }
    public void setIdKelas(int idKelas) { this.idKelas = idKelas; }
    public String getKodeKelas() { return kodeKelas; }
    public void setKodeKelas(String kodeKelas) { this.kodeKelas = kodeKelas; }
    public String getNamaKelas() { return namaKelas; }
    public void setNamaKelas(String namaKelas) { this.namaKelas = namaKelas; }
}
