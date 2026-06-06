package controller;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import koneksi.Koneksi;
import model.Siswa;
import util.UIUtils.Option;

public class SiswaController {
  private final Connection conn = Koneksi.getConnection();

  public List<Siswa> getAll() throws SQLException {
    List<Siswa> data = new ArrayList<>();
    String sql = "SELECT s.*, CONCAT(k.kode_kelas,' - ',k.nama_kelas) kelas FROM siswa s LEFT JOIN kelas k ON s.id_kelas=k.id_kelas ORDER BY s.nama_siswa";
    try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
      while (rs.next()) data.add(new Siswa(rs.getInt("id_siswa"), rs.getString("nis"), rs.getString("nama_siswa"), rs.getInt("id_kelas"), rs.getString("kelas"), rs.getString("jenis_kelamin"), rs.getString("alamat")));
    }
    return data;
  }
  public int count() throws SQLException { try (PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) FROM siswa"); ResultSet rs = ps.executeQuery()) { rs.next(); return rs.getInt(1); } }
  public List<Option> kelasOptions() throws SQLException {
    List<Option> data = new ArrayList<>();
    try (PreparedStatement ps = conn.prepareStatement("SELECT id_kelas, CONCAT(kode_kelas,' - ',nama_kelas) label FROM kelas ORDER BY kode_kelas"); ResultSet rs = ps.executeQuery()) { while (rs.next()) data.add(new Option(rs.getInt(1), rs.getString(2))); }
    return data;
  }
  public void insert(Siswa s) throws SQLException { try (PreparedStatement ps = conn.prepareStatement("INSERT INTO siswa(nis,nama_siswa,id_kelas,jenis_kelamin,alamat) VALUES(?,?,?,?,?)")) { fill(ps, s); ps.executeUpdate(); } }
  public void update(Siswa s) throws SQLException { try (PreparedStatement ps = conn.prepareStatement("UPDATE siswa SET nis=?, nama_siswa=?, id_kelas=?, jenis_kelamin=?, alamat=? WHERE id_siswa=?")) { fill(ps, s); ps.setInt(6, s.getIdSiswa()); ps.executeUpdate(); } }
  public void delete(int id) throws SQLException { try (PreparedStatement ps = conn.prepareStatement("DELETE FROM siswa WHERE id_siswa=?")) { ps.setInt(1, id); ps.executeUpdate(); } }
  private void fill(PreparedStatement ps, Siswa s) throws SQLException { ps.setString(1, s.getNis()); ps.setString(2, s.getNamaSiswa()); if (s.getIdKelas() == 0) ps.setNull(3, Types.INTEGER); else ps.setInt(3, s.getIdKelas()); ps.setString(4, s.getJenisKelamin()); ps.setString(5, s.getAlamat()); }
}
