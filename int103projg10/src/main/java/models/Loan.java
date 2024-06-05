package models;

import java.time.LocalDateTime;

public class Loan {
    private User user;
    private Book book;
    private LocalDateTime borrowDate;
    private LocalDateTime returnDate;

    public Loan(User user, Book book) {
        this.user = user;
        this.book = book;
        this.borrowDate = LocalDateTime.now();
        this.returnDate = null;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public LocalDateTime getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDateTime borrowDate) {
        this.borrowDate = borrowDate;
    }

    public LocalDateTime getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDateTime returnDate) {
        this.returnDate = returnDate;
    }
}
