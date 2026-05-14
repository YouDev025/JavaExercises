package algorithms;

import java.util.InputMismatchException;
import java.util.Scanner;

// Class to compute the sum of digits of a number using recursion
// Repeatedly extracts the last digit and adds it to the sum of the remaining digits
// Time Complexity: O(d) where d is the number of digits | Space Complexity: O(d)
public class SumOfDigits {

    // ─────────────────────────────────────────────
    //  RECURSIVE METHOD
    // ─────────────────────────────────────────────

    // Recursively computes the sum of all digits of the given number
    // Negative numbers are handled by converting to positive before processing
    public static int sumOfDigits(int n) {

        // Ensure the number is positive so digit extraction works correctly
        // The sign of a number does not affect its digit sum
        n = Math.abs(n);

        // Base case: a single digit number is its own digit sum
        if (n < 10) {
            return n;
        }

        // Recursive case: extract the last digit with n % 10
        // then recursively compute the sum of the remaining digits with n / 10
        // Example: sumOfDigits(456) = 6 + sumOfDigits(45)
        //                           = 6 + 5 + sumOfDigits(4)
        //                           = 6 + 5 + 4 = 15
        return (n % 10) + sumOfDigits(n / 10);
    }

    // ─────────────────────────────────────────────
    //  VERBOSE RECURSIVE METHOD
    // ─────────────────────────────────────────────

    // Computes the sum of digits recursively while printing each call
    // Visualizes how the call stack builds and unwinds at each recursion level
    public static int sumOfDigitsVerbose(int n, int depth) {

        // Ensure the number is positive
        n = Math.abs(n);

        String indent = "  ".repeat(depth);

        // Base case: single digit — return it directly and show the return
        if (n < 10) {
            System.out.println(indent + "sumOfDigits(" + n + ") = " + n + "  ← base case");
            return n;
        }

        int lastDigit      = n % 10;
        int remainingDigits = n / 10;

        // Print the current call showing how it splits into last digit + recursive call
        System.out.println(indent + "sumOfDigits(" + n + ") = "
                + lastDigit + " + sumOfDigits(" + remainingDigits + ")");

        // Recurse on the remaining digits after removing the last digit
        int result = lastDigit + sumOfDigitsVerbose(remainingDigits, depth + 1);

        // Print the resolved return value as the call stack unwinds
        System.out.println(indent + "sumOfDigits(" + n + ") = " + result);

        return result;
    }

    // ─────────────────────────────────────────────
    //  INPUT HELPERS
    // ─────────────────────────────────────────────

    // Helper method to read a valid integer from the user
    // Keeps prompting until the user enters a proper integer value
    public static int readInt(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                int value = scanner.nextInt();
                return value;
            } catch (InputMismatchException e) {
                // Clear the invalid token from the scanner buffer before retrying
                scanner.nextLine();
                System.out.println("  Invalid input. Please enter a whole number.");
            }
        }
    }

    // ─────────────────────────────────────────────
    //  MAIN
    // ─────────────────────────────────────────────

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n─── Sum of Digits Menu ───");
            System.out.println("1. Compute sum of digits");
            System.out.println("2. Compute with step-by-step trace");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            int choice;

            try {
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                // Clear the invalid token and prompt the user to try again
                scanner.nextLine();
                System.out.println("  Invalid option. Please enter 1, 2, or 3.");
                continue;
            }

            switch (choice) {

                case 1:
                    // Read the number and compute its digit sum directly
                    int number = readInt(scanner, "Enter a number: ");
                    int result = sumOfDigits(number);
                    System.out.println("Sum of digits of " + number + " = " + result);
                    break;

                case 2:
                    // Read the number and show every recursive call and return value
                    int verboseNumber = readInt(scanner, "Enter a number: ");
                    System.out.println("\n─── Recursive Trace ───");
                    int verboseResult = sumOfDigitsVerbose(Math.abs(verboseNumber), 0);
                    System.out.println("\nSum of digits of " + verboseNumber + " = " + verboseResult);
                    break;

                case 3:
                    // Exit the menu loop
                    running = false;
                    System.out.println("Exiting.");
                    break;

                default:
                    System.out.println("  Invalid option. Please choose 1, 2, or 3.");
            }
        }

        // Close the scanner to release system resources
        scanner.close();
    }
}