// File: util/Koneksi.java
package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/** Helper koneksi JDBC ke database kas_sekolah. */
public final class Koneksi {
    private static final String URL = "jdbc:mysql://localhost:3306/kas_sekolah?useSSL=false&serverTimezone=Asia/Jakarta&allowPublicKeyRetrieval=true";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private Koneksi() {
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
