// File: controller/AuthController.java
package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.User;
import util.Koneksi;

public class AuthController {
    public User login(String username, String password) throws SQLException {
        String sql = "SELECT id_user, nama, username, password, role FROM users WHERE username = ? AND password = ?";
        try (Connection c = Koneksi.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, username); ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return new User(rs.getInt("id_user"), rs.getString("nama"), rs.getString("username"), rs.getString("password"), rs.getString("role"));
            }
        }
        return null;
    }
}
