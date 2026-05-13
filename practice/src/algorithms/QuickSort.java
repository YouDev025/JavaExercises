package algorithms;

import java.util.InputMismatchException;
import java.util.Scanner;

// Class to implement quick sort using the last element as the pivot
// Partitions the array around the pivot so all smaller elements are on the left
// and all larger elements are on the right, then recursively sorts each side
// Time Complexity: O(n log n) average | O(n²) worst case | Space Complexity: O(log n)
public class QuickSort {

    // ─────────────────────────────────────────────
    //  PARTITION
    // ─────────────────────────────────────────────

    // Partitions the subarray arr[low..high] around the pivot (last element)
    // All elements smaller than pivot are moved to its left
    // All elements greater than pivot are moved to its right
    // Returns the final index where the pivot is placed
    public static int partition(int[] arr, int low, int high) {

        // Choose the last element of the current subarray as the pivot
        int pivot = arr[high];

        // i tracks the boundary of elements smaller than the pivot
        // Everything at index <= i is smaller than or equal to pivot
        int i = low - 1;

        System.out.println("  Pivot: " + pivot
                + "  subarray: " + subarrayToString(arr, low, high));

        // Scan every element except the pivot from left to right
        for (int j = low; j < high; j++) {

            // If the current element is smaller than or equal to the pivot
            // expand the left partition and swap the current element into it
            if (arr[j] <= pivot) {
                i++;

                // Swap arr[i] and arr[j] to place the smaller element
                // in the left partition
                int temp = arr[i];
                arr[i]   = arr[j];
                arr[j]   = temp;
            }
        }

        // Place the pivot in its correct sorted position
        // by swapping it with the first element of the right partition
        int temp    = arr[i + 1];
        arr[i + 1]  = arr[high];
        arr[high]   = temp;

        // Print where the pivot landed after partitioning
        System.out.println("  Placed pivot " + pivot
                + " at index " + (i + 1)
                + "  →  " + subarrayToString(arr, low, high));

        // Return the index where the pivot is now permanently placed
        return i + 1;
    }

    // ─────────────────────────────────────────────
    //  QUICK SORT — MAIN RECURSIVE METHOD
    // ─────────────────────────────────────────────

    // Recursively sorts the subarray arr[low..high] using quick sort
    // low  — starting index of the current subarray
    // high — ending index of the current subarray
    public static void quickSort(int[] arr, int low, int high, int depth) {

        // Base case: a subarray of size 1 or less is already sorted
        if (low >= high) {
            return;
        }

        System.out.println("\n" + "  ".repeat(depth)
                + "Sort range [" + low + ".." + high + "]:");

        // Partition the array around the pivot and get the pivot's final index
        // The pivot is now in its correct sorted position and will not move again
        int pivotIndex = partition(arr, low, high);

        // Recursively sort the left partition (elements smaller than pivot)
        quickSort(arr, low, pivotIndex - 1, depth + 1);

        // Recursively sort the right partition (elements greater than pivot)
        quickSort(arr, pivotIndex + 1, high, depth + 1);
    }

    // ─────────────────────────────────────────────
    //  PRINT HELPERS
    // ─────────────────────────────────────────────

    // Returns a string representation of a subarray from index low to high
    public static String subarrayToString(int[] arr, int low, int high) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = low; i <= high; i++) {
            sb.append(arr[i]);
            if (i < high) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }

    // Prints all elements of an integer array on one line
    public static void printArray(int[] arr) {
        System.out.print("[");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]);
            if (i < arr.length - 1) System.out.print(", ");
        }
        System.out.println("]");
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

    // ─────────────────────────────────────────────
    //  MAIN
    // ─────────────────────────────────────────────

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // Read a valid positive integer for the size of the array
        int n = readPositiveInt(scanner, "Enter number of elements: ");

        // Declare an integer array of size n
        int[] arr = new int[n];

        // Loop to read n integers from the user and store them in the array
        System.out.println("Enter " + n + " elements:");
        for (int i = 0; i < n; i++) {
            arr[i] = readInt(scanner, "  Element [" + (i + 1) + "]: ");
        }

        // Display the original unsorted array before sorting begins
        System.out.print("\nOriginal array: ");
        printArray(arr);

        // Run quick sort and display the partition steps at each recursion level
        System.out.println("\n─── Quick Sort Steps ───");
        quickSort(arr, 0, n - 1, 0);

        // Display the final sorted array after all partitions are complete
        System.out.print("\nSorted array:   ");
        printArray(arr);

        // Close the scanner to release system resources
        scanner.close();
    }
}