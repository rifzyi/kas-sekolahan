// File: controller/PengeluaranController.java
package controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import model.Pengeluaran;
import util.Koneksi;
import util.UIUtils.Option;

public class PengeluaranController {
    public List<Pengeluaran> getAll(String keyword) throws SQLException {
        List<Pengeluaran> data = new ArrayList<>();
        String sql = "SELECT p.id_pengeluaran, p.tanggal, p.id_kategori_pengeluaran, COALESCE(k.nama_kategori, '-') AS nama_kategori, p.nominal, p.keterangan "
                + "FROM pengeluaran p LEFT JOIN kategori_pengeluaran k ON p.id_kategori_pengeluaran = k.id_kategori_pengeluaran "
                + "WHERE (? = '' OR k.nama_kategori LIKE ? OR p.keterangan LIKE ? OR DATE_FORMAT(p.tanggal, '%Y-%m-%d') LIKE ?) "
                + "ORDER BY p.tanggal DESC, p.id_pengeluaran DESC";
        String safeKeyword = keyword == null ? "" : keyword.trim();
        String like = "%" + safeKeyword + "%";
        try (Connection connection = Koneksi.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, safeKeyword);
            statement.setString(2, like);
            statement.setString(3, like);
            statement.setString(4, like);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    data.add(new Pengeluaran(resultSet.getInt("id_pengeluaran"), resultSet.getDate("tanggal").toLocalDate(), resultSet.getInt("id_kategori_pengeluaran"), resultSet.getString("nama_kategori"), resultSet.getDouble("nominal"), resultSet.getString("keterangan")));
                }
            }
        }
        return data;
    }

    public List<Pengeluaran> getAll() throws SQLException { return getAll(""); }
    public List<Pengeluaran> getByRange(LocalDate awal, LocalDate akhir) throws SQLException { return getAll(""); }

    public void insert(Pengeluaran pengeluaran) throws SQLException {
        String sql = "INSERT INTO pengeluaran (tanggal, id_kategori_pengeluaran, nominal, keterangan) VALUES (?, ?, ?, ?)";
        try (Connection connection = Koneksi.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            fillStatement(statement, pengeluaran);
            statement.executeUpdate();
        }
    }

    public void update(Pengeluaran pengeluaran) throws SQLException {
        String sql = "UPDATE pengeluaran SET tanggal = ?, id_kategori_pengeluaran = ?, nominal = ?, keterangan = ? WHERE id_pengeluaran = ?";
        try (Connection connection = Koneksi.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            fillStatement(statement, pengeluaran);
            statement.setInt(5, pengeluaran.getIdPengeluaran());
            statement.executeUpdate();
        }
    }

    public void delete(int idPengeluaran) throws SQLException {
        try (Connection connection = Koneksi.getConnection(); PreparedStatement statement = connection.prepareStatement("DELETE FROM pengeluaran WHERE id_pengeluaran = ?")) {
            statement.setInt(1, idPengeluaran);
            statement.executeUpdate();
        }
    }

    public double total() throws SQLException { return total(null, null); }

    public double total(LocalDate awal, LocalDate akhir) throws SQLException {
        String sql = awal == null ? "SELECT COALESCE(SUM(nominal), 0) FROM pengeluaran" : "SELECT COALESCE(SUM(nominal), 0) FROM pengeluaran WHERE tanggal BETWEEN ? AND ?";
        try (Connection connection = Koneksi.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            if (awal != null) {
                statement.setDate(1, Date.valueOf(awal));
                statement.setDate(2, Date.valueOf(akhir));
            }
            try (ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                return resultSet.getDouble(1);
            }
        }
    }

    public List<Option> getKategoriOptions() throws SQLException {
        List<Option> options = new ArrayList<>();
        String sql = "SELECT id_kategori_pengeluaran, nama_kategori FROM kategori_pengeluaran ORDER BY nama_kategori";
        try (Connection connection = Koneksi.getConnection(); PreparedStatement statement = connection.prepareStatement(sql); ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) options.add(new Option(resultSet.getInt("id_kategori_pengeluaran"), resultSet.getString("nama_kategori")));
        }
        return options;
    }

    public List<Option> kategoriOptions() throws SQLException { return getKategoriOptions(); }
    public List<Option> getKategoriPengeluaran() throws SQLException { return getKategoriOptions(); }
    public List<Option> kategoriAll() throws SQLException { return getKategoriOptions(); }

    public void insertKategori(String nama) throws SQLException {
        try (Connection connection = Koneksi.getConnection(); PreparedStatement statement = connection.prepareStatement("INSERT INTO kategori_pengeluaran(nama_kategori) VALUES(?)")) {
            statement.setString(1, nama);
            statement.executeUpdate();
        }
    }

    public void updateKategori(int id, String nama) throws SQLException {
        try (Connection connection = Koneksi.getConnection(); PreparedStatement statement = connection.prepareStatement("UPDATE kategori_pengeluaran SET nama_kategori=? WHERE id_kategori_pengeluaran=?")) {
            statement.setString(1, nama);
            statement.setInt(2, id);
            statement.executeUpdate();
        }
    }

    public void deleteKategori(int id) throws SQLException {
        try (Connection connection = Koneksi.getConnection(); PreparedStatement statement = connection.prepareStatement("DELETE FROM kategori_pengeluaran WHERE id_kategori_pengeluaran=?")) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    private void fillStatement(PreparedStatement statement, Pengeluaran pengeluaran) throws SQLException {
        statement.setDate(1, Date.valueOf(pengeluaran.getTanggal()));
        if (pengeluaran.getIdKategoriPengeluaran() <= 0) statement.setNull(2, Types.INTEGER); else statement.setInt(2, pengeluaran.getIdKategoriPengeluaran());
        statement.setDouble(3, pengeluaran.getNominal());
        statement.setString(4, pengeluaran.getKeterangan());
    }
}
