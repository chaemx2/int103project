package infrastructure.repositories;

import domain.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class InMemoryBookRepository implements BookRepository {
    private List<Book> books = new ArrayList<>();

    @Override
    public void save(Book book) {
        books.add(book);
    }

    @Override
    public Book findById(String id) {
        return books.stream().filter(book -> book.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public List<Book> findByCategory(String categoryId) {
        return books.stream().filter(book -> book.getCategory().equals(categoryId)).collect(Collectors.toList());
    }
}
