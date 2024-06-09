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
            System.out.println("2. View and Borrow Books");
            System.out.println("3. Return Books");
            System.out.println("4. Check Loan History");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    manageUsers(scanner, userService);
                    break;
                case 2:
                    UserMenu.viewAndBorrowBooks(scanner, bookService, borrowService, new User("admin", "admin", true));
                    break;
                case 3:
                    UserMenu.returnBooks(scanner, bookService, borrowService, new User("admin", "admin", true));
                    break;
                case 4:
                    UserMenu.checkLoanHistory(scanner, borrowService, new User("admin", "admin", true));
                    break;
                case 5:
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
            scanner.nextLine();

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
}