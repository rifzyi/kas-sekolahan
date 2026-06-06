package controller;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import koneksi.Koneksi;
import model.Pengeluaran;
import util.UIUtils.Option;

public class PengeluaranController {
  private final Connection conn = Koneksi.getConnection();

  public List<Pengeluaran> getAll() throws SQLException { return getByRange(null, null); }
  public List<Pengeluaran> getByRange(LocalDate awal, LocalDate akhir) throws SQLException {
    List<Pengeluaran> data = new ArrayList<>();
    String sql = "SELECT p.*, k.nama_kategori kategori FROM pengeluaran p LEFT JOIN kategori_pengeluaran k ON p.id_kategori_pengeluaran=k.id_kategori_pengeluaran" + (awal == null ? "" : " WHERE p.tanggal BETWEEN ? AND ?") + " ORDER BY p.tanggal DESC, p.id_pengeluaran DESC";
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
      if (awal != null) { ps.setDate(1, Date.valueOf(awal)); ps.setDate(2, Date.valueOf(akhir)); }
      try (ResultSet rs = ps.executeQuery()) { while (rs.next()) data.add(new Pengeluaran(rs.getInt("id_pengeluaran"), rs.getDate("tanggal").toLocalDate(), rs.getInt("id_kategori_pengeluaran"), rs.getString("kategori"), rs.getDouble("nominal"), rs.getString("keterangan"))); }
    }
    return data;
  }
  public double total() throws SQLException { return total(null, null); }
  public double total(LocalDate awal, LocalDate akhir) throws SQLException {
    String sql = "SELECT COALESCE(SUM(nominal),0) FROM pengeluaran" + (awal == null ? "" : " WHERE tanggal BETWEEN ? AND ?");
    try (PreparedStatement ps = conn.prepareStatement(sql)) { if (awal != null) { ps.setDate(1, Date.valueOf(awal)); ps.setDate(2, Date.valueOf(akhir)); } try (ResultSet rs = ps.executeQuery()) { rs.next(); return rs.getDouble(1); } }
  }
  public List<Option> kategoriOptions() throws SQLException {
    List<Option> data = new ArrayList<>();
    try (PreparedStatement ps = conn.prepareStatement("SELECT id_kategori_pengeluaran,nama_kategori FROM kategori_pengeluaran ORDER BY nama_kategori"); ResultSet rs = ps.executeQuery()) { while (rs.next()) data.add(new Option(rs.getInt(1), rs.getString(2))); }
    return data;
  }
  public List<Option> getKategoriOptions() throws SQLException { return kategoriOptions(); }
  public List<Option> getKategoriPengeluaran() throws SQLException { return kategoriOptions(); }
  public void insert(Pengeluaran p) throws SQLException { try (PreparedStatement ps = conn.prepareStatement("INSERT INTO pengeluaran(tanggal,id_kategori_pengeluaran,nominal,keterangan) VALUES(?,?,?,?)")) { fill(ps, p); ps.executeUpdate(); } }
  public void update(Pengeluaran p) throws SQLException { try (PreparedStatement ps = conn.prepareStatement("UPDATE pengeluaran SET tanggal=?, id_kategori_pengeluaran=?, nominal=?, keterangan=? WHERE id_pengeluaran=?")) { fill(ps, p); ps.setInt(5, p.getIdPengeluaran()); ps.executeUpdate(); } }
  public void delete(int id) throws SQLException { try (PreparedStatement ps = conn.prepareStatement("DELETE FROM pengeluaran WHERE id_pengeluaran=?")) { ps.setInt(1, id); ps.executeUpdate(); } }
  private void fill(PreparedStatement ps, Pengeluaran p) throws SQLException { ps.setDate(1, Date.valueOf(p.getTanggal())); if (p.getIdKategoriPengeluaran() == 0) ps.setNull(2, Types.INTEGER); else ps.setInt(2, p.getIdKategoriPengeluaran()); ps.setDouble(3, p.getNominal()); ps.setString(4, p.getKeterangan()); }
  public List<Option> kategoriAll() throws SQLException { return kategoriOptions(); }
  public void insertKategori(String nama) throws SQLException { try (PreparedStatement ps = conn.prepareStatement("INSERT INTO kategori_pengeluaran(nama_kategori) VALUES(?)")) { ps.setString(1, nama); ps.executeUpdate(); } }
  public void updateKategori(int id, String nama) throws SQLException { try (PreparedStatement ps = conn.prepareStatement("UPDATE kategori_pengeluaran SET nama_kategori=? WHERE id_kategori_pengeluaran=?")) { ps.setString(1, nama); ps.setInt(2, id); ps.executeUpdate(); } }
  public void deleteKategori(int id) throws SQLException { try (PreparedStatement ps = conn.prepareStatement("DELETE FROM kategori_pengeluaran WHERE id_kategori_pengeluaran=?")) { ps.setInt(1, id); ps.executeUpdate(); } }
}
