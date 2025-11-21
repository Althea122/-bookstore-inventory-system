import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Inventory {

    private LinkedList<Book> bookList;
    private Queue<String> customerOrders;

    public Inventory() {
        this.bookList = new LinkedList<>();
        this.customerOrders = new LinkedList<>();
    }



    public void addBook(Scanner scanner) {
        System.out.println("\n--- Adding New Book ---");
        scanner.nextLine();

        System.out.print("Enter book title: ");
        String title = scanner.nextLine();
        System.out.print("Enter author name: ");
        String author = scanner.nextLine();
        System.out.print("Enter ISBN: ");
        String isbn = scanner.nextLine();

        double price = 0.0;
        boolean validPrice = false;

        while (!validPrice) {
            try {
                System.out.print("Enter price: $");
                price = scanner.nextDouble();
                scanner.nextLine();
                if (price < 0) {
                    System.out.println("Price cannot be negative. Please try again.");
                } else {
                    validPrice = true;
                }
            } catch (java.util.InputMismatchException e) {
                System.out.println("Invalid price format. Please enter a numerical value (e.g., 19.99).");
                scanner.nextLine();
            }
        }

        Book newBook = new Book(title, author, isbn, price);
        bookList.add(newBook);
        System.out.println("\nSuccessfully added: " + newBook.getTitle());
    }

    public void displayAllBooks() {
        if (bookList.isEmpty()) {
            System.out.println("\nThe inventory is currently empty. No books to display.");
            return;
        }

        System.out.println("\n--- Current Book Inventory ---");
        int index = 1;
        for (Book book : bookList) {
            System.out.println(index + ". " + book.toString());
            index++;
        }
        System.out.println("------------------------------");
    }

    public void sortBooksByTitle() {
        System.out.println("Sorting books by title...");
        int n = bookList.size();
        if (n <= 1) {
            System.out.println("Not enough books to sort.");
            return;
        }

        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - 1 - i; j++) {
                Book bookA = bookList.get(j);
                Book bookB = bookList.get(j + 1);

                if (bookA.getTitle().compareTo(bookB.getTitle()) > 0) {
                    bookList.set(j, bookB);
                    bookList.set(j + 1, bookA);
                }
            }
        }
        System.out.println("Books sorted successfully!");
    }

    public void searchBookByTitle(Scanner scanner) {
        scanner.nextLine();
        System.out.print("\nEnter the title of the book to search for: ");
        String searchTitle = scanner.nextLine();

        Book foundBook = null;

        for (Book book : bookList) {
            if (book.getTitle().equalsIgnoreCase(searchTitle)) {
                foundBook = book;
                break;
            }
        }

        if (foundBook != null) {
            System.out.println("\nBook found:");
            System.out.println(foundBook);
        } else {
            System.out.println("\nBook with title '" + searchTitle + "' was not found in inventory.");
        }
    }

    public void addOrderToQueue(Scanner scanner) {
        scanner.nextLine();
        System.out.print("Enter the title of the book to order: ");
        String orderTitle = scanner.nextLine();

        customerOrders.offer(orderTitle);
        System.out.println("Order for \"" + orderTitle + "\" has been added to the queue.");
    }

    public void processNextOrder() {
        System.out.println("Processing next order...");

        String processedOrder = customerOrders.poll();

        if (processedOrder != null) {
            System.out.println("Processed order for: " + processedOrder);
        } else {
            System.out.println("The order queue is empty. No orders to process.");
        }
    }

    public static void main(String[] args) {
        Inventory manager = new Inventory();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        manager.bookList.add(new Book("The Hunger Games", "Suzanne Collins", "9780439023481", 10.99));
        manager.bookList.add(new Book("Harry Potter and the Sorcerer's Stone", "J.K. Rowling", "9780590353424", 14.99));
        manager.bookList.add(new Book("Dune", "Frank Herbert", "9780441172719", 17.99));

        System.out.println("--- Welcome to the Book Inventory Management System ---");

        while (running) {
            System.out.println("\nPlease choose an option:");
            System.out.println("1. Add a new book");
            System.out.println("2. Display all books");
            System.out.println("3. Sort books by title");
            System.out.println("4. Search for a book by title");
            System.out.println("5. Add a customer order to the queue");
            System.out.println("6. Process the next customer order");
            System.out.println("7. Exit ");
            System.out.print("\n" +
                    "Enter your choice (1-7): ");

            try {
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        manager.addBook(scanner);
                        break;
                    case 2:
                        manager.displayAllBooks();
                        break;
                    case 3:
                        manager.sortBooksByTitle();
                        break;
                    case 4:
                        manager.searchBookByTitle(scanner);
                        break;
                    case 5:
                        manager.addOrderToQueue(scanner);
                        break;
                    case 6:
                        manager.processNextOrder();
                        break;
                    case 7:
                        System.out.println("\nThank you for using the Bookstore Inventory Management System!");
                        running = false;
                        break;
                    default:
                        System.out.println("\nInvalid choice. Please enter a number between 1 and 7.");
                }
            } catch (java.util.InputMismatchException e) {
                System.out.println("\nInvalid input. Please enter a number for the menu choice.");
                scanner.nextLine();
            }
        }
        scanner.close();
    }
}