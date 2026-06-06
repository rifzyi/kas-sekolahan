package koneksi;

import java.sql.Connection;
import java.sql.DriverManager;

public class Koneksi {

    private static Connection conn;

    public static Connection getConnection() {

        try {

            if (conn == null) {

                String url = "jdbc:mysql://localhost:3306/kas_sekolah";
                String user = "root";
                String pass = "";

                conn = DriverManager.getConnection(url, user, pass);

                System.out.println("Koneksi Berhasil");

            }

        } catch (Exception e) {

            System.out.println("Koneksi Gagal");
            e.printStackTrace();

        }

        return conn;
    }
}