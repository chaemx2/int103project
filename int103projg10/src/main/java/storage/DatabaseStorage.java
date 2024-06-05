package storage;

import models.Book;
import models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseStorage {
    private Connection connection;

    public DatabaseStorage(Connection connection) {
        this.connection = connection;
    }

    public void addUser(User user) throws SQLException {
        String query = "INSERT INTO users (username, password) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.executeUpdate();
        }
    }

    public List<User> getUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String query = "SELECT username, password FROM users";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                users.add(new User(resultSet.getString("username"), resultSet.getString("password")));
            }
        }
        return users;
    }

    public void addBook(Book book) throws SQLException {
        String query = "INSERT INTO books (title, author, category, isAvailable) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthor());
            statement.setString(3, book.getCategory());
            statement.setBoolean(4, book.isAvailable());
            statement.executeUpdate();
        }
    }

    public List<Book> getBooks() throws SQLException {
        List<Book> books = new ArrayList<>();
        String query = "SELECT title, author, category, isAvailable FROM books";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                Book book = new Book(resultSet.getString("title"), resultSet.getString("author"), resultSet.getString("category"));
                book.setAvailable(resultSet.getBoolean("isAvailable"));
                books.add(book);
            }
        }
        return books;
    }
}