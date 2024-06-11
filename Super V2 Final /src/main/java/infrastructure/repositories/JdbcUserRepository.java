package infrastructure.repositories;

import config.Config;
import domain.User;

import java.sql.*;

public class JdbcUserRepository implements UserRepository {
    private final String url = Config.get("db.url");
    private final String username = Config.get("db.username");
    private final String password = Config.get("db.password");

    static {
        try {
            Class.forName(Config.get("db.driver"));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL JDBC driver not found.", e);
        }
    }

    public JdbcUserRepository() {
        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            if (conn != null) {
                String sql = "CREATE TABLE IF NOT EXISTS users (" +
                        "username VARCHAR(50) PRIMARY KEY," +
                        "password VARCHAR(50) NOT NULL," +
                        "isAdmin BOOLEAN NOT NULL)";
                Statement stmt = conn.createStatement();
                stmt.execute(sql);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void save(User user) {
        String sql = "INSERT INTO users(username, password, isAdmin) VALUES(?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            pstmt.setBoolean(3, user.isAdmin());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public User findByUsername(String username) {
        String sql = "SELECT username, password, isAdmin FROM users WHERE username = ?";
        try (Connection conn = DriverManager.getConnection(url, this.username, this.password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new User(rs.getString("username"),
                        rs.getString("password"),
                        rs.getBoolean("isAdmin"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public boolean exists(String username) {
        String sql = "SELECT 1 FROM users WHERE username = ?";
        try (Connection conn = DriverManager.getConnection(url, this.username, this.password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public void delete(String username) {
        String sql = "DELETE FROM users WHERE username = ?";
        try (Connection conn = DriverManager.getConnection(url, this.username, this.password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}