package algorithms;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

// Class to find the number of ways to climb n stairs taking 1 or 2 steps at a time
// This is structurally identical to the Fibonacci sequence
// ways(n) = ways(n-1) + ways(n-2)
// Taking 1 step leaves n-1 stairs | Taking 2 steps leaves n-2 stairs
// Time Complexity: O(n) all approaches | Space Complexity: O(n) memo | O(1) iterative
public class ClimbingStairs {

    // ─────────────────────────────────────────────
    //  APPROACH 1 — NAIVE RECURSION
    // ─────────────────────────────────────────────

    // Computes the number of ways to climb n stairs using naive recursion
    // Recomputes overlapping subproblems repeatedly — exponential time
    // Time Complexity: O(2^n) | Space Complexity: O(n) call stack
    public static long climbNaive(int n) {

        // Base case: 0 stairs remaining — one valid way (do nothing)
        if (n == 0) return 1;

        // Base case: exactly 1 stair remaining — only one way (take 1 step)
        if (n == 1) return 1;

        // Recursive case: either take 1 step (leaving n-1) or 2 steps (leaving n-2)
        // Total ways = sum of ways from both choices
        return climbNaive(n - 1) + climbNaive(n - 2);
    }

    // ─────────────────────────────────────────────
    //  APPROACH 2 — MEMOIZATION (TOP-DOWN DP)
    // ─────────────────────────────────────────────

    // Computes the number of ways to climb n stairs using memoized recursion
    // Stores results of already-solved subproblems to avoid redundant computation
    // Time Complexity: O(n) | Space Complexity: O(n) cache + O(n) call stack
    public static long climbMemo(int n, Map<Integer, Long> memo) {

        // Base cases: 0 or 1 stairs — exactly one way to handle each
        if (n == 0) return 1;
        if (n == 1) return 1;

        // Return the cached result immediately if this stair count was already solved
        if (memo.containsKey(n)) {
            return memo.get(n);
        }

        // Compute and cache the result before returning
        long result = climbMemo(n - 1, memo) + climbMemo(n - 2, memo);
        memo.put(n, result);

        return result;
    }

    // ─────────────────────────────────────────────
    //  APPROACH 3 — ITERATIVE (BOTTOM-UP DP)
    // ─────────────────────────────────────────────

    // Computes the number of ways to climb n stairs iteratively
    // Builds the solution from the smallest subproblem upward using two variables
    // Time Complexity: O(n) | Space Complexity: O(1)
    public static long climbIterative(int n) {

        // Base cases handled directly
        if (n == 0) return 1;
        if (n == 1) return 1;

        // prev = ways(i-2) and curr = ways(i-1)
        // Start with the two known base values
        long prev = 1;
        long curr = 1;

        // Build up from stair 2 to stair n
        for (int i = 2; i <= n; i++) {

            // ways(i) = ways(i-1) + ways(i-2)
            long next = prev + curr;

            // Slide the window forward — oldest value is no longer needed
            prev = curr;
            curr = next;
        }

        // curr now holds ways(n)
        return curr;
    }

    // ─────────────────────────────────────────────
    //  APPROACH 4 — FULL DP TABLE
    // ─────────────────────────────────────────────

    // Computes the number of ways to climb n stairs storing all intermediate values
    // Useful for printing the complete table of ways for each stair count
    // Time Complexity: O(n) | Space Complexity: O(n) DP table
    public static long[] climbDPTable(int n) {

        // dp[i] = number of distinct ways to reach stair i from stair 0
        long[] dp = new long[n + 1];

        // Base cases
        dp[0] = 1;  // 1 way to stay at ground (take no steps)
        dp[1] = 1;  // 1 way to reach stair 1 (take one 1-step)

        // Fill each entry using the recurrence dp[i] = dp[i-1] + dp[i-2]
        for (int i = 2; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }

        return dp;
    }

    // ─────────────────────────────────────────────
    //  PATH PRINTER
    // ─────────────────────────────────────────────

    // Prints every distinct sequence of steps that reaches stair n
    // Uses backtracking to enumerate all valid 1-step and 2-step combinations
    // Note: only suitable for small n (≤ 20) due to exponential path count
    public static void printAllPaths(int n, int[] steps, int index, int current) {

        // Base case: reached exactly stair n — print the full step sequence
        if (current == n) {
            System.out.print("  [");
            for (int i = 0; i < index; i++) {
                System.out.print(steps[i]);
                if (i < index - 1) System.out.print(", ");
            }
            System.out.println("]");
            return;
        }

        // Try taking 1 step if it does not exceed n
        if (current + 1 <= n) {
            steps[index] = 1;
            printAllPaths(n, steps, index + 1, current + 1);
        }

        // Try taking 2 steps if it does not exceed n
        if (current + 2 <= n) {
            steps[index] = 2;
            printAllPaths(n, steps, index + 1, current + 2);
        }
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

    // Helper method to read a valid positive integer within a given range
    // Rejects values below min or above max
    public static int readIntInRange(Scanner scanner, String prompt, int min, int max) {
        while (true) {
            int value = readInt(scanner, prompt);
            if (value >= min && value <= max) {
                return value;
            }
            System.out.println("  Invalid input. Please enter a number between "
                    + min + " and " + max + ".");
        }
    }

    // ─────────────────────────────────────────────
    //  MAIN MENU
    // ─────────────────────────────────────────────

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n─── Climbing Stairs Menu ───");
            System.out.println("1. Count ways  (iterative)");
            System.out.println("2. Count ways  (memoization)");
            System.out.println("3. Count ways  (naive recursion)");
            System.out.println("4. Print DP table for all stairs up to n");
            System.out.println("5. Print all distinct step paths");
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
                    // Count ways using the iterative approach
                    int n1 = readIntInRange(scanner, "Enter number of stairs: ", 0, 70);
                    System.out.println("Ways to climb " + n1 + " stairs: "
                            + climbIterative(n1));
                    break;

                case 2:
                    // Count ways using memoized recursion
                    int n2 = readIntInRange(scanner, "Enter number of stairs: ", 0, 70);
                    Map<Integer, Long> memo = new HashMap<>();
                    System.out.println("Ways to climb " + n2 + " stairs: "
                            + climbMemo(n2, memo));
                    System.out.println("Subproblems solved: " + memo.size());
                    break;

                case 3:
                    // Count ways using naive recursion — capped to prevent long waits
                    int n3 = readIntInRange(scanner, "Enter number of stairs (0-40): ", 0, 40);
                    System.out.println("Ways to climb " + n3 + " stairs: "
                            + climbNaive(n3));
                    break;

                case 4:
                    // Print the full DP table showing ways for every stair up to n
                    int n4   = readIntInRange(scanner, "Enter number of stairs: ", 0, 70);
                    long[] dp = climbDPTable(n4);
                    System.out.println("\n─── DP Table ───");
                    System.out.printf("%-10s %-10s %s%n", "Stairs", "Ways", "Relation");
                    System.out.println("─".repeat(42));
                    for (int i = 0; i <= n4; i++) {
                        String relation = (i < 2)
                                ? "base case"
                                : "dp[" + (i-1) + "] + dp[" + (i-2) + "] = "
                                + dp[i-1] + " + " + dp[i-2];
                        System.out.printf("%-10d %-10d %s%n", i, dp[i], relation);
                    }
                    break;

                case 5:
                    // Print every distinct sequence of steps for n stairs
                    // Capped at 15 to avoid flooding the output
                    int n5 = readIntInRange(scanner, "Enter number of stairs (1-15): ", 1, 15);
                    long total = climbIterative(n5);
                    System.out.println("\nAll distinct paths to climb " + n5
                            + " stairs (" + total + " total):");
                    System.out.println("─".repeat(40));
                    int[] steps = new int[n5];
                    printAllPaths(n5, steps, 0, 0);
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