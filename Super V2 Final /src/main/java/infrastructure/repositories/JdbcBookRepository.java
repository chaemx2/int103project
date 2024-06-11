package infrastructure.repositories;

import config.Config;
import domain.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcBookRepository implements BookRepository {
    private final String url = Config.get("db.url");
    private final String username = Config.get("db.username");
    private final String password = Config.get("db.password");

    public JdbcBookRepository() {
        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            if (conn != null) {
                String sql = "CREATE TABLE IF NOT EXISTS books (" +
                        "id VARCHAR(50) PRIMARY KEY," +
                        "name VARCHAR(100) NOT NULL," +
                        "category VARCHAR(50) NOT NULL," +
                        "amount INTEGER NOT NULL)";
                Statement stmt = conn.createStatement();
                stmt.execute(sql);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void save(Book book) {
        String sql = "REPLACE INTO books(id, name, category, amount) VALUES(?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, book.getId());
            pstmt.setString(2, book.getName());
            pstmt.setString(3, book.getCategory());
            pstmt.setInt(4, book.getAmount());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Book findById(String id) {
        String sql = "SELECT id, name, category, amount FROM books WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Book(rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("category"),
                        rs.getInt("amount"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Book> findByCategory(String categoryId) {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT id, name, category, amount FROM books WHERE category = ?";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, categoryId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                books.add(new Book(rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("category"),
                        rs.getInt("amount")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return books;
    }

    @Override
    public void delete(String id) {
        String sql = "DELETE FROM books WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}