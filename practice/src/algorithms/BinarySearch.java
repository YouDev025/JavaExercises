package algorithms;

import java.util.InputMismatchException;
import java.util.Scanner;

// Class to implement iterative binary search on a sorted array
// Binary search repeatedly divides the search range in half
// Time Complexity: O(log n) | Space Complexity: O(1)
public class BinarySearch {

    // Searches for the target value in a sorted array using iterative binary search
    // Returns the index of the target if found, -1 otherwise
    public static int binarySearch(int[] arr, int target) {

        // Left pointer starts at the beginning of the array
        int left = 0;

        // Right pointer starts at the end of the array
        int right = arr.length - 1;

        // Continue searching as long as the search range is valid
        while (left <= right) {

            // Calculate the middle index of the current search range
            // Using left + (right - left) / 2 instead of (left + right) / 2
            // prevents integer overflow when left and right are very large
            int mid = left + (right - left) / 2;

            // Target found at the middle index — return immediately
            if (arr[mid] == target) {
                return mid;
            }

            // Target is greater than the middle element
            // Discard the left half by moving the left pointer past mid
            if (arr[mid] < target) {
                left = mid + 1;

                // Target is less than the middle element
                // Discard the right half by moving the right pointer before mid
            } else {
                right = mid - 1;
            }
        }

        // Search range is exhausted — target is not in the array
        return -1;
    }

    // Searches for the FIRST occurrence of a duplicate target value
    // Returns the index of the first occurrence, -1 if not found
    public static int binarySearchFirst(int[] arr, int target) {

        int left  = 0;
        int right = arr.length - 1;

        // Stores the index of the most recent match found
        int result = -1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (arr[mid] == target) {

                // Record this position as a potential first occurrence
                // then keep searching the left half for an earlier one
                result = mid;
                right = mid - 1;

            } else if (arr[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return result;
    }

    // Searches for the LAST occurrence of a duplicate target value
    // Returns the index of the last occurrence, -1 if not found
    public static int binarySearchLast(int[] arr, int target) {

        int left  = 0;
        int right = arr.length - 1;

        // Stores the index of the most recent match found
        int result = -1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (arr[mid] == target) {

                // Record this position as a potential last occurrence
                // then keep searching the right half for a later one
                result = mid;
                left = mid + 1;

            } else if (arr[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return result;
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
            System.out.println("\n─── Binary Search Menu ───");
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
                    // Perform standard binary search for a target value
                    int target1 = readInt(scanner, "Enter value to search: ");
                    int index = binarySearch(arr, target1);

                    if (index == -1) {
                        System.out.println(target1 + " not found in the array.");
                    } else {
                        System.out.println(target1 + " found at index " + index + ".");
                    }
                    break;

                case 2:
                    // Find the first occurrence of a value in case of duplicates
                    int target2 = readInt(scanner, "Enter value to find first occurrence of: ");
                    int firstIndex = binarySearchFirst(arr, target2);

                    if (firstIndex == -1) {
                        System.out.println(target2 + " not found in the array.");
                    } else {
                        System.out.println("First occurrence of " + target2 + " is at index " + firstIndex + ".");
                    }
                    break;

                case 3:
                    // Find the last occurrence of a value in case of duplicates
                    int target3 = readInt(scanner, "Enter value to find last occurrence of: ");
                    int lastIndex = binarySearchLast(arr, target3);

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