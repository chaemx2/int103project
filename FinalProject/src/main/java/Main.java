import application.UserService;
import application.BookService;
import application.BorrowService;
import domain.User;
import domain.User;
import infrastructure.repositories.JdbcUserRepository;
import infrastructure.repositories.JdbcBookRepository;
import infrastructure.repositories.JdbcBorrowRecordRepository;
import infrastructure.repositories.JdbcCategoryRepository;
import ui.AdminMenu;
import ui.UserMenu;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);


        JdbcUserRepository userRepository = new JdbcUserRepository();
        JdbcBookRepository bookRepository = new JdbcBookRepository();
        JdbcBorrowRecordRepository borrowRecordRepository = new JdbcBorrowRecordRepository();
        JdbcCategoryRepository categoryRepository = new JdbcCategoryRepository();

        UserService userService = new UserService(userRepository);
        BookService bookService = new BookService(bookRepository, categoryRepository, borrowRecordRepository);
        BorrowService borrowService = new BorrowService(borrowRecordRepository);

        if (!userRepository.exists("admin")) {
            userService.registerUser("admin", "admin123", true);
            System.out.println("Admin account created with username: admin and password: admin123");
        }

        while (true) {
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
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
                    System.out.println("You have registered");
                    break;
                case 2:
                    System.out.print("Username: ");
                    username = scanner.nextLine();
                    System.out.print("Password: ");
                    password = scanner.nextLine();
                    try {
                        User user = userService.loginUser(username, password);
                        System.out.println("You have logged in");
                        if (user.isAdmin()) {
                            AdminMenu.display(scanner, userService, bookService, borrowService);
                        } else {
                            UserMenu.display(scanner, bookService, borrowService, user);
                        }
                    } catch (RuntimeException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 3:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }
}