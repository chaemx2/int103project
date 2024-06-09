package application;

import domain.Book;
import domain.Category;
import infrastructure.repositories.BookRepository;
import infrastructure.repositories.CategoryRepository;

import java.util.List;

public class BookService {
    private BookRepository bookRepository;
    private CategoryRepository categoryRepository;

    public BookService(BookRepository bookRepository, CategoryRepository categoryRepository) {
        this.bookRepository = bookRepository;
        this.categoryRepository = categoryRepository;
    }

    public void addBook(String id, String name, String category, int amount) {
        Book book = new Book(id, name, category, amount);
        bookRepository.save(book);
    }

    public void addCategory(String id, String name) {
        Category category = new Category(id, name);
        categoryRepository.save(category);
    }

    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    public List<Book> getBooksByCategory(String categoryId) {
        return bookRepository.findByCategory(categoryId);
    }

    public Book borrowBook(String bookId) {
        Book book = bookRepository.findById(bookId);
        if (book == null) {
            throw new RuntimeException("Invalid book");
        }
        if (book.getAmount() == 0) {
            throw new RuntimeException("This book is unavailable at this moment");
        }
        book.setAmount(book.getAmount() - 1);
        bookRepository.save(book);
        return book;
    }

    public void returnBook(String bookId) {
        Book book = bookRepository.findById(bookId);
        if (book == null) {
            throw new RuntimeException("Invalid book");
        }
        book.setAmount(book.getAmount() + 1);
        bookRepository.save(book);
    }
}