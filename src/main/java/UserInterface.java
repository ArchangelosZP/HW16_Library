import java.util.Scanner;
import java.util.stream.Stream;

public class UserInterface implements Commands {

    Manager manager;
    Scanner scanner;

    public UserInterface(Manager manager, Scanner scanner) {
        this.manager = manager;
        this.scanner = scanner;
    }

    private void contextMenu(String availableCommands) {
        System.out.println("Commands: " + availableCommands);
        System.out.print("Enter command >");
    }

    public void start() {
        String availableCommands = String.format("%s, %s, %s, %s, %s",
                Commands.ADD, Commands.DELETE, Commands.FIND, Commands.VIEW, Commands.EXIT);
        contextMenu(availableCommands);
    }

    public void add() {
        String confirmation;
        String availableCommands = String.format("%s, %s", Commands.YES, Commands.NO);
        System.out.print("Enter the author the book >");
        String author = scanner.nextLine();
        System.out.print("Enter the title the book >");
        String title = scanner.nextLine();
        do {
            System.out.printf("Add \"%s. %s\" to the library?\n", author, title);
            contextMenu(availableCommands);
            confirmation = scanner.nextLine();
        } while (!(confirmation.equalsIgnoreCase(YES) || confirmation.equalsIgnoreCase(NO)));
        if (confirmation.equalsIgnoreCase(YES)) {
            if (manager.add(author, title)) {
                System.out.println("The book has been successfully added to the library");
            } else {
                System.out.println("This book has already been added to the library");
            }
        }
    }

    public void del() {
        String confirmation;
        String availableCommands = String.format("%s, %s", Commands.YES, Commands.NO);
        ;
        System.out.println("Enter the ID of the book to be removed from the library >");
        Integer id = Integer.parseInt(scanner.nextLine());
        String bookToDelete = manager.findID(id);
        if (bookToDelete != null) {
            do {
                System.out.printf("Do you really want to delete [%s]?\n", bookToDelete);
                contextMenu(availableCommands);
                confirmation = scanner.nextLine();
            } while (!(confirmation.equalsIgnoreCase(YES) || confirmation.equalsIgnoreCase(NO)));
            if (confirmation.equalsIgnoreCase(YES)) {
                if (manager.del(id)) {
                    System.out.println("Book successfully deleted");
                } else {
                    System.out.println("The book cannot be deleted.");
                }
            }
        } else {
            System.out.println("There is no book with the specified ID in the library");
        }
    }

    public void view() {
        if (manager.view()) {
            Stream<String> stream = manager.booksToScreen.stream();
            stream.forEach(System.out::println);
        } else {
            System.out.println("Library is empty");
        }
    }

    public void find() {
        System.out.print("Please enter text to search >");
        String find = scanner.nextLine();
        if (manager.find(find)) {
            Stream<String> stream = manager.booksToScreen.stream();
            stream.forEach(System.out::println);
        } else {
            System.out.println("Sorry, nothing found");
        }
    }

}