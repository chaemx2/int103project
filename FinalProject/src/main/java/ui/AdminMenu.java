package ui;

import application.BookService;
import application.BorrowService;
import application.UserService;
import domain.User;

import java.util.Scanner;

public class AdminMenu {
    public static void display(Scanner scanner, UserService userService, BookService bookService, BorrowService borrowService) {
        while (true) {
            System.out.println("Admin Home");
            System.out.println("1. Manage Users");
            System.out.println("2. Manage Books");
            System.out.println("3. View and Borrow Books");
            System.out.println("4. Return Books");
            System.out.println("5. Check Loan History");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    manageUsers(scanner, userService);
                    break;
                case 2:
                    manageBooks(scanner, bookService);
                    break;
                case 3:
                    UserMenu.viewAndBorrowBooks(scanner, bookService, borrowService, new User("admin", "admin", true));
                    break;
                case 4:
                    UserMenu.returnBooks(scanner, bookService, borrowService, new User("admin", "admin", true));
                    break;
                case 5:
                    UserMenu.checkLoanHistory(scanner, borrowService, new User("admin", "admin", true));
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    private static void manageUsers(Scanner scanner, UserService userService) {
        while (true) {
            System.out.println("Manage Users");
            System.out.println("1. Register User");
            System.out.println("2. Delete User");
            System.out.println("3. Back");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.print("Username: ");
                    String username = scanner.nextLine();
                    System.out.print("Password: ");
                    String password = scanner.nextLine();
                    userService.registerUser(username, password, false);
                    System.out.println("User registered");
                    break;
                case 2:
                    System.out.print("Username: ");
                    username = scanner.nextLine();
                    userService.deleteUser(username);
                    System.out.println("User deleted");
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    private static void manageBooks(Scanner scanner, BookService bookService) {
        while (true) {
            System.out.println("Manage Books");
            System.out.println("1. Add Book");
            System.out.println("2. Delete Book");
            System.out.println("3. Update Book");
            System.out.println("4. Back");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.print("Book ID: ");
                    String id = scanner.nextLine();
                    System.out.print("Book Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Category: ");
                    String category = scanner.nextLine();
                    System.out.print("Amount: ");
                    int amount = scanner.nextInt();
                    scanner.nextLine(); // consume newline
                    bookService.addBook(id, name, category, amount);
                    System.out.println("Book added");
                    break;
                case 2:
                    System.out.print("Book ID: ");
                    String bookId = scanner.nextLine();
                    bookService.deleteBook(bookId);
                    System.out.println("Book deleted");
                    break;
                case 3:
                    System.out.print("Book ID: ");
                    id = scanner.nextLine();
                    System.out.print("Book Name: ");
                    name = scanner.nextLine();
                    System.out.print("Category: ");
                    category = scanner.nextLine();
                    System.out.print("Amount: ");
                    amount = scanner.nextInt();
                    scanner.nextLine(); // consume newline
                    bookService.updateBook(id, name, category, amount);
                    System.out.println("Book updated");
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }
}