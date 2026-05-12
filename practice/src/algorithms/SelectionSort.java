package algorithms;

import java.util.InputMismatchException;
import java.util.Scanner;

// Class to implement selection sort and print the array state after each pass
// Selection sort finds the minimum element in the unsorted portion and places it
// at the beginning of that portion on every pass
// Time Complexity: O(n²) all cases | Space Complexity: O(1)
public class SelectionSort {

    // Sorts the array in ascending order using selection sort
    // Prints the array state and highlights the placed element after every pass
    public static void selectionSort(int[] arr) {

        int n = arr.length;

        // Counter to track the total number of swaps performed
        int totalSwaps = 0;

        // Outer loop moves the boundary of the unsorted portion one step right per pass
        // After pass i, the first i+1 elements are in their final sorted positions
        for (int i = 0; i < n - 1; i++) {

            // Assume the first element of the unsorted portion is the minimum
            int minIndex = i;

            // Scan the unsorted portion to find the actual minimum element
            for (int j = i + 1; j < n; j++) {

                // Update minIndex whenever a smaller element is found
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }

            // Only swap if the minimum element is not already in its correct position
            if (minIndex != i) {

                // Swap the found minimum element with the first element of the unsorted portion
                int temp       = arr[minIndex];
                arr[minIndex]  = arr[i];
                arr[i]         = temp;

                totalSwaps++;
            }

            // Print the array state after this pass
            // Clearly marks which element was just placed into its sorted position
            System.out.print("Pass " + (i + 1) + ": ");
            printArrayWithMarker(arr, i);
            System.out.println("  →  placed " + arr[i] + " at index " + i);
        }

        System.out.println("\nTotal swaps: " + totalSwaps);
    }

    // Prints all elements of the array on one line
    // Places a marker (*) next to the element at the sorted boundary index
    // to visually indicate which element was placed in this pass
    public static void printArrayWithMarker(int[] arr, int sortedUpTo) {
        System.out.print("[");
        for (int i = 0; i < arr.length; i++) {

            // Mark the element just placed into its final sorted position
            if (i == sortedUpTo) {
                System.out.print(arr[i] + "*");
            } else {
                System.out.print(arr[i]);
            }

            if (i < arr.length - 1) System.out.print(", ");
        }
        System.out.print("]");
    }

    // Prints all elements of the array on one line without any marker
    public static void printArray(int[] arr) {
        System.out.print("[");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]);
            if (i < arr.length - 1) System.out.print(", ");
        }
        System.out.print("]");
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

        // Display the original unsorted array before sorting begins
        System.out.print("\nOriginal array: ");
        printArray(arr);
        System.out.println();

        // Run selection sort and print the array state after each pass
        System.out.println("\n─── Sorting Steps (* = placed in sorted position) ───");
        selectionSort(arr);

        // Display the final sorted array after all passes are complete
        System.out.print("\nSorted array:   ");
        printArray(arr);
        System.out.println();

        // Close the scanner to release system resources
        scanner.close();
    }
}