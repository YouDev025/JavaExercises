package projects;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

// Simple Banking System
// Features: multiple accounts, deposit, withdrawal, transfer, transaction history
// Concepts: OOP, ArrayList, custom exceptions, file I/O, encapsulation
public class BankingSystem {

    // ─────────────────────────────────────────────
    //  CUSTOM EXCEPTIONS
    // ─────────────────────────────────────────────

    // Thrown when a withdrawal or transfer exceeds the available balance
    static class InsufficientFundsException extends Exception {
        double amount;
        double balance;

        InsufficientFundsException(double amount, double balance) {
            super(String.format(
                    "Insufficient funds. Requested: $%.2f  Available: $%.2f",
                    amount, balance));
            this.amount  = amount;
            this.balance = balance;
        }
    }

    // Thrown when an account number is not found in the bank
    static class AccountNotFoundException extends Exception {
        AccountNotFoundException(String accountNumber) {
            super("Account not found: " + accountNumber);
        }
    }

    // Thrown when an operation is attempted on a frozen account
    static class AccountFrozenException extends Exception {
        AccountFrozenException(String accountNumber) {
            super("Account " + accountNumber + " is frozen. Contact the bank.");
        }
    }

    // Thrown when an invalid amount (zero or negative) is entered
    static class InvalidAmountException extends Exception {
        InvalidAmountException(double amount) {
            super(String.format("Invalid amount: $%.2f. Must be greater than zero.", amount));
        }
    }

    // ─────────────────────────────────────────────
    //  TRANSACTION CLASS
    // ─────────────────────────────────────────────

    // Represents a single financial event on an account
    // Every deposit, withdrawal, or transfer creates a Transaction record
    static class Transaction {

        // Possible transaction types
        enum Type { DEPOSIT, WITHDRAWAL, TRANSFER_IN, TRANSFER_OUT, OPENING_BALANCE }

        Type   type;        // The kind of transaction
        double amount;      // The amount involved in this transaction
        double balanceAfter;// The account balance immediately after this transaction
        String description; // Human-readable detail about this transaction
        String timestamp;   // When this transaction occurred

        static final DateTimeFormatter FORMATTER =
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        Transaction(Type type, double amount, double balanceAfter, String description) {
            this.type         = type;
            this.amount       = amount;
            this.balanceAfter = balanceAfter;
            this.description  = description;
            this.timestamp    = LocalDateTime.now().format(FORMATTER);
        }

        // Serializes this transaction to a pipe-delimited string for file storage
        String toFileLine() {
            return type + "|" + amount + "|" + balanceAfter
                    + "|" + description + "|" + timestamp;
        }

        // Deserializes a pipe-delimited file line back into a Transaction object
        static Transaction fromFileLine(String line) {
            String[] p = line.split("\\|", -1);
            Transaction t = new Transaction(
                    Type.valueOf(p[0]),
                    Double.parseDouble(p[1]),
                    Double.parseDouble(p[2]),
                    p[3]);
            t.timestamp = p[4];
            return t;
        }

        // Returns a formatted single-line summary of this transaction
        @Override
        public String toString() {
            String sign = (type == Type.DEPOSIT || type == Type.TRANSFER_IN
                    || type == Type.OPENING_BALANCE) ? "+" : "-";
            return String.format("  %-21s %s%-11s $%-12.2f  Balance: $%.2f",
                    timestamp, sign, type, amount, balanceAfter);
        }
    }

    // ─────────────────────────────────────────────
    //  ACCOUNT CLASS
    // ─────────────────────────────────────────────

    // Represents a bank account with full transaction history
    // Encapsulates balance manipulation — all changes go through validated methods
    static class Account {

        enum AccountType { CHECKING, SAVINGS }

        private static int counter = 1000;

        private String             accountNumber;  // Unique account identifier
        private String             ownerName;      // Full name of the account holder
        private AccountType        type;           // CHECKING or SAVINGS
        private double             balance;        // Current available balance
        private boolean            frozen;         // If true, no transactions are allowed
        private ArrayList<Transaction> history;    // Complete transaction history

        // Minimum balance requirements per account type
        static final double MIN_BALANCE_CHECKING = 0.0;
        static final double MIN_BALANCE_SAVINGS  = 50.0;

        // Constructor for creating a brand new account
        Account(String ownerName, AccountType type, double openingBalance) {
            this.accountNumber = "ACC" + (++counter);
            this.ownerName     = ownerName;
            this.type          = type;
            this.balance       = openingBalance;
            this.frozen        = false;
            this.history       = new ArrayList<>();

            // Record the opening deposit in the transaction history
            history.add(new Transaction(
                    Transaction.Type.OPENING_BALANCE,
                    openingBalance, openingBalance,
                    "Account opened"));
        }

        // Constructor for loading an account from a save file
        Account(String accountNumber, String ownerName, AccountType type,
                double balance, boolean frozen,
                ArrayList<Transaction> history) {
            this.accountNumber = accountNumber;
            this.ownerName     = ownerName;
            this.type          = type;
            this.balance       = balance;
            this.frozen        = frozen;
            this.history       = history;

            // Keep the counter ahead of any loaded account numbers
            int num = Integer.parseInt(accountNumber.replace("ACC", ""));
            if (num >= counter) counter = num;
        }

        // ── GETTERS ──

        public String      getAccountNumber() { return accountNumber; }
        public String      getOwnerName()     { return ownerName; }
        public AccountType getType()          { return type; }
        public double      getBalance()       { return balance; }
        public boolean     isFrozen()         { return frozen; }
        public ArrayList<Transaction> getHistory() { return history; }

        // ── DEPOSIT ──

        // Adds the given amount to the balance and records the transaction
        void deposit(double amount, String note)
                throws InvalidAmountException, AccountFrozenException {

            // Reject zero or negative deposit amounts
            if (amount <= 0) throw new InvalidAmountException(amount);

            // Reject any transaction on a frozen account
            if (frozen) throw new AccountFrozenException(accountNumber);

            balance += amount;
            history.add(new Transaction(
                    Transaction.Type.DEPOSIT, amount, balance,
                    "Deposit" + (note.isEmpty() ? "" : ": " + note)));
        }

        // ── WITHDRAW ──

        // Deducts the given amount from the balance if funds and rules allow it
        void withdraw(double amount, String note)
                throws InvalidAmountException,
                AccountFrozenException,
                InsufficientFundsException {

            if (amount <= 0) throw new InvalidAmountException(amount);
            if (frozen)      throw new AccountFrozenException(accountNumber);

            // Savings accounts must maintain a minimum balance
            double minBalance = (type == AccountType.SAVINGS)
                    ? MIN_BALANCE_SAVINGS : MIN_BALANCE_CHECKING;

            if (balance - amount < minBalance) {
                throw new InsufficientFundsException(amount, balance - minBalance);
            }

            balance -= amount;
            history.add(new Transaction(
                    Transaction.Type.WITHDRAWAL, amount, balance,
                    "Withdrawal" + (note.isEmpty() ? "" : ": " + note)));
        }

        // ── INTERNAL TRANSFER CREDIT ──

        // Credits an incoming transfer amount — called on the receiving account
        void creditTransfer(double amount, String fromAccount)
                throws InvalidAmountException, AccountFrozenException {

            if (amount <= 0) throw new InvalidAmountException(amount);
            if (frozen)      throw new AccountFrozenException(accountNumber);

            balance += amount;
            history.add(new Transaction(
                    Transaction.Type.TRANSFER_IN, amount, balance,
                    "Transfer from " + fromAccount));
        }

        // ── INTERNAL TRANSFER DEBIT ──

        // Debits an outgoing transfer amount — called on the sending account
        void debitTransfer(double amount, String toAccount)
                throws InvalidAmountException,
                AccountFrozenException,
                InsufficientFundsException {

            if (amount <= 0) throw new InvalidAmountException(amount);
            if (frozen)      throw new AccountFrozenException(accountNumber);

            double minBalance = (type == AccountType.SAVINGS)
                    ? MIN_BALANCE_SAVINGS : MIN_BALANCE_CHECKING;

            if (balance - amount < minBalance) {
                throw new InsufficientFundsException(amount, balance - minBalance);
            }

            balance -= amount;
            history.add(new Transaction(
                    Transaction.Type.TRANSFER_OUT, amount, balance,
                    "Transfer to " + toAccount));
        }

        // ── FREEZE / UNFREEZE ──

        void freeze()   { this.frozen = true;  }
        void unfreeze() { this.frozen = false; }

        // ── SERIALIZATION ──

        // Serializes the account header line for file storage
        // Full transaction history is stored in separate lines
        String toFileLine() {
            return accountNumber + "|" + ownerName + "|" + type
                    + "|" + balance + "|" + frozen;
        }

        // ── DISPLAY SUMMARY ──

        // Returns a formatted one-line summary of this account
        @Override
        public String toString() {
            String status = frozen ? " [FROZEN]" : "";
            return String.format("  %-12s %-24s %-10s $%-14.2f%s",
                    accountNumber, ownerName, type, balance, status);
        }
    }

    // ─────────────────────────────────────────────
    //  BANK CLASS
    // ─────────────────────────────────────────────

    // Core banking engine managing all accounts and inter-account operations
    // Handles account creation, retrieval, transfers, and file persistence
    static class Bank {

        private String                     bankName;    // Name of this bank
        private HashMap<String, Account>   accounts;    // Keyed by account number
        private ArrayList<String>          orderList;   // Maintains insertion order
        private static final String        FILE_PATH = "bank_data.txt";

        Bank(String bankName) {
            this.bankName  = bankName;
            this.accounts  = new HashMap<>();
            this.orderList = new ArrayList<>();
        }

        // ── OPEN ACCOUNT ──

        // Creates a new account and registers it with the bank
        Account openAccount(String ownerName, Account.AccountType type,
                            double openingBalance)
                throws InvalidAmountException {

            // Enforce minimum opening deposit per account type
            double minOpening = (type == Account.AccountType.SAVINGS) ? 50.0 : 0.0;
            if (openingBalance < minOpening) {
                throw new InvalidAmountException(openingBalance);
            }

            Account account = new Account(ownerName, type, openingBalance);
            accounts.put(account.getAccountNumber(), account);
            orderList.add(account.getAccountNumber());
            return account;
        }

        // ── CLOSE ACCOUNT ──

        // Removes an account from the bank after confirming zero balance
        void closeAccount(String accountNumber)
                throws AccountNotFoundException, InsufficientFundsException {

            Account account = getAccount(accountNumber);

            // Do not close an account that still has funds in it
            if (account.getBalance() > 0) {
                throw new InsufficientFundsException(account.getBalance(), 0);
            }

            accounts.remove(accountNumber);
            orderList.remove(accountNumber);
            System.out.println("  Account " + accountNumber + " closed.");
        }

        // ── GET ACCOUNT (with validation) ──

        // Retrieves an account by number — throws exception if not found
        Account getAccount(String accountNumber) throws AccountNotFoundException {
            Account account = accounts.get(accountNumber);
            if (account == null) throw new AccountNotFoundException(accountNumber);
            return account;
        }

        // ── DEPOSIT ──

        void deposit(String accountNumber, double amount, String note)
                throws AccountNotFoundException,
                InvalidAmountException,
                AccountFrozenException {

            Account account = getAccount(accountNumber);
            account.deposit(amount, note);
            System.out.printf("  ✓ Deposited $%.2f to %s  |  New balance: $%.2f%n",
                    amount, accountNumber, account.getBalance());
        }

        // ── WITHDRAW ──

        void withdraw(String accountNumber, double amount, String note)
                throws AccountNotFoundException,
                InvalidAmountException,
                AccountFrozenException,
                InsufficientFundsException {

            Account account = getAccount(accountNumber);
            account.withdraw(amount, note);
            System.out.printf("  ✓ Withdrew $%.2f from %s  |  New balance: $%.2f%n",
                    amount, accountNumber, account.getBalance());
        }

        // ── TRANSFER ──

        // Atomically moves funds from one account to another
        // If any step fails, no money is moved — both accounts stay consistent
        void transfer(String fromNumber, String toNumber, double amount)
                throws AccountNotFoundException,
                InvalidAmountException,
                AccountFrozenException,
                InsufficientFundsException {

            Account from = getAccount(fromNumber);
            Account to   = getAccount(toNumber);

            // Debit the sender — may throw InsufficientFundsException
            // This is done before crediting to ensure atomicity
            from.debitTransfer(amount, toNumber);

            // Credit the receiver — both records are updated together
            try {
                to.creditTransfer(amount, fromNumber);
            } catch (Exception e) {
                // If crediting fails, roll back the debit to maintain consistency
                from.balance += amount;
                from.getHistory().remove(from.getHistory().size() - 1);
                throw new AccountFrozenException(toNumber);
            }

            System.out.printf(
                    "  ✓ Transferred $%.2f from %s to %s%n"
                            + "    %s new balance: $%.2f%n"
                            + "    %s new balance: $%.2f%n",
                    amount, fromNumber, toNumber,
                    fromNumber, from.getBalance(),
                    toNumber,   to.getBalance());
        }

        // ── PRINT TRANSACTION HISTORY ──

        // Displays the complete transaction history for a given account
        void printHistory(String accountNumber) throws AccountNotFoundException {

            Account account = getAccount(accountNumber);
            ArrayList<Transaction> history = account.getHistory();

            System.out.println("\n" + "═".repeat(70));
            System.out.println("  Transaction History — " + accountNumber
                    + " (" + account.getOwnerName() + ")");
            System.out.println("═".repeat(70));

            if (history.isEmpty()) {
                System.out.println("  No transactions yet.");
            } else {
                for (Transaction t : history) {
                    System.out.println(t);
                }
            }

            System.out.println("─".repeat(70));
            System.out.printf("  Current Balance: $%.2f%n", account.getBalance());
            System.out.println("═".repeat(70));
        }

        // ── LIST ALL ACCOUNTS ──

        void listAccounts() {
            if (accounts.isEmpty()) {
                System.out.println("  No accounts registered.");
                return;
            }
            System.out.println("\n" + "═".repeat(66));
            System.out.println("  " + bankName + " — All Accounts");
            System.out.printf("  %-12s %-24s %-10s %-14s%n",
                    "Account", "Owner", "Type", "Balance");
            System.out.println("─".repeat(66));
            for (String num : orderList) {
                System.out.println(accounts.get(num));
            }
            System.out.println("─".repeat(66));

            double total = accounts.values().stream()
                    .mapToDouble(Account::getBalance).sum();
            System.out.printf("  Total deposits held: $%.2f%n", total);
            System.out.println("═".repeat(66));
        }

        // ── FREEZE / UNFREEZE ACCOUNT ──

        void freezeAccount(String accountNumber) throws AccountNotFoundException {
            getAccount(accountNumber).freeze();
            System.out.println("  Account " + accountNumber + " has been frozen.");
        }

        void unfreezeAccount(String accountNumber) throws AccountNotFoundException {
            getAccount(accountNumber).unfreeze();
            System.out.println("  Account " + accountNumber + " has been unfrozen.");
        }

        // ── ACCOUNT STATEMENT ──

        // Prints a mini bank statement for one account
        void printStatement(String accountNumber) throws AccountNotFoundException {

            Account account = getAccount(accountNumber);

            // Compute totals from transaction history
            double totalDeposited  = 0;
            double totalWithdrawn  = 0;
            int    txCount         = 0;

            for (Transaction t : account.getHistory()) {
                if (t.type == Transaction.Type.DEPOSIT
                        || t.type == Transaction.Type.TRANSFER_IN) {
                    totalDeposited += t.amount;
                } else if (t.type == Transaction.Type.WITHDRAWAL
                        || t.type == Transaction.Type.TRANSFER_OUT) {
                    totalWithdrawn += t.amount;
                }
                txCount++;
            }

            System.out.println("\n" + "═".repeat(50));
            System.out.println("  ACCOUNT STATEMENT");
            System.out.println("═".repeat(50));
            System.out.println("  Account:       " + account.getAccountNumber());
            System.out.println("  Owner:         " + account.getOwnerName());
            System.out.println("  Type:          " + account.getType());
            System.out.println("  Status:        "
                    + (account.isFrozen() ? "FROZEN" : "Active"));
            System.out.println("─".repeat(50));
            System.out.printf ("  Current Balance:   $%.2f%n", account.getBalance());
            System.out.printf ("  Total Deposited:   $%.2f%n", totalDeposited);
            System.out.printf ("  Total Withdrawn:   $%.2f%n", totalWithdrawn);
            System.out.println("  Transactions:      " + txCount);
            System.out.println("═".repeat(50));
        }

        // ── SAVE TO FILE ──

        void saveToFile() {
            try (BufferedWriter writer = new BufferedWriter(
                    new FileWriter(FILE_PATH))) {

                for (String num : orderList) {
                    Account acc = accounts.get(num);

                    // Write account header line
                    writer.write("ACCOUNT:" + acc.toFileLine());
                    writer.newLine();

                    // Write each transaction on its own line under the account
                    for (Transaction t : acc.getHistory()) {
                        writer.write("TX:" + t.toFileLine());
                        writer.newLine();
                    }

                    // Separator between accounts for readability
                    writer.write("---");
                    writer.newLine();
                }

                System.out.println("  Saved " + accounts.size()
                        + " account(s) to \"" + FILE_PATH + "\".");

            } catch (IOException e) {
                System.out.println("  Save error: " + e.getMessage());
            }
        }

        // ── LOAD FROM FILE ──

        void loadFromFile() {
            File file = new File(FILE_PATH);
            if (!file.exists()) {
                System.out.println("  No save file found. Starting fresh.");
                return;
            }

            accounts.clear();
            orderList.clear();

            try (BufferedReader reader = new BufferedReader(
                    new FileReader(FILE_PATH))) {

                String  line;
                Account current = null;
                int     loaded  = 0;

                while ((line = reader.readLine()) != null) {

                    if (line.startsWith("ACCOUNT:")) {
                        // Parse the account header
                        String[]           parts   = line.substring(8).split("\\|", -1);
                        String             num     = parts[0];
                        String             owner   = parts[1];
                        Account.AccountType type   = Account.AccountType.valueOf(parts[2]);
                        double             bal     = Double.parseDouble(parts[3]);
                        boolean            frozen  = Boolean.parseBoolean(parts[4]);
                        current = new Account(num, owner, type, bal, frozen,
                                new ArrayList<>());
                        accounts.put(num, current);
                        orderList.add(num);
                        loaded++;

                    } else if (line.startsWith("TX:") && current != null) {
                        // Parse a transaction belonging to the current account
                        current.getHistory().add(
                                Transaction.fromFileLine(line.substring(3)));

                    } else if (line.equals("---")) {
                        // Account block ended — move to the next one
                        current = null;
                    }
                }

                System.out.println("  Loaded " + loaded
                        + " account(s) from \"" + FILE_PATH + "\".");

            } catch (IOException e) {
                System.out.println("  Load error: " + e.getMessage());
            }
        }

        // Returns true if there is at least one registered account
        boolean hasAccounts() { return !accounts.isEmpty(); }
    }

    // ─────────────────────────────────────────────
    //  INPUT HELPERS
    // ─────────────────────────────────────────────

    static int readInt(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                int val = scanner.nextInt();
                scanner.nextLine();
                return val;
            } catch (InputMismatchException e) {
                scanner.nextLine();
                System.out.println("  Invalid input. Please enter a whole number.");
            }
        }
    }

    static double readDouble(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                double val = scanner.nextDouble();
                scanner.nextLine();
                return val;
            } catch (InputMismatchException e) {
                scanner.nextLine();
                System.out.println("  Invalid input. Please enter a number.");
            }
        }
    }

    static String readString(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String val = scanner.nextLine().trim();
            if (!val.isEmpty()) return val;
            System.out.println("  Invalid input. Cannot be empty.");
        }
    }

    // ─────────────────────────────────────────────
    //  MAIN MENU
    // ─────────────────────────────────────────────

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Bank    bank    = new Bank("JavaBank");

        System.out.println("═".repeat(60));
        System.out.println("         SIMPLE BANKING SYSTEM — JavaBank");
        System.out.println("═".repeat(60));
        bank.loadFromFile();

        boolean running = true;

        while (running) {
            System.out.println("\n─── Main Menu ───");
            System.out.println(" 1.  Open new account");
            System.out.println(" 2.  Close account");
            System.out.println(" 3.  Deposit");
            System.out.println(" 4.  Withdraw");
            System.out.println(" 5.  Transfer between accounts");
            System.out.println(" 6.  View transaction history");
            System.out.println(" 7.  View account statement");
            System.out.println(" 8.  List all accounts");
            System.out.println(" 9.  Freeze account");
            System.out.println(" 10. Unfreeze account");
            System.out.println(" 11. Save data");
            System.out.println(" 12. Load data");
            System.out.println(" 13. Exit");
            System.out.print("Choose an option: ");

            int choice;
            try {
                choice = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                scanner.nextLine();
                System.out.println("  Invalid option. Please enter 1–13.");
                continue;
            }

            try {
                switch (choice) {

                    case 1:
                        // Open a new checking or savings account
                        String ownerName = readString(scanner, "  Account holder name: ");
                        System.out.println("  Account type: 1=CHECKING  2=SAVINGS");
                        int typeChoice   = readInt(scanner, "  Choose: ");
                        Account.AccountType accType = (typeChoice == 2)
                                ? Account.AccountType.SAVINGS
                                : Account.AccountType.CHECKING;
                        double opening = readDouble(scanner,
                                "  Opening deposit ($): ");
                        Account newAcc = bank.openAccount(ownerName, accType, opening);
                        System.out.printf(
                                "  ✓ Account opened!%n"
                                        + "  Account Number: %s%n"
                                        + "  Type: %s  |  Balance: $%.2f%n",
                                newAcc.getAccountNumber(), accType, opening);
                        break;

                    case 2:
                        // Close an account (must have zero balance)
                        bank.listAccounts();
                        String closeNum = readString(scanner, "  Account number to close: ");
                        bank.closeAccount(closeNum);
                        break;

                    case 3:
                        // Deposit funds into an account
                        bank.listAccounts();
                        String depNum  = readString(scanner, "  Account number: ");
                        double depAmt  = readDouble(scanner,  "  Amount to deposit ($): ");
                        String depNote = readString(scanner,  "  Note (or press Enter): ");
                        bank.deposit(depNum, depAmt, depNote);
                        break;

                    case 4:
                        // Withdraw funds from an account
                        bank.listAccounts();
                        String withNum  = readString(scanner, "  Account number: ");
                        double withAmt  = readDouble(scanner,  "  Amount to withdraw ($): ");
                        String withNote = readString(scanner,  "  Note (or press Enter): ");
                        bank.withdraw(withNum, withAmt, withNote);
                        break;

                    case 5:
                        // Transfer funds between two accounts
                        bank.listAccounts();
                        String fromNum   = readString(scanner, "  From account number: ");
                        String toNum     = readString(scanner, "  To account number:   ");
                        double transAmt  = readDouble(scanner, "  Amount to transfer ($): ");
                        bank.transfer(fromNum, toNum, transAmt);
                        break;

                    case 6:
                        // Display full transaction history for an account
                        bank.listAccounts();
                        String histNum = readString(scanner, "  Account number: ");
                        bank.printHistory(histNum);
                        break;

                    case 7:
                        // Print a summary statement for an account
                        bank.listAccounts();
                        String stmtNum = readString(scanner, "  Account number: ");
                        bank.printStatement(stmtNum);
                        break;

                    case 8:
                        // List all accounts registered in the bank
                        bank.listAccounts();
                        break;

                    case 9:
                        // Freeze an account to block all transactions
                        bank.listAccounts();
                        String freezeNum = readString(scanner, "  Account number to freeze: ");
                        bank.freezeAccount(freezeNum);
                        break;

                    case 10:
                        // Unfreeze a previously frozen account
                        bank.listAccounts();
                        String unfreezeNum = readString(scanner,
                                "  Account number to unfreeze: ");
                        bank.unfreezeAccount(unfreezeNum);
                        break;

                    case 11:
                        // Persist all account data to file
                        bank.saveToFile();
                        break;

                    case 12:
                        // Reload all account data from file
                        bank.loadFromFile();
                        break;

                    case 13:
                        // Auto-save and exit
                        System.out.println("  Auto-saving...");
                        bank.saveToFile();
                        running = false;
                        System.out.println("  Thank you for banking with JavaBank!");
                        break;

                    default:
                        System.out.println("  Invalid option. Please choose 1–13.");
                }

                // ── EXCEPTION HANDLERS ──

            } catch (InsufficientFundsException e) {
                System.out.println("  ✗ " + e.getMessage());
            } catch (AccountNotFoundException e) {
                System.out.println("  ✗ " + e.getMessage());
            } catch (AccountFrozenException e) {
                System.out.println("  ✗ " + e.getMessage());
            } catch (InvalidAmountException e) {
                System.out.println("  ✗ " + e.getMessage());
            }
        }

        scanner.close();
    }
}