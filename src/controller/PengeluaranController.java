package controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import koneksi.Koneksi;
import model.Pengeluaran;

public class PengeluaranController {
  private final Connection conn = Koneksi.getConnection();

  public List<Pengeluaran> getAll(String keyword) throws Exception {
    List<Pengeluaran> list = new ArrayList<>();
    String sql =
        "SELECT * FROM pengeluaran WHERE keperluan LIKE ? OR keterangan LIKE " +
        "? OR tanggal LIKE ? ORDER BY tanggal DESC, id_pengeluaran DESC";
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
      String key = "%" + keyword + "%";
      ps.setString(1, key);
      ps.setString(2, key);
      ps.setString(3, key);
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next())
          list.add(map(rs));
      }
    }
    return list;
  }

  public List<Pengeluaran> getByDate(LocalDate awal, LocalDate akhir)
      throws Exception {
    List<Pengeluaran> list = new ArrayList<>();
    String sql = "SELECT * FROM pengeluaran WHERE tanggal BETWEEN ? AND ? " +
                 "ORDER BY tanggal ASC, id_pengeluaran ASC";
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setDate(1, Date.valueOf(awal));
      ps.setDate(2, Date.valueOf(akhir));
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next())
          list.add(map(rs));
      }
    }
    return list;
  }

  public double total() throws Exception { return total(null, null); }

  public double total(LocalDate awal, LocalDate akhir) throws Exception {
    String sql = awal == null
                     ? "SELECT COALESCE(SUM(nominal),0) total FROM pengeluaran"
                     : "SELECT COALESCE(SUM(nominal),0) total FROM " +
                       "pengeluaran WHERE tanggal BETWEEN ? AND ?";
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
      if (awal != null) {
        ps.setDate(1, Date.valueOf(awal));
        ps.setDate(2, Date.valueOf(akhir));
      }
      try (ResultSet rs = ps.executeQuery()) {
        return rs.next() ? rs.getDouble("total") : 0;
      }
    }
  }

  public void insert(Pengeluaran p) throws Exception {
    String sql = "INSERT INTO pengeluaran (tanggal, keperluan, nominal, " +
                 "keterangan) VALUES (?, ?, ?, ?)";
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
      fill(ps, p);
      ps.executeUpdate();
    }
  }

  public void update(Pengeluaran p) throws Exception {
    String sql = "UPDATE pengeluaran SET tanggal = ?, keperluan = ?, nominal " +
                 "= ?, keterangan = ? WHERE id_pengeluaran = ?";
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
      fill(ps, p);
      ps.setInt(5, p.getIdPengeluaran());
      ps.executeUpdate();
    }
  }

  public void delete(int id)throws Exception {
    try (PreparedStatement ps = conn.prepareStatement(
             "DELETE FROM pengeluaran WHERE id_pengeluaran = ?")) {
      ps.setInt(1, id);
      ps.executeUpdate();
    }
  }

  private void fill(PreparedStatement ps, Pengeluaran p) throws Exception {
    ps.setDate(1, Date.valueOf(p.getTanggal()));
    ps.setString(2, p.getKeperluan());
    ps.setDouble(3, p.getNominal());
    ps.setString(4, p.getKeterangan());
  }

  private Pengeluaran map(ResultSet rs) throws Exception {
    return new Pengeluaran(rs.getInt("id_pengeluaran"),
                           rs.getDate("tanggal").toLocalDate(),
                           rs.getString("keperluan"), rs.getDouble("nominal"),
                           rs.getString("keterangan"));
  }
}
