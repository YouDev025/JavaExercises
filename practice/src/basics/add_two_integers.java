package basics;

import java.util.Scanner;

public class add_two_integers {
    public static void main(String[] args) {
        // Create Scanner object for user input
        Scanner scanner = new Scanner(System.in);

        // Variable to control the loop
        boolean continueProgram = true;

        // Welcome message
        System.out.println("=" .repeat(50));
        System.out.println("        ADD TWO INTEGERS");
        System.out.println("=" .repeat(50));
        System.out.println("This program adds two integers and displays the result.\n");

        // Main program loop using do-while (ensures at least one execution)
        do {
            // Get two integers from the user
            int num1 = getValidInteger(scanner, "first");
            int num2 = getValidInteger(scanner, "second");

            // Calculate the sum
            int sum = num1 + num2;

            // Display the result
            System.out.println("\n" + "-".repeat(40));
            System.out.println(num1 + " + " + num2 + " = " + sum);
            System.out.println("-".repeat(40));

            // Ask user if they want to continue
            continueProgram = askToContinue(scanner);

        } while (continueProgram);

        // Exit message
        System.out.println("\nThank you for using the Addition Program!");
        System.out.println("Goodbye!");

        // Close the scanner to prevent resource leak
        scanner.close();
    }

    /**
     * Gets and validates an integer input from the user
     * @param scanner Scanner object for input
     * @param position String indicating which number is being entered (first/second)
     * @return valid integer entered by user
     */
    private static int getValidInteger(Scanner scanner, String position) {
        int number;

        while (true) {
            System.out.print("Enter the " + position + " integer: ");

            // Check if input is a valid integer
            if (scanner.hasNextInt()) {
                number = scanner.nextInt();
                scanner.nextLine(); // Clear the newline character from buffer
                return number;
            } else {
                // Invalid input - not an integer
                String invalidInput = scanner.nextLine();
                System.out.println("❌ Error: '" + invalidInput + "' is not a valid integer.");
                System.out.println("   Please enter a whole number (e.g., -5, 0, 42).\n");
            }
        }
    }

    /**
     * Asks user if they want to continue or exit
     * @param scanner Scanner object for input
     * @return true if user wants to continue, false otherwise
     */
    private static boolean askToContinue(Scanner scanner) {
        String choice;

        while (true) {
            System.out.print("\nDo you want to add two more numbers? (y/n): ");
            choice = scanner.nextLine().toLowerCase();

            if (choice.equals("y") || choice.equals("yes")) {
                System.out.println(); // Add blank line for better readability
                return true;
            } else if (choice.equals("n") || choice.equals("no")) {
                return false;
            } else {
                System.out.println("❌ Invalid input! Please enter 'y' for yes or 'n' for no.");
            }
        }
    }
}