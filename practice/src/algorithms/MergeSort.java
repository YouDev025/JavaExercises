package algorithms;

import java.util.InputMismatchException;
import java.util.Scanner;

// Class to implement merge sort on an integer array using divide and conquer
// Recursively splits the array into halves, sorts each half, then merges them
// Time Complexity: O(n log n) all cases | Space Complexity: O(n) for temporary arrays
public class MergeSort {

    // ─────────────────────────────────────────────
    //  MERGE SORT — MAIN RECURSIVE METHOD
    // ─────────────────────────────────────────────

    // Recursively divides the array into two halves and sorts each half
    // then merges the sorted halves back together in order
    // low  — starting index of the current subarray
    // high — ending index of the current subarray
    public static void mergeSort(int[] arr, int low, int high, int depth) {

        // Base case: a subarray of size 1 or less is already sorted
        // Recursion stops when low and high point to the same element
        if (low >= high) {
            return;
        }

        // Calculate the middle index to split the array into two halves
        // Using low + (high - low) / 2 prevents integer overflow
        int mid = low + (high - low) / 2;

        // Print the current division to visualize the divide step
        System.out.println("  ".repeat(depth)
                + "Divide  [" + low + ".." + mid + "] and [" + (mid + 1) + ".." + high + "]");

        // Recursive call to sort the left half
        mergeSort(arr, low, mid, depth + 1);

        // Recursive call to sort the right half
        mergeSort(arr, mid + 1, high, depth + 1);

        // Merge the two sorted halves back together into the original array
        merge(arr, low, mid, high, depth);
    }

    // ─────────────────────────────────────────────
    //  MERGE — COMBINE TWO SORTED HALVES
    // ─────────────────────────────────────────────

    // Merges two sorted subarrays: arr[low..mid] and arr[mid+1..high]
    // into a single sorted subarray within arr
    public static void merge(int[] arr, int low, int mid, int high, int depth) {

        // Calculate sizes of the two halves to be merged
        int leftSize  = mid - low + 1;
        int rightSize = high - mid;

        // Create temporary arrays to hold copies of each half
        // Direct merging into the original array would overwrite values before they are used
        int[] left  = new int[leftSize];
        int[] right = new int[rightSize];

        // Copy the left half of the original array into the temporary left array
        for (int i = 0; i < leftSize; i++) {
            left[i] = arr[low + i];
        }

        // Copy the right half of the original array into the temporary right array
        for (int j = 0; j < rightSize; j++) {
            right[j] = arr[mid + 1 + j];
        }

        // Pointer i traverses the left temporary array
        // Pointer j traverses the right temporary array
        // Pointer k places elements back into the original array
        int i = 0, j = 0, k = low;

        // Compare elements from both halves and place the smaller one first
        while (i < leftSize && j < rightSize) {

            if (left[i] <= right[j]) {
                // Left element is smaller or equal — place it next in the merged result
                arr[k++] = left[i++];
            } else {
                // Right element is smaller — place it next in the merged result
                arr[k++] = right[j++];
            }
        }

        // Copy any remaining elements from the left half into the merged result
        // These elements are already in order and all larger than the last placed element
        while (i < leftSize) {
            arr[k++] = left[i++];
        }

        // Copy any remaining elements from the right half into the merged result
        // These elements are already in order and all larger than the last placed element
        while (j < rightSize) {
            arr[k++] = right[j++];
        }

        // Print the merged subarray to visualize the conquer step
        System.out.print("  ".repeat(depth) + "Merge   [");
        for (int x = low; x <= high; x++) {
            System.out.print(arr[x]);
            if (x < high) System.out.print(", ");
        }
        System.out.println("]");
    }

    // ─────────────────────────────────────────────
    //  PRINT HELPER
    // ─────────────────────────────────────────────

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

        // Run merge sort and display the divide and merge steps at each recursion level
        System.out.println("\n─── Merge Sort Steps ───");
        mergeSort(arr, 0, n - 1, 0);

        // Display the final sorted array after all merges are complete
        System.out.print("\nSorted array:   ");
        printArray(arr);

        // Close the scanner to release system resources
        scanner.close();
    }
}