CREATE DATABASE IF NOT EXISTS kas_sekolah CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE kas_sekolah;

CREATE TABLE IF NOT EXISTS users (
  id_user INT AUTO_INCREMENT PRIMARY KEY,
  nama VARCHAR(100) NOT NULL,
  username VARCHAR(50) NOT NULL UNIQUE,
  password VARCHAR(100) NOT NULL,
  role ENUM('Admin','Bendahara') NOT NULL DEFAULT 'Bendahara'
);

CREATE TABLE IF NOT EXISTS pengaturan (
  id_pengaturan INT AUTO_INCREMENT PRIMARY KEY,
  nama_sekolah VARCHAR(150) NOT NULL,
  alamat TEXT,
  telepon VARCHAR(30),
  kepala_sekolah VARCHAR(100),
  bendahara VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS kelas (
  id_kelas INT AUTO_INCREMENT PRIMARY KEY,
  kode_kelas VARCHAR(20) NOT NULL UNIQUE,
  nama_kelas VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS siswa (
  id_siswa INT AUTO_INCREMENT PRIMARY KEY,
  nis VARCHAR(30) NOT NULL UNIQUE,
  nama_siswa VARCHAR(120) NOT NULL,
  id_kelas INT NULL,
  jenis_kelamin ENUM('Laki-laki','Perempuan') NOT NULL,
  alamat TEXT,
  CONSTRAINT fk_siswa_kelas FOREIGN KEY (id_kelas) REFERENCES kelas(id_kelas)
    ON UPDATE CASCADE ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS kategori_pemasukan (
  id_kategori_pemasukan INT AUTO_INCREMENT PRIMARY KEY,
  nama_kategori VARCHAR(100) NOT NULL UNIQUE,
  is_spp TINYINT(1) NOT NULL DEFAULT 0
);

CREATE TABLE IF NOT EXISTS pemasukan (
  id_pemasukan INT AUTO_INCREMENT PRIMARY KEY,
  tanggal DATE NOT NULL,
  id_siswa INT NULL,
  id_kategori_pemasukan INT NULL,
  nominal DECIMAL(15,2) NOT NULL,
  keterangan TEXT,
  id_user_input INT NULL,
  CONSTRAINT fk_pemasukan_siswa FOREIGN KEY (id_siswa) REFERENCES siswa(id_siswa)
    ON UPDATE CASCADE ON DELETE SET NULL,
  CONSTRAINT fk_pemasukan_kategori FOREIGN KEY (id_kategori_pemasukan) REFERENCES kategori_pemasukan(id_kategori_pemasukan)
    ON UPDATE CASCADE ON DELETE SET NULL,
  CONSTRAINT fk_pemasukan_user FOREIGN KEY (id_user_input) REFERENCES users(id_user)
    ON UPDATE CASCADE ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS kategori_pengeluaran (
  id_kategori_pengeluaran INT AUTO_INCREMENT PRIMARY KEY,
  nama_kategori VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS pengeluaran (
  id_pengeluaran INT AUTO_INCREMENT PRIMARY KEY,
  tanggal DATE NOT NULL,
  id_kategori_pengeluaran INT NULL,
  nominal DECIMAL(15,2) NOT NULL,
  keterangan TEXT,
  id_user_input INT NULL,
  CONSTRAINT fk_pengeluaran_kategori FOREIGN KEY (id_kategori_pengeluaran) REFERENCES kategori_pengeluaran(id_kategori_pengeluaran)
    ON UPDATE CASCADE ON DELETE SET NULL,
  CONSTRAINT fk_pengeluaran_user FOREIGN KEY (id_user_input) REFERENCES users(id_user)
    ON UPDATE CASCADE ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS tabungan_siswa (
  id_tabungan INT AUTO_INCREMENT PRIMARY KEY,
  id_siswa INT NOT NULL,
  tanggal DATE NOT NULL,
  jenis ENUM('Setor','Tarik') NOT NULL,
  nominal DECIMAL(15,2) NOT NULL,
  keterangan TEXT,
  CONSTRAINT fk_tabungan_siswa FOREIGN KEY (id_siswa) REFERENCES siswa(id_siswa)
    ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS audit_log (
  id_log INT AUTO_INCREMENT PRIMARY KEY,
  id_user INT NULL,
  aksi VARCHAR(100) NOT NULL,
  detail TEXT,
  waktu DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_audit_user FOREIGN KEY (id_user) REFERENCES users(id_user)
    ON UPDATE CASCADE ON DELETE SET NULL
);

INSERT INTO users (nama, username, password, role)
SELECT 'Administrator', 'admin', 'admin123', 'Admin'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'admin');

INSERT INTO pengaturan (nama_sekolah, alamat, telepon, kepala_sekolah, bendahara)
SELECT 'Madrasah Ibtidaiyah', '-', '-', '-', '-'
WHERE NOT EXISTS (SELECT 1 FROM pengaturan);

INSERT INTO kelas (kode_kelas, nama_kelas)
SELECT 'I', 'Kelas I' WHERE NOT EXISTS (SELECT 1 FROM kelas WHERE kode_kelas = 'I');
INSERT INTO kelas (kode_kelas, nama_kelas)
SELECT 'II', 'Kelas II' WHERE NOT EXISTS (SELECT 1 FROM kelas WHERE kode_kelas = 'II');
INSERT INTO kelas (kode_kelas, nama_kelas)
SELECT 'III', 'Kelas III' WHERE NOT EXISTS (SELECT 1 FROM kelas WHERE kode_kelas = 'III');
INSERT INTO kelas (kode_kelas, nama_kelas)
SELECT 'IV', 'Kelas IV' WHERE NOT EXISTS (SELECT 1 FROM kelas WHERE kode_kelas = 'IV');
INSERT INTO kelas (kode_kelas, nama_kelas)
SELECT 'V', 'Kelas V' WHERE NOT EXISTS (SELECT 1 FROM kelas WHERE kode_kelas = 'V');
INSERT INTO kelas (kode_kelas, nama_kelas)
SELECT 'VI', 'Kelas VI' WHERE NOT EXISTS (SELECT 1 FROM kelas WHERE kode_kelas = 'VI');

INSERT INTO kategori_pemasukan (nama_kategori, is_spp)
SELECT 'SPP', 1 WHERE NOT EXISTS (SELECT 1 FROM kategori_pemasukan WHERE nama_kategori = 'SPP');
INSERT INTO kategori_pemasukan (nama_kategori, is_spp)
SELECT 'Infak Siswa', 0 WHERE NOT EXISTS (SELECT 1 FROM kategori_pemasukan WHERE nama_kategori = 'Infak Siswa');
INSERT INTO kategori_pemasukan (nama_kategori, is_spp)
SELECT 'Bantuan Operasional', 0 WHERE NOT EXISTS (SELECT 1 FROM kategori_pemasukan WHERE nama_kategori = 'Bantuan Operasional');

INSERT INTO kategori_pengeluaran (nama_kategori)
SELECT 'ATK' WHERE NOT EXISTS (SELECT 1 FROM kategori_pengeluaran WHERE nama_kategori = 'ATK');
INSERT INTO kategori_pengeluaran (nama_kategori)
SELECT 'Kegiatan Siswa' WHERE NOT EXISTS (SELECT 1 FROM kategori_pengeluaran WHERE nama_kategori = 'Kegiatan Siswa');
