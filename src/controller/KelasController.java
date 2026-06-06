package controller;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import koneksi.Koneksi;
import model.Kelas;

public class KelasController {
  private final Connection conn = Koneksi.getConnection();

  public List<Kelas> getAll() throws SQLException {
    List<Kelas> data = new ArrayList<>();
    try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM kelas ORDER BY kode_kelas"); ResultSet rs = ps.executeQuery()) {
      while (rs.next()) data.add(new Kelas(rs.getInt("id_kelas"), rs.getString("kode_kelas"), rs.getString("nama_kelas")));
    }
    return data;
  }
  public void insert(Kelas k) throws SQLException { try (PreparedStatement ps = conn.prepareStatement("INSERT INTO kelas(kode_kelas,nama_kelas) VALUES(?,?)")) { ps.setString(1, k.getKodeKelas()); ps.setString(2, k.getNamaKelas()); ps.executeUpdate(); } }
  public void update(Kelas k) throws SQLException { try (PreparedStatement ps = conn.prepareStatement("UPDATE kelas SET kode_kelas=?, nama_kelas=? WHERE id_kelas=?")) { ps.setString(1, k.getKodeKelas()); ps.setString(2, k.getNamaKelas()); ps.setInt(3, k.getIdKelas()); ps.executeUpdate(); } }
  public void delete(int id) throws SQLException { try (PreparedStatement ps = conn.prepareStatement("DELETE FROM kelas WHERE id_kelas=?")) { ps.setInt(1, id); ps.executeUpdate(); } }
}
