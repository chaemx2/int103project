package storage;

import models.Book;
import models.Loan;
import models.User;
import java.util.ArrayList;
import java.util.List;

public class InMemoryStorage {
    private List<User> users = new ArrayList<>();
    private List<Book> books = new ArrayList<>();
    private List<Loan> loans = new ArrayList<>();

    public void addUser(User user) {
        users.add(user);
    }

    public List<User> getUsers() {
        return users;
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public List<Book> getBooks() {
        return books;
    }

    public void addLoan(Loan loan) {
        loans.add(loan);
    }

    public List<Loan> getLoans() {
        return loans;
    }
}
