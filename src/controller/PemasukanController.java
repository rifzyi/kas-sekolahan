package controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import koneksi.Koneksi;
import model.Pemasukan;

public class PemasukanController {
  private final Connection conn = Koneksi.getConnection();

  public List<Pemasukan> getAll(String keyword) throws Exception {
    List<Pemasukan> list = new ArrayList<>();
    String sql =
        "SELECT * FROM pemasukan WHERE sumber_dana LIKE ? OR keterangan LIKE " +
        "? OR tanggal LIKE ? ORDER BY tanggal DESC, id_pemasukan DESC";
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

  public List<Pemasukan> getByDate(LocalDate awal, LocalDate akhir)
      throws Exception {
    List<Pemasukan> list = new ArrayList<>();
    String sql = "SELECT * FROM pemasukan WHERE tanggal BETWEEN ? AND ? " +
                 "ORDER BY tanggal ASC, id_pemasukan ASC";
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
                     ? "SELECT COALESCE(SUM(nominal),0) total FROM pemasukan"
                     : "SELECT COALESCE(SUM(nominal),0) total FROM pemasukan " +
                       "WHERE tanggal BETWEEN ? AND ?";
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

  public void insert(Pemasukan p) throws Exception {
    String sql = "INSERT INTO pemasukan (tanggal, sumber_dana, nominal, " +
                 "keterangan) VALUES (?, ?, ?, ?)";
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
      fill(ps, p);
      ps.executeUpdate();
    }
  }

  public void update(Pemasukan p) throws Exception {
    String sql = "UPDATE pemasukan SET tanggal = ?, sumber_dana = ?, nominal " +
                 "= ?, keterangan = ? WHERE id_pemasukan = ?";
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
      fill(ps, p);
      ps.setInt(5, p.getIdPemasukan());
      ps.executeUpdate();
    }
  }

  public void delete(int id)throws Exception {
    try (PreparedStatement ps = conn.prepareStatement(
             "DELETE FROM pemasukan WHERE id_pemasukan = ?")) {
      ps.setInt(1, id);
      ps.executeUpdate();
    }
  }

  private void fill(PreparedStatement ps, Pemasukan p) throws Exception {
    ps.setDate(1, Date.valueOf(p.getTanggal()));
    ps.setString(2, p.getSumberDana());
    ps.setDouble(3, p.getNominal());
    ps.setString(4, p.getKeterangan());
  }

  private Pemasukan map(ResultSet rs) throws Exception {
    return new Pemasukan(rs.getInt("id_pemasukan"),
                         rs.getDate("tanggal").toLocalDate(),
                         rs.getString("sumber_dana"), rs.getDouble("nominal"),
                         rs.getString("keterangan"));
  }
}
