CREATE DATABASE IF NOT EXISTS kas_sekolah CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE kas_sekolah;

CREATE TABLE IF NOT EXISTS users (

  id_user INT AUTO_INCREMENT PRIMARY KEY,
  nama VARCHAR(100) NOT NULL,
  username VARCHAR(50) NOT NULL UNIQUE,
  password VARCHAR(100) NOT NULL,
  role ENUM('Admin','Bendahara') DEFAULT 'Bendahara'
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS kelas (
  id_kelas INT AUTO_INCREMENT PRIMARY KEY,
  kode_kelas VARCHAR(20) NOT NULL UNIQUE,
  nama_kelas VARCHAR(100) NOT NULL
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS siswa (
  id_siswa INT AUTO_INCREMENT PRIMARY KEY,
  nis VARCHAR(20) NOT NULL UNIQUE,
  nama_siswa VARCHAR(100) NOT NULL,
  id_kelas INT,
  jenis_kelamin ENUM('Laki-laki','Perempuan'),
  alamat TEXT,
  CONSTRAINT fk_siswa_kelas FOREIGN KEY (id_kelas) REFERENCES kelas(id_kelas)
    ON UPDATE CASCADE ON DELETE SET NULL
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS kategori_pemasukan (
  id_kategori_pemasukan INT AUTO_INCREMENT PRIMARY KEY,
  nama_kategori VARCHAR(100) NOT NULL UNIQUE
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS pemasukan (
  id_pemasukan INT AUTO_INCREMENT PRIMARY KEY,
  tanggal DATE NOT NULL,
  id_kategori_pemasukan INT,
  nominal DOUBLE NOT NULL,
  keterangan TEXT,
  CONSTRAINT fk_pemasukan_kategori FOREIGN KEY (id_kategori_pemasukan)
    REFERENCES kategori_pemasukan(id_kategori_pemasukan)
    ON UPDATE CASCADE ON DELETE SET NULL
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS kategori_pengeluaran (
  id_kategori_pengeluaran INT AUTO_INCREMENT PRIMARY KEY,
  nama_kategori VARCHAR(100) NOT NULL UNIQUE
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS pengeluaran (
  id_pengeluaran INT AUTO_INCREMENT PRIMARY KEY,
  tanggal DATE NOT NULL,
  id_kategori_pengeluaran INT,
  nominal DOUBLE NOT NULL,
  keterangan TEXT,
  CONSTRAINT fk_pengeluaran_kategori FOREIGN KEY (id_kategori_pengeluaran)
    REFERENCES kategori_pengeluaran(id_kategori_pengeluaran)
    ON UPDATE CASCADE ON DELETE SET NULL
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS pengaturan (
  id_pengaturan INT AUTO_INCREMENT PRIMARY KEY,
  nama_sekolah VARCHAR(150) NOT NULL,
  alamat TEXT,
  telepon VARCHAR(30),
  email VARCHAR(100),
  kepala_sekolah VARCHAR(100),
  bendahara VARCHAR(100),
  logo_path VARCHAR(255)
) ENGINE=InnoDB;

INSERT INTO users (nama, username, password, role)
SELECT 'Administrator', 'admin', 'admin123', 'Admin'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'admin');

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

INSERT INTO kategori_pemasukan (nama_kategori)
SELECT 'SPP' WHERE NOT EXISTS (SELECT 1 FROM kategori_pemasukan WHERE nama_kategori = 'SPP');
INSERT INTO kategori_pemasukan (nama_kategori)
SELECT 'Dana BOS' WHERE NOT EXISTS (SELECT 1 FROM kategori_pemasukan WHERE nama_kategori = 'Dana BOS');
INSERT INTO kategori_pemasukan (nama_kategori)
SELECT 'Donasi' WHERE NOT EXISTS (SELECT 1 FROM kategori_pemasukan WHERE nama_kategori = 'Donasi');
INSERT INTO kategori_pemasukan (nama_kategori)
SELECT 'Infak' WHERE NOT EXISTS (SELECT 1 FROM kategori_pemasukan WHERE nama_kategori = 'Infak');
INSERT INTO kategori_pemasukan (nama_kategori)
SELECT 'Bantuan Pemerintah' WHERE NOT EXISTS (SELECT 1 FROM kategori_pemasukan WHERE nama_kategori = 'Bantuan Pemerintah');

INSERT INTO kategori_pengeluaran (nama_kategori)
SELECT 'ATK' WHERE NOT EXISTS (SELECT 1 FROM kategori_pengeluaran WHERE nama_kategori = 'ATK');
INSERT INTO kategori_pengeluaran (nama_kategori)
SELECT 'Listrik' WHERE NOT EXISTS (SELECT 1 FROM kategori_pengeluaran WHERE nama_kategori = 'Listrik');
INSERT INTO kategori_pengeluaran (nama_kategori)
SELECT 'Air' WHERE NOT EXISTS (SELECT 1 FROM kategori_pengeluaran WHERE nama_kategori = 'Air');
INSERT INTO kategori_pengeluaran (nama_kategori)
SELECT 'Internet' WHERE NOT EXISTS (SELECT 1 FROM kategori_pengeluaran WHERE nama_kategori = 'Internet');
INSERT INTO kategori_pengeluaran (nama_kategori)
SELECT 'Honor Guru' WHERE NOT EXISTS (SELECT 1 FROM kategori_pengeluaran WHERE nama_kategori = 'Honor Guru');
INSERT INTO kategori_pengeluaran (nama_kategori)
SELECT 'Kegiatan Sekolah' WHERE NOT EXISTS (SELECT 1 FROM kategori_pengeluaran WHERE nama_kategori = 'Kegiatan Sekolah');

INSERT INTO pengaturan (nama_sekolah, alamat, telepon, email, kepala_sekolah, bendahara, logo_path)
SELECT 'MI Nahdlotut Tholibin', '-', '-', '-', '-', '-', ''
WHERE NOT EXISTS (SELECT 1 FROM pengaturan);

