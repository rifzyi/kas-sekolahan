// File: controller/TabunganController.java
package controller;

import java.sql.*;import java.util.*;import model.Tabungan;import util.Koneksi;import util.SessionManager;

public class TabunganController {
    public void setor(Tabungan t)throws SQLException{ simpan(t,"Setor"); }
    public void tarik(Tabungan t)throws SQLException{ if(getSaldo(t.getIdSiswa())<t.getNominal()) throw new SQLException("Saldo tabungan tidak mencukupi."); simpan(t,"Tarik"); }
    private void simpan(Tabungan t,String jenis)throws SQLException{ String sql="INSERT INTO tabungan_siswa(id_siswa,tanggal,jenis,nominal,keterangan) VALUES(?,?,?,?,?)"; try(Connection c=Koneksi.getConnection();PreparedStatement ps=c.prepareStatement(sql)){ ps.setInt(1,t.getIdSiswa());ps.setDate(2,t.getTanggal());ps.setString(3,jenis);ps.setDouble(4,t.getNominal());ps.setString(5,t.getKeterangan());ps.executeUpdate(); } AuditLogger.log(SessionManager.getInstance().getCurrentUserId(),jenis.toUpperCase()+" TABUNGAN",t.getKeterangan()+" nominal "+t.getNominal()); }
    public double getSaldo(int idSiswa)throws SQLException{ String sql="SELECT COALESCE(SUM(CASE WHEN jenis='Setor' THEN nominal ELSE -nominal END),0) FROM tabungan_siswa WHERE id_siswa=?"; try(Connection c=Koneksi.getConnection();PreparedStatement ps=c.prepareStatement(sql)){ ps.setInt(1,idSiswa); try(ResultSet rs=ps.executeQuery()){ rs.next(); return rs.getDouble(1); }} }
    public List<Tabungan> getMutasiBySiswa(int idSiswa)throws SQLException{ List<Tabungan> list=new ArrayList<>(); String sql="SELECT t.id_tabungan,t.id_siswa,t.tanggal,t.jenis,t.nominal,t.keterangan,COALESCE(s.nama_siswa,'-') nama_siswa FROM tabungan_siswa t LEFT JOIN siswa s ON t.id_siswa=s.id_siswa WHERE t.id_siswa=? ORDER BY t.tanggal DESC,t.id_tabungan DESC"; try(Connection c=Koneksi.getConnection();PreparedStatement ps=c.prepareStatement(sql)){ ps.setInt(1,idSiswa); try(ResultSet rs=ps.executeQuery()){ while(rs.next()) list.add(new Tabungan(rs.getInt(1),rs.getInt(2),rs.getDate(3),rs.getString(4),rs.getDouble(5),rs.getString(6),rs.getString(7))); }} return list; }
}
