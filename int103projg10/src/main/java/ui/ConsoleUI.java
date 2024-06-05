package ui;

import models.*;
import services.*;
import storage.InMemoryStorage;

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
            if (loggedInUser == null) {
                showInitialMenu();
            } else {
                showUserMenu();
            }
        }
    }

    private void showInitialMenu() {
        System.out.println("Library Management System");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("3. Exit");
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
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private void showUserMenu() {
        System.out.println("Library Management System");
        System.out.println("1. View All Books");
        System.out.println("2. Borrow Book");
        System.out.println("3. Return Book");
        System.out.println("4. Check Loan History");
        if (loggedInUser instanceof Admin) {
            System.out.println("5. Add Book");
            System.out.println("6. Remove User");
            System.out.println("7. Remove Book");
            System.out.println("8. Logout");
        } else {
            System.out.println("5. Logout");
        }
        System.out.print("Choose an option: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        if (loggedInUser instanceof Admin) {
            switch (choice) {
                case 1:
                    viewAllBooks();
                    break;
                case 2:
                    borrowBook();
                    break;
                case 3:
                    returnBook();
                    break;
                case 4:
                    checkLoanHistory();
                    break;
                case 5:
                    addBook();
                    break;
                case 6:
                    removeUser();
                    break;
                case 7:
                    removeBook();
                    break;
                case 8:
                    logout();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } else {
            switch (choice) {
                case 1:
                    viewAllBooks();
                    break;
                case 2:
                    borrowBook();
                    break;
                case 3:
                    returnBook();
                    break;
                case 4:
                    checkLoanHistory();
                    break;
                case 5:
                    logout();
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
        if (loggedInUser instanceof Admin) {
            System.out.println("Logged in as Admin.");
        } else if (loggedInUser != null) {
            System.out.println("Logged in as User.");
        }
    }

    private void logout() {
        loggedInUser = null;
        System.out.println("Logged out successfully.");
    }

    private void viewAllBooks() {
        System.out.println("All Books:");
        for (Book book : bookService.getAllBooks()) {
            System.out.println(book.getTitle() + " by " + book.getAuthor() + " (" + book.getCategory() + ")");
        }
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

    private void checkLoanHistory() {
        if (loggedInUser instanceof Admin) {
            for (Loan loan : loanService.getAllLoans()) {
                System.out.println(loan.getUser().getUsername() + " borrowed " + loan.getBook().getTitle() + " on " + loan.getBorrowDate() + " and returned on " + loan.getReturnDate());
            }
        } else if (loggedInUser != null) {
            for (Loan loan : loanService.getLoansByUser(loggedInUser)) {
                System.out.println("You borrowed " + loan.getBook().getTitle() + " on " + loan.getBorrowDate() + " and returned on " + loan.getReturnDate());
            }
        } else {
            System.out.println("You must be logged in to check loan history.");
        }
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

    private void removeUser() {
        System.out.print("Enter username to remove: ");
        String username = scanner.nextLine();
        ((Admin) loggedInUser).removeUser(username, new InMemoryStorage());
    }

    private void removeBook() {
        System.out.print("Enter book title to remove: ");
        String title = scanner.nextLine();
        ((Admin) loggedInUser).removeBook(title, new InMemoryStorage());
    }
}