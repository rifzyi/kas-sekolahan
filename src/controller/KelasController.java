// File: controller/KelasController.java
package controller;

import java.sql.*;import java.util.*;import model.Kelas;import util.Koneksi;import util.Option;

public class KelasController {
    public List<Kelas> getAll() throws SQLException { List<Kelas> list=new ArrayList<>(); String sql="SELECT id_kelas,kode_kelas,nama_kelas FROM kelas ORDER BY kode_kelas"; try(Connection c=Koneksi.getConnection();PreparedStatement ps=c.prepareStatement(sql);ResultSet rs=ps.executeQuery()){ while(rs.next()) list.add(new Kelas(rs.getInt(1),rs.getString(2),rs.getString(3))); } return list; }
    public void insert(Kelas k) throws SQLException { try(Connection c=Koneksi.getConnection();PreparedStatement ps=c.prepareStatement("INSERT INTO kelas(kode_kelas,nama_kelas) VALUES(?,?)")){ ps.setString(1,k.getKodeKelas());ps.setString(2,k.getNamaKelas());ps.executeUpdate(); } }
    public void update(Kelas k) throws SQLException { try(Connection c=Koneksi.getConnection();PreparedStatement ps=c.prepareStatement("UPDATE kelas SET kode_kelas=?,nama_kelas=? WHERE id_kelas=?")){ ps.setString(1,k.getKodeKelas());ps.setString(2,k.getNamaKelas());ps.setInt(3,k.getIdKelas());ps.executeUpdate(); } }
    public void delete(int id) throws SQLException { try(Connection c=Koneksi.getConnection();PreparedStatement ps=c.prepareStatement("DELETE FROM kelas WHERE id_kelas=?")){ ps.setInt(1,id);ps.executeUpdate(); } }
    public List<Option> getOptions() throws SQLException { List<Option> ops=new ArrayList<>(); for(Kelas k:getAll()) ops.add(new Option(k.getIdKelas(), k.getKodeKelas()+" - "+k.getNamaKelas())); return ops; }
}
