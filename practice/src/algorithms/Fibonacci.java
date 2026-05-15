package algorithms;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

// Class to compute the nth Fibonacci number using three approaches
// Naive recursion, memoization, and iterative (O(n) time)
// Fibonacci sequence: 0, 1, 1, 2, 3, 5, 8, 13, 21, 34 ...
// F(n) = F(n-1) + F(n-2)  for n > 1
public class Fibonacci {

    // ─────────────────────────────────────────────
    //  APPROACH 1 — NAIVE RECURSION
    // ─────────────────────────────────────────────

    // Computes F(n) by directly applying the Fibonacci recurrence
    // No caching — the same subproblems are recomputed many times
    // Time Complexity: O(2^n) | Space Complexity: O(n) call stack
    public static long fibNaive(int n) {

        // Base case: F(0) = 0
        if (n == 0) return 0;

        // Base case: F(1) = 1
        if (n == 1) return 1;

        // Recursive case: each call spawns two more recursive calls
        // This creates an exponential number of redundant computations
        return fibNaive(n - 1) + fibNaive(n - 2);
    }

    // Tracks how many times fibNaive is called to demonstrate redundant computation
    static long naiveCallCount = 0;

    // Instrumented version of naive recursion that counts every function call
    public static long fibNaiveCounted(int n) {
        naiveCallCount++;

        if (n == 0) return 0;
        if (n == 1) return 1;

        return fibNaiveCounted(n - 1) + fibNaiveCounted(n - 2);
    }

    // ─────────────────────────────────────────────
    //  APPROACH 2 — MEMOIZATION (TOP-DOWN DP)
    // ─────────────────────────────────────────────

    // Computes F(n) recursively but caches each result in a HashMap
    // When a subproblem is encountered again its stored result is returned immediately
    // Time Complexity: O(n) | Space Complexity: O(n) cache + O(n) call stack
    public static long fibMemo(int n, Map<Integer, Long> memo) {

        // Base case: F(0) = 0
        if (n == 0) return 0;

        // Base case: F(1) = 1
        if (n == 1) return 1;

        // If this subproblem has already been solved, return the cached result
        // This prevents redundant recomputation of the same Fibonacci values
        if (memo.containsKey(n)) {
            return memo.get(n);
        }

        // Compute the result and store it in the cache before returning
        long result = fibMemo(n - 1, memo) + fibMemo(n - 2, memo);
        memo.put(n, result);

        return result;
    }

    // ─────────────────────────────────────────────
    //  APPROACH 3 — ITERATIVE  ⋆ Challenge
    // ─────────────────────────────────────────────

    // Computes F(n) iteratively using only two variables
    // Builds the sequence from the bottom up without any recursion or extra storage
    // Time Complexity: O(n) | Space Complexity: O(1)
    public static long fibIterative(int n) {

        // Base cases handled directly
        if (n == 0) return 0;
        if (n == 1) return 1;

        // prev holds F(i-2) and curr holds F(i-1)
        // At the start of each iteration they represent the two most recent values
        long prev = 0;
        long curr = 1;

        // Iterate from 2 up to n, computing the next Fibonacci value each step
        for (int i = 2; i <= n; i++) {

            // Compute the next value as the sum of the previous two
            long next = prev + curr;

            // Slide the window forward — discard the oldest value
            prev = curr;
            curr = next;
        }

        // curr now holds F(n)
        return curr;
    }

    // ─────────────────────────────────────────────
    //  PERFORMANCE BENCHMARK
    // ─────────────────────────────────────────────

    // Measures and compares the execution time of all three approaches
    // for the given value of n and prints a formatted summary table
    public static void benchmark(int n) {

        System.out.println("\n─── Fibonacci Benchmark  n = " + n + " ───");
        System.out.println("─".repeat(54));
        System.out.printf("%-20s %12s %12s%n", "Approach", "Result", "Time");
        System.out.println("─".repeat(54));

        // ── Iterative ──
        long startIter  = System.nanoTime();
        long iterResult = fibIterative(n);
        long iterTime   = System.nanoTime() - startIter;

        System.out.printf("%-20s %12d %8.3f ms%n",
                "Iterative", iterResult, iterTime / 1_000_000.0);

        // ── Memoization ──
        Map<Integer, Long> memo      = new HashMap<>();
        long               startMemo = System.nanoTime();
        long               memoResult = fibMemo(n, memo);
        long               memoTime   = System.nanoTime() - startMemo;

        System.out.printf("%-20s %12d %8.3f ms%n",
                "Memoization", memoResult, memoTime / 1_000_000.0);

        // ── Naive Recursion (capped at n=45 to prevent excessive wait time) ──
        if (n <= 45) {
            naiveCallCount = 0;
            long startNaive  = System.nanoTime();
            long naiveResult = fibNaiveCounted(n);
            long naiveTime   = System.nanoTime() - startNaive;

            System.out.printf("%-20s %12d %8.3f ms%n",
                    "Naive Recursion", naiveResult, naiveTime / 1_000_000.0);
            System.out.println("─".repeat(54));
            System.out.println("Naive call count:   " + naiveCallCount
                    + "  (vs " + (2 * n + 1) + " for memo/iterative)");
        } else {
            System.out.printf("%-20s %12s %8s%n",
                    "Naive Recursion", "skipped", "n > 45");
            System.out.println("─".repeat(54));
            System.out.println("Naive skipped — O(2^n) would take too long for n > 45");
        }

        System.out.println("─".repeat(54));
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

    // Helper method to read a valid non-negative integer (zero or greater)
    // Rejects negative values since Fibonacci is not defined for negative indices
    public static int readNonNegativeInt(Scanner scanner, String prompt) {
        while (true) {
            int value = readInt(scanner, prompt);
            if (value >= 0) {
                return value;
            }
            System.out.println("  Invalid input. Please enter a non-negative number.");
        }
    }

    // ─────────────────────────────────────────────
    //  MAIN MENU
    // ─────────────────────────────────────────────

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n─── Fibonacci Menu ───");
            System.out.println("1. Naive recursion      O(2^n)");
            System.out.println("2. Memoization          O(n) time  O(n) space");
            System.out.println("3. Iterative            O(n) time  O(1) space");
            System.out.println("4. Print Fibonacci sequence up to n");
            System.out.println("5. Benchmark all three approaches");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");

            int choice;

            try {
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                scanner.nextLine();
                System.out.println("  Invalid option. Please enter a number between 1 and 6.");
                continue;
            }

            switch (choice) {

                case 1:
                    // Compute F(n) using naive recursion — capped to prevent long waits
                    int n1 = readNonNegativeInt(scanner, "Enter n (0-45): ");
                    if (n1 > 45) {
                        System.out.println("  n capped at 45 to prevent excessive wait time.");
                        n1 = 45;
                    }
                    naiveCallCount = 0;
                    long naive = fibNaiveCounted(n1);
                    System.out.println("F(" + n1 + ") = " + naive);
                    System.out.println("Total recursive calls: " + naiveCallCount);
                    break;

                case 2:
                    // Compute F(n) using memoization
                    int n2     = readNonNegativeInt(scanner, "Enter n: ");
                    Map<Integer, Long> memo = new HashMap<>();
                    long memoResult = fibMemo(n2, memo);
                    System.out.println("F(" + n2 + ") = " + memoResult);
                    System.out.println("Unique subproblems solved: " + memo.size());
                    break;

                case 3:
                    // Compute F(n) using the iterative approach
                    int n3   = readNonNegativeInt(scanner, "Enter n: ");
                    long iter = fibIterative(n3);
                    System.out.println("F(" + n3 + ") = " + iter);
                    break;

                case 4:
                    // Print the full Fibonacci sequence from F(0) to F(n)
                    int n4 = readNonNegativeInt(scanner, "Enter n: ");
                    System.out.print("Fibonacci sequence: ");
                    for (int i = 0; i <= n4; i++) {
                        System.out.print("F(" + i + ")=" + fibIterative(i));
                        if (i < n4) System.out.print("  ");
                    }
                    System.out.println();
                    break;

                case 5:
                    // Benchmark all three approaches and display timing comparison
                    int n5 = readNonNegativeInt(scanner, "Enter n for benchmark: ");
                    benchmark(n5);
                    break;

                case 6:
                    // Exit the menu loop
                    running = false;
                    System.out.println("Exiting.");
                    break;

                default:
                    System.out.println("  Invalid option. Please choose between 1 and 6.");
            }
        }

        // Close the scanner to release system resources
        scanner.close();
    }
}