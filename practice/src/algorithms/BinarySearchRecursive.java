package algorithms;

import java.util.InputMismatchException;
import java.util.Scanner;

// Class to implement recursive binary search on a sorted array
// Each recursive call narrows the search range by half until the target is found
// Time Complexity: O(log n) | Space Complexity: O(log n) due to recursive call stack
public class BinarySearchRecursive {

    // Searches for the target value in a sorted array using recursion
    // Returns the index of the target if found, -1 otherwise
    // low  — the starting index of the current search range
    // high — the ending index of the current search range
    public static int binarySearch(int[] arr, int low, int high, int target) {

        // Base case: search range is exhausted — target is not in the array
        // This happens when low crosses high after repeated halving
        if (low > high) {
            return -1;
        }

        // Calculate the middle index of the current search range
        // Using low + (high - low) / 2 prevents integer overflow
        int mid = low + (high - low) / 2;

        // Base case: target found at the middle index — return immediately
        if (arr[mid] == target) {
            return mid;
        }

        // Recursive case: target is greater than the middle element
        // Discard the left half and search only the right half
        if (arr[mid] < target) {
            return binarySearch(arr, mid + 1, high, target);
        }

        // Recursive case: target is less than the middle element
        // Discard the right half and search only the left half
        return binarySearch(arr, low, mid - 1, target);
    }

    // Searches for the FIRST occurrence of a duplicate target value using recursion
    // Returns the index of the first occurrence, -1 if not found
    public static int binarySearchFirst(int[] arr, int low, int high, int target, int result) {

        // Base case: search range exhausted — return the last recorded result
        if (low > high) {
            return result;
        }

        int mid = low + (high - low) / 2;

        if (arr[mid] == target) {

            // Record this index as a potential first occurrence
            // then keep searching the left half for an earlier match
            result = mid;
            return binarySearchFirst(arr, low, mid - 1, target, result);

        } else if (arr[mid] < target) {

            // Target is in the right half
            return binarySearchFirst(arr, mid + 1, high, target, result);

        } else {

            // Target is in the left half
            return binarySearchFirst(arr, low, mid - 1, target, result);
        }
    }

    // Searches for the LAST occurrence of a duplicate target value using recursion
    // Returns the index of the last occurrence, -1 if not found
    public static int binarySearchLast(int[] arr, int low, int high, int target, int result) {

        // Base case: search range exhausted — return the last recorded result
        if (low > high) {
            return result;
        }

        int mid = low + (high - low) / 2;

        if (arr[mid] == target) {

            // Record this index as a potential last occurrence
            // then keep searching the right half for a later match
            result = mid;
            return binarySearchLast(arr, mid + 1, high, target, result);

        } else if (arr[mid] < target) {

            // Target is in the right half
            return binarySearchLast(arr, mid + 1, high, target, result);

        } else {

            // Target is in the left half
            return binarySearchLast(arr, low, mid - 1, target, result);
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

        // Loop to read n sorted integers from the user
        System.out.println("Enter " + n + " sorted elements in ascending order:");
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
            System.out.println("\n─── Binary Search Menu (Recursive) ───");
            System.out.println("1. Search for a value");
            System.out.println("2. Find first occurrence");
            System.out.println("3. Find last occurrence");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");

            int choice;

            try {
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                // Clear the invalid token and prompt the user to try again
                scanner.nextLine();
                System.out.println("  Invalid option. Please enter a number between 1 and 4.");
                continue;
            }

            switch (choice) {

                case 1:
                    // Perform recursive binary search for a target value
                    int target1 = readInt(scanner, "Enter value to search: ");
                    int index = binarySearch(arr, 0, n - 1, target1);

                    if (index == -1) {
                        System.out.println(target1 + " not found in the array.");
                    } else {
                        System.out.println(target1 + " found at index " + index + ".");
                    }
                    break;

                case 2:
                    // Find the first occurrence of a value in case of duplicates
                    int target2 = readInt(scanner, "Enter value to find first occurrence of: ");
                    int firstIndex = binarySearchFirst(arr, 0, n - 1, target2, -1);

                    if (firstIndex == -1) {
                        System.out.println(target2 + " not found in the array.");
                    } else {
                        System.out.println("First occurrence of " + target2 + " is at index " + firstIndex + ".");
                    }
                    break;

                case 3:
                    // Find the last occurrence of a value in case of duplicates
                    int target3 = readInt(scanner, "Enter value to find last occurrence of: ");
                    int lastIndex = binarySearchLast(arr, 0, n - 1, target3, -1);

                    if (lastIndex == -1) {
                        System.out.println(target3 + " not found in the array.");
                    } else {
                        System.out.println("Last occurrence of " + target3 + " is at index " + lastIndex + ".");
                    }
                    break;

                case 4:
                    // Exit the search loop
                    searching = false;
                    System.out.println("Exiting.");
                    break;

                default:
                    System.out.println("  Invalid option. Please choose between 1 and 4.");
            }
        }

        // Close the scanner to release system resources
        scanner.close();
    }
}