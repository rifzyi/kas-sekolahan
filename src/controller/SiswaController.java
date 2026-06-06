package controller;


import java.sql.*;import java.util.*;import koneksi.Koneksi;import model.Siswa;

public class SiswaController{private final Connection conn=Koneksi.getConnection();
 public List<Siswa> getAll(String keyword)throws Exception{List<Siswa> list=new ArrayList<>();String sql="SELECT s.*, k.kode_kelas, k.nama_kelas FROM siswa s LEFT JOIN kelas k ON s.id_kelas=k.id_kelas WHERE s.nis LIKE ? OR s.nama_siswa LIKE ? OR COALESCE(k.nama_kelas,'') LIKE ? OR COALESCE(k.kode_kelas,'') LIKE ? ORDER BY s.id_siswa DESC";try(PreparedStatement ps=conn.prepareStatement(sql)){String k="%"+keyword+"%";for(int i=1;i<=4;i++)ps.setString(i,k);try(ResultSet rs=ps.executeQuery()){while(rs.next())list.add(map(rs));}}return list;}
 public int count()throws Exception{try(PreparedStatement ps=conn.prepareStatement("SELECT COUNT(*) total FROM siswa");ResultSet rs=ps.executeQuery()){return rs.next()?rs.getInt("total"):0;}}
 public void insert(Siswa s)throws Exception{String sql="INSERT INTO siswa (nis,nama_siswa,id_kelas,jenis_kelamin,alamat) VALUES (?,?,?,?,?)";try(PreparedStatement ps=conn.prepareStatement(sql)){fill(ps,s);ps.executeUpdate();}}
 public void update(Siswa s)throws Exception{String sql="UPDATE siswa SET nis=?, nama_siswa=?, id_kelas=?, jenis_kelamin=?, alamat=? WHERE id_siswa=?";try(PreparedStatement ps=conn.prepareStatement(sql)){fill(ps,s);ps.setInt(6,s.getIdSiswa());ps.executeUpdate();}}
 public void delete(int id)throws Exception{try(PreparedStatement ps=conn.prepareStatement("DELETE FROM siswa WHERE id_siswa=?")){ps.setInt(1,id);ps.executeUpdate();}}
 private void fill(PreparedStatement ps,Siswa s)throws Exception{ps.setString(1,s.getNis());ps.setString(2,s.getNamaSiswa());ps.setInt(3,s.getIdKelas());ps.setString(4,s.getJenisKelamin());ps.setString(5,s.getAlamat());}
 private Siswa map(ResultSet rs)throws Exception{return new Siswa(rs.getInt("id_siswa"),rs.getString("nis"),rs.getString("nama_siswa"),rs.getInt("id_kelas"),rs.getString("kode_kelas"),rs.getString("nama_kelas"),rs.getString("jenis_kelamin"),rs.getString("alamat"));}}

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import koneksi.Koneksi;
import model.Siswa;

public class SiswaController {
  private final Connection conn = Koneksi.getConnection();

  public List<Siswa> getAll(String keyword) throws Exception {
    List<Siswa> list = new ArrayList<>();
    String sql = "SELECT * FROM siswa WHERE nis LIKE ? OR nama_siswa LIKE ? " +
                 "OR kelas LIKE ? ORDER BY id_siswa DESC";
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

  public int count() throws Exception {
    try (PreparedStatement ps =
             conn.prepareStatement("SELECT COUNT(*) total FROM siswa");
         ResultSet rs = ps.executeQuery()) {
      return rs.next() ? rs.getInt("total") : 0;
    }
  }

  public void insert(Siswa s) throws Exception {
    String sql = "INSERT INTO siswa (nis, nama_siswa, kelas, jenis_kelamin, " +
                 "alamat) VALUES (?, ?, ?, ?, ?)";
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
      fill(ps, s);
      ps.executeUpdate();
    }
  }

  public void update(Siswa s) throws Exception {
    String sql = "UPDATE siswa SET nis = ?, nama_siswa = ?, kelas = ?, " +
                 "jenis_kelamin = ?, alamat = ? WHERE id_siswa = ?";
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
      fill(ps, s);
      ps.setInt(6, s.getIdSiswa());
      ps.executeUpdate();
    }
  }

  public void delete(int id)throws Exception {
    try (PreparedStatement ps =
             conn.prepareStatement("DELETE FROM siswa WHERE id_siswa = ?")) {
      ps.setInt(1, id);
      ps.executeUpdate();
    }
  }

  private void fill(PreparedStatement ps, Siswa s) throws Exception {
    ps.setString(1, s.getNis());
    ps.setString(2, s.getNamaSiswa());
    ps.setString(3, s.getKelas());
    ps.setString(4, s.getJenisKelamin());
    ps.setString(5, s.getAlamat());
  }

  private Siswa map(ResultSet rs) throws Exception {
    return new Siswa(rs.getInt("id_siswa"), rs.getString("nis"),
                     rs.getString("nama_siswa"), rs.getString("kelas"),
                     rs.getString("jenis_kelamin"), rs.getString("alamat"));
  }
}

