package controller;

import java.sql.*;import koneksi.Koneksi;import model.Pengaturan;

public class PengaturanController{private final Connection conn=Koneksi.getConnection();
 public Pengaturan get()throws Exception{try(PreparedStatement ps=conn.prepareStatement("SELECT * FROM pengaturan ORDER BY id_pengaturan LIMIT 1");ResultSet rs=ps.executeQuery()){return rs.next()?map(rs):new Pengaturan(0,"MI Nahdlotut Tholibin","","","","","","");}}
 public void save(Pengaturan p)throws Exception{if(p.getIdPengaturan()==0){try(PreparedStatement ps=conn.prepareStatement("INSERT INTO pengaturan (nama_sekolah,alamat,telepon,email,kepala_sekolah,bendahara,logo_path) VALUES (?,?,?,?,?,?,?)")){fill(ps,p);ps.executeUpdate();}}else{try(PreparedStatement ps=conn.prepareStatement("UPDATE pengaturan SET nama_sekolah=?, alamat=?, telepon=?, email=?, kepala_sekolah=?, bendahara=?, logo_path=? WHERE id_pengaturan=?")){fill(ps,p);ps.setInt(8,p.getIdPengaturan());ps.executeUpdate();}}}
 private void fill(PreparedStatement ps,Pengaturan p)throws Exception{ps.setString(1,p.getNamaSekolah());ps.setString(2,p.getAlamat());ps.setString(3,p.getTelepon());ps.setString(4,p.getEmail());ps.setString(5,p.getKepalaSekolah());ps.setString(6,p.getBendahara());ps.setString(7,p.getLogoPath());}
 private Pengaturan map(ResultSet rs)throws Exception{return new Pengaturan(rs.getInt("id_pengaturan"),rs.getString("nama_sekolah"),rs.getString("alamat"),rs.getString("telepon"),rs.getString("email"),rs.getString("kepala_sekolah"),rs.getString("bendahara"),rs.getString("logo_path"));}}
