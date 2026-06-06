CREATE DATABASE IF NOT EXISTS kas_sekolah CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE kas_sekolah;

CREATE TABLE IF NOT EXISTS users (
    id_user INT AUTO_INCREMENT PRIMARY KEY,
    nama VARCHAR(100) NOT NULL,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    role ENUM('Admin','Bendahara') DEFAULT 'Bendahara'
);

CREATE TABLE IF NOT EXISTS siswa (
    id_siswa INT AUTO_INCREMENT PRIMARY KEY,
    nis VARCHAR(20) NOT NULL UNIQUE,
    nama_siswa VARCHAR(100) NOT NULL,
    kelas VARCHAR(20) NOT NULL,
    jenis_kelamin ENUM('Laki-laki','Perempuan'),
    alamat TEXT
);

CREATE TABLE IF NOT EXISTS pemasukan (
    id_pemasukan INT AUTO_INCREMENT PRIMARY KEY,
    tanggal DATE NOT NULL,
    sumber_dana VARCHAR(100) NOT NULL,
    nominal DOUBLE NOT NULL,
    keterangan TEXT
);

CREATE TABLE IF NOT EXISTS pengeluaran (
    id_pengeluaran INT AUTO_INCREMENT PRIMARY KEY,
    tanggal DATE NOT NULL,
    keperluan VARCHAR(100) NOT NULL,
    nominal DOUBLE NOT NULL,
    keterangan TEXT
);

INSERT INTO users (nama, username, password, role)
SELECT 'Administrator', 'admin', 'admin123', 'Admin'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'admin');
