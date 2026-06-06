// File: controller/PengaturanController.java
package controller;

import java.sql.*;import model.Pengaturan;import util.Koneksi;import util.SessionManager;

public class PengaturanController {
    public Pengaturan get() throws SQLException { String sql="SELECT id_pengaturan,nama_sekolah,alamat,telepon,kepala_sekolah,bendahara FROM pengaturan ORDER BY id_pengaturan LIMIT 1"; try(Connection c=Koneksi.getConnection();PreparedStatement ps=c.prepareStatement(sql);ResultSet rs=ps.executeQuery()){ if(rs.next()) return new Pengaturan(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6)); } return new Pengaturan(0,"","","","",""); }
    public void save(Pengaturan p)throws SQLException{ if(p.getIdPengaturan()>0) update(p); else insert(p); AuditLogger.log(SessionManager.getInstance().getCurrentUserId(),"SIMPAN PENGATURAN",p.getNamaSekolah()); }
    private void insert(Pengaturan p)throws SQLException{ try(Connection c=Koneksi.getConnection();PreparedStatement ps=c.prepareStatement("INSERT INTO pengaturan(nama_sekolah,alamat,telepon,kepala_sekolah,bendahara) VALUES(?,?,?,?,?)")){ fill(ps,p); ps.executeUpdate(); }}
    private void update(Pengaturan p)throws SQLException{ try(Connection c=Koneksi.getConnection();PreparedStatement ps=c.prepareStatement("UPDATE pengaturan SET nama_sekolah=?,alamat=?,telepon=?,kepala_sekolah=?,bendahara=? WHERE id_pengaturan=?")){ fill(ps,p); ps.setInt(6,p.getIdPengaturan()); ps.executeUpdate(); }}
    private void fill(PreparedStatement ps,Pengaturan p)throws SQLException{ ps.setString(1,p.getNamaSekolah());ps.setString(2,p.getAlamat());ps.setString(3,p.getTelepon());ps.setString(4,p.getKepalaSekolah());ps.setString(5,p.getBendahara()); }
}
