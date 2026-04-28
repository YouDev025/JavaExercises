package oop;
import java.util.Scanner;

public class BankAccount {
    private String accountNumber;
    private String accountHolder;
    private double balance;

    public BankAccount(String accountNumber, String accountHolder, double initialBalance) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = initialBalance;
    }

    public String getAccountNumber() { return accountNumber; }
    public String getAccountHolder() { return accountHolder; }
    public double getBalance() { return balance; }

    // ── Deposit ──────────────────────────────────────────────
    public void deposit(double amount) {
        if (amount <= 0) {
            System.out.println("❌ Invalid deposit amount. Must be greater than zero.");
            return;
        }
        balance += amount;
        System.out.printf("✅ Successfully deposited $%.2f%n", amount);
    }

    // ── Withdraw (with validation) ────────────────────────────
    public void withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("❌ Invalid withdrawal amount. Must be greater than zero.");
            return;
        }
        if (amount > balance) {
            System.out.printf("❌ Insufficient funds! Available balance: $%.2f%n", balance);
            return;
        }
        balance -= amount;
        System.out.printf("✅ Successfully withdrew $%.2f%n", amount);
    }

    // ── Display Balance ───────────────────────────────────────
    public void displayBalance() {
        System.out.println("\n====== Account Summary ======");
        System.out.println("Account Number : " + accountNumber);
        System.out.println("Account Holder : " + accountHolder);
        System.out.printf( "Current Balance: $%.2f%n", balance);
        System.out.println("=============================");
    }

    // ── Main ─────────────────────────────────────────────────
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Account setup
        System.out.println("╔══════════════════════════════╗");
        System.out.println("║     🏦 Bank Account Setup    ║");
        System.out.println("╚══════════════════════════════╝");

        System.out.print("Enter Account Number : ");
        String accNum = scanner.nextLine();

        System.out.print("Enter Account Holder : ");
        String holder = scanner.nextLine();

        System.out.print("Enter Initial Balance : $");
        double initialBalance = scanner.nextDouble();

        BankAccount account = new BankAccount(accNum, holder, initialBalance);
        account.displayBalance();

        // Menu loop
        boolean running = true;
        while (running) {
            System.out.println("\n╔══════════════════════════════╗");
            System.out.println("║         🏦 Main Menu         ║");
            System.out.println("╠══════════════════════════════╣");
            System.out.println("║  1. Deposit                  ║");
            System.out.println("║  2. Withdraw                 ║");
            System.out.println("║  3. Display Balance          ║");
            System.out.println("║  4. Exit                     ║");
            System.out.println("╚══════════════════════════════╝");
            System.out.print("Choose an option (1-4): ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter deposit amount: $");
                    double depositAmount = scanner.nextDouble();
                    account.deposit(depositAmount);
                    break;

                case 2:
                    System.out.print("Enter withdrawal amount: $");
                    double withdrawAmount = scanner.nextDouble();
                    account.withdraw(withdrawAmount);
                    break;

                case 3:
                    account.displayBalance();
                    break;

                case 4:
                    System.out.println("\n👋 Thank you for banking with us. Goodbye!");
                    running = false;
                    break;

                default:
                    System.out.println("❌ Invalid option. Please choose between 1 and 4.");
            }
        }

        scanner.close();
    }
}