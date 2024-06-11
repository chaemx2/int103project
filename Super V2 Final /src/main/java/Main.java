import application.UserService;
import application.BookService;
import application.BorrowService;
import config.Config;
import domain.Category;
import domain.User;
import infrastructure.repositories.*;
import ui.AdminMenu;
import ui.UserMenu;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String repositoryType = Config.get("repository.type");
        BookRepository bookRepository;
        BorrowRecordRepository borrowRecordRepository;
        CategoryRepository categoryRepository;
        UserRepository userRepository;

        switch (repositoryType) {
            case "jdbc":
                bookRepository = new JdbcBookRepository();
                borrowRecordRepository = new JdbcBorrowRecordRepository();
                categoryRepository = new JdbcCategoryRepository();
                userRepository = new JdbcUserRepository();
                break;
            case "file":
                bookRepository = new FileBookRepository();
                borrowRecordRepository = new FileBorrowRecordRepository();
                categoryRepository = new FileCategoryRepository();
                userRepository = new FileUserRepository();
                break;
            case "inmemory":
            default:
                bookRepository = new InMemoryBookRepository();
                borrowRecordRepository = new InMemoryBorrowRecordRepository();
                categoryRepository = new InMemoryCategoryRepository();
                userRepository = new InMemoryUserRepository();
                break;
        }

        BookService bookService = new BookService(bookRepository, categoryRepository, borrowRecordRepository);
        BorrowService borrowService = new BorrowService(borrowRecordRepository);
        UserService userService = new UserService(userRepository);

        if (!userService.exists("admin")) {
            userService.save(new User("admin", "admin", true));
        }

        if (repositoryType.equals("inmemory")) {
            if (categoryRepository.findById("1") == null) {
                categoryRepository.save(new Category("1", "Fiction"));
                categoryRepository.save(new Category("2", "Non-Fiction"));
                categoryRepository.save(new Category("3", "Science"));
            }

            if (bookRepository.findById("1") == null) {
                bookService.addBook("1", "Book 1", "1", 10);
                bookService.addBook("2", "Book 2", "2", 5);
                bookService.addBook("3", "Book 3", "3", 3);
            }
        }

        while (true) {
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Username: ");
                    String username = scanner.nextLine();
                    System.out.print("Password: ");
                    String password = scanner.nextLine();
                    if (username.length() >= 4 && password.length() >= 4) {
                        if (!userService.exists(username)) {
                            userService.save(new User(username, password, false));
                            System.out.println("You have registered");
                        } else {
                            System.out.println("This username is already used");
                        }
                    } else {
                        System.out.println("Username and password must be at least 4 characters long");
                    }
                    break;
                case 2:
                    System.out.print("Username: ");
                    username = scanner.nextLine();
                    System.out.print("Password: ");
                    password = scanner.nextLine();
                    User user = userService.findByUsername(username);
                    if (user != null && user.getPassword().equals(password)) {
                        if (user.isAdmin()) {
                            AdminMenu.display(scanner, bookService, borrowService, userService);
                        } else {
                            UserMenu.display(scanner, bookService, borrowService, user);
                        }
                    } else {
                        System.out.println("Your username or password is incorrect");
                    }
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }
}