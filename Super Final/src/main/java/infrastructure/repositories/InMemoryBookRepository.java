package infrastructure.repositories;

import domain.Book;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class InMemoryBookRepository implements BookRepository {
    private Map<String, Book> books = new HashMap<>();

    @Override
    public void save(Book book) {
        books.put(book.getId(), book);
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
    }
}