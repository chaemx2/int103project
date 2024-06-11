package infrastructure.repositories;

import domain.Book;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FileBookRepository implements BookRepository {
    private final String filename = "books.txt";
    private Map<String, Book> books = new HashMap<>();

    public FileBookRepository() {
        loadFromFile();
    }

    private void loadFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String id = parts[0];
                    String name = parts[1];
                    String category = parts[2];
                    int amount = Integer.parseInt(parts[3]);
                    books.put(id, new Book(id, name, category, amount));
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading books from file: " + e.getMessage());
        }
    }

    private void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Book book : books.values()) {
                writer.write(book.getId() + "," + book.getName() + "," + book.getCategory() + "," + book.getAmount());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving books to file: " + e.getMessage());
        }
    }

    @Override
    public void save(Book book) {
        books.put(book.getId(), book);
        saveToFile();
    }

    @Override
    public Book findById(String id) {
        return books.get(id);
    }

    @Override
    public List<Book> findByCategory(String categoryId) {
        return books.values().stream()
                .filter(book -> book.getCategory().equals(categoryId))
                .collect(Collectors.toList());
    }

    @Override
    public void delete(String id) {
        books.remove(id);
        saveToFile();
    }
}