// File: controller/DashboardController.java
package controller;

import java.sql.*;import java.util.*;import util.Koneksi;import util.UIUtils;

public class DashboardController {
    public double getTotalPemasukan()throws SQLException{return sum("SELECT COALESCE(SUM(nominal),0) FROM pemasukan");}
    public double getTotalPengeluaran()throws SQLException{return sum("SELECT COALESCE(SUM(nominal),0) FROM pengeluaran");}
    public double getSaldo()throws SQLException{return getTotalPemasukan()-getTotalPengeluaran();}
    private double sum(String sql)throws SQLException{try(Connection c=Koneksi.getConnection();PreparedStatement ps=c.prepareStatement(sql);ResultSet rs=ps.executeQuery()){rs.next();return rs.getDouble(1);}}
    public List<Object[]> getRecentTransactions()throws SQLException{ List<Object[]> rows=new ArrayList<>(); String sql="SELECT tanggal,'Pemasukan' jenis,COALESCE(k.nama_kategori,'-') kategori,nominal,keterangan FROM pemasukan p LEFT JOIN kategori_pemasukan k ON p.id_kategori_pemasukan=k.id_kategori_pemasukan UNION ALL SELECT tanggal,'Pengeluaran',COALESCE(k.nama_kategori,'-'),nominal,keterangan FROM pengeluaran p LEFT JOIN kategori_pengeluaran k ON p.id_kategori_pengeluaran=k.id_kategori_pengeluaran ORDER BY tanggal DESC LIMIT 5"; try(Connection c=Koneksi.getConnection();PreparedStatement ps=c.prepareStatement(sql);ResultSet rs=ps.executeQuery()){ while(rs.next()) rows.add(new Object[]{rs.getDate(1),rs.getString(2),rs.getString(3),UIUtils.formatRupiah(rs.getDouble(4)),rs.getString(5)}); } return rows; }
}
