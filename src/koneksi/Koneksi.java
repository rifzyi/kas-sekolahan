package koneksi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class Koneksi {
  private static final String HOST_URL = "jdbc:mysql://localhost:3306/";
  private static final String DB_URL = "jdbc:mysql://localhost:3306/kas_sekolah?useSSL=false&serverTimezone=Asia/Jakarta&allowPublicKeyRetrieval=true";
  private static final String USER = "root";
  private static final String PASS = "";
  private static Connection connection;

  private Koneksi() {}

  public static synchronized Connection getConnection() {
    try {
      if (connection == null || connection.isClosed()) {
        Class.forName("com.mysql.cj.jdbc.Driver");
        initializeDatabase();
        connection = DriverManager.getConnection(DB_URL, USER, PASS);
      }
      return connection;
    } catch (Exception e) {
      throw new RuntimeException("Koneksi database gagal: " + e.getMessage(), e);
    }
  }

  private static void initializeDatabase() throws SQLException {
    try (Connection server = DriverManager.getConnection(HOST_URL + "?useSSL=false&serverTimezone=Asia/Jakarta&allowPublicKeyRetrieval=true", USER, PASS);
         Statement st = server.createStatement()) {
      st.executeUpdate("CREATE DATABASE IF NOT EXISTS kas_sekolah CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci");
    }
    try (Connection db = DriverManager.getConnection(DB_URL, USER, PASS); Statement st = db.createStatement()) {
      st.executeUpdate("CREATE TABLE IF NOT EXISTS users (id_user INT AUTO_INCREMENT PRIMARY KEY, nama VARCHAR(100) NOT NULL, username VARCHAR(50) NOT NULL UNIQUE, password VARCHAR(100) NOT NULL, role ENUM('Admin','Bendahara') NOT NULL DEFAULT 'Bendahara')");
      st.executeUpdate("CREATE TABLE IF NOT EXISTS kelas (id_kelas INT AUTO_INCREMENT PRIMARY KEY, kode_kelas VARCHAR(20) NOT NULL UNIQUE, nama_kelas VARCHAR(100) NOT NULL)");
      st.executeUpdate("CREATE TABLE IF NOT EXISTS siswa (id_siswa INT AUTO_INCREMENT PRIMARY KEY, nis VARCHAR(30) NOT NULL UNIQUE, nama_siswa VARCHAR(120) NOT NULL, id_kelas INT NULL, jenis_kelamin ENUM('Laki-laki','Perempuan') NOT NULL, alamat TEXT, CONSTRAINT fk_siswa_kelas FOREIGN KEY (id_kelas) REFERENCES kelas(id_kelas) ON UPDATE CASCADE ON DELETE SET NULL)");
      st.executeUpdate("CREATE TABLE IF NOT EXISTS kategori_pemasukan (id_kategori_pemasukan INT AUTO_INCREMENT PRIMARY KEY, nama_kategori VARCHAR(100) NOT NULL UNIQUE)");
      st.executeUpdate("CREATE TABLE IF NOT EXISTS kategori_pengeluaran (id_kategori_pengeluaran INT AUTO_INCREMENT PRIMARY KEY, nama_kategori VARCHAR(100) NOT NULL UNIQUE)");
      st.executeUpdate("CREATE TABLE IF NOT EXISTS pemasukan (id_pemasukan INT AUTO_INCREMENT PRIMARY KEY, tanggal DATE NOT NULL, id_kategori_pemasukan INT NULL, nominal DECIMAL(15,2) NOT NULL, keterangan TEXT, CONSTRAINT fk_pemasukan_kategori FOREIGN KEY (id_kategori_pemasukan) REFERENCES kategori_pemasukan(id_kategori_pemasukan) ON UPDATE CASCADE ON DELETE SET NULL)");
      st.executeUpdate("CREATE TABLE IF NOT EXISTS pengeluaran (id_pengeluaran INT AUTO_INCREMENT PRIMARY KEY, tanggal DATE NOT NULL, id_kategori_pengeluaran INT NULL, nominal DECIMAL(15,2) NOT NULL, keterangan TEXT, CONSTRAINT fk_pengeluaran_kategori FOREIGN KEY (id_kategori_pengeluaran) REFERENCES kategori_pengeluaran(id_kategori_pengeluaran) ON UPDATE CASCADE ON DELETE SET NULL)");
      st.executeUpdate("CREATE TABLE IF NOT EXISTS pengaturan (id_pengaturan INT AUTO_INCREMENT PRIMARY KEY, nama_sekolah VARCHAR(150) NOT NULL, alamat TEXT, telepon VARCHAR(30), email VARCHAR(100), kepala_sekolah VARCHAR(100), bendahara VARCHAR(100), logo_path VARCHAR(255))");
      seed(db);
    }
  }

  private static void seed(Connection db) throws SQLException {
    insert(db, "INSERT INTO users (nama, username, password, role) SELECT ?,?,?,? WHERE NOT EXISTS (SELECT 1 FROM users WHERE username=?)", "Administrator", "admin", "admin123", "Admin", "admin");
    String[][] kelas = {{"I", "Kelas I"}, {"II", "Kelas II"}, {"III", "Kelas III"}, {"IV", "Kelas IV"}, {"V", "Kelas V"}, {"VI", "Kelas VI"}};
    for (String[] k : kelas) insert(db, "INSERT INTO kelas (kode_kelas,nama_kelas) SELECT ?,? WHERE NOT EXISTS (SELECT 1 FROM kelas WHERE kode_kelas=?)", k[0], k[1], k[0]);
    for (String k : new String[] {"Infak Siswa", "Bantuan Operasional", "Donasi", "Kegiatan Sekolah"}) insert(db, "INSERT INTO kategori_pemasukan (nama_kategori) SELECT ? WHERE NOT EXISTS (SELECT 1 FROM kategori_pemasukan WHERE nama_kategori=?)", k, k);
    for (String k : new String[] {"ATK", "Listrik dan Air", "Perawatan Sekolah", "Kegiatan Siswa"}) insert(db, "INSERT INTO kategori_pengeluaran (nama_kategori) SELECT ? WHERE NOT EXISTS (SELECT 1 FROM kategori_pengeluaran WHERE nama_kategori=?)", k, k);
    insert(db, "INSERT INTO pengaturan (nama_sekolah,alamat,telepon,email,kepala_sekolah,bendahara,logo_path) SELECT ?,?,?,?,?,?,? WHERE NOT EXISTS (SELECT 1 FROM pengaturan)", "MI Nahdlotut Tholibin", "-", "-", "-", "-", "-", "");
  }

  private static void insert(Connection db, String sql, Object... values) throws SQLException {
    try (PreparedStatement ps = db.prepareStatement(sql)) {
      for (int i = 0; i < values.length; i++) ps.setObject(i + 1, values[i]);
      ps.executeUpdate();
    }
  }
}
