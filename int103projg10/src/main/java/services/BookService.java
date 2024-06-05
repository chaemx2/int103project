package services;

import models.Book;
import storage.InMemoryStorage;

import java.util.List;
import java.util.Optional;

public class BookService {
    private InMemoryStorage storage;

    public BookService(InMemoryStorage storage) {
        this.storage = storage;
    }

    public void addBook(Book book) {
        storage.addBook(book);
    }

    public Optional<Book> findBook(String title) {
        return storage.getBooks().stream()
                .filter(book -> book.getTitle().equalsIgnoreCase(title))
                .findFirst();
    }

    public List<Book> getAllBooks() {
        return storage.getBooks();
    }
}