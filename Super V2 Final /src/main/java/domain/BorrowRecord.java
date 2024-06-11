package domain;

import java.time.LocalDate;

public class BorrowRecord {
    private String userId;
    private String bookId;
    private LocalDate borrowDate;
    private LocalDate dueDate;

    public BorrowRecord(String userId, String bookId, LocalDate borrowDate, LocalDate dueDate) {
        this.userId = userId;
        this.bookId = bookId;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
    }

    public String getUserId() {
        return userId;
    }

    public String getBookId() {
        return bookId;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }
}