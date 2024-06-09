package infrastructure.repositories;

import domain.Book;

import java.util.List;

public interface BookRepository {
    void save(Book book);

    Book findById(String id);

    List<Book> findByCategory(String categoryId);
}