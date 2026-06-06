// File: controller/DashboardController.java
package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import util.Koneksi;

public class DashboardController {
    public double[] getSummary() throws SQLException {
        double totalPemasukan = sum("SELECT COALESCE(SUM(nominal), 0) FROM pemasukan");
        double totalPengeluaran = sum("SELECT COALESCE(SUM(nominal), 0) FROM pengeluaran");
        return new double[] {totalPemasukan, totalPengeluaran, totalPemasukan - totalPengeluaran};
    }

    public int countSiswa() throws SQLException {
        try (Connection connection = Koneksi.getConnection(); PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) FROM siswa"); ResultSet resultSet = statement.executeQuery()) {
            resultSet.next();
            return resultSet.getInt(1);
        }
    }

    private double sum(String sql) throws SQLException {
        try (Connection connection = Koneksi.getConnection(); PreparedStatement statement = connection.prepareStatement(sql); ResultSet resultSet = statement.executeQuery()) {
            resultSet.next();
            return resultSet.getDouble(1);
        }
    }
}
