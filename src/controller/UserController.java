package controller;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import koneksi.Koneksi;
import model.User;

public class UserController {
  private final Connection conn = Koneksi.getConnection();

  public User login(String username, String password) throws SQLException {
    try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM users WHERE username=? AND password=?")) {
      ps.setString(1, username); ps.setString(2, password);
      try (ResultSet rs = ps.executeQuery()) { return rs.next() ? map(rs) : null; }
    }
  }
  public List<User> getAll() throws SQLException {
    List<User> data = new ArrayList<>();
    try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM users ORDER BY nama"); ResultSet rs = ps.executeQuery()) { while (rs.next()) data.add(map(rs)); }
    return data;
  }
  public void insert(User u) throws SQLException { try (PreparedStatement ps = conn.prepareStatement("INSERT INTO users(nama,username,password,role) VALUES(?,?,?,?)")) { fill(ps, u); ps.executeUpdate(); } }
  public void update(User u) throws SQLException { try (PreparedStatement ps = conn.prepareStatement("UPDATE users SET nama=?, username=?, password=?, role=? WHERE id_user=?")) { fill(ps, u); ps.setInt(5, u.getIdUser()); ps.executeUpdate(); } }
  public void delete(int id) throws SQLException { try (PreparedStatement ps = conn.prepareStatement("DELETE FROM users WHERE id_user=?")) { ps.setInt(1, id); ps.executeUpdate(); } }
  private void fill(PreparedStatement ps, User u) throws SQLException { ps.setString(1, u.getNama()); ps.setString(2, u.getUsername()); ps.setString(3, u.getPassword()); ps.setString(4, u.getRole()); }
  private User map(ResultSet rs) throws SQLException { return new User(rs.getInt("id_user"), rs.getString("nama"), rs.getString("username"), rs.getString("password"), rs.getString("role")); }
}
