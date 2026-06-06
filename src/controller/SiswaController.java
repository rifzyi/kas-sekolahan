// File: controller/SiswaController.java
package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import model.Siswa;
import util.Koneksi;
import util.UIUtils.Option;

public class SiswaController {
    public List<Siswa> getAll(String keyword) throws SQLException {
        List<Siswa> data = new ArrayList<>();
        String sql = "SELECT s.id_siswa, s.nis, s.nama_siswa, s.id_kelas, COALESCE(CONCAT(k.kode_kelas, ' - ', k.nama_kelas), '-') AS nama_kelas, s.jenis_kelamin, s.alamat "
                + "FROM siswa s LEFT JOIN kelas k ON s.id_kelas = k.id_kelas "
                + "WHERE (? = '' OR s.nis LIKE ? OR s.nama_siswa LIKE ? OR k.nama_kelas LIKE ? OR k.kode_kelas LIKE ?) "
                + "ORDER BY s.nama_siswa ASC";
        String like = "%" + (keyword == null ? "" : keyword.trim()) + "%";
        String safeKeyword = keyword == null ? "" : keyword.trim();
        try (Connection connection = Koneksi.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, safeKeyword);
            statement.setString(2, like);
            statement.setString(3, like);
            statement.setString(4, like);
            statement.setString(5, like);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    data.add(new Siswa(resultSet.getInt("id_siswa"), resultSet.getString("nis"), resultSet.getString("nama_siswa"), resultSet.getInt("id_kelas"), resultSet.getString("nama_kelas"), resultSet.getString("jenis_kelamin"), resultSet.getString("alamat")));
                }
            }
        }
        return data;
    }

    public List<Siswa> getAll() throws SQLException { return getAll(""); }

    public void insert(Siswa siswa) throws SQLException {
        String sql = "INSERT INTO siswa (nis, nama_siswa, id_kelas, jenis_kelamin, alamat) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = Koneksi.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            fillStatement(statement, siswa);
            statement.executeUpdate();
        }
    }

    public void update(Siswa siswa) throws SQLException {
        String sql = "UPDATE siswa SET nis = ?, nama_siswa = ?, id_kelas = ?, jenis_kelamin = ?, alamat = ? WHERE id_siswa = ?";
        try (Connection connection = Koneksi.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            fillStatement(statement, siswa);
            statement.setInt(6, siswa.getIdSiswa());
            statement.executeUpdate();
        }
    }

    public void delete(int idSiswa) throws SQLException {
        try (Connection connection = Koneksi.getConnection(); PreparedStatement statement = connection.prepareStatement("DELETE FROM siswa WHERE id_siswa = ?")) {
            statement.setInt(1, idSiswa);
            statement.executeUpdate();
        }
    }

    public List<Option> getKelasOptions() throws SQLException {
        List<Option> options = new ArrayList<>();
        String sql = "SELECT id_kelas, CONCAT(kode_kelas, ' - ', nama_kelas) AS label FROM kelas ORDER BY kode_kelas";
        try (Connection connection = Koneksi.getConnection(); PreparedStatement statement = connection.prepareStatement(sql); ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                options.add(new Option(resultSet.getInt("id_kelas"), resultSet.getString("label")));
            }
        }
        return options;
    }

    public List<Option> kelasOptions() throws SQLException { return getKelasOptions(); }

    public int count() throws SQLException {
        try (Connection connection = Koneksi.getConnection(); PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) FROM siswa"); ResultSet resultSet = statement.executeQuery()) {
            resultSet.next();
            return resultSet.getInt(1);
        }
    }

    private void fillStatement(PreparedStatement statement, Siswa siswa) throws SQLException {
        statement.setString(1, siswa.getNis());
        statement.setString(2, siswa.getNamaSiswa());
        if (siswa.getIdKelas() <= 0) statement.setNull(3, Types.INTEGER); else statement.setInt(3, siswa.getIdKelas());
        statement.setString(4, siswa.getJenisKelamin());
        statement.setString(5, siswa.getAlamat());
    }
}
