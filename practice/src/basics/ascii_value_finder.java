package basics;

import java.util.Scanner;

public class ascii_value_finder {
    public static void main(String[] args) {
        // Create Scanner object for user input
        Scanner scanner = new Scanner(System.in);

        // Variable to control the loop
        boolean continueProgram = true;

        // Welcome message
        System.out.println("=" .repeat(50));
        System.out.println("     ASCII VALUE FINDER");
        System.out.println("=" .repeat(50));
        System.out.println("This program finds the ASCII value of any character.\n");

        // Main program loop using do-while (ensures at least one execution)
        do {
            // Get valid character input from user
            char userChar = getValidCharacter(scanner);

            // Convert char to ASCII value using type casting
            int asciiValue = (int) userChar;

            // Display the result
            System.out.println("\n" + "-".repeat(40));
            System.out.println("Character: '" + userChar + "'");
            System.out.println("ASCII Value: " + asciiValue);
            System.out.println("-".repeat(40));

            // Ask user if they want to continue
            continueProgram = askToContinue(scanner);

        } while (continueProgram);

        // Exit message
        System.out.println("\nThank you for using ASCII Value Finder!");
        System.out.println("Goodbye!");

        // Close the scanner to prevent resource leak
        scanner.close();
    }

    /**
     * Gets and validates a single character input from the user
     * @param scanner Scanner object for input
     * @return valid single character
     */
    private static char getValidCharacter(Scanner scanner) {
        String input;
        char userChar;

        while (true) {
            System.out.print("\nEnter a character: ");
            input = scanner.nextLine();

            // Check if input is empty
            if (input.isEmpty()) {
                System.out.println("❌ Error: Please enter a character (not empty).");
                continue;
            }

            // Check if input is exactly one character
            if (input.length() != 1) {
                System.out.println("❌ Error: Please enter ONLY ONE character.");
                System.out.println("   You entered " + input.length() + " characters: '" + input + "'");
                continue;
            }

            // Valid input found
            userChar = input.charAt(0);
            break;
        }

        return userChar;
    }

    /**
     * Asks user if they want to continue or exit
     * @param scanner Scanner object for input
     * @return true if user wants to continue, false otherwise
     */
    private static boolean askToContinue(Scanner scanner) {
        String choice;

        while (true) {
            System.out.print("\nDo you want to find ASCII value for another character? (y/n): ");
            choice = scanner.nextLine().toLowerCase();

            if (choice.equals("y") || choice.equals("yes")) {
                return true;
            } else if (choice.equals("n") || choice.equals("no")) {
                return false;
            } else {
                System.out.println("❌ Invalid input! Please enter 'y' for yes or 'n' for no.");
            }
        }
    }
}