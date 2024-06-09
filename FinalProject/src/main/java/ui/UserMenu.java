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
            System.out.println("Home");
            System.out.println("1. View and Borrow Books");
            System.out.println("2. Return Books");
            System.out.println("3. Check Loan History");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

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
        List<Category> categories = bookService.getCategories();
        for (int i = 0; i < categories.size(); i++) {
            System.out.println((i + 1) + ". " + categories.get(i).getName());
        }
        System.out.println((categories.size() + 1) + ". Back");
        System.out.print("Choose a category: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice > 0 && choice <= categories.size()) {
            Category category = categories.get(choice - 1);
            List<Book> books = bookService.getBooksByCategory(category.getId());
            for (int i = 0; i < books.size(); i++) {
                Book book = books.get(i);
                System.out.println((i + 1) + ". ID: " + book.getId() + ", Name: " + book.getName() + ", Amount: " + book.getAmount());
            }
            System.out.println((books.size() + 1) + ". Back");
            System.out.print("Choose a book to borrow: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            if (choice > 0 && choice <= books.size()) {
                Book book = books.get(choice - 1);
                try {
                    bookService.borrowBook(book.getId());
                    borrowService.borrowBook(user.getUsername(), book.getId());
                    System.out.println("You have borrowed the book");
                } catch (RuntimeException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    public static void returnBooks(Scanner scanner, BookService bookService, BorrowService borrowService, User user) {
        List<BorrowRecord> records = borrowService.getBorrowRecordsByUser(user.getUsername());
        for (int i = 0; i < records.size(); i++) {
            BorrowRecord record = records.get(i);
            System.out.println((i + 1) + ". ID: " + record.getBookId() + ", Borrow Date: " + record.getBorrowDate() + ", Due Date: " + record.getDueDate());
        }
        System.out.println((records.size() + 1) + ". Back");
        System.out.print("Choose a book to return: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice > 0 && choice <= records.size()) {
            BorrowRecord record = records.get(choice - 1);
            try {
                bookService.returnBook(record.getBookId());
                borrowService.returnBook(user.getUsername(), record.getBookId());
                System.out.println("You have returned the book on time");
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void checkLoanHistory(Scanner scanner, BorrowService borrowService, User user) {
        List<BorrowRecord> records = borrowService.getBorrowRecordsByUser(user.getUsername());
        for (BorrowRecord record : records) {
            System.out.println("ID: " + record.getBookId() + ", Borrow Date: " + record.getBorrowDate() + ", Due Date: " + record.getDueDate());
        }
        System.out.println("Press any key to go back...");
        scanner.nextLine();
    }
}