package application;

import domain.Book;
import domain.BorrowRecord;
import domain.Category;
import infrastructure.repositories.BookRepository;
import infrastructure.repositories.BorrowRecordRepository;
import infrastructure.repositories.CategoryRepository;

import java.time.LocalDate;
import java.util.List;

public class BookService {
    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final BorrowRecordRepository borrowRecordRepository;

    public BookService(BookRepository bookRepository, CategoryRepository categoryRepository, BorrowRecordRepository borrowRecordRepository) {
        this.bookRepository = bookRepository;
        this.categoryRepository = categoryRepository;
        this.borrowRecordRepository = borrowRecordRepository;
    }

    public void addBook(String id, String name, String category, int amount) {
        Book book = new Book(id, name, category, amount);
        bookRepository.save(book);
    }

    public void deleteBook(String id) {
        bookRepository.delete(id);
    }

    public void updateBook(String id, String name, String category, int amount) {
        Book book = new Book(id, name, category, amount);
        bookRepository.save(book);
    }

    public List<Book> getBooksByCategory(String categoryId) {
        return bookRepository.findByCategory(categoryId);
    }

    public Book findById(String id) {
        return bookRepository.findById(id);
    }

    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    public void borrowBook(String bookId, String userId) {
        Book book = bookRepository.findById(bookId);
        if (book != null && book.getAmount() > 0) {
            book.setAmount(book.getAmount() - 1);
            bookRepository.save(book);
            borrowRecordRepository.save(new BorrowRecord(userId, bookId, LocalDate.now(), LocalDate.now().plusWeeks(2)));
        } else {
            throw new RuntimeException("Book is not available.");
        }
    }

    public void returnBook(String bookId, String userId) {
        Book book = bookRepository.findById(bookId);
        if (book != null) {
            book.setAmount(book.getAmount() + 1);
            bookRepository.save(book);
            BorrowRecord record = borrowRecordRepository.findByUserIdAndBookId(userId, bookId);
            if (record != null) {
                borrowRecordRepository.delete(record);
            } else {
                throw new RuntimeException("No borrow record found for the user and book.");
            }
        } else {
            throw new RuntimeException("Invalid book ID.");
        }
    }
}