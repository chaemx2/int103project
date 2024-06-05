package storage;

import models.Book;
import models.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileStorage {
    private File userFile = new File("users.txt");
    private File bookFile = new File("books.txt");

    public void addUser(User user) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(userFile, true))) {
            writer.write(user.getUsername() + "," + user.getPassword());
            writer.newLine();
        }
    }

    public List<User> getUsers() throws IOException {
        List<User> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(userFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                users.add(new User(parts[0], parts[1]));
            }
        }
        return users;
    }

    public void addBook(Book book) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(bookFile, true))) {
            writer.write(book.getTitle() + "," + book.getAuthor() + "," + book.getCategory() + "," + book.isAvailable());
            writer.newLine();
        }
    }

    public List<Book> getBooks() throws IOException {
        List<Book> books = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(bookFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                Book book = new Book(parts[0], parts[1], parts[2]);
                book.setAvailable(Boolean.parseBoolean(parts[3]));
                books.add(book);
            }
        }
        return books;
    }
}