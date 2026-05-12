package algorithms;

import java.util.InputMismatchException;
import java.util.Scanner;

// Class to implement insertion sort for both integers and strings
// Insertion sort builds the sorted portion one element at a time
// by picking each element and inserting it into its correct position
// Time Complexity: O(n²) worst/average case | O(n) best case | Space Complexity: O(1)
public class InsertionSort {

    // ─────────────────────────────────────────────
    //  INTEGER INSERTION SORT
    // ─────────────────────────────────────────────

    // Sorts an integer array in ascending order using insertion sort
    // Prints the array state after each element is inserted into position
    public static void insertionSortInt(int[] arr) {

        int n = arr.length;

        // Outer loop picks one element at a time starting from index 1
        // Index 0 is trivially sorted — a single element is always in order
        for (int i = 1; i < n; i++) {

            // Store the current element to be inserted into the sorted portion
            int key = arr[i];

            // Start comparing from the last element of the sorted portion
            int j = i - 1;

            // Shift all sorted elements that are greater than key one position to the right
            // This creates the gap where key will be inserted
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
            }

            // Insert key into its correct position in the sorted portion
            arr[j + 1] = key;

            // Print the array state after inserting this element
            System.out.print("Pass " + i + " (inserted " + key + "): ");
            printIntArray(arr);
            System.out.println();
        }
    }

    // ─────────────────────────────────────────────
    //  STRING INSERTION SORT  ⋆ Challenge
    // ─────────────────────────────────────────────

    // Sorts a String array in alphabetical order using insertion sort
    // Uses compareTo() for lexicographic comparison between strings
    // Prints the array state after each string is inserted into position
    public static void insertionSortString(String[] arr) {

        int n = arr.length;

        // Outer loop picks one string at a time starting from index 1
        for (int i = 1; i < n; i++) {

            // Store the current string to be inserted into the sorted portion
            String key = arr[i];

            // Start comparing from the last element of the sorted portion
            int j = i - 1;

            // Shift all sorted strings that come after key alphabetically one position to the right
            // compareTo() returns positive if arr[j] comes after key alphabetically
            while (j >= 0 && arr[j].compareToIgnoreCase(key) > 0) {
                arr[j + 1] = arr[j];
                j--;
            }

            // Insert key into its correct alphabetical position
            arr[j + 1] = key;

            // Print the array state after inserting this string
            System.out.print("Pass " + i + " (inserted \"" + key + "\"): ");
            printStringArray(arr);
            System.out.println();
        }
    }

    // ─────────────────────────────────────────────
    //  PRINT HELPERS
    // ─────────────────────────────────────────────

    // Prints all elements of an integer array on one line
    public static void printIntArray(int[] arr) {
        System.out.print("[");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]);
            if (i < arr.length - 1) System.out.print(", ");
        }
        System.out.print("]");
    }

    // Prints all elements of a String array on one line
    public static void printStringArray(String[] arr) {
        System.out.print("[");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]);
            if (i < arr.length - 1) System.out.print(", ");
        }
        System.out.print("]");
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

    // Helper method to read a non-empty string from the user
    // Rejects blank input and prompts the user to re-enter
    public static String readString(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = scanner.nextLine().trim();
            if (!value.isEmpty()) {
                return value;
            }
            System.out.println("  Invalid input. Please enter a non-empty string.");
        }
    }

    // ─────────────────────────────────────────────
    //  MAIN MENU
    // ─────────────────────────────────────────────

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        boolean running = true;

        while (running) {
            System.out.println("\n─── Insertion Sort Menu ───");
            System.out.println("1. Sort integers");
            System.out.println("2. Sort strings");
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
                    // ── Integer sort ──
                    int n = readPositiveInt(scanner, "\nEnter number of integers: ");
                    int[] intArr = new int[n];

                    System.out.println("Enter " + n + " integers:");
                    for (int i = 0; i < n; i++) {
                        intArr[i] = readInt(scanner, "  Element [" + (i + 1) + "]: ");
                    }

                    // Display original array before sorting
                    System.out.print("\nOriginal array: ");
                    printIntArray(intArr);
                    System.out.println();

                    // Run insertion sort and print state after each pass
                    System.out.println("\n─── Sorting Steps ───");
                    insertionSortInt(intArr);

                    // Display the final sorted integer array
                    System.out.print("\nSorted array:   ");
                    printIntArray(intArr);
                    System.out.println();
                    break;

                case 2:
                    // ── String sort ──
                    int m = readPositiveInt(scanner, "\nEnter number of strings: ");
                    String[] strArr = new String[m];

                    // Consume the leftover newline from nextInt() before reading strings
                    scanner.nextLine();

                    System.out.println("Enter " + m + " strings:");
                    for (int i = 0; i < m; i++) {
                        strArr[i] = readString(scanner, "  String [" + (i + 1) + "]: ");
                    }

                    // Display original array before sorting
                    System.out.print("\nOriginal array: ");
                    printStringArray(strArr);
                    System.out.println();

                    // Run insertion sort on strings and print state after each pass
                    System.out.println("\n─── Sorting Steps ───");
                    insertionSortString(strArr);

                    // Display the final sorted string array
                    System.out.print("\nSorted array:   ");
                    printStringArray(strArr);
                    System.out.println();
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