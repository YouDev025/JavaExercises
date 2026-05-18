package projects;

import java.io.*;
import java.time.*;
import java.time.format.*;
import java.time.temporal.ChronoUnit;
import java.util.*;

// Library Management System
// Features: add/remove books, search, borrow/return, member management, due dates
// Concepts: Multiple classes, HashMap, LinkedList, LocalDate, file I/O
public class LibrarySystem {

    // ─────────────────────────────────────────────
    //  CONSTANTS
    // ─────────────────────────────────────────────

    static final int    LOAN_DAYS        = 14;    // Default loan period in days
    static final double FINE_PER_DAY     = 0.50;  // Fine charged per overdue day
    static final int    MAX_LOANS        = 3;     // Maximum books a member can borrow
    static final DateTimeFormatter DATE_FMT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // ─────────────────────────────────────────────
    //  BOOK CLASS
    // ─────────────────────────────────────────────

    static class Book {

        enum Genre { FICTION, NON_FICTION, SCIENCE, HISTORY,
            TECHNOLOGY, BIOGRAPHY, OTHER }

        private static int counter = 1000;

        String  bookId;       // Unique book identifier
        String  title;        // Full title of the book
        String  author;       // Author's full name
        String  isbn;         // International Standard Book Number
        Genre   genre;        // Book genre classification
        int     year;         // Publication year
        int     totalCopies;  // Total copies owned by the library
        int     available;    // Copies currently available for borrowing

        // Constructor for a new book entry
        Book(String title, String author, String isbn,
             Genre genre, int year, int copies) {
            this.bookId       = "BK" + (++counter);
            this.title        = title;
            this.author       = author;
            this.isbn         = isbn;
            this.genre        = genre;
            this.year         = year;
            this.totalCopies  = copies;
            this.available    = copies;
        }

        // Constructor for loading a book from file
        Book(String bookId, String title, String author, String isbn,
             Genre genre, int year, int totalCopies, int available) {
            this.bookId      = bookId;
            this.title       = title;
            this.author      = author;
            this.isbn        = isbn;
            this.genre       = genre;
            this.year        = year;
            this.totalCopies = totalCopies;
            this.available   = available;
            int num = Integer.parseInt(bookId.replace("BK", ""));
            if (num >= counter) counter = num;
        }

        boolean isAvailable()  { return available > 0; }

        // Decrements available copies when a book is borrowed
        boolean checkout() {
            if (available <= 0) return false;
            available--;
            return true;
        }

        // Increments available copies when a book is returned
        void returnCopy() {
            if (available < totalCopies) available++;
        }

        // Serializes book to pipe-delimited string for file storage
        String toFileLine() {
            return bookId + "|" + title + "|" + author + "|" + isbn
                    + "|" + genre + "|" + year + "|"
                    + totalCopies + "|" + available;
        }

        // Deserializes a pipe-delimited line into a Book object
        static Book fromFileLine(String line) {
            String[] p = line.split("\\|", -1);
            return new Book(p[0], p[1], p[2], p[3],
                    Genre.valueOf(p[4]),
                    Integer.parseInt(p[5]),
                    Integer.parseInt(p[6]),
                    Integer.parseInt(p[7]));
        }

        @Override
        public String toString() {
            return String.format("  %-8s %-32s %-22s %-6d %-4d/%-4d",
                    bookId, truncate(title, 30), truncate(author, 20),
                    year, available, totalCopies);
        }
    }

    // ─────────────────────────────────────────────
    //  MEMBER CLASS
    // ─────────────────────────────────────────────

    static class Member {

        enum MemberType { STUDENT, FACULTY, PUBLIC }

        private static int counter = 2000;

        String            memberId;       // Unique member identifier
        String            name;           // Full name of the member
        String            email;          // Contact email address
        String            phone;          // Contact phone number
        MemberType        type;           // Member classification
        LocalDate         joinDate;       // Date membership was created
        double            totalFines;     // Cumulative unpaid fines
        // LinkedList of active transaction IDs for this member
        LinkedList<String> activeLoans;

        Member(String name, String email, String phone, MemberType type) {
            this.memberId    = "MB" + (++counter);
            this.name        = name;
            this.email       = email;
            this.phone       = phone;
            this.type        = type;
            this.joinDate    = LocalDate.now();
            this.totalFines  = 0.0;
            this.activeLoans = new LinkedList<>();
        }

        // Constructor for loading from file
        Member(String memberId, String name, String email, String phone,
               MemberType type, LocalDate joinDate,
               double totalFines, LinkedList<String> activeLoans) {
            this.memberId    = memberId;
            this.name        = name;
            this.email       = email;
            this.phone       = phone;
            this.type        = type;
            this.joinDate    = joinDate;
            this.totalFines  = totalFines;
            this.activeLoans = activeLoans;
            int num = Integer.parseInt(memberId.replace("MB", ""));
            if (num >= counter) counter = num;
        }

        // Returns true if member can borrow more books
        boolean canBorrow() {
            return activeLoans.size() < MAX_LOANS && totalFines == 0.0;
        }

        // Adds a transaction ID to the member's active loans list
        void addLoan(String transactionId) {
            activeLoans.add(transactionId);
        }

        // Removes a transaction ID when a book is returned
        void removeLoan(String transactionId) {
            activeLoans.remove(transactionId);
        }

        // Serializes member to pipe-delimited string for file storage
        String toFileLine() {
            String loans = String.join(",", activeLoans);
            return memberId + "|" + name + "|" + email + "|" + phone
                    + "|" + type + "|" + joinDate.format(DATE_FMT)
                    + "|" + totalFines + "|" + loans;
        }

        // Deserializes a pipe-delimited line into a Member object
        static Member fromFileLine(String line) {
            String[] p = line.split("\\|", -1);
            LinkedList<String> loans = new LinkedList<>();
            if (!p[7].isEmpty()) {
                Collections.addAll(loans, p[7].split(","));
            }
            return new Member(p[0], p[1], p[2], p[3],
                    MemberType.valueOf(p[4]),
                    LocalDate.parse(p[5], DATE_FMT),
                    Double.parseDouble(p[6]),
                    loans);
        }

        @Override
        public String toString() {
            return String.format("  %-8s %-24s %-28s %-10s %-4d loan(s) $%.2f fines",
                    memberId, name, email, type,
                    activeLoans.size(), totalFines);
        }
    }

    // ─────────────────────────────────────────────
    //  TRANSACTION CLASS
    // ─────────────────────────────────────────────

    // Records every borrow and return event in the library
    // Handles due date calculation, overdue detection, and fine computation
    static class Transaction {

        enum Status { ACTIVE, RETURNED, OVERDUE }

        private static int counter = 3000;

        String    transId;      // Unique transaction identifier
        String    bookId;       // ID of the borrowed book
        String    memberId;     // ID of the borrowing member
        String    bookTitle;    // Snapshot of book title at time of borrowing
        String    memberName;   // Snapshot of member name at time of borrowing
        LocalDate borrowDate;   // Date the book was checked out
        LocalDate dueDate;      // Date the book is due back
        LocalDate returnDate;   // Actual return date (null if still active)
        Status    status;       // ACTIVE | RETURNED | OVERDUE
        double    fine;         // Fine charged on this transaction

        Transaction(String bookId, String memberId,
                    String bookTitle, String memberName) {
            this.transId    = "TX" + (++counter);
            this.bookId     = bookId;
            this.memberId   = memberId;
            this.bookTitle  = bookTitle;
            this.memberName = memberName;
            this.borrowDate = LocalDate.now();
            this.dueDate    = LocalDate.now().plusDays(LOAN_DAYS);
            this.returnDate = null;
            this.status     = Status.ACTIVE;
            this.fine       = 0.0;
        }

        // Constructor for loading from file
        Transaction(String transId, String bookId, String memberId,
                    String bookTitle, String memberName,
                    LocalDate borrowDate, LocalDate dueDate,
                    LocalDate returnDate, Status status, double fine) {
            this.transId    = transId;
            this.bookId     = bookId;
            this.memberId   = memberId;
            this.bookTitle  = bookTitle;
            this.memberName = memberName;
            this.borrowDate = borrowDate;
            this.dueDate    = dueDate;
            this.returnDate = returnDate;
            this.status     = status;
            this.fine       = fine;
            int num = Integer.parseInt(transId.replace("TX", ""));
            if (num >= counter) counter = num;
        }

        // Refreshes the status by checking today's date against the due date
        void refreshStatus() {
            if (status != Status.RETURNED
                    && LocalDate.now().isAfter(dueDate)) {
                status = Status.OVERDUE;
            }
        }

        // Calculates the overdue fine based on days past the due date
        double calculateFine() {
            if (status == Status.RETURNED && returnDate != null) {
                long overdueDays = ChronoUnit.DAYS.between(dueDate, returnDate);
                return overdueDays > 0 ? overdueDays * FINE_PER_DAY : 0.0;
            }
            if (status == Status.OVERDUE || status == Status.ACTIVE) {
                long overdueDays = ChronoUnit.DAYS.between(dueDate, LocalDate.now());
                return overdueDays > 0 ? overdueDays * FINE_PER_DAY : 0.0;
            }
            return 0.0;
        }

        // Returns the number of days remaining until the due date
        long daysUntilDue() {
            return ChronoUnit.DAYS.between(LocalDate.now(), dueDate);
        }

        // Serializes transaction to pipe-delimited string for file storage
        String toFileLine() {
            String ret = (returnDate == null) ? ""
                    : returnDate.format(DATE_FMT);
            return transId + "|" + bookId + "|" + memberId
                    + "|" + bookTitle + "|" + memberName
                    + "|" + borrowDate.format(DATE_FMT)
                    + "|" + dueDate.format(DATE_FMT)
                    + "|" + ret + "|" + status + "|" + fine;
        }

        // Deserializes a pipe-delimited line into a Transaction object
        static Transaction fromFileLine(String line) {
            String[] p     = line.split("\\|", -1);
            LocalDate ret  = p[7].isEmpty() ? null
                    : LocalDate.parse(p[7], DATE_FMT);
            return new Transaction(p[0], p[1], p[2], p[3], p[4],
                    LocalDate.parse(p[5], DATE_FMT),
                    LocalDate.parse(p[6], DATE_FMT),
                    ret, Status.valueOf(p[8]),
                    Double.parseDouble(p[9]));
        }

        @Override
        public String toString() {
            String retStr = (returnDate == null) ? "─────────────"
                    : returnDate.format(DATE_FMT);
            String fineStr = fine > 0 ? String.format("$%.2f", fine) : "None";
            return String.format(
                    "  %-8s %-8s %-8s %-24s %-13s %-13s %-13s %-10s %-8s",
                    transId, bookId, memberId,
                    truncate(bookTitle, 22),
                    borrowDate.format(DATE_FMT),
                    dueDate.format(DATE_FMT),
                    retStr, status, fineStr);
        }
    }

    // ─────────────────────────────────────────────
    //  LIBRARY CLASS
    // ─────────────────────────────────────────────

    // Core engine managing all library operations
    // Uses HashMaps for O(1) average lookups by ID
    // Maintains LinkedLists for ordered history and active loans
    static class Library {

        private String                      libraryName;
        private HashMap<String, Book>        books;         // bookId → Book
        private HashMap<String, Member>      members;       // memberId → Member
        private HashMap<String, Transaction> transactions;  // transId → Transaction
        private ArrayList<String>            bookOrder;     // Insertion order for display
        private ArrayList<String>            memberOrder;   // Insertion order for display
        private ArrayList<String>            transOrder;    // Insertion order for display

        private static final String BOOKS_FILE   = "library_books.txt";
        private static final String MEMBERS_FILE = "library_members.txt";
        private static final String TRANS_FILE   = "library_transactions.txt";

        Library(String libraryName) {
            this.libraryName  = libraryName;
            this.books        = new HashMap<>();
            this.members      = new HashMap<>();
            this.transactions = new HashMap<>();
            this.bookOrder    = new ArrayList<>();
            this.memberOrder  = new ArrayList<>();
            this.transOrder   = new ArrayList<>();
        }

        // ── ADD BOOK ──

        // Registers a new book (or adds copies of an existing ISBN)
        Book addBook(String title, String author, String isbn,
                     Book.Genre genre, int year, int copies) {
            // Check if a book with the same ISBN already exists
            for (Book b : books.values()) {
                if (b.isbn.equals(isbn)) {
                    b.totalCopies += copies;
                    b.available   += copies;
                    System.out.printf("  Added %d more copies of \"%s\" "
                            + "(total: %d)%n", copies, b.title, b.totalCopies);
                    return b;
                }
            }
            Book book = new Book(title, author, isbn, genre, year, copies);
            books.put(book.bookId, book);
            bookOrder.add(book.bookId);
            System.out.printf("  Book added: %s — \"%s\" by %s%n",
                    book.bookId, book.title, book.author);
            return book;
        }

        // ── REMOVE BOOK ──

        // Removes a book from the catalogue if no copies are currently borrowed
        boolean removeBook(String bookId) {
            Book book = books.get(bookId);
            if (book == null) {
                System.out.println("  Book not found: " + bookId);
                return false;
            }
            if (book.available < book.totalCopies) {
                System.out.println("  Cannot remove — "
                        + (book.totalCopies - book.available)
                        + " copy/copies still on loan.");
                return false;
            }
            books.remove(bookId);
            bookOrder.remove(bookId);
            System.out.println("  Removed: \"" + book.title + "\"");
            return true;
        }

        // ── REGISTER MEMBER ──

        Member registerMember(String name, String email,
                              String phone, Member.MemberType type) {
            // Prevent duplicate email registrations
            for (Member m : members.values()) {
                if (m.email.equalsIgnoreCase(email)) {
                    System.out.println("  A member with this email already exists: "
                            + m.memberId);
                    return null;
                }
            }
            Member member = new Member(name, email, phone, type);
            members.put(member.memberId, member);
            memberOrder.add(member.memberId);
            System.out.printf("  Member registered: %s — %s%n",
                    member.memberId, member.name);
            return member;
        }

        // ── REMOVE MEMBER ──

        boolean removeMember(String memberId) {
            Member m = members.get(memberId);
            if (m == null) {
                System.out.println("  Member not found: " + memberId);
                return false;
            }
            if (!m.activeLoans.isEmpty()) {
                System.out.println("  Cannot remove — member has "
                        + m.activeLoans.size() + " active loan(s).");
                return false;
            }
            if (m.totalFines > 0) {
                System.out.printf("  Cannot remove — member has $%.2f "
                        + "outstanding fines.%n", m.totalFines);
                return false;
            }
            members.remove(memberId);
            memberOrder.remove(memberId);
            System.out.println("  Removed member: " + m.name);
            return true;
        }

        // ── BORROW BOOK ──

        // Creates a transaction linking a member to a book for LOAN_DAYS days
        Transaction borrowBook(String memberId, String bookId) {
            Member member = members.get(memberId);
            Book   book   = books.get(bookId);

            if (member == null) {
                System.out.println("  Member not found: " + memberId);
                return null;
            }
            if (book == null) {
                System.out.println("  Book not found: " + bookId);
                return null;
            }
            if (!book.isAvailable()) {
                System.out.println("  No copies available for: \""
                        + book.title + "\"");
                return null;
            }
            if (!member.canBorrow()) {
                if (member.totalFines > 0) {
                    System.out.printf(
                            "  Borrow blocked — member has $%.2f unpaid fines.%n",
                            member.totalFines);
                } else {
                    System.out.println("  Borrow limit reached ("
                            + MAX_LOANS + " books max).");
                }
                return null;
            }

            // Deduct from available copies and create the transaction record
            book.checkout();
            Transaction tx = new Transaction(
                    bookId, memberId, book.title, member.name);
            transactions.put(tx.transId, tx);
            transOrder.add(tx.transId);
            member.addLoan(tx.transId);

            System.out.printf("  ✓ Borrowed: \"%s\"%n"
                            + "  Member: %s  |  Due: %s  |  Trans: %s%n",
                    book.title, member.name,
                    tx.dueDate.format(DATE_FMT), tx.transId);
            return tx;
        }

        // ── RETURN BOOK ──

        // Closes a transaction, computes any fine, and restores book availability
        boolean returnBook(String transactionId) {
            Transaction tx = transactions.get(transactionId);
            if (tx == null) {
                System.out.println("  Transaction not found: " + transactionId);
                return false;
            }
            if (tx.status == Transaction.Status.RETURNED) {
                System.out.println("  This book was already returned.");
                return false;
            }

            Book   book   = books.get(tx.bookId);
            Member member = members.get(tx.memberId);

            // Compute and apply any overdue fine
            tx.returnDate = LocalDate.now();
            tx.fine       = tx.calculateFine();
            tx.status     = Transaction.Status.RETURNED;

            if (book   != null) book.returnCopy();
            if (member != null) {
                member.removeLoan(transactionId);
                member.totalFines += tx.fine;
            }

            System.out.printf("  ✓ Returned: \"%s\"%n  Returned by: %s%n",
                    tx.bookTitle, tx.memberName);
            if (tx.fine > 0) {
                System.out.printf("  ⚠ Overdue fine: $%.2f "
                        + "(please pay at the desk)%n", tx.fine);
            } else {
                System.out.println("  No fine — returned on time.");
            }
            return true;
        }

        // ── PAY FINE ──

        // Reduces a member's outstanding fines by the paid amount
        void payFine(String memberId, double amount) {
            Member m = members.get(memberId);
            if (m == null) {
                System.out.println("  Member not found.");
                return;
            }
            if (m.totalFines == 0) {
                System.out.println("  No fines outstanding for " + m.name);
                return;
            }
            double paid = Math.min(amount, m.totalFines);
            m.totalFines = Math.max(0, m.totalFines - amount);
            System.out.printf("  Payment of $%.2f received.%n"
                    + "  Remaining fines: $%.2f%n", paid, m.totalFines);
        }

        // ── SEARCH BOOKS ──

        // Searches catalogue by title, author, or ISBN (case-insensitive)
        void searchBooks(String query) {
            String q = query.toLowerCase();
            ArrayList<Book> results = new ArrayList<>();

            for (Book b : books.values()) {
                if (b.title.toLowerCase().contains(q)
                        || b.author.toLowerCase().contains(q)
                        || b.isbn.contains(q)) {
                    results.add(b);
                }
            }

            if (results.isEmpty()) {
                System.out.println("  No books found for: \"" + query + "\"");
                return;
            }

            printBookTableHeader();
            for (Book b : results) {
                System.out.println(b);
            }
            System.out.println("  Found " + results.size() + " result(s).");
        }

        // ── SEARCH MEMBERS ──

        // Searches members by name or email (case-insensitive)
        void searchMembers(String query) {
            String q = query.toLowerCase();
            ArrayList<Member> results = new ArrayList<>();

            for (Member m : members.values()) {
                if (m.name.toLowerCase().contains(q)
                        || m.email.toLowerCase().contains(q)) {
                    results.add(m);
                }
            }

            if (results.isEmpty()) {
                System.out.println("  No members found for: \"" + query + "\"");
                return;
            }

            printMemberTableHeader();
            for (Member m : results) System.out.println(m);
            System.out.println("  Found " + results.size() + " result(s).");
        }

        // ── LIST ALL BOOKS ──

        void listAllBooks() {
            if (books.isEmpty()) {
                System.out.println("  No books in catalogue.");
                return;
            }
            printBookTableHeader();
            for (String id : bookOrder) System.out.println(books.get(id));
            System.out.println("─".repeat(70));
            System.out.println("  Total titles: " + books.size());
        }

        // ── LIST ALL MEMBERS ──

        void listAllMembers() {
            if (members.isEmpty()) {
                System.out.println("  No members registered.");
                return;
            }
            printMemberTableHeader();
            for (String id : memberOrder) System.out.println(members.get(id));
            System.out.println("─".repeat(80));
            System.out.println("  Total members: " + members.size());
        }

        // ── VIEW MEMBER DETAILS ──

        void viewMember(String memberId) {
            Member m = members.get(memberId);
            if (m == null) {
                System.out.println("  Member not found: " + memberId);
                return;
            }
            System.out.println("\n" + "═".repeat(56));
            System.out.println("  MEMBER PROFILE");
            System.out.println("═".repeat(56));
            System.out.println("  ID:          " + m.memberId);
            System.out.println("  Name:        " + m.name);
            System.out.println("  Email:       " + m.email);
            System.out.println("  Phone:       " + m.phone);
            System.out.println("  Type:        " + m.type);
            System.out.println("  Joined:      " + m.joinDate.format(DATE_FMT));
            System.out.printf ("  Fines:       $%.2f%n", m.totalFines);
            System.out.println("  Active Loans: " + m.activeLoans.size()
                    + " / " + MAX_LOANS);

            if (!m.activeLoans.isEmpty()) {
                System.out.println("\n  Current Loans:");
                System.out.println("  " + "─".repeat(54));
                for (String txId : m.activeLoans) {
                    Transaction tx = transactions.get(txId);
                    if (tx != null) {
                        tx.refreshStatus();
                        long daysLeft = tx.daysUntilDue();
                        String status = daysLeft < 0
                                ? "OVERDUE by " + Math.abs(daysLeft) + " days"
                                : daysLeft + " days left";
                        System.out.printf("  %-8s %-28s Due: %s  [%s]%n",
                                tx.transId,
                                truncate(tx.bookTitle, 26),
                                tx.dueDate.format(DATE_FMT),
                                status);
                    }
                }
            }
            System.out.println("═".repeat(56));
        }

        // ── VIEW BOOK DETAILS ──

        void viewBook(String bookId) {
            Book b = books.get(bookId);
            if (b == null) {
                System.out.println("  Book not found: " + bookId);
                return;
            }
            System.out.println("\n" + "═".repeat(56));
            System.out.println("  BOOK DETAILS");
            System.out.println("═".repeat(56));
            System.out.println("  ID:           " + b.bookId);
            System.out.println("  Title:        " + b.title);
            System.out.println("  Author:       " + b.author);
            System.out.println("  ISBN:         " + b.isbn);
            System.out.println("  Genre:        " + b.genre);
            System.out.println("  Year:         " + b.year);
            System.out.printf ("  Availability: %d / %d copies%n",
                    b.available, b.totalCopies);
            System.out.println("  Status:       "
                    + (b.isAvailable() ? "Available" : "All copies on loan"));
            System.out.println("═".repeat(56));
        }

        // ── OVERDUE REPORT ──

        // Scans all active transactions and flags those past their due date
        void overdueReport() {
            ArrayList<Transaction> overdue = new ArrayList<>();

            for (Transaction tx : transactions.values()) {
                tx.refreshStatus();
                if (tx.status == Transaction.Status.OVERDUE) {
                    overdue.add(tx);
                }
            }

            if (overdue.isEmpty()) {
                System.out.println("  No overdue books.");
                return;
            }

            // Sort overdue transactions by due date (oldest first)
            overdue.sort(Comparator.comparing(tx -> tx.dueDate));

            System.out.println("\n" + "═".repeat(72));
            System.out.println("  OVERDUE BOOKS REPORT");
            System.out.printf("  %-8s %-22s %-18s %-13s %-8s%n",
                    "Trans", "Book Title", "Member", "Due Date", "Fine");
            System.out.println("─".repeat(72));

            double totalFines = 0;
            for (Transaction tx : overdue) {
                double fine = tx.calculateFine();
                totalFines += fine;
                System.out.printf("  %-8s %-22s %-18s %-13s $%.2f%n",
                        tx.transId,
                        truncate(tx.bookTitle, 20),
                        truncate(tx.memberName, 16),
                        tx.dueDate.format(DATE_FMT),
                        fine);
            }

            System.out.println("─".repeat(72));
            System.out.printf("  Overdue books: %d  |  Total fines accrued: $%.2f%n",
                    overdue.size(), totalFines);
            System.out.println("═".repeat(72));
        }

        // ── TRANSACTION HISTORY ──

        // Displays all transactions — optionally filtered by member or book
        void transactionHistory(String filter) {
            ArrayList<Transaction> list = new ArrayList<>();

            for (String id : transOrder) {
                Transaction tx = transactions.get(id);
                tx.refreshStatus();
                if (filter == null
                        || tx.memberId.equals(filter)
                        || tx.bookId.equals(filter)) {
                    list.add(tx);
                }
            }

            if (list.isEmpty()) {
                System.out.println("  No transactions found.");
                return;
            }

            System.out.println("\n" + "═".repeat(110));
            System.out.printf("  %-8s %-8s %-8s %-24s %-13s %-13s %-13s %-10s %-8s%n",
                    "TransID", "BookID", "MemberID", "Title",
                    "Borrowed", "Due", "Returned", "Status", "Fine");
            System.out.println("─".repeat(110));

            for (Transaction tx : list) {
                System.out.println(tx);
            }

            System.out.println("═".repeat(110));
            System.out.println("  Total transactions shown: " + list.size());
        }

        // ── LIBRARY STATISTICS ──

        void statistics() {
            long available  = books.values().stream()
                    .filter(Book::isAvailable).count();
            long onLoan     = transactions.values().stream()
                    .filter(t -> t.status != Transaction.Status.RETURNED).count();
            long overdue    = transactions.values().stream()
                    .filter(t -> {
                        t.refreshStatus();
                        return t.status == Transaction.Status.OVERDUE;
                    }).count();
            double fines    = members.values().stream()
                    .mapToDouble(m -> m.totalFines).sum();

            System.out.println("\n" + "═".repeat(50));
            System.out.println("  " + libraryName + " — STATISTICS");
            System.out.println("═".repeat(50));
            System.out.println("  Total Titles:       " + books.size());
            System.out.println("  Available Titles:   " + available);
            System.out.println("  Registered Members: " + members.size());
            System.out.println("  Active Loans:       " + onLoan);
            System.out.println("  Overdue Books:      " + overdue);
            System.out.printf ("  Outstanding Fines:  $%.2f%n", fines);
            System.out.println("  Total Transactions: " + transactions.size());
            System.out.println("═".repeat(50));
        }

        // ── FILE PERSISTENCE ──

        void saveAll() {
            saveBooks();
            saveMembers();
            saveTransactions();
            System.out.println("  All data saved.");
        }

        void loadAll() {
            loadBooks();
            loadMembers();
            loadTransactions();
            System.out.println("  All data loaded.");
        }

        private void saveBooks() {
            try (BufferedWriter w = new BufferedWriter(
                    new FileWriter(BOOKS_FILE))) {
                for (String id : bookOrder) {
                    w.write(books.get(id).toFileLine());
                    w.newLine();
                }
            } catch (IOException e) {
                System.out.println("  Error saving books: " + e.getMessage());
            }
        }

        private void loadBooks() {
            books.clear(); bookOrder.clear();
            File f = new File(BOOKS_FILE);
            if (!f.exists()) return;
            try (BufferedReader r = new BufferedReader(new FileReader(f))) {
                String line;
                while ((line = r.readLine()) != null) {
                    if (!line.trim().isEmpty()) {
                        Book b = Book.fromFileLine(line);
                        books.put(b.bookId, b);
                        bookOrder.add(b.bookId);
                    }
                }
            } catch (IOException e) {
                System.out.println("  Error loading books: " + e.getMessage());
            }
        }

        private void saveMembers() {
            try (BufferedWriter w = new BufferedWriter(
                    new FileWriter(MEMBERS_FILE))) {
                for (String id : memberOrder) {
                    w.write(members.get(id).toFileLine());
                    w.newLine();
                }
            } catch (IOException e) {
                System.out.println("  Error saving members: " + e.getMessage());
            }
        }

        private void loadMembers() {
            members.clear(); memberOrder.clear();
            File f = new File(MEMBERS_FILE);
            if (!f.exists()) return;
            try (BufferedReader r = new BufferedReader(new FileReader(f))) {
                String line;
                while ((line = r.readLine()) != null) {
                    if (!line.trim().isEmpty()) {
                        Member m = Member.fromFileLine(line);
                        members.put(m.memberId, m);
                        memberOrder.add(m.memberId);
                    }
                }
            } catch (IOException e) {
                System.out.println("  Error loading members: " + e.getMessage());
            }
        }

        private void saveTransactions() {
            try (BufferedWriter w = new BufferedWriter(
                    new FileWriter(TRANS_FILE))) {
                for (String id : transOrder) {
                    w.write(transactions.get(id).toFileLine());
                    w.newLine();
                }
            } catch (IOException e) {
                System.out.println("  Error saving transactions: " + e.getMessage());
            }
        }

        private void loadTransactions() {
            transactions.clear(); transOrder.clear();
            File f = new File(TRANS_FILE);
            if (!f.exists()) return;
            try (BufferedReader r = new BufferedReader(new FileReader(f))) {
                String line;
                while ((line = r.readLine()) != null) {
                    if (!line.trim().isEmpty()) {
                        Transaction tx = Transaction.fromFileLine(line);
                        tx.refreshStatus();
                        transactions.put(tx.transId, tx);
                        transOrder.add(tx.transId);
                    }
                }
            } catch (IOException e) {
                System.out.println("  Error loading transactions: " + e.getMessage());
            }
        }

        // ── DISPLAY HELPERS ──

        private void printBookTableHeader() {
            System.out.println("\n" + "─".repeat(70));
            System.out.printf("  %-8s %-32s %-22s %-6s %-9s%n",
                    "ID", "Title", "Author", "Year", "Avail/Total");
            System.out.println("─".repeat(70));
        }

        private void printMemberTableHeader() {
            System.out.println("\n" + "─".repeat(80));
            System.out.printf("  %-8s %-24s %-28s %-10s %-6s %-8s%n",
                    "ID", "Name", "Email", "Type", "Loans", "Fines");
            System.out.println("─".repeat(80));
        }
    }

    // ─────────────────────────────────────────────
    //  UTILITY METHODS
    // ─────────────────────────────────────────────

    // Truncates a string to max length and appends ellipsis if needed
    static String truncate(String s, int max) {
        if (s == null) return "";
        return s.length() <= max ? s : s.substring(0, max - 1) + "…";
    }

    // ─────────────────────────────────────────────
    //  INPUT HELPERS
    // ─────────────────────────────────────────────

    static int readInt(Scanner sc, String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            try {
                int v = sc.nextInt(); sc.nextLine();
                if (v >= min && v <= max) return v;
                System.out.println("  Enter " + min + "–" + max + ".");
            } catch (InputMismatchException e) {
                sc.nextLine();
                System.out.println("  Invalid input.");
            }
        }
    }

    static String readString(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String v = sc.nextLine().trim();
            if (!v.isEmpty()) return v;
            System.out.println("  Cannot be empty.");
        }
    }

    static int readYear(Scanner sc) {
        int thisYear = LocalDate.now().getYear();
        return readInt(sc, "  Publication year: ", 1000, thisYear);
    }

    // ─────────────────────────────────────────────
    //  MAIN MENU
    // ─────────────────────────────────────────────

    public static void main(String[] args) {

        Scanner sc  = new Scanner(System.in);
        Library lib = new Library("JavaLib Public Library");

        System.out.println("═".repeat(60));
        System.out.println("       📚  LIBRARY MANAGEMENT SYSTEM");
        System.out.println("═".repeat(60));
        lib.loadAll();

        // Pre-load sample data if library is empty
        if (lib.books.isEmpty()) {
            System.out.println("  Loading sample catalogue...");
            lib.addBook("Clean Code", "Robert C. Martin",
                    "978-0132350884", Book.Genre.TECHNOLOGY, 2008, 3);
            lib.addBook("The Pragmatic Programmer",
                    "David Thomas", "978-0135957059",
                    Book.Genre.TECHNOLOGY, 2019, 2);
            lib.addBook("Introduction to Algorithms",
                    "Cormen et al.", "978-0262033848",
                    Book.Genre.SCIENCE, 2009, 2);
            lib.addBook("1984", "George Orwell",
                    "978-0451524935", Book.Genre.FICTION, 1949, 4);
            lib.addBook("Sapiens", "Yuval Noah Harari",
                    "978-0062316110", Book.Genre.HISTORY, 2011, 3);
        }

        boolean running = true;

        while (running) {
            System.out.println("\n─── Main Menu ───");
            System.out.println(" 1.  Add book             9.  View book details");
            System.out.println(" 2.  Remove book          10. View member details");
            System.out.println(" 3.  Register member      11. Transaction history");
            System.out.println(" 4.  Remove member        12. Overdue report");
            System.out.println(" 5.  Borrow book          13. Pay fine");
            System.out.println(" 6.  Return book          14. Statistics");
            System.out.println(" 7.  Search books         15. Save data");
            System.out.println(" 8.  Search members       16. Exit");
            System.out.print("Choose an option: ");

            int choice;
            try {
                choice = sc.nextInt(); sc.nextLine();
            } catch (InputMismatchException e) {
                sc.nextLine();
                System.out.println("  Invalid input.");
                continue;
            }

            switch (choice) {

                case 1:
                    // Add a new book to the catalogue
                    String title  = readString(sc, "  Title:  ");
                    String author = readString(sc, "  Author: ");
                    String isbn   = readString(sc, "  ISBN:   ");
                    System.out.println("  Genre: 1=FICTION 2=NON_FICTION "
                            + "3=SCIENCE 4=HISTORY 5=TECHNOLOGY 6=BIOGRAPHY 7=OTHER");
                    int gi = readInt(sc, "  Choose genre: ", 1, 7);
                    Book.Genre[] genres = Book.Genre.values();
                    int year   = readYear(sc);
                    int copies = readInt(sc, "  Number of copies: ", 1, 100);
                    lib.addBook(title, author, isbn, genres[gi - 1], year, copies);
                    break;

                case 2:
                    // Remove a book from the catalogue
                    lib.listAllBooks();
                    String rmBook = readString(sc, "  Book ID to remove: ");
                    lib.removeBook(rmBook);
                    break;

                case 3:
                    // Register a new library member
                    String mName  = readString(sc, "  Full name:  ");
                    String mEmail = readString(sc, "  Email:      ");
                    String mPhone = readString(sc, "  Phone:      ");
                    System.out.println("  Type: 1=STUDENT  2=FACULTY  3=PUBLIC");
                    int mt = readInt(sc, "  Choose type: ", 1, 3);
                    Member.MemberType[] mTypes = Member.MemberType.values();
                    lib.registerMember(mName, mEmail, mPhone, mTypes[mt - 1]);
                    break;

                case 4:
                    // Remove a member (must have no loans or fines)
                    lib.listAllMembers();
                    String rmMember = readString(sc, "  Member ID to remove: ");
                    lib.removeMember(rmMember);
                    break;

                case 5:
                    // Borrow a book — links a member to a book via a transaction
                    lib.listAllBooks();
                    String bkId  = readString(sc, "  Book ID to borrow:  ");
                    lib.listAllMembers();
                    String mbId  = readString(sc, "  Member ID:          ");
                    lib.borrowBook(mbId, bkId);
                    break;

                case 6:
                    // Return a borrowed book using the transaction ID
                    lib.transactionHistory(null);
                    String txId = readString(sc, "  Transaction ID to return: ");
                    lib.returnBook(txId);
                    break;

                case 7:
                    // Search the book catalogue by keyword
                    String bQuery = readString(sc,
                            "  Search books (title / author / ISBN): ");
                    lib.searchBooks(bQuery);
                    break;

                case 8:
                    // Search members by name or email
                    String mQuery = readString(sc,
                            "  Search members (name / email): ");
                    lib.searchMembers(mQuery);
                    break;

                case 9:
                    // View full details for a specific book
                    lib.listAllBooks();
                    String viewBk = readString(sc, "  Book ID: ");
                    lib.viewBook(viewBk);
                    break;

                case 10:
                    // View full profile for a specific member
                    lib.listAllMembers();
                    String viewMb = readString(sc, "  Member ID: ");
                    lib.viewMember(viewMb);
                    break;

                case 11:
                    // View transaction history — full or filtered by ID
                    System.out.println("  1. All transactions");
                    System.out.println("  2. Filter by member ID");
                    System.out.println("  3. Filter by book ID");
                    int txFilter = readInt(sc, "  Choose: ", 1, 3);
                    if (txFilter == 1) {
                        lib.transactionHistory(null);
                    } else if (txFilter == 2) {
                        lib.listAllMembers();
                        String fmId = readString(sc, "  Member ID: ");
                        lib.transactionHistory(fmId);
                    } else {
                        lib.listAllBooks();
                        String fbId = readString(sc, "  Book ID: ");
                        lib.transactionHistory(fbId);
                    }
                    break;

                case 12:
                    // Generate the overdue books report
                    lib.overdueReport();
                    break;

                case 13:
                    // Accept a fine payment from a member
                    lib.listAllMembers();
                    String payId = readString(sc, "  Member ID: ");
                    Member pm = lib.members.get(payId);
                    if (pm != null) {
                        System.out.printf("  Outstanding fines: $%.2f%n",
                                pm.totalFines);
                    }
                    double payAmt = 0;
                    System.out.print("  Amount to pay ($): ");
                    try {
                        payAmt = sc.nextDouble(); sc.nextLine();
                    } catch (InputMismatchException e) {
                        sc.nextLine();
                    }
                    lib.payFine(payId, payAmt);
                    break;

                case 14:
                    // Display library-wide statistics summary
                    lib.statistics();
                    break;

                case 15:
                    // Save all data to files
                    lib.saveAll();
                    break;

                case 16:
                    // Auto-save and exit the application
                    System.out.println("  Auto-saving...");
                    lib.saveAll();
                    running = false;
                    System.out.println("  Goodbye!");
                    break;

                default:
                    System.out.println("  Invalid option. Choose 1–16.");
            }
        }
        sc.close();
    }
}