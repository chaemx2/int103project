package services;

import models.Book;
import models.Loan;
import models.User;
import storage.InMemoryStorage;

import java.time.LocalDateTime;
import java.util.Optional;

public class LoanService {
    private InMemoryStorage storage;

    public LoanService(InMemoryStorage storage) {
        this.storage = storage;
    }

    public void borrowBook(User user, String bookTitle) {
        Optional<Book> bookOpt = storage.getBooks().stream()
                .filter(book -> book.getTitle().equalsIgnoreCase(bookTitle) && book.isAvailable())
                .findFirst();

        if (bookOpt.isPresent()) {
            Book book = bookOpt.get();
            book.setAvailable(false);
            Loan loan = new Loan(user, book);
            storage.addLoan(loan);
            System.out.println("Book borrowed successfully.");
        } else {
            System.out.println("Book is not available.");
        }
    }

    public void returnBook(User user, String bookTitle) {
        Optional<Loan> loanOpt = storage.getLoans().stream()
                .filter(loan -> loan.getUser().equals(user) && loan.getBook().getTitle().equalsIgnoreCase(bookTitle) && loan.getReturnDate() == null)
                .findFirst();

        if (loanOpt.isPresent()) {
            Loan loan = loanOpt.get();
            loan.setReturnDate(LocalDateTime.now());
            loan.getBook().setAvailable(true);
            System.out.println("Book returned successfully.");
        } else {
            System.out.println("No matching loan found.");
        }
    }
}