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
        try (Connection connection = Koneksi.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            statement.setString(2, password);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new User(resultSet.getInt("id_user"), resultSet.getString("nama"), resultSet.getString("username"), resultSet.getString("password"), resultSet.getString("role"));
                }
            }
        }
        return null;
    }
}
