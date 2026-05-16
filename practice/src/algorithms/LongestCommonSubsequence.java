package algorithms;

import java.util.InputMismatchException;
import java.util.Scanner;

// Class to find the Longest Common Subsequence (LCS) of two strings
// LCS is the longest sequence of characters that appears in both strings
// in the same relative order but not necessarily contiguously
// Time Complexity: O(m * n) | Space Complexity: O(m * n) for the DP table
public class LongestCommonSubsequence {

    // ─────────────────────────────────────────────
    //  APPROACH 1 — LCS LENGTH ONLY (BOTTOM-UP DP)
    // ─────────────────────────────────────────────

    // Builds a 2D DP table where dp[i][j] = length of LCS of
    // the first i characters of str1 and the first j characters of str2
    // Returns only the length without reconstructing the actual LCS string
    public static int lcsLength(String str1, String str2) {

        int m = str1.length();
        int n = str2.length();

        // dp[i][j] = LCS length of str1[0..i-1] and str2[0..j-1]
        // Row 0 and column 0 are base cases (empty string) — LCS length is 0
        int[][] dp = new int[m + 1][n + 1];

        // Fill the DP table row by row
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {

                // Characters match — extend the LCS found so far by 1
                // This character is included in the LCS
                if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;

                } else {

                    // Characters do not match — take the best LCS found
                    // by either skipping the current character of str1 or str2
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        // Bottom-right cell holds the LCS length for the full strings
        return dp[m][n];
    }

    // ─────────────────────────────────────────────
    //  APPROACH 2 — LCS LENGTH AND ACTUAL STRING  ⋆ Challenge
    // ─────────────────────────────────────────────

    // Builds the full DP table and then traces back through it
    // to reconstruct the actual LCS string character by character
    public static String lcs(String str1, String str2) {

        int m = str1.length();
        int n = str2.length();

        // Build the DP table — same logic as lcsLength
        int[][] dp = new int[m + 1][n + 1];

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        // Traceback: reconstruct the LCS string by walking back through the DP table
        // Start from dp[m][n] and move toward dp[0][0]
        StringBuilder lcs = new StringBuilder();
        int i = m;
        int j = n;

        while (i > 0 && j > 0) {

            // If the characters match this position contributed to the LCS
            // Include this character and move diagonally up-left
            if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                lcs.append(str1.charAt(i - 1));
                i--;
                j--;

                // Move in the direction of the larger neighbor
                // If the cell above is larger, move up (skip str1's character)
            } else if (dp[i - 1][j] > dp[i][j - 1]) {
                i--;

                // Otherwise move left (skip str2's character)
            } else {
                j--;
            }
        }

        // The LCS was built in reverse order during traceback — reverse it back
        return lcs.reverse().toString();
    }

    // ─────────────────────────────────────────────
    //  DP TABLE PRINTER
    // ─────────────────────────────────────────────

    // Builds and prints the full DP table with row and column headers
    // Shows exactly how each cell was computed from its neighbors
    public static void printDPTable(String str1, String str2) {

        int m = str1.length();
        int n = str2.length();

        int[][] dp = new int[m + 1][n + 1];

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        System.out.println("\n─── DP Table ───");

        // Print column headers (str2 characters)
        System.out.print("      ");
        System.out.printf("%-4s", "ε");
        for (int j = 0; j < n; j++) {
            System.out.printf("%-4c", str2.charAt(j));
        }
        System.out.println();
        System.out.println("    " + "─".repeat(4 * (n + 2)));

        // Print each row with its str1 character label
        for (int i = 0; i <= m; i++) {
            if (i == 0) {
                System.out.printf("%-6s", "ε |");
            } else {
                System.out.printf("%-6s", str1.charAt(i - 1) + " |");
            }
            for (int j = 0; j <= n; j++) {
                System.out.printf("%-4d", dp[i][j]);
            }
            System.out.println();
        }

        System.out.println();
    }

    // ─────────────────────────────────────────────
    //  VERBOSE TRACEBACK PRINTER
    // ─────────────────────────────────────────────

    // Prints every decision made during the traceback phase
    // Clearly shows which characters were included and which were skipped
    public static void printTraceback(String str1, String str2) {

        int m = str1.length();
        int n = str2.length();

        int[][] dp = new int[m + 1][n + 1];

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        System.out.println("\n─── Traceback Steps ───");

        StringBuilder result = new StringBuilder();
        int i = m;
        int j = n;
        int step = 1;

        while (i > 0 && j > 0) {

            char c1 = str1.charAt(i - 1);
            char c2 = str2.charAt(j - 1);

            if (c1 == c2) {
                // Matching characters — include in LCS and move diagonally
                System.out.println("Step " + step++ + ": '"
                        + c1 + "' matches at str1[" + (i-1) + "] and str2[" + (j-1)
                        + "]  →  include in LCS, move diagonal ↖");
                result.append(c1);
                i--;
                j--;

            } else if (dp[i - 1][j] > dp[i][j - 1]) {
                // Larger value is above — skip str1's current character
                System.out.println("Step " + step++ + ": '"
                        + c1 + "' vs '" + c2 + "' no match  →  move up ↑  (skip '"
                        + c1 + "' from str1)");
                i--;

            } else {
                // Larger or equal value is to the left — skip str2's current character
                System.out.println("Step " + step++ + ": '"
                        + c1 + "' vs '" + c2 + "' no match  →  move left ←  (skip '"
                        + c2 + "' from str2)");
                j--;
            }
        }

        System.out.println("\nLCS (reversed during traceback): \""
                + result + "\"");
        System.out.println("LCS (correct order):             \""
                + result.reverse() + "\"");
    }

    // ─────────────────────────────────────────────
    //  HIGHLIGHT LCS IN ORIGINAL STRINGS
    // ─────────────────────────────────────────────

    // Prints both original strings with LCS characters marked by brackets
    // Helps visualize exactly which characters from each string form the LCS
    public static void highlightLCS(String str1, String str2, String lcsStr) {

        System.out.println("\n─── LCS Highlighted (brackets mark matched characters) ───");

        // Mark matched characters in str1
        StringBuilder marked1 = new StringBuilder();
        int lcsIdx = 0;
        for (int i = 0; i < str1.length() && lcsIdx < lcsStr.length(); i++) {
            if (str1.charAt(i) == lcsStr.charAt(lcsIdx)) {
                marked1.append("[").append(str1.charAt(i)).append("]");
                lcsIdx++;
            } else {
                marked1.append(str1.charAt(i));
            }
        }
        // Append any remaining unmatched characters
        while (lcsIdx <= str1.length() - 1 &&
                marked1.length() - str1.length() < lcsStr.length() * 2) {
            marked1.append(str1.charAt(str1.length() - 1));
            break;
        }

        // Mark matched characters in str2
        StringBuilder marked2 = new StringBuilder();
        lcsIdx = 0;
        for (int i = 0; i < str2.length() && lcsIdx < lcsStr.length(); i++) {
            if (str2.charAt(i) == lcsStr.charAt(lcsIdx)) {
                marked2.append("[").append(str2.charAt(i)).append("]");
                lcsIdx++;
            } else {
                marked2.append(str2.charAt(i));
            }
        }

        System.out.println("str1: " + marked1);
        System.out.println("str2: " + marked2);
        System.out.println("LCS:  " + lcsStr + "  (length " + lcsStr.length() + ")");
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

    // Helper method to read a non-empty string from the user
    public static String readString(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = scanner.nextLine().trim();
            if (!value.isEmpty()) {
                return value;
            }
            System.out.println("  Invalid input. Please enter a non-empty string.");
        }
    }

    // ─────────────────────────────────────────────
    //  MAIN MENU
    // ─────────────────────────────────────────────

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n─── LCS Menu ───");
            System.out.println("1. Find LCS length only");
            System.out.println("2. Find LCS length and actual string");
            System.out.println("3. Print full DP table");
            System.out.println("4. Print traceback steps");
            System.out.println("5. Highlight LCS in both strings");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");

            int choice;

            try {
                choice = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                scanner.nextLine();
                System.out.println("  Invalid option. Please enter a number between 1 and 6.");
                continue;
            }

            switch (choice) {

                case 1:
                    // Compute and display only the LCS length
                    String s1a = readString(scanner, "Enter first string:  ");
                    String s2a = readString(scanner, "Enter second string: ");
                    System.out.println("LCS length: " + lcsLength(s1a, s2a));
                    break;

                case 2:
                    // Compute and display both the LCS length and the actual LCS string
                    String s1b  = readString(scanner, "Enter first string:  ");
                    String s2b  = readString(scanner, "Enter second string: ");
                    String lcsB = lcs(s1b, s2b);
                    System.out.println("LCS length: " + lcsB.length());
                    System.out.println("LCS string: \"" + lcsB + "\"");
                    break;

                case 3:
                    // Print the complete DP table for the two input strings
                    String s1c = readString(scanner, "Enter first string:  ");
                    String s2c = readString(scanner, "Enter second string: ");
                    printDPTable(s1c, s2c);
                    System.out.println("LCS: \"" + lcs(s1c, s2c) + "\"");
                    break;

                case 4:
                    // Print every traceback decision step by step
                    String s1d = readString(scanner, "Enter first string:  ");
                    String s2d = readString(scanner, "Enter second string: ");
                    printDPTable(s1d, s2d);
                    printTraceback(s1d, s2d);
                    break;

                case 5:
                    // Highlight which characters in each string form the LCS
                    String s1e  = readString(scanner, "Enter first string:  ");
                    String s2e  = readString(scanner, "Enter second string: ");
                    String lcsE = lcs(s1e, s2e);
                    highlightLCS(s1e, s2e, lcsE);
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