package data_struct;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

// Class to reverse an ArrayList manually without using Collections.reverse()
// Uses a two-pointer approach to swap elements from both ends toward the center
// Time Complexity: O(n) | Space Complexity: O(1)
public class ReverseArrayList {

    // Method to reverse the ArrayList in-place using two pointers
    // No extra list is created — elements are swapped within the same list
    public static void reverse(ArrayList<Integer> list) {

        // Left pointer starts at the beginning of the list
        // Right pointer starts at the end of the list
        int left = 0;
        int right = list.size() - 1;

        // Continue swapping until the two pointers meet in the middle
        while (left < right) {

            // Store the left element temporarily before overwriting it
            int temp = list.get(left);

            // Place the right element at the left position
            list.set(left, list.get(right));

            // Place the stored left element at the right position
            list.set(right, temp);

            // Move both pointers one step toward the center
            left++;
            right--;
        }
    }

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

    // Helper method to read a valid positive integer greater than zero
    // Rejects non-integers and values that are zero or negative
    public static int readPositiveInt(Scanner scanner, String prompt) {
        while (true) {
            try {
                int value = readInt(scanner, prompt);

                // Reject zero or negative values as they are not valid sizes
                if (value <= 0) {
                    System.out.println("  Invalid input. Please enter a number greater than 0.");
                    continue;
                }

                return value;

            } catch (InputMismatchException e) {
                // Clear the invalid token from the scanner buffer before retrying
                scanner.nextLine();
                System.out.println("  Invalid input. Please enter a whole number greater than 0.");
            }
        }
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // Create a new ArrayList to hold integer elements
        ArrayList<Integer> list = new ArrayList<>();

        // Read a valid positive integer for the number of elements
        int n = readPositiveInt(scanner, "Enter number of elements: ");

        // Loop to read n integers from the user with validation on each entry
        System.out.println("Enter " + n + " integers:");
        for (int i = 0; i < n; i++) {
            // Keep prompting for each position until a valid integer is entered
            int element = readInt(scanner, "  Element [" + (i + 1) + "]: ");
            list.add(element);
        }

        // Display the original list before reversal
        System.out.println("\nOriginal list: " + list);

        // Reverse the list using the two-pointer method
        reverse(list);

        // Display the list after reversal
        System.out.println("Reversed list: " + list);

        // Close the scanner to release system resources
        scanner.close();
    }
}