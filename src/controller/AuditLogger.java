// File: controller/AuditLogger.java
package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import util.Koneksi;

public final class AuditLogger {
    private AuditLogger() {}
    public static void log(int userId, String aksi, String detail) {
        String sql = "INSERT INTO audit_log (id_user, aksi, detail, waktu) VALUES (?, ?, ?, NOW())";
        try (Connection c = Koneksi.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            if (userId <= 0) ps.setNull(1, java.sql.Types.INTEGER); else ps.setInt(1, userId);
            ps.setString(2, aksi); ps.setString(3, detail); ps.executeUpdate();
        } catch (SQLException ignored) { }
    }
}
