package controller;


import java.sql.*;import java.util.*;import koneksi.Koneksi;import model.User;

public class UserController{private final Connection conn=Koneksi.getConnection();
 public User login(String username,String password)throws Exception{try(PreparedStatement ps=conn.prepareStatement("SELECT * FROM users WHERE username=? AND password=?")){ps.setString(1,username);ps.setString(2,password);try(ResultSet rs=ps.executeQuery()){return rs.next()?map(rs):null;}}}
 public List<User> getAll(String keyword)throws Exception{List<User> list=new ArrayList<>();String sql="SELECT * FROM users WHERE nama LIKE ? OR username LIKE ? OR role LIKE ? ORDER BY id_user DESC";try(PreparedStatement ps=conn.prepareStatement(sql)){String k="%"+keyword+"%";ps.setString(1,k);ps.setString(2,k);ps.setString(3,k);try(ResultSet rs=ps.executeQuery()){while(rs.next())list.add(map(rs));}}return list;}
 public void insert(User u)throws Exception{try(PreparedStatement ps=conn.prepareStatement("INSERT INTO users (nama,username,password,role) VALUES (?,?,?,?)")){fill(ps,u);ps.executeUpdate();}}
 public void update(User u)throws Exception{try(PreparedStatement ps=conn.prepareStatement("UPDATE users SET nama=?, username=?, password=?, role=? WHERE id_user=?")){fill(ps,u);ps.setInt(5,u.getIdUser());ps.executeUpdate();}}
 public void delete(int id)throws Exception{try(PreparedStatement ps=conn.prepareStatement("DELETE FROM users WHERE id_user=?")){ps.setInt(1,id);ps.executeUpdate();}}
 private void fill(PreparedStatement ps,User u)throws Exception{ps.setString(1,u.getNama());ps.setString(2,u.getUsername());ps.setString(3,u.getPassword());ps.setString(4,u.getRole());}
 private User map(ResultSet rs)throws Exception{return new User(rs.getInt("id_user"),rs.getString("nama"),rs.getString("username"),rs.getString("password"),rs.getString("role"));}}

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import koneksi.Koneksi;
import model.User;

public class UserController {
  private final Connection conn = Koneksi.getConnection();

  public User login(String username, String password) throws Exception {
    String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setString(1, username);
      ps.setString(2, password);
      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next())
          return map(rs);
      }
    }
    return null;
  }

  public List<User> getAll(String keyword) throws Exception {
    List<User> list = new ArrayList<>();
    String sql = "SELECT * FROM users WHERE nama LIKE ? OR username LIKE ? " +
                 "OR role LIKE ? ORDER BY id_user DESC";
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
      String key = "%" + keyword + "%";
      ps.setString(1, key);
      ps.setString(2, key);
      ps.setString(3, key);
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next())
          list.add(map(rs));
      }
    }
    return list;
  }

  public void insert(User user) throws Exception {
    String sql = "INSERT INTO users (nama, username, password, role) VALUES " +
                 "(?, ?, ?, ?)";
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setString(1, user.getNama());
      ps.setString(2, user.getUsername());
      ps.setString(3, user.getPassword());
      ps.setString(4, user.getRole());
      ps.executeUpdate();
    }
  }

  public void update(User user) throws Exception {
    String sql = "UPDATE users SET nama = ?, username = ?, password = ?, " +
                 "role = ? WHERE id_user = ?";
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setString(1, user.getNama());
      ps.setString(2, user.getUsername());
      ps.setString(3, user.getPassword());
      ps.setString(4, user.getRole());
      ps.setInt(5, user.getIdUser());
      ps.executeUpdate();
    }
  }

  public void delete(int id)throws Exception {
    try (PreparedStatement ps =
             conn.prepareStatement("DELETE FROM users WHERE id_user = ?")) {
      ps.setInt(1, id);
      ps.executeUpdate();
    }
  }

  private User map(ResultSet rs) throws Exception {
    return new User(rs.getInt("id_user"), rs.getString("nama"),
                    rs.getString("username"), rs.getString("password"),
                    rs.getString("role"));
  }
}

