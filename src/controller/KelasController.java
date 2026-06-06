package controller;

import java.sql.*;import java.util.*;import koneksi.Koneksi;import model.Kelas;import util.UIUtils.Option;

public class KelasController{private final Connection conn=Koneksi.getConnection();
 public List<Kelas> getAll(String keyword)throws Exception{List<Kelas> list=new ArrayList<>();String sql="SELECT * FROM kelas WHERE kode_kelas LIKE ? OR nama_kelas LIKE ? ORDER BY kode_kelas";try(PreparedStatement ps=conn.prepareStatement(sql)){String k="%"+keyword+"%";ps.setString(1,k);ps.setString(2,k);try(ResultSet rs=ps.executeQuery()){while(rs.next())list.add(map(rs));}}return list;}
 public List<Option> options()throws Exception{List<Option> list=new ArrayList<>();for(Kelas k:getAll(""))list.add(new Option(k.getIdKelas(),k.toString()));return list;}
 public int count()throws Exception{try(PreparedStatement ps=conn.prepareStatement("SELECT COUNT(*) total FROM kelas");ResultSet rs=ps.executeQuery()){return rs.next()?rs.getInt("total"):0;}}
 public void insert(Kelas k)throws Exception{try(PreparedStatement ps=conn.prepareStatement("INSERT INTO kelas (kode_kelas,nama_kelas) VALUES (?,?)")){ps.setString(1,k.getKodeKelas());ps.setString(2,k.getNamaKelas());ps.executeUpdate();}}
 public void update(Kelas k)throws Exception{try(PreparedStatement ps=conn.prepareStatement("UPDATE kelas SET kode_kelas=?, nama_kelas=? WHERE id_kelas=?")){ps.setString(1,k.getKodeKelas());ps.setString(2,k.getNamaKelas());ps.setInt(3,k.getIdKelas());ps.executeUpdate();}}
 public void delete(int id)throws Exception{try(PreparedStatement ps=conn.prepareStatement("DELETE FROM kelas WHERE id_kelas=?")){ps.setInt(1,id);ps.executeUpdate();}}
 private Kelas map(ResultSet rs)throws Exception{return new Kelas(rs.getInt("id_kelas"),rs.getString("kode_kelas"),rs.getString("nama_kelas"));}}
