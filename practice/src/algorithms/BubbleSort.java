package algorithms;

import java.util.InputMismatchException;
import java.util.Scanner;

// Class to implement bubble sort and count the total number of swaps performed
// Bubble sort repeatedly compares adjacent elements and swaps them if out of order
// Time Complexity: O(n²) worst/average case | O(n) best case | Space Complexity: O(1)
public class BubbleSort {

    // Sorts the array in ascending order using bubble sort
    // Returns the total number of swaps performed during sorting
    public static int bubbleSort(int[] arr) {

        int n = arr.length;

        // Counter to track the total number of swaps across all passes
        int totalSwaps = 0;

        // Outer loop controls the number of passes over the array
        // After each pass, the largest unsorted element bubbles to its correct position
        // so the last i elements are already sorted and do not need to be checked again
        for (int i = 0; i < n - 1; i++) {

            // Flag to detect if any swap occurred during this pass
            // If no swaps occur, the array is already sorted and we can stop early
            boolean swapped = false;

            // Inner loop compares adjacent elements within the unsorted portion
            // The range shrinks by i each pass since the last i elements are sorted
            for (int j = 0; j < n - 1 - i; j++) {

                // If the current element is greater than the next, swap them
                if (arr[j] > arr[j + 1]) {

                    // Swap arr[j] and arr[j + 1]
                    int temp  = arr[j];
                    arr[j]    = arr[j + 1];
                    arr[j + 1] = temp;

                    // Increment swap counters
                    totalSwaps++;
                    swapped = true;
                }
            }

            // Early termination: no swaps in this pass means array is fully sorted
            if (!swapped) {
                System.out.println("  Early termination at pass " + (i + 1) + " — array already sorted.");
                break;
            }
        }

        return totalSwaps;
    }

    // Prints each pass of the bubble sort process to visualize how elements move
    // Shows the array state and swap count after every pass
    public static void bubbleSortVerbose(int[] arr) {

        int n = arr.length;
        int totalSwaps = 0;
        int pass = 0;

        System.out.println("\n─── Sorting Steps ───");

        for (int i = 0; i < n - 1; i++) {

            pass++;
            int passSwaps = 0;
            boolean swapped = false;

            for (int j = 0; j < n - 1 - i; j++) {

                if (arr[j] > arr[j + 1]) {

                    // Swap arr[j] and arr[j + 1]
                    int temp   = arr[j];
                    arr[j]     = arr[j + 1];
                    arr[j + 1] = temp;

                    totalSwaps++;
                    passSwaps++;
                    swapped = true;
                }
            }

            // Print the array state after this pass along with swap counts
            System.out.print("Pass " + pass + ": ");
            printArray(arr);
            System.out.println("  swaps this pass: " + passSwaps
                    + "  |  total swaps so far: " + totalSwaps);

            // Early termination if no swaps occurred in this pass
            if (!swapped) {
                System.out.println("  Early termination — no swaps in pass " + pass + ".");
                break;
            }
        }

        System.out.println("\nTotal passes:  " + pass);
        System.out.println("Total swaps:   " + totalSwaps);
    }

    // Prints all elements of the array on one line
    public static void printArray(int[] arr) {
        System.out.print("[");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]);
            if (i < arr.length - 1) System.out.print(", ");
        }
        System.out.print("]");
    }

    // Creates and returns a copy of the given array
    // Used to preserve the original array for verbose mode
    public static int[] copyArray(int[] arr) {
        int[] copy = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            copy[i] = arr[i];
        }
        return copy;
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
        System.out.println("Enter " + n + " elements:");
        for (int i = 0; i < n; i++) {
            arr[i] = readInt(scanner, "  Element [" + (i + 1) + "]: ");
        }

        // Display the original unsorted array
        System.out.print("\nOriginal array: ");
        printArray(arr);
        System.out.println();

        // Run verbose sort on a copy to show step-by-step sorting process
        int[] verboseCopy = copyArray(arr);
        bubbleSortVerbose(verboseCopy);

        // Run standard sort on the original array and display the final result
        int totalSwaps = bubbleSort(arr);

        System.out.print("\nSorted array:  ");
        printArray(arr);
        System.out.println();
        System.out.println("Total swaps:   " + totalSwaps);

        // Close the scanner to release system resources
        scanner.close();
    }
}