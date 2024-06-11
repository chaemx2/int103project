package infrastructure.repositories;

import domain.Category;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FileCategoryRepository implements CategoryRepository {
    private final String filename = "categories.txt";
    private Map<String, Category> categories = new HashMap<>();

    public FileCategoryRepository() {
        loadFromFile();
    }

    private void loadFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String id = parts[0];
                    String name = parts[1];
                    categories.put(id, new Category(id, name));
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading categories from file: " + e.getMessage());
        }
    }

    private void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Category category : categories.values()) {
                writer.write(category.getId() + "," + category.getName());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving categories to file: " + e.getMessage());
        }
    }

    @Override
    public void save(Category category) {
        categories.put(category.getId(), category);
        saveToFile();
    }

    @Override
    public List<Category> findAll() {
        return categories.values().stream().collect(Collectors.toList());
    }

    @Override
    public Category findById(String id) {
        return categories.get(id);
    }

    @Override
    public void delete(String id) {
        categories.remove(id);
        saveToFile();
    }
}