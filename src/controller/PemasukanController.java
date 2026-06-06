// File: controller/PemasukanController.java
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
import model.Pemasukan;
import util.Koneksi;
import util.UIUtils.Option;

public class PemasukanController {
    public List<Pemasukan> getAll(String keyword) throws SQLException {
        List<Pemasukan> data = new ArrayList<>();
        String sql = "SELECT p.id_pemasukan, p.tanggal, p.id_kategori_pemasukan, COALESCE(k.nama_kategori, '-') AS nama_kategori, p.nominal, p.keterangan "
                + "FROM pemasukan p LEFT JOIN kategori_pemasukan k ON p.id_kategori_pemasukan = k.id_kategori_pemasukan "
                + "WHERE (? = '' OR k.nama_kategori LIKE ? OR p.keterangan LIKE ? OR DATE_FORMAT(p.tanggal, '%Y-%m-%d') LIKE ?) "
                + "ORDER BY p.tanggal DESC, p.id_pemasukan DESC";
        String safeKeyword = keyword == null ? "" : keyword.trim();
        String like = "%" + safeKeyword + "%";
        try (Connection connection = Koneksi.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, safeKeyword);
            statement.setString(2, like);
            statement.setString(3, like);
            statement.setString(4, like);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    data.add(new Pemasukan(resultSet.getInt("id_pemasukan"), resultSet.getDate("tanggal").toLocalDate(), resultSet.getInt("id_kategori_pemasukan"), resultSet.getString("nama_kategori"), resultSet.getDouble("nominal"), resultSet.getString("keterangan")));
                }
            }
        }
        return data;
    }

    public List<Pemasukan> getAll() throws SQLException { return getAll(""); }
    public List<Pemasukan> getByRange(LocalDate awal, LocalDate akhir) throws SQLException { return getAll(""); }

    public void insert(Pemasukan pemasukan) throws SQLException {
        String sql = "INSERT INTO pemasukan (tanggal, id_kategori_pemasukan, nominal, keterangan) VALUES (?, ?, ?, ?)";
        try (Connection connection = Koneksi.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            fillStatement(statement, pemasukan);
            statement.executeUpdate();
        }
    }

    public void update(Pemasukan pemasukan) throws SQLException {
        String sql = "UPDATE pemasukan SET tanggal = ?, id_kategori_pemasukan = ?, nominal = ?, keterangan = ? WHERE id_pemasukan = ?";
        try (Connection connection = Koneksi.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            fillStatement(statement, pemasukan);
            statement.setInt(5, pemasukan.getIdPemasukan());
            statement.executeUpdate();
        }
    }

    public void delete(int idPemasukan) throws SQLException {
        try (Connection connection = Koneksi.getConnection(); PreparedStatement statement = connection.prepareStatement("DELETE FROM pemasukan WHERE id_pemasukan = ?")) {
            statement.setInt(1, idPemasukan);
            statement.executeUpdate();
        }
    }

    public double total() throws SQLException { return total(null, null); }

    public double total(LocalDate awal, LocalDate akhir) throws SQLException {
        String sql = awal == null ? "SELECT COALESCE(SUM(nominal), 0) FROM pemasukan" : "SELECT COALESCE(SUM(nominal), 0) FROM pemasukan WHERE tanggal BETWEEN ? AND ?";
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
        String sql = "SELECT id_kategori_pemasukan, nama_kategori FROM kategori_pemasukan ORDER BY nama_kategori";
        try (Connection connection = Koneksi.getConnection(); PreparedStatement statement = connection.prepareStatement(sql); ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) options.add(new Option(resultSet.getInt("id_kategori_pemasukan"), resultSet.getString("nama_kategori")));
        }
        return options;
    }

    public List<Option> kategoriOptions() throws SQLException { return getKategoriOptions(); }
    public List<Option> getKategoriPemasukan() throws SQLException { return getKategoriOptions(); }
    public List<Option> kategoriAll() throws SQLException { return getKategoriOptions(); }

    public void insertKategori(String nama) throws SQLException {
        try (Connection connection = Koneksi.getConnection(); PreparedStatement statement = connection.prepareStatement("INSERT INTO kategori_pemasukan(nama_kategori) VALUES(?)")) {
            statement.setString(1, nama);
            statement.executeUpdate();
        }
    }

    public void updateKategori(int id, String nama) throws SQLException {
        try (Connection connection = Koneksi.getConnection(); PreparedStatement statement = connection.prepareStatement("UPDATE kategori_pemasukan SET nama_kategori=? WHERE id_kategori_pemasukan=?")) {
            statement.setString(1, nama);
            statement.setInt(2, id);
            statement.executeUpdate();
        }
    }

    public void deleteKategori(int id) throws SQLException {
        try (Connection connection = Koneksi.getConnection(); PreparedStatement statement = connection.prepareStatement("DELETE FROM kategori_pemasukan WHERE id_kategori_pemasukan=?")) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    private void fillStatement(PreparedStatement statement, Pemasukan pemasukan) throws SQLException {
        statement.setDate(1, Date.valueOf(pemasukan.getTanggal()));
        if (pemasukan.getIdKategoriPemasukan() <= 0) statement.setNull(2, Types.INTEGER); else statement.setInt(2, pemasukan.getIdKategoriPemasukan());
        statement.setDouble(3, pemasukan.getNominal());
        statement.setString(4, pemasukan.getKeterangan());
    }
}
