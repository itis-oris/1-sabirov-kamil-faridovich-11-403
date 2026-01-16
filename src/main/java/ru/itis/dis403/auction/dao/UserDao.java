package ru.itis.dis403.auction.dao;

import ru.itis.dis403.auction.config.DatabaseConfig;
import ru.itis.dis403.auction.model.User;
import java.sql.*;

public class UserDao {
    public User findByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = DatabaseConfig.prepare(conn, sql, username);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return map(rs);
        } catch (SQLException e) { throw new RuntimeException(e); }
        return null;
    }

    public void save(User user) {
        String sql = "INSERT INTO users (username, password, email, role) VALUES (?, ?, ?, ?) RETURNING id";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = DatabaseConfig.prepare(conn, sql, user.getUsername(), user.getPassword(), user.getEmail(), user.getRole());
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) user.setId(rs.getInt(1));
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    private User map(ResultSet rs) throws SQLException {
        User u = new User();
        u.setId(rs.getInt("id"));
        u.setUsername(rs.getString("username"));
        u.setPassword(rs.getString("password"));
        u.setEmail(rs.getString("email"));
        u.setRole(rs.getString("role"));
        return u;
    }
}