package services;

import models.User;
import storage.InMemoryStorage;
import java.util.Optional;

public class UserService {
    private InMemoryStorage storage;

    public UserService(InMemoryStorage storage) {
        this.storage = storage;
    }

    public void registerUser(String username, String password) {
        Optional<User> userOpt = storage.getUsers().stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst();

        if (userOpt.isPresent()) {
            System.out.println("Username already exists.");
        } else {
            User user = new User(username, password);
            storage.addUser(user);
            System.out.println("User registered successfully.");
        }
    }

    public void loginUser(String username, String password) {
        Optional<User> userOpt = storage.getUsers().stream()
                .filter(user -> user.getUsername().equals(username) && user.getPassword().equals(password))
                .findFirst();

        if (userOpt.isPresent()) {
            System.out.println("Login successful!");
        } else {
            System.out.println("Invalid username or password.");
        }
    }
}