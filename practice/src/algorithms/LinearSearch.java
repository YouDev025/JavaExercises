package algorithms;

import java.util.InputMismatchException;
import java.util.Scanner;

// Class to implement linear search to find an element in an array
// Linear search scans every element from left to right until the target is found
// Time Complexity: O(n) worst case | O(1) best case | Space Complexity: O(1)
public class LinearSearch {

    // Searches for the first occurrence of the target value in the array
    // Returns the index of the target if found, -1 otherwise
    public static int linearSearch(int[] arr, int target) {

        // Traverse every element in the array from left to right
        for (int i = 0; i < arr.length; i++) {

            // Return the index immediately when the target is found
            // This is the best case O(1) when the target is at index 0
            if (arr[i] == target) {
                return i;
            }
        }

        // Target was not found anywhere in the array
        return -1;
    }

    // Searches for ALL occurrences of the target value in the array
    // Returns an array of all indices where the target was found
    // Returns an empty array if the target does not exist
    public static int[] linearSearchAll(int[] arr, int target) {

        // Counter to track how many times the target appears in the array
        int count = 0;

        // First pass: count the total number of occurrences
        for (int num : arr) {
            if (num == target) {
                count++;
            }
        }

        // If no occurrences were found, return an empty array
        int[] indices = new int[count];

        // Second pass: record the index of every occurrence
        int pos = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == target) {
                indices[pos++] = i;
            }
        }

        return indices;
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
            int value = readInt(scanner, prompt);
            if (value > 0) {
                return value;
            }
            System.out.println("  Invalid input. Please enter a number greater than 0.");
        }
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // Read a valid positive integer for the size of the array
        int n = readPositiveInt(scanner, "Enter number of elements: ");

        // Declare an integer array of size n
        int[] arr = new int[n];

        // Loop to read n integers from the user and store them in the array
        System.out.println("Enter " + n + " elements (duplicates allowed):");
        for (int i = 0; i < n; i++) {
            arr[i] = readInt(scanner, "  Element [" + (i + 1) + "]: ");
        }

        // Display the array with indices for reference
        System.out.print("\nArray: ");
        for (int i = 0; i < n; i++) {
            System.out.print("[" + i + "]" + arr[i]);
            if (i < n - 1) System.out.print("  ");
        }
        System.out.println();

        // Interactive search loop — allows multiple searches on the same array
        boolean searching = true;

        while (searching) {
            System.out.println("\n─── Search Menu ───");
            System.out.println("1. Find first occurrence");
            System.out.println("2. Find all occurrences");
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
                    // Search for the first occurrence of the target value
                    int target1 = readInt(scanner, "Enter value to search: ");
                    int index = linearSearch(arr, target1);

                    if (index == -1) {
                        System.out.println(target1 + " not found in the array.");
                    } else {
                        System.out.println(target1 + " found at index " + index + ".");
                    }
                    break;

                case 2:
                    // Search for all occurrences of the target value
                    int target2 = readInt(scanner, "Enter value to search: ");
                    int[] indices = linearSearchAll(arr, target2);

                    if (indices.length == 0) {
                        System.out.println(target2 + " not found in the array.");
                    } else {
                        System.out.print(target2 + " found at indices: ");
                        for (int i = 0; i < indices.length; i++) {
                            System.out.print(indices[i]);
                            if (i < indices.length - 1) System.out.print(", ");
                        }
                        System.out.println();
                        System.out.println("Total occurrences: " + indices.length);
                    }
                    break;

                case 3:
                    // Exit the search loop
                    searching = false;
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