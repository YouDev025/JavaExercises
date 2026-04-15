package basics;

import java.util.Scanner;

public class rectangle_area_calculator {
    public static void main(String[] args) {
        // Create Scanner object for user input
        Scanner scanner = new Scanner(System.in);

        // Variable to control the loop
        boolean continueProgram = true;

        // Welcome message
        System.out.println("=" .repeat(50));
        System.out.println("     RECTANGLE AREA CALCULATOR");
        System.out.println("=" .repeat(50));
        System.out.println("Formula: Area = Length × Width");
        System.out.println("This program calculates the area of a rectangle.\n");

        // Main program loop using do-while (ensures at least one execution)
        do {
            // Get length and width from the user
            double length = getPositiveDouble(scanner, "length");
            double width = getPositiveDouble(scanner, "width");

            // Calculate the area
            double area = length * width;

            // Display the result
            System.out.println("\n" + "-".repeat(40));
            System.out.printf("Rectangle Dimensions: %.2f × %.2f\n", length, width);
            System.out.printf("Area: %.2f square units\n", area);
            System.out.println("-".repeat(40));

            // Ask user if they want to continue
            continueProgram = askToContinue(scanner);

        } while (continueProgram);

        // Exit message
        System.out.println("\nThank you for using the Rectangle Area Calculator!");
        System.out.println("Goodbye!");

        // Close the scanner to prevent resource leak
        scanner.close();
    }

    /**
     * Gets and validates a positive double input from the user
     * @param scanner Scanner object for input
     * @param dimensionName String indicating which dimension is being entered (length/width)
     * @return valid positive double entered by user
     */
    private static double getPositiveDouble(Scanner scanner, String dimensionName) {
        double value;

        while (true) {
            System.out.print("Enter the " + dimensionName + " of the rectangle: ");

            // Check if input is a valid number
            if (scanner.hasNextDouble()) {
                value = scanner.nextDouble();
                scanner.nextLine(); // Clear the newline character from buffer

                // Check if the number is positive
                if (value > 0) {
                    return value;
                } else if (value == 0) {
                    System.out.println("❌ Error: " + dimensionName + " cannot be zero.");
                    System.out.println("   A rectangle must have positive dimensions.\n");
                } else {
                    System.out.println("❌ Error: " + dimensionName + " cannot be negative.");
                    System.out.println("   Please enter a positive number.\n");
                }
            } else {
                // Invalid input - not a number
                String invalidInput = scanner.nextLine();
                System.out.println("❌ Error: '" + invalidInput + "' is not a valid number.");
                System.out.println("   Please enter a positive number (e.g., 5.5, 10, 3.2).\n");
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
            System.out.print("\nDo you want to calculate area for another rectangle? (y/n): ");
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