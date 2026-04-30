package oop;
import java.util.Scanner;
import java.util.ArrayList;

public class Book {

    // ── Private Fields ────────────────────────────────────────
    private String title;
    private String author;
    private String isbn;
    private boolean isAvailable;
    private String borrowedBy;
    private int    totalBorrows;

    // ── No-Arg Constructor ────────────────────────────────────
    public Book() {
        this.title        = "Unknown";
        this.author       = "Unknown";
        this.isbn         = "N/A";
        this.isAvailable  = true;
        this.borrowedBy   = null;
        this.totalBorrows = 0;
    }

    // ── Parameterized Constructor ─────────────────────────────
    public Book(String title, String author, String isbn) {
        setTitle(title);
        setAuthor(author);
        setIsbn(isbn);
        this.isAvailable  = true;
        this.borrowedBy   = null;
        this.totalBorrows = 0;
    }

    // ── Getters ───────────────────────────────────────────────
    public String  getTitle()        { return title; }
    public String  getAuthor()       { return author; }
    public String  getIsbn()         { return isbn; }
    public boolean isAvailable()     { return isAvailable; }
    public String  getBorrowedBy()   { return borrowedBy; }
    public int     getTotalBorrows() { return totalBorrows; }

    // ── Setters (with validation) ─────────────────────────────
    public void setTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            System.out.println("❌ Title cannot be empty.");
            return;
        }
        this.title = title.trim();
    }

    public void setAuthor(String author) {
        if (author == null || author.trim().isEmpty()) {
            System.out.println("❌ Author cannot be empty.");
            return;
        }
        this.author = author.trim();
    }

    public void setIsbn(String isbn) {
        if (isbn == null || isbn.trim().isEmpty()) {
            System.out.println("❌ ISBN cannot be empty.");
            return;
        }
        this.isbn = isbn.trim();
    }

    // ── Borrow ────────────────────────────────────────────────
    public void borrowBook(String borrowerName) {
        if (borrowerName == null || borrowerName.trim().isEmpty()) {
            System.out.println("❌ Borrower name cannot be empty.");
            return;
        }
        if (!isAvailable) {
            System.out.println("❌ Sorry! \"" + title + "\" is currently borrowed by " + borrowedBy + ".");
            return;
        }
        isAvailable  = false;
        borrowedBy   = borrowerName.trim();
        totalBorrows++;

        System.out.println("\n📖 Book Borrowed Successfully!");
        System.out.println("══════════════════════════════════");
        System.out.println("  Book      : " + title);
        System.out.println("  Author    : " + author);
        System.out.println("  Borrower  : " + borrowedBy);
        System.out.println("  Status    : ❌ Not Available");
        System.out.println("══════════════════════════════════");
    }

    // ── Return ────────────────────────────────────────────────
    public void returnBook() {
        if (isAvailable) {
            System.out.println("⚠️  \"" + title + "\" is already in the library. No return needed.");
            return;
        }

        System.out.println("\n✅ Book Returned Successfully!");
        System.out.println("══════════════════════════════════");
        System.out.println("  Book      : " + title);
        System.out.println("  Author    : " + author);
        System.out.println("  Returned by : " + borrowedBy);
        System.out.println("  Status    : ✅ Now Available");
        System.out.println("══════════════════════════════════");

        isAvailable = true;
        borrowedBy  = null;
    }

    // ── Display Info ──────────────────────────────────────────
    public void displayInfo() {
        System.out.println("\n╔══════════════════════════════════╗");
        System.out.println("║          📚 Book Details         ║");
        System.out.println("╠══════════════════════════════════╣");
        System.out.printf( "║  Title        : %-18s║%n", title);
        System.out.printf( "║  Author       : %-18s║%n", author);
        System.out.printf( "║  ISBN         : %-18s║%n", isbn);
        System.out.printf( "║  Status       : %-18s║%n", isAvailable ? "✅ Available" : "❌ Borrowed");
        System.out.printf( "║  Borrowed By  : %-18s║%n", borrowedBy != null ? borrowedBy : "—");
        System.out.printf( "║  Total Borrows: %-18d║%n", totalBorrows);
        System.out.println("╚══════════════════════════════════╝");
    }

    @Override
    public String toString() {
        return String.format("Book{title='%s', author='%s', isbn='%s', available=%b}",
                title, author, isbn, isAvailable);
    }

    // ── Main ─────────────────────────────────────────────────
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Book> library = new ArrayList<>();

        System.out.println("╔══════════════════════════════════╗");
        System.out.println("║     📚 Library Book Manager      ║");
        System.out.println("╚══════════════════════════════════╝");

        boolean running = true;
        while (running) {
            System.out.println("\n╔══════════════════════════════════╗");
            System.out.println("║            Main Menu             ║");
            System.out.println("╠══════════════════════════════════╣");
            System.out.println("║  1. Add a New Book               ║");
            System.out.println("║  2. Borrow a Book                ║");
            System.out.println("║  3. Return a Book                ║");
            System.out.println("║  4. Display Book Info            ║");
            System.out.println("║  5. Display All Books            ║");
            System.out.println("║  6. Exit                         ║");
            System.out.println("╚══════════════════════════════════╝");
            System.out.print("Choose an option (1-6): ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                // ── Add Book ──────────────────────────────────
                case 1:
                    System.out.print("Enter Title  : ");
                    String title = scanner.nextLine();
                    System.out.print("Enter Author : ");
                    String author = scanner.nextLine();
                    System.out.print("Enter ISBN   : ");
                    String isbn = scanner.nextLine();

                    library.add(new Book(title, author, isbn));
                    System.out.println("✅ Book added to the library!");
                    break;

                // ── Borrow Book ───────────────────────────────
                case 2:
                    if (library.isEmpty()) {
                        System.out.println("⚠️  No books in the library yet.");
                        break;
                    }
                    System.out.print("Enter book title to borrow : ");
                    String borrowTitle = scanner.nextLine();
                    System.out.print("Enter your name            : ");
                    String borrower = scanner.nextLine();

                    boolean foundBorrow = false;
                    for (Book b : library) {
                        if (b.getTitle().equalsIgnoreCase(borrowTitle)) {
                            b.borrowBook(borrower);
                            foundBorrow = true;
                            break;
                        }
                    }
                    if (!foundBorrow) System.out.println("❌ Book not found: " + borrowTitle);
                    break;

                // ── Return Book ───────────────────────────────
                case 3:
                    if (library.isEmpty()) {
                        System.out.println("⚠️  No books in the library yet.");
                        break;
                    }
                    System.out.print("Enter book title to return : ");
                    String returnTitle = scanner.nextLine();

                    boolean foundReturn = false;
                    for (Book b : library) {
                        if (b.getTitle().equalsIgnoreCase(returnTitle)) {
                            b.returnBook();
                            foundReturn = true;
                            break;
                        }
                    }
                    if (!foundReturn) System.out.println("❌ Book not found: " + returnTitle);
                    break;

                // ── Display One Book ──────────────────────────
                case 4:
                    if (library.isEmpty()) {
                        System.out.println("⚠️  No books in the library yet.");
                        break;
                    }
                    System.out.print("Enter book title to display : ");
                    String searchTitle = scanner.nextLine();

                    boolean foundDisplay = false;
                    for (Book b : library) {
                        if (b.getTitle().equalsIgnoreCase(searchTitle)) {
                            b.displayInfo();
                            foundDisplay = true;
                            break;
                        }
                    }
                    if (!foundDisplay) System.out.println("❌ Book not found: " + searchTitle);
                    break;

                // ── Display All Books ─────────────────────────
                case 5:
                    if (library.isEmpty()) {
                        System.out.println("⚠️  No books in the library yet.");
                        break;
                    }
                    System.out.println("\n📚 All Books in Library:");
                    System.out.println("════════════════════════════════════════════════════════");
                    System.out.printf("%-25s %-20s %-15s %-12s%n",
                            "Title", "Author", "ISBN", "Status");
                    System.out.println("────────────────────────────────────────────────────────");
                    for (Book b : library) {
                        System.out.printf("%-25s %-20s %-15s %-12s%n",
                                b.getTitle(),
                                b.getAuthor(),
                                b.getIsbn(),
                                b.isAvailable() ? "✅ Available" : "❌ Borrowed");
                    }
                    System.out.println("════════════════════════════════════════════════════════");
                    break;

                // ── Exit ──────────────────────────────────────
                case 6:
                    System.out.println("\n👋 Exiting Library Manager. Goodbye!");
                    running = false;
                    break;

                default:
                    System.out.println("❌ Invalid option. Please choose between 1 and 6.");
            }
        }

        scanner.close();
    }
}