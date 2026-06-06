package koneksi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class Koneksi {
  private static final String HOST_URL = "jdbc:mysql://localhost:3306/";
  private static final String DB_URL =
      "jdbc:mysql://localhost:3306/" +
      "kas_sekolah?useSSL=false&serverTimezone=Asia/" +
      "Jakarta&allowPublicKeyRetrieval=true";
  private static final String USER = "root";
  private static final String PASS = "";
  private static Connection conn;

  private Koneksi() {}

  public static Connection getConnection() {
    try {
      if (conn == null || conn.isClosed()) {
        Class.forName("com.mysql.cj.jdbc.Driver");
        initializeDatabase();
        conn = DriverManager.getConnection(DB_URL, USER, PASS);
      }
    } catch (Exception e) {
      throw new RuntimeException("Koneksi database gagal: " + e.getMessage(),
                                 e);
    }
    return conn;
  }

  private static void initializeDatabase() throws SQLException {
    try (Connection serverConnection = DriverManager.getConnection(
             HOST_URL + ("?useSSL=false&serverTimezone=Asia/" +
                         "Jakarta&allowPublicKeyRetrieval=true"),
             USER, PASS);
         Statement statement = serverConnection.createStatement()) {
      statement.executeUpdate(
          "CREATE DATABASE IF NOT EXISTS kas_sekolah CHARACTER SET utf8mb4 " +
          "COLLATE utf8mb4_unicode_ci");
    }

    try (Connection databaseConnection =
             DriverManager.getConnection(DB_URL, USER, PASS);
         Statement statement = databaseConnection.createStatement()) {
      statement.executeUpdate(
          "CREATE TABLE IF NOT EXISTS users ("
          + "id_user INT AUTO_INCREMENT PRIMARY KEY,"
          + "nama VARCHAR(100) NOT NULL,"
          + "username VARCHAR(50) NOT NULL UNIQUE,"
          + "password VARCHAR(100) NOT NULL,"
          + "role ENUM('Admin','Bendahara') DEFAULT 'Bendahara')");
      statement.executeUpdate("CREATE TABLE IF NOT EXISTS siswa ("
                              + "id_siswa INT AUTO_INCREMENT PRIMARY KEY,"
                              + "nis VARCHAR(20) NOT NULL UNIQUE,"
                              + "nama_siswa VARCHAR(100) NOT NULL,"
                              + "kelas VARCHAR(20) NOT NULL,"
                              + "jenis_kelamin ENUM('Laki-laki','Perempuan'),"
                              + "alamat TEXT)");
      statement.executeUpdate("CREATE TABLE IF NOT EXISTS pemasukan ("
                              + "id_pemasukan INT AUTO_INCREMENT PRIMARY KEY,"
                              + "tanggal DATE NOT NULL,"
                              + "sumber_dana VARCHAR(100) NOT NULL,"
                              + "nominal DOUBLE NOT NULL,"
                              + "keterangan TEXT)");
      statement.executeUpdate("CREATE TABLE IF NOT EXISTS pengeluaran ("
                              + "id_pengeluaran INT AUTO_INCREMENT PRIMARY KEY,"
                              + "tanggal DATE NOT NULL,"
                              + "keperluan VARCHAR(100) NOT NULL,"
                              + "nominal DOUBLE NOT NULL,"
                              + "keterangan TEXT)");
      try (PreparedStatement ps = databaseConnection.prepareStatement(
               "INSERT INTO users (nama, username, password, role) "
               + "SELECT ?, ?, ?, ? WHERE NOT EXISTS (SELECT 1 FROM users " +
                 "WHERE username = ?)")) {
        ps.setString(1, "Administrator");
        ps.setString(2, "admin");
        ps.setString(3, "admin123");
        ps.setString(4, "Admin");
        ps.setString(5, "admin");
        ps.executeUpdate();
      }
    }
  }
}
