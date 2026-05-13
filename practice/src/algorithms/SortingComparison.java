package algorithms;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

// Class to compare the performance of bubble sort, selection sort, and insertion sort
// Measures execution time using System.nanoTime() across different array types and sizes
public class SortingComparison {

    // ─────────────────────────────────────────────
    //  SORTING ALGORITHMS
    // ─────────────────────────────────────────────

    // Bubble sort with early termination flag
    // Repeatedly swaps adjacent elements that are out of order
    public static void bubbleSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;
            for (int j = 0; j < n - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp   = arr[j];
                    arr[j]     = arr[j + 1];
                    arr[j + 1] = temp;
                    swapped    = true;
                }
            }
            // Stop early if no swaps occurred in this pass
            if (!swapped) break;
        }
    }

    // Selection sort — finds the minimum in the unsorted portion each pass
    // and places it at the start of that portion
    public static void selectionSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                int temp       = arr[minIndex];
                arr[minIndex]  = arr[i];
                arr[i]         = temp;
            }
        }
    }

    // Insertion sort — picks each element and inserts it into
    // its correct position within the already sorted portion
    public static void insertionSort(int[] arr) {
        int n = arr.length;
        for (int i = 1; i < n; i++) {
            int key = arr[i];
            int j   = i - 1;
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }

    // ─────────────────────────────────────────────
    //  ARRAY GENERATORS
    // ─────────────────────────────────────────────

    // Generates an array of random integers between 0 and 999999
    public static int[] generateRandom(int size) {
        Random rand = new Random(42);
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = rand.nextInt(1000000);
        }
        return arr;
    }

    // Generates an array already sorted in ascending order
    public static int[] generateSorted(int size) {
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = i;
        }
        return arr;
    }

    // Generates an array sorted in descending order (worst case for most algorithms)
    public static int[] generateReverseSorted(int size) {
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = size - i;
        }
        return arr;
    }

    // Generates a nearly sorted array with only a few elements out of place
    public static int[] generateNearlySorted(int size) {
        int[] arr = generateSorted(size);
        Random rand = new Random(42);

        // Swap roughly 5% of elements to introduce slight disorder
        int swaps = Math.max(1, size / 20);
        for (int i = 0; i < swaps; i++) {
            int x = rand.nextInt(size);
            int y = rand.nextInt(size);
            int temp = arr[x];
            arr[x]   = arr[y];
            arr[y]   = temp;
        }
        return arr;
    }

    // Creates and returns an exact copy of the given array
    // Used to ensure every algorithm sorts an identical input
    public static int[] copyArray(int[] arr) {
        int[] copy = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            copy[i] = arr[i];
        }
        return copy;
    }

    // ─────────────────────────────────────────────
    //  BENCHMARK ENGINE
    // ─────────────────────────────────────────────

    // Measures the execution time of each sorting algorithm on the given array
    // Each algorithm sorts its own copy to ensure a fair and independent comparison
    public static void benchmark(String label, int[] original) {

        System.out.println("\n  [ " + label + " — " + original.length + " elements ]");
        System.out.println("  " + "─".repeat(52));

        // ── Bubble Sort ──
        int[] arr1    = copyArray(original);
        long  start1  = System.nanoTime();
        bubbleSort(arr1);
        long  end1    = System.nanoTime();
        long  bubble  = end1 - start1;

        // ── Selection Sort ──
        int[] arr2       = copyArray(original);
        long  start2     = System.nanoTime();
        selectionSort(arr2);
        long  end2       = System.nanoTime();
        long  selection  = end2 - start2;

        // ── Insertion Sort ──
        int[] arr3       = copyArray(original);
        long  start3     = System.nanoTime();
        insertionSort(arr3);
        long  end3       = System.nanoTime();
        long  insertion  = end3 - start3;

        // Determine the fastest algorithm for this run
        long fastest = Math.min(bubble, Math.min(selection, insertion));

        // Print results in a formatted table with winner marked
        System.out.printf("  %-20s %15s%n", "Algorithm", "Time (ms)");
        System.out.println("  " + "─".repeat(38));
        System.out.printf("  %-20s %12.3f ms %s%n",
                "Bubble Sort",    bubble    / 1_000_000.0, bubble    == fastest ? " <- fastest" : "");
        System.out.printf("  %-20s %12.3f ms %s%n",
                "Selection Sort", selection / 1_000_000.0, selection == fastest ? " <- fastest" : "");
        System.out.printf("  %-20s %12.3f ms %s%n",
                "Insertion Sort", insertion / 1_000_000.0, insertion == fastest ? " <- fastest" : "");
        System.out.println("  " + "─".repeat(38));
    }

    // Runs benchmarks across all four array types for a given size
    public static void runAllBenchmarks(int size) {
        System.out.println("\n" + "═".repeat(56));
        System.out.println("  BENCHMARK — Array Size: " + size);
        System.out.println("═".repeat(56));

        benchmark("Random Order",       generateRandom(size));
        benchmark("Already Sorted",     generateSorted(size));
        benchmark("Reverse Sorted",     generateReverseSorted(size));
        benchmark("Nearly Sorted",      generateNearlySorted(size));
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
    //  MAIN MENU
    // ─────────────────────────────────────────────

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n─── Sorting Performance Comparison ───");
            System.out.println("1. Run with preset sizes  (1K, 5K, 10K)");
            System.out.println("2. Run with custom size");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            int choice;

            try {
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                scanner.nextLine();
                System.out.println("  Invalid option. Please enter 1, 2, or 3.");
                continue;
            }

            switch (choice) {

                case 1:
                    // Run benchmarks on three preset sizes to show how performance scales
                    runAllBenchmarks(1_000);
                    runAllBenchmarks(5_000);
                    runAllBenchmarks(10_000);
                    break;

                case 2:
                    // Run benchmark on a user-defined array size
                    int size = readPositiveInt(scanner, "Enter array size: ");
                    runAllBenchmarks(size);
                    break;

                case 3:
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