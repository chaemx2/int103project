package models;

import storage.InMemoryStorage;
import java.util.List;

public class Admin extends User {

    public Admin(String username, String password) {
        super(username, password);
    }

    public void addUser(User user, InMemoryStorage storage) {
        storage.addUser(user);
        System.out.println("User added successfully.");
    }

    public void addBook(Book book, InMemoryStorage storage) {
        storage.addBook(book);
        System.out.println("Book added successfully.");
    }

    public void removeUser(String username, InMemoryStorage storage) {
        storage.getUsers().removeIf(user -> user.getUsername().equals(username));
        System.out.println("User removed successfully.");
    }

    public void removeBook(String title, InMemoryStorage storage) {
        storage.getBooks().removeIf(book -> book.getTitle().equals(title));
        System.out.println("Book removed successfully.");
    }

    public List<User> getAllUsers(InMemoryStorage storage) {
        return storage.getUsers();
    }

    public List<Book> getAllBooks(InMemoryStorage storage) {
        return storage.getBooks();
    }
}