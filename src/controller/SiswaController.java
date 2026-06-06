// File: controller/SiswaController.java
package controller;

import java.sql.*;import java.util.*;import model.Siswa;import util.Koneksi;import util.Option;import util.SessionManager;

public class SiswaController {
    public List<Siswa> getAll() throws SQLException { return getAll(""); }
    public List<Siswa> getAll(String keyword) throws SQLException { List<Siswa> data=new ArrayList<>(); String safe=keyword==null?"":keyword.trim(); String like="%"+safe+"%"; String sql="SELECT s.id_siswa,s.nis,s.nama_siswa,s.id_kelas,COALESCE(k.nama_kelas,'-') nama_kelas,s.jenis_kelamin,s.alamat FROM siswa s LEFT JOIN kelas k ON s.id_kelas=k.id_kelas WHERE (?='' OR s.nis LIKE ? OR s.nama_siswa LIKE ? OR k.nama_kelas LIKE ?) ORDER BY s.nama_siswa"; try(Connection c=Koneksi.getConnection();PreparedStatement ps=c.prepareStatement(sql)){ ps.setString(1,safe);ps.setString(2,like);ps.setString(3,like);ps.setString(4,like); try(ResultSet rs=ps.executeQuery()){ while(rs.next()) data.add(new Siswa(rs.getInt("id_siswa"),rs.getString("nis"),rs.getString("nama_siswa"),rs.getInt("id_kelas"),rs.getString("nama_kelas"),rs.getString("jenis_kelamin"),rs.getString("alamat"))); }} return data; }
    public void insert(Siswa s) throws SQLException { String sql="INSERT INTO siswa(nis,nama_siswa,id_kelas,jenis_kelamin,alamat) VALUES(?,?,?,?,?)"; try(Connection c=Koneksi.getConnection();PreparedStatement ps=c.prepareStatement(sql)){ fill(ps,s); ps.executeUpdate(); } AuditLogger.log(SessionManager.getInstance().getCurrentUserId(),"INSERT SISWA",s.getNamaSiswa()); }
    public void update(Siswa s) throws SQLException { String sql="UPDATE siswa SET nis=?,nama_siswa=?,id_kelas=?,jenis_kelamin=?,alamat=? WHERE id_siswa=?"; try(Connection c=Koneksi.getConnection();PreparedStatement ps=c.prepareStatement(sql)){ fill(ps,s); ps.setInt(6,s.getIdSiswa()); ps.executeUpdate(); } AuditLogger.log(SessionManager.getInstance().getCurrentUserId(),"UPDATE SISWA",s.getNamaSiswa()); }
    public void delete(int id) throws SQLException { try(Connection c=Koneksi.getConnection();PreparedStatement ps=c.prepareStatement("DELETE FROM siswa WHERE id_siswa=?")){ ps.setInt(1,id); ps.executeUpdate(); } AuditLogger.log(SessionManager.getInstance().getCurrentUserId(),"DELETE SISWA","ID "+id); }
    private void fill(PreparedStatement ps,Siswa s)throws SQLException{ ps.setString(1,s.getNis());ps.setString(2,s.getNamaSiswa()); if(s.getIdKelas()<=0) ps.setNull(3,Types.INTEGER); else ps.setInt(3,s.getIdKelas()); ps.setString(4,s.getJenisKelamin());ps.setString(5,s.getAlamat()); }
    public List<Option> getKelasOptions() throws SQLException { return new KelasController().getOptions(); }
    public List<Option> kelasOptions() throws SQLException { return getKelasOptions(); }
    public List<Option> getSiswaOptions() throws SQLException { List<Option> ops=new ArrayList<>(); for(Siswa s:getAll()) ops.add(new Option(s.getIdSiswa(), s.getNis()+" - "+s.getNamaSiswa())); return ops; }
    public int count() throws SQLException { try(Connection c=Koneksi.getConnection();PreparedStatement ps=c.prepareStatement("SELECT COUNT(*) FROM siswa");ResultSet rs=ps.executeQuery()){ rs.next(); return rs.getInt(1);} }
}
