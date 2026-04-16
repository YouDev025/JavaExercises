package basics;

import java.util.Scanner;

public class leap_year_checker {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        char choice;

        do {
            System.out.println("=== Leap Year Checker ===");

            try {
                System.out.print("Enter a year: ");
                int year = scanner.nextInt();

                boolean isLeap;

                if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) {
                    isLeap = true;
                } else {
                    isLeap = false;
                }

                if (isLeap) {
                    System.out.println(year + " is a LEAP year.");
                } else {
                    System.out.println(year + " is NOT a leap year.");
                }

            } catch (Exception e) {
                System.out.println(" Invalid input! Please enter a valid integer year.");
                scanner.nextLine(); // clear buffer
            }

            System.out.print("\nDo you want to check another year? (y/n): ");
            choice = scanner.next().charAt(0);

            System.out.println();

        } while (choice == 'y' || choice == 'Y');

        System.out.println("Program exited. Goodbye!");
        scanner.close();
    }
}
