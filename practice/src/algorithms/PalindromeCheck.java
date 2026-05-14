package algorithms;

import java.util.InputMismatchException;
import java.util.Scanner;

// Class to check if a string is a palindrome using recursion
// A palindrome reads the same forwards and backwards (e.g. "racecar", "madam")
// Compares the outermost characters and recurses inward until the base case is reached
// Time Complexity: O(n) | Space Complexity: O(n) due to recursive call stack
public class PalindromeCheck {

    // ─────────────────────────────────────────────
    //  RECURSIVE METHOD — CASE SENSITIVE
    // ─────────────────────────────────────────────

    // Recursively checks if the substring from index left to right is a palindrome
    // Compares the outermost characters and moves inward with each recursive call
    public static boolean isPalindrome(String str, int left, int right) {

        // Base case: zero or one character remaining in the current range
        // A single character or empty string is always a palindrome
        if (left >= right) {
            return true;
        }

        // If the outermost characters do not match the string is not a palindrome
        // No further recursion is needed — return false immediately
        if (str.charAt(left) != str.charAt(right)) {
            return false;
        }

        // Recursive case: outermost characters match
        // Move both pointers inward and check the next pair
        return isPalindrome(str, left + 1, right - 1);
    }

    // ─────────────────────────────────────────────
    //  RECURSIVE METHOD — CASE INSENSITIVE
    // ─────────────────────────────────────────────

    // Same logic as isPalindrome but ignores character casing
    // and skips non-alphanumeric characters (spaces, punctuation)
    // Useful for checking natural language phrases like "A man a plan a canal Panama"
    public static boolean isPalindromeIgnoreCase(String str, int left, int right) {

        // Base case: pointers have crossed — all valid characters matched
        if (left >= right) {
            return true;
        }

        // Skip non-alphanumeric characters on the left side
        // Advances left pointer past spaces, punctuation, and special characters
        while (left < right && !Character.isLetterOrDigit(str.charAt(left))) {
            left++;
        }

        // Skip non-alphanumeric characters on the right side
        // Retreats right pointer past spaces, punctuation, and special characters
        while (left < right && !Character.isLetterOrDigit(str.charAt(right))) {
            right--;
        }

        // Re-check base case after skipping non-alphanumeric characters
        if (left >= right) {
            return true;
        }

        // Compare the current outermost valid characters in a case-insensitive way
        if (Character.toLowerCase(str.charAt(left)) != Character.toLowerCase(str.charAt(right))) {
            return false;
        }

        // Recursive case: valid characters match — move both pointers inward
        return isPalindromeIgnoreCase(str, left + 1, right - 1);
    }

    // ─────────────────────────────────────────────
    //  VERBOSE RECURSIVE METHOD
    // ─────────────────────────────────────────────

    // Checks if a string is a palindrome while printing each comparison step
    // Visualizes how the call stack builds inward and unwinds outward
    public static boolean isPalindromeVerbose(String str, int left, int right, int depth) {

        String indent = "  ".repeat(depth);

        // Base case: single character or empty range — always a palindrome
        if (left >= right) {
            System.out.println(indent + "Base case reached — 0 or 1 character remaining → true");
            return true;
        }

        char leftChar  = str.charAt(left);
        char rightChar = str.charAt(right);

        System.out.println(indent + "Compare  '" + leftChar + "' (index " + left  + ")"
                + " and '" + rightChar + "' (index " + right + ")");

        // Mismatch found — print the failure and return false
        if (leftChar != rightChar) {
            System.out.println(indent + "Mismatch found → not a palindrome");
            return false;
        }

        // Characters match — recurse inward and print the result on the way back
        System.out.println(indent + "Match → recurse inward");
        boolean result = isPalindromeVerbose(str, left + 1, right - 1, depth + 1);
        System.out.println(indent + "Return " + result);
        return result;
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

    // Helper method to read a non-empty string from the user
    // Rejects blank input and prompts the user to re-enter
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
            System.out.println("\n─── Palindrome Check Menu ───");
            System.out.println("1. Check palindrome (case sensitive)");
            System.out.println("2. Check palindrome (ignore case and spaces)");
            System.out.println("3. Check with step-by-step trace");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");

            int choice;

            try {
                choice = scanner.nextInt();
                // Consume the leftover newline before reading strings
                scanner.nextLine();
            } catch (InputMismatchException e) {
                // Clear the invalid token and prompt the user to try again
                scanner.nextLine();
                System.out.println("  Invalid option. Please enter a number between 1 and 4.");
                continue;
            }

            switch (choice) {

                case 1:
                    // Check if the input string is a palindrome (case sensitive)
                    String input1  = readString(scanner, "Enter a string: ");
                    boolean result1 = isPalindrome(input1, 0, input1.length() - 1);
                    System.out.println("\n\"" + input1 + "\" is "
                            + (result1 ? "" : "NOT ") + "a palindrome.");
                    break;

                case 2:
                    // Check if the input string is a palindrome ignoring case and non-alphanumeric characters
                    String input2  = readString(scanner, "Enter a string: ");
                    boolean result2 = isPalindromeIgnoreCase(input2, 0, input2.length() - 1);
                    System.out.println("\n\"" + input2 + "\" is "
                            + (result2 ? "" : "NOT ") + "a palindrome (ignoring case and spaces).");
                    break;

                case 3:
                    // Show every character comparison and recursive call step by step
                    String input3 = readString(scanner, "Enter a string: ");
                    System.out.println("\n─── Recursive Trace for \"" + input3 + "\" ───");
                    boolean result3 = isPalindromeVerbose(input3, 0, input3.length() - 1, 0);
                    System.out.println("\n\"" + input3 + "\" is "
                            + (result3 ? "" : "NOT ") + "a palindrome.");
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