package algorithms;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

// Class to solve the Coin Change problem using bottom-up dynamic programming
// Find the minimum number of coins needed to make a given amount
// dp[i] = minimum number of coins required to make amount i
// Time Complexity: O(amount * coins) | Space Complexity: O(amount)
public class CoinChange {

    // ─────────────────────────────────────────────
    //  APPROACH 1 — BOTTOM-UP DP (MINIMUM COINS)
    // ─────────────────────────────────────────────

    // Computes the minimum number of coins needed to make the target amount
    // Returns -1 if the amount cannot be made with the given coin denominations
    public static int minCoins(int[] coins, int amount) {

        // dp[i] stores the minimum number of coins needed to make amount i
        // Initialize all entries to amount + 1 as a sentinel for "unreachable"
        // amount + 1 is safely larger than any valid answer since the minimum
        // coin is 1 and you would never need more than amount coins of value 1
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, amount + 1);

        // Base case: zero coins are needed to make amount 0
        dp[0] = 0;

        // Outer loop builds up the solution for every amount from 1 to target
        for (int i = 1; i <= amount; i++) {

            // Inner loop tries every coin denomination for the current amount i
            for (int coin : coins) {

                // Only consider this coin if it does not exceed the current amount
                if (coin <= i) {

                    // If using this coin improves the current best for amount i, update it
                    // dp[i - coin] is the best solution for the remaining amount after using coin
                    dp[i] = Math.min(dp[i], dp[i - coin] + 1);
                }
            }
        }

        // If dp[amount] is still the sentinel value, the amount cannot be reached
        return dp[amount] > amount ? -1 : dp[amount];
    }

    // ─────────────────────────────────────────────
    //  APPROACH 2 — TRACEBACK (WHICH COINS USED)
    // ─────────────────────────────────────────────

    // Computes the minimum coins and also traces back which coins were used
    // Returns the list of coins in the optimal solution, or an empty array if impossible
    public static int[] minCoinsWithTrace(int[] coins, int amount) {

        // dp[i] stores the minimum number of coins needed to make amount i
        int[] dp       = new int[amount + 1];
        // coinUsed[i] stores which coin denomination was last used to reach amount i
        // Used during traceback to reconstruct the full solution
        int[] coinUsed = new int[amount + 1];

        Arrays.fill(dp, amount + 1);
        Arrays.fill(coinUsed, -1);

        // Base case: no coins needed to make amount 0
        dp[0] = 0;

        // Fill the DP table recording which coin produced the optimal step at each amount
        for (int i = 1; i <= amount; i++) {
            for (int coin : coins) {
                if (coin <= i && dp[i - coin] + 1 < dp[i]) {
                    dp[i]       = dp[i - coin] + 1;
                    coinUsed[i] = coin;
                }
            }
        }

        // Amount is unreachable — return an empty array
        if (dp[amount] > amount) return new int[0];

        // Traceback: reconstruct the list of coins used by following coinUsed[]
        // Start from the target amount and subtract the used coin each step
        int[] result = new int[dp[amount]];
        int   idx    = 0;
        int   curr   = amount;

        while (curr > 0) {
            result[idx++] = coinUsed[curr];
            curr         -= coinUsed[curr];
        }

        return result;
    }

    // ─────────────────────────────────────────────
    //  APPROACH 3 — COUNT TOTAL COMBINATIONS
    // ─────────────────────────────────────────────

    // Counts the total number of distinct coin combinations that make the amount
    // Order does not matter: {1,1,2} and {2,1,1} are counted as one combination
    // Time Complexity: O(amount * coins) | Space Complexity: O(amount)
    public static long countCombinations(int[] coins, int amount) {

        // dp[i] stores the number of distinct combinations to make amount i
        long[] dp = new long[amount + 1];

        // Base case: exactly one way to make amount 0 (use no coins)
        dp[0] = 1;

        // Outer loop iterates over coins — processing one coin at a time
        // ensures each combination is counted once regardless of order
        for (int coin : coins) {

            // For each coin, update all amounts it can contribute to
            for (int i = coin; i <= amount; i++) {
                dp[i] += dp[i - coin];
            }
        }

        return dp[amount];
    }

    // ─────────────────────────────────────────────
    //  DP TABLE PRINTER
    // ─────────────────────────────────────────────

    // Prints the full DP table showing the minimum coins for every amount from 0 to target
    // Helps visualize how each subproblem builds on previously solved smaller amounts
    public static void printDPTable(int[] coins, int amount) {

        int[] dp       = new int[amount + 1];
        int[] coinUsed = new int[amount + 1];

        Arrays.fill(dp, amount + 1);
        Arrays.fill(coinUsed, -1);
        dp[0] = 0;

        for (int i = 1; i <= amount; i++) {
            for (int coin : coins) {
                if (coin <= i && dp[i - coin] + 1 < dp[i]) {
                    dp[i]       = dp[i - coin] + 1;
                    coinUsed[i] = coin;
                }
            }
        }

        System.out.println("\n─── DP Table ───");
        System.out.printf("%-10s %-15s %-15s%n", "Amount", "Min Coins", "Last Coin Used");
        System.out.println("─".repeat(42));

        for (int i = 0; i <= amount; i++) {
            String minCoinsStr = (dp[i] > amount) ? "∞ (unreachable)" : String.valueOf(dp[i]);
            String lastCoin    = (coinUsed[i] == -1) ? (i == 0 ? "none (base)" : "unreachable")
                    : String.valueOf(coinUsed[i]);
            System.out.printf("%-10d %-15s %-15s%n", i, minCoinsStr, lastCoin);
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

    // Reads an array of coin denominations ensuring all values are positive
    public static int[] readCoins(Scanner scanner) {
        int n = readPositiveInt(scanner, "Enter number of coin denominations: ");
        int[] coins = new int[n];
        System.out.println("Enter " + n + " coin denominations:");
        for (int i = 0; i < n; i++) {
            coins[i] = readPositiveInt(scanner, "  Coin [" + (i + 1) + "]: ");
        }
        return coins;
    }

    // ─────────────────────────────────────────────
    //  MAIN MENU
    // ─────────────────────────────────────────────

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n─── Coin Change Menu ───");
            System.out.println("1. Find minimum number of coins");
            System.out.println("2. Find minimum coins and show which coins are used");
            System.out.println("3. Count total distinct combinations");
            System.out.println("4. Print full DP table");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            int choice;

            try {
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                scanner.nextLine();
                System.out.println("  Invalid option. Please enter a number between 1 and 5.");
                continue;
            }

            switch (choice) {

                case 1:
                    // Find and display the minimum number of coins for the target amount
                    int[] coins1 = readCoins(scanner);
                    int   amount1 = readPositiveInt(scanner, "Enter target amount: ");
                    int   result1 = minCoins(coins1, amount1);

                    if (result1 == -1) {
                        System.out.println("Amount " + amount1
                                + " cannot be made with the given coins.");
                    } else {
                        System.out.println("Minimum coins to make " + amount1
                                + ": " + result1);
                    }
                    break;

                case 2:
                    // Find minimum coins and trace back which denominations were used
                    int[] coins2  = readCoins(scanner);
                    int   amount2 = readPositiveInt(scanner, "Enter target amount: ");
                    int[] used    = minCoinsWithTrace(coins2, amount2);

                    if (used.length == 0) {
                        System.out.println("Amount " + amount2
                                + " cannot be made with the given coins.");
                    } else {
                        System.out.println("Minimum coins: " + used.length);
                        System.out.print("Coins used:    ");
                        // Count occurrences of each denomination for a cleaner display
                        Arrays.sort(used);
                        for (int i = 0; i < used.length; ) {
                            int coin  = used[i];
                            int count = 0;
                            while (i < used.length && used[i] == coin) {
                                count++;
                                i++;
                            }
                            System.out.print(coin + " x" + count + "  ");
                        }
                        System.out.println();
                        System.out.println("Verification:  " + Arrays.toString(used)
                                + " = " + Arrays.stream(used).sum());
                    }
                    break;

                case 3:
                    // Count all distinct coin combinations that sum to the target amount
                    int[] coins3  = readCoins(scanner);
                    int   amount3 = readPositiveInt(scanner, "Enter target amount: ");
                    long  combos  = countCombinations(coins3, amount3);
                    System.out.println("Total distinct combinations to make "
                            + amount3 + ": " + combos);
                    break;

                case 4:
                    // Print the full DP table for every amount from 0 to target
                    int[] coins4  = readCoins(scanner);
                    int   amount4 = readPositiveInt(scanner, "Enter target amount: ");
                    printDPTable(coins4, amount4);
                    break;

                case 5:
                    // Exit the menu loop
                    running = false;
                    System.out.println("Exiting.");
                    break;

                default:
                    System.out.println("  Invalid option. Please choose between 1 and 5.");
            }
        }

        // Close the scanner to release system resources
        scanner.close();
    }
}