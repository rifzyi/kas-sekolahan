package controller;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import koneksi.Koneksi;
import model.Pemasukan;
import util.UIUtils.Option;

public class PemasukanController {
  private final Connection conn = Koneksi.getConnection();

  public List<Pemasukan> getAll() throws SQLException { return getByRange(null, null); }
  public List<Pemasukan> getByRange(LocalDate awal, LocalDate akhir) throws SQLException {
    List<Pemasukan> data = new ArrayList<>();
    String sql = "SELECT p.*, k.nama_kategori kategori FROM pemasukan p LEFT JOIN kategori_pemasukan k ON p.id_kategori_pemasukan=k.id_kategori_pemasukan" + (awal == null ? "" : " WHERE p.tanggal BETWEEN ? AND ?") + " ORDER BY p.tanggal DESC, p.id_pemasukan DESC";
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
      if (awal != null) { ps.setDate(1, Date.valueOf(awal)); ps.setDate(2, Date.valueOf(akhir)); }
      try (ResultSet rs = ps.executeQuery()) { while (rs.next()) data.add(new Pemasukan(rs.getInt("id_pemasukan"), rs.getDate("tanggal").toLocalDate(), rs.getInt("id_kategori_pemasukan"), rs.getString("kategori"), rs.getDouble("nominal"), rs.getString("keterangan"))); }
    }
    return data;
  }
  public double total() throws SQLException { return total(null, null); }
  public double total(LocalDate awal, LocalDate akhir) throws SQLException {
    String sql = "SELECT COALESCE(SUM(nominal),0) FROM pemasukan" + (awal == null ? "" : " WHERE tanggal BETWEEN ? AND ?");
    try (PreparedStatement ps = conn.prepareStatement(sql)) { if (awal != null) { ps.setDate(1, Date.valueOf(awal)); ps.setDate(2, Date.valueOf(akhir)); } try (ResultSet rs = ps.executeQuery()) { rs.next(); return rs.getDouble(1); } }
  }
  public List<Option> kategoriOptions() throws SQLException {
    List<Option> data = new ArrayList<>();
    try (PreparedStatement ps = conn.prepareStatement("SELECT id_kategori_pemasukan,nama_kategori FROM kategori_pemasukan ORDER BY nama_kategori"); ResultSet rs = ps.executeQuery()) { while (rs.next()) data.add(new Option(rs.getInt(1), rs.getString(2))); }
    return data;
  }
  public List<Option> getKategoriOptions() throws SQLException { return kategoriOptions(); }
  public List<Option> getKategoriPemasukan() throws SQLException { return kategoriOptions(); }
  public void insert(Pemasukan p) throws SQLException { try (PreparedStatement ps = conn.prepareStatement("INSERT INTO pemasukan(tanggal,id_kategori_pemasukan,nominal,keterangan) VALUES(?,?,?,?)")) { fill(ps, p); ps.executeUpdate(); } }
  public void update(Pemasukan p) throws SQLException { try (PreparedStatement ps = conn.prepareStatement("UPDATE pemasukan SET tanggal=?, id_kategori_pemasukan=?, nominal=?, keterangan=? WHERE id_pemasukan=?")) { fill(ps, p); ps.setInt(5, p.getIdPemasukan()); ps.executeUpdate(); } }
  public void delete(int id) throws SQLException { try (PreparedStatement ps = conn.prepareStatement("DELETE FROM pemasukan WHERE id_pemasukan=?")) { ps.setInt(1, id); ps.executeUpdate(); } }
  private void fill(PreparedStatement ps, Pemasukan p) throws SQLException { ps.setDate(1, Date.valueOf(p.getTanggal())); if (p.getIdKategoriPemasukan() == 0) ps.setNull(2, Types.INTEGER); else ps.setInt(2, p.getIdKategoriPemasukan()); ps.setDouble(3, p.getNominal()); ps.setString(4, p.getKeterangan()); }
  public List<Option> kategoriAll() throws SQLException { return kategoriOptions(); }
  public void insertKategori(String nama) throws SQLException { try (PreparedStatement ps = conn.prepareStatement("INSERT INTO kategori_pemasukan(nama_kategori) VALUES(?)")) { ps.setString(1, nama); ps.executeUpdate(); } }
  public void updateKategori(int id, String nama) throws SQLException { try (PreparedStatement ps = conn.prepareStatement("UPDATE kategori_pemasukan SET nama_kategori=? WHERE id_kategori_pemasukan=?")) { ps.setString(1, nama); ps.setInt(2, id); ps.executeUpdate(); } }
  public void deleteKategori(int id) throws SQLException { try (PreparedStatement ps = conn.prepareStatement("DELETE FROM kategori_pemasukan WHERE id_kategori_pemasukan=?")) { ps.setInt(1, id); ps.executeUpdate(); } }
}
