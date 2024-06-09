package ui;

import application.BookService;
import application.BorrowService;
import domain.Book;
import domain.BorrowRecord;
import domain.Category;
import domain.User;

import java.util.List;
import java.util.Scanner;

public class UserMenu {
    public static void display(Scanner scanner, BookService bookService, BorrowService borrowService, User user) {
        while (true) {
            System.out.println("User Home");
            System.out.println("1. View and Borrow Books");
            System.out.println("2. Return Books");
            System.out.println("3. Check Loan History");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    viewAndBorrowBooks(scanner, bookService, borrowService, user);
                    break;
                case 2:
                    returnBooks(scanner, bookService, borrowService, user);
                    break;
                case 3:
                    checkLoanHistory(scanner, borrowService, user);
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    public static void viewAndBorrowBooks(Scanner scanner, BookService bookService, BorrowService borrowService, User user) {
        System.out.println("Categories:");
        List<Category> categories = bookService.getCategories();
        for (int i = 0; i < categories.size(); i++) {
            System.out.println((i + 1) + ". " + categories.get(i).getName());
        }
        System.out.print("Choose a category: ");
        int categoryChoice = scanner.nextInt();
        scanner.nextLine(); // consume newline

        if (categoryChoice < 1 || categoryChoice > categories.size()) {
            System.out.println("Invalid choice");
            return;
        }

        Category selectedCategory = categories.get(categoryChoice - 1);
        List<Book> books = bookService.getBooksByCategory(selectedCategory.getId());
        for (Book book : books) {
            System.out.println("ID: " + book.getId() + ", Name: " + book.getName() + ", Available: " + book.getAmount());
        }

        System.out.print("Enter the book ID to borrow: ");
        String bookId = scanner.nextLine();
        try {
            bookService.borrowBook(bookId, user.getUsername());
            System.out.println("You have borrowed the book");
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void returnBooks(Scanner scanner, BookService bookService, BorrowService borrowService, User user) {
        List<BorrowRecord> borrowRecords = borrowService.getBorrowRecordsByUser(user.getUsername());
        for (BorrowRecord record : borrowRecords) {
            System.out.println("ID: " + record.getBookId() + ", Borrowed Date: " + record.getBorrowDate() + ", Due Date: " + record.getDueDate());
        }

        System.out.print("Enter the book ID to return: ");
        String bookId = scanner.nextLine();
        try {
            bookService.returnBook(bookId, user.getUsername());
            System.out.println("You have returned the book on time");
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void checkLoanHistory(Scanner scanner, BorrowService borrowService, User user) {
        List<BorrowRecord> borrowRecords = borrowService.getBorrowRecordsByUser(user.getUsername());
        for (BorrowRecord record : borrowRecords) {
            System.out.println("ID: " + record.getBookId() + ", Borrowed Date: " + record.getBorrowDate() + ", Due Date: " + record.getDueDate());
        }
    }
}