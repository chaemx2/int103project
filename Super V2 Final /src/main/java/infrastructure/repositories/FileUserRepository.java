package infrastructure.repositories;

import domain.User;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class FileUserRepository implements UserRepository {
    private final String filename = "users.txt";
    private Map<String, User> users = new HashMap<>();

    public FileUserRepository() {
        loadFromFile();
    }

    private void loadFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String username = parts[0];
                    String password = parts[1];
                    boolean isAdmin = Boolean.parseBoolean(parts[2]);
                    users.put(username, new User(username, password, isAdmin));
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading users from file: " + e.getMessage());
        }
    }

    private void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (User user : users.values()) {
                writer.write(user.getUsername() + "," + user.getPassword() + "," + user.isAdmin());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving users to file: " + e.getMessage());
        }
    }

    @Override
    public void save(User user) {
        users.put(user.getUsername(), user);
        saveToFile();
    }

    @Override
    public User findByUsername(String username) {
        return users.get(username);
    }

    @Override
    public boolean exists(String username) {
        return users.containsKey(username);
    }

    @Override
    public void delete(String username) {
        users.remove(username);
        saveToFile();
    }
}