package infrastructure.repositories;

import config.Config;
import domain.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcCategoryRepository implements CategoryRepository {
    private final String url = Config.get("db.url");
    private final String username = Config.get("db.username");
    private final String password = Config.get("db.password");

    public JdbcCategoryRepository() {
        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            if (conn != null) {
                String sql = "CREATE TABLE IF NOT EXISTS categories (" +
                        "id VARCHAR(50) PRIMARY KEY," +
                        "name VARCHAR(100) NOT NULL)";
                Statement stmt = conn.createStatement();
                stmt.execute(sql);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void save(Category category) {
        String sql = "REPLACE INTO categories(id, name) VALUES(?, ?)";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, category.getId());
            pstmt.setString(2, category.getName());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Category> findAll() {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT id, name FROM categories";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                categories.add(new Category(rs.getString("id"), rs.getString("name")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return categories;
    }

    @Override
    public Category findById(String id) {
        String sql = "SELECT id, name FROM categories WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Category(rs.getString("id"), rs.getString("name"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public void delete(String id) {
        String sql = "DELETE FROM categories WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}