package algorithms;

import java.util.InputMismatchException;
import java.util.Scanner;

// Class to solve the 0/1 Knapsack problem using bottom-up dynamic programming
// Each item can either be included (1) or excluded (0) — cannot be split
// dp[i][w] = maximum value achievable using first i items with capacity w
// Time Complexity: O(n * W) | Space Complexity: O(n * W)
// n = number of items | W = knapsack capacity
public class Knapsack {

    // ─────────────────────────────────────────────
    //  APPROACH 1 — BOTTOM-UP DP (MAX VALUE ONLY)
    // ─────────────────────────────────────────────

    // Computes the maximum value that fits within the given capacity
    // using a full 2D DP table
    public static int knapsack(int[] weights, int[] values, int capacity) {

        int n = weights.length;

        // dp[i][w] = max value using first i items with knapsack capacity w
        // Row 0 (no items) and column 0 (zero capacity) are base cases — value is 0
        int[][] dp = new int[n + 1][capacity + 1];

        // Fill the table row by row — each row represents adding one more item
        for (int i = 1; i <= n; i++) {

            // Current item's weight and value (0-indexed in the input arrays)
            int itemWeight = weights[i - 1];
            int itemValue  = values[i - 1];

            for (int w = 0; w <= capacity; w++) {

                // Option 1: exclude the current item — carry forward the value from above
                dp[i][w] = dp[i - 1][w];

                // Option 2: include the current item — only if it fits in the knapsack
                // Adding item i gives its value plus the best value for remaining capacity
                if (itemWeight <= w) {
                    int includeValue = itemValue + dp[i - 1][w - itemWeight];

                    // Take the better of including or excluding the current item
                    dp[i][w] = Math.max(dp[i][w], includeValue);
                }
            }
        }

        // Bottom-right cell holds the optimal value using all items and full capacity
        return dp[n][capacity];
    }

    // ─────────────────────────────────────────────
    //  APPROACH 2 — DP WITH ITEM TRACEBACK
    // ─────────────────────────────────────────────

    // Computes the maximum value and traces back which items were selected
    // Returns a Result object containing the max value and selected item indices
    public static Result knapsackWithTrace(int[] weights, int[] values,
                                           String[] names,  int capacity) {

        int n    = weights.length;
        int[][] dp = new int[n + 1][capacity + 1];

        // Build the DP table exactly as in knapsack()
        for (int i = 1; i <= n; i++) {
            int itemWeight = weights[i - 1];
            int itemValue  = values[i - 1];

            for (int w = 0; w <= capacity; w++) {
                dp[i][w] = dp[i - 1][w];

                if (itemWeight <= w) {
                    int includeValue = itemValue + dp[i - 1][w - itemWeight];
                    dp[i][w] = Math.max(dp[i][w], includeValue);
                }
            }
        }

        // Traceback: determine which items were selected by walking back through the table
        // Start from dp[n][capacity] and move upward row by row
        boolean[] selected    = new boolean[n];
        int       remainingCap = capacity;

        for (int i = n; i > 0; i--) {

            // If the value changed from the row above, item i was included
            // because excluding it would have kept dp[i][w] == dp[i-1][w]
            if (dp[i][remainingCap] != dp[i - 1][remainingCap]) {
                selected[i - 1] = true;

                // Reduce the remaining capacity by the included item's weight
                remainingCap -= weights[i - 1];
            }
        }

        return new Result(dp[n][capacity], selected, dp);
    }

    // ─────────────────────────────────────────────
    //  APPROACH 3 — SPACE-OPTIMIZED (1D DP)
    // ─────────────────────────────────────────────

    // Computes the maximum knapsack value using only a single 1D array
    // Iterating capacity from high to low prevents an item from being used twice
    // Time Complexity: O(n * W) | Space Complexity: O(W)
    public static int knapsackOptimized(int[] weights, int[] values, int capacity) {

        // dp[w] = max value achievable with capacity w using items processed so far
        int[] dp = new int[capacity + 1];

        for (int i = 0; i < weights.length; i++) {

            // Traverse capacity from high to low to ensure each item is used at most once
            // If we went low to high, a single item could be included multiple times
            for (int w = capacity; w >= weights[i]; w--) {
                dp[w] = Math.max(dp[w], values[i] + dp[w - weights[i]]);
            }
        }

        return dp[capacity];
    }

    // ─────────────────────────────────────────────
    //  RESULT CLASS
    // ─────────────────────────────────────────────

    // Holds the full result of the knapsack solution including the DP table
    static class Result {
        int     maxValue;   // The optimal total value
        boolean[] selected; // selected[i] = true if item i was chosen
        int[][]  dp;        // The full DP table for printing

        Result(int maxValue, boolean[] selected, int[][] dp) {
            this.maxValue = maxValue;
            this.selected = selected;
            this.dp       = dp;
        }
    }

    // ─────────────────────────────────────────────
    //  DP TABLE PRINTER
    // ─────────────────────────────────────────────

    // Prints the full 2D DP table with row and column headers
    // Each cell shows the best value achievable at that (item, capacity) combination
    public static void printDPTable(int[][] dp, String[] names,
                                    int[] weights, int[] values, int capacity) {

        int n = names.length;

        System.out.println("\n─── DP Table  dp[i][w] = max value ───");

        // Print column headers (capacity 0 to W)
        System.out.printf("%-18s", "Item");
        for (int w = 0; w <= capacity; w++) {
            System.out.printf("%-5d", w);
        }
        System.out.println();
        System.out.println("─".repeat(18 + 5 * (capacity + 1)));

        // Row 0: no items selected
        System.out.printf("%-18s", "0: (none)");
        for (int w = 0; w <= capacity; w++) {
            System.out.printf("%-5d", dp[0][w]);
        }
        System.out.println();

        // Rows 1..n: one item added per row
        for (int i = 1; i <= n; i++) {
            String label = i + ": " + names[i-1]
                    + "(w=" + weights[i-1] + ",v=" + values[i-1] + ")";
            System.out.printf("%-18s", label.length() > 17
                    ? label.substring(0, 17) : label);
            for (int w = 0; w <= capacity; w++) {
                System.out.printf("%-5d", dp[i][w]);
            }
            System.out.println();
        }
    }

    // ─────────────────────────────────────────────
    //  RESULT SUMMARY PRINTER
    // ─────────────────────────────────────────────

    // Prints a formatted summary of the selected items and their totals
    public static void printResult(Result result, String[] names,
                                   int[] weights, int[] values, int capacity) {

        System.out.println("\n─── Knapsack Solution ───");
        System.out.printf("%-20s %-10s %-10s%n", "Item", "Weight", "Value");
        System.out.println("─".repeat(42));

        int totalWeight = 0;
        int totalValue  = 0;

        for (int i = 0; i < names.length; i++) {
            if (result.selected[i]) {
                System.out.printf("%-20s %-10d %-10d%n",
                        "✓ " + names[i], weights[i], values[i]);
                totalWeight += weights[i];
                totalValue  += values[i];
            } else {
                System.out.printf("%-20s %-10s %-10s%n",
                        "✗ " + names[i], "-", "-");
            }
        }

        System.out.println("─".repeat(42));
        System.out.printf("%-20s %-10d %-10d%n",
                "TOTAL", totalWeight, totalValue);
        System.out.println("Capacity used: " + totalWeight + " / " + capacity);
        System.out.println("Max value:     " + result.maxValue);
    }

    // ─────────────────────────────────────────────
    //  INPUT HELPERS
    // ─────────────────────────────────────────────

    // Helper method to read a valid integer from the user
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
    public static String readString(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = scanner.nextLine().trim();
            if (!value.isEmpty()) return value;
            System.out.println("  Invalid input. Please enter a non-empty name.");
        }
    }

    // Reads all item data — name, weight, and value — for n items
    public static void readItems(Scanner scanner, int n,
                                 String[] names, int[] weights, int[] values) {
        System.out.println("Enter item details:");
        for (int i = 0; i < n; i++) {
            System.out.println("  Item " + (i + 1) + ":");
            scanner.nextLine();
            names[i]   = readString(scanner, "    Name:   ");
            weights[i] = readPositiveInt(scanner, "    Weight: ");
            values[i]  = readPositiveInt(scanner, "    Value:  ");
        }
    }

    // ─────────────────────────────────────────────
    //  MAIN MENU
    // ─────────────────────────────────────────────

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        // Preset example to allow quick testing without re-entering data
        String[] defaultNames   = {"Laptop", "Phone", "Watch", "Camera", "Tablet"};
        int[]    defaultWeights = {3, 1, 2, 4, 2};
        int[]    defaultValues  = {10, 5, 3, 12, 8};
        int      defaultCap     = 6;

        while (running) {
            System.out.println("\n─── 0/1 Knapsack Menu ───");
            System.out.println("1. Use preset example");
            System.out.println("2. Enter custom items");
            System.out.println("3. Space-optimized (1D DP) on preset");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");

            int choice;

            try {
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                scanner.nextLine();
                System.out.println("  Invalid option. Please enter a number between 1 and 4.");
                continue;
            }

            switch (choice) {

                case 1:
                    // Run full DP with traceback on the preset example
                    System.out.println("\nPreset Items:");
                    System.out.printf("%-20s %-10s %-10s%n", "Name", "Weight", "Value");
                    System.out.println("─".repeat(42));
                    for (int i = 0; i < defaultNames.length; i++) {
                        System.out.printf("%-20s %-10d %-10d%n",
                                defaultNames[i], defaultWeights[i], defaultValues[i]);
                    }
                    System.out.println("Capacity: " + defaultCap);

                    Result presetResult = knapsackWithTrace(
                            defaultWeights, defaultValues, defaultNames, defaultCap);

                    printDPTable(presetResult.dp, defaultNames,
                            defaultWeights, defaultValues, defaultCap);
                    printResult(presetResult, defaultNames,
                            defaultWeights, defaultValues, defaultCap);
                    break;

                case 2:
                    // Read custom items and capacity from the user
                    int      n       = readPositiveInt(scanner, "\nEnter number of items: ");
                    String[] names   = new String[n];
                    int[]    weights = new int[n];
                    int[]    values  = new int[n];

                    readItems(scanner, n, names, weights, values);

                    int capacity = readPositiveInt(scanner, "Enter knapsack capacity: ");

                    Result customResult = knapsackWithTrace(weights, values, names, capacity);

                    printDPTable(customResult.dp, names, weights, values, capacity);
                    printResult(customResult, names, weights, values, capacity);
                    break;

                case 3:
                    // Run space-optimized 1D DP on the preset example
                    int optimized = knapsackOptimized(
                            defaultWeights, defaultValues, defaultCap);
                    System.out.println("\nSpace-Optimized Result (1D DP):");
                    System.out.println("Max value with capacity "
                            + defaultCap + ": " + optimized);
                    System.out.println("(Item traceback not available in 1D mode)");
                    break;

                case 4:
                    // Exit the menu loop
                    running = false;
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