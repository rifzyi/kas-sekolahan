package koneksi;

import java.sql.Connection;
import java.sql.SQLException;

public final class Koneksi {
    private Koneksi() {
    }

    public static Connection getConnection() {
        try {
            return util.Koneksi.getConnection();
        } catch (SQLException ex) {
            throw new RuntimeException("Koneksi database gagal: " + ex.getMessage(), ex);
        }
    }
}
