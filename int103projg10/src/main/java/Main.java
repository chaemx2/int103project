import services.BookService;
import services.LoanService;
import services.UserService;
import storage.InMemoryStorage;
import ui.ConsoleUI;

public class Main {
    public static void main(String[] args) {
        InMemoryStorage storage = new InMemoryStorage();
        UserService userService = new UserService(storage);
        BookService bookService = new BookService(storage);
        LoanService loanService = new LoanService(storage);

        ConsoleUI ui = new ConsoleUI(userService, bookService, loanService);
        ui.start();
    }
}
