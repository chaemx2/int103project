package ui;

import models.Book;
import models.User;
import services.BookService;
import services.LoanService;
import services.UserService;
import java.util.Scanner;

public class ConsoleUI {
    private Scanner scanner = new Scanner(System.in);
    private UserService userService;
    private BookService bookService;
    private LoanService loanService;
    private User loggedInUser;

    public ConsoleUI(UserService userService, BookService bookService, LoanService loanService) {
        this.userService = userService;
        this.bookService = bookService;
        this.loanService = loanService;
    }

    public void start() {
        while (true) {
            System.out.println("Library Management System");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Add Book");
            System.out.println("4. Borrow Book");
            System.out.println("5. Return Book");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    registerUser();
                    break;
                case 2:
                    loginUser();
                    break;
                case 3:
                    if (loggedInUser instanceof Admin) {
                        addBook();
                    } else {
                        System.out.println("Only admin can add books.");
                    }
                    break;
                case 4:
                    borrowBook();
                    break;
                case 5:
                    returnBook();
                    break;
                case 6:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void registerUser() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        userService.registerUser(username, password);
    }

    private void loginUser() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        loggedInUser = userService.loginUser(username, password);
    }

    private void addBook() {
        System.out.print("Enter title: ");
        String title = scanner.nextLine();
        System.out.print("Enter author: ");
        String author = scanner.nextLine();
        System.out.print("Enter category: ");
        String category = scanner.nextLine();
        bookService.addBook(new Book(title, author, category));
    }

    private void borrowBook() {
        if (loggedInUser != null) {
            System.out.print("Enter book title: ");
            String bookTitle = scanner.nextLine();
            loanService.borrowBook(loggedInUser, bookTitle);
        } else {
            System.out.println("You must be logged in to borrow a book.");
        }
    }

    private void returnBook() {
        if (loggedInUser != null) {
            System.out.print("Enter book title: ");
            String bookTitle = scanner.nextLine();
            loanService.returnBook(loggedInUser, bookTitle);
        } else {
            System.out.println("You must be logged in to return a book.");
        }
    }
}