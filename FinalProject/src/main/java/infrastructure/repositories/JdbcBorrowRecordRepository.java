package infrastructure.repositories;

import config.Config;
import domain.BorrowRecord;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JdbcBorrowRecordRepository implements BorrowRecordRepository {
    private final String url = Config.get("db.url");
    private final String username = Config.get("db.username");
    private final String password = Config.get("db.password");

    public JdbcBorrowRecordRepository() {
        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            if (conn != null) {
                String sql = "CREATE TABLE IF NOT EXISTS borrow_records (" +
                        "userId VARCHAR(50) NOT NULL," +
                        "bookId VARCHAR(50) NOT NULL," +
                        "borrowDate DATE NOT NULL," +
                        "dueDate DATE NOT NULL," +
                        "PRIMARY KEY (userId, bookId))";
                Statement stmt = conn.createStatement();
                stmt.execute(sql);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void save(BorrowRecord record) {
        String sql = "INSERT INTO borrow_records(userId, bookId, borrowDate, dueDate) VALUES(?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, record.getUserId());
            pstmt.setString(2, record.getBookId());
            pstmt.setDate(3, Date.valueOf(record.getBorrowDate()));
            pstmt.setDate(4, Date.valueOf(record.getDueDate()));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public BorrowRecord findByUserIdAndBookId(String userId, String bookId) {
        String sql = "SELECT userId, bookId, borrowDate, dueDate FROM borrow_records WHERE userId = ? AND bookId = ?";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userId);
            pstmt.setString(2, bookId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new BorrowRecord(rs.getString("userId"),
                        rs.getString("bookId"),
                        rs.getDate("borrowDate").toLocalDate(),
                        rs.getDate("dueDate").toLocalDate());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<BorrowRecord> findByUserId(String userId) {
        List<BorrowRecord> records = new ArrayList<>();
        String sql = "SELECT userId, bookId, borrowDate, dueDate FROM borrow_records WHERE userId = ?";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                records.add(new BorrowRecord(rs.getString("userId"),
                        rs.getString("bookId"),
                        rs.getDate("borrowDate").toLocalDate(),
                        rs.getDate("dueDate").toLocalDate()));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return records;
    }

    @Override
    public void delete(BorrowRecord record) {
        String sql = "DELETE FROM borrow_records WHERE userId = ? AND bookId = ?";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, record.getUserId());
            pstmt.setString(2, record.getBookId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}