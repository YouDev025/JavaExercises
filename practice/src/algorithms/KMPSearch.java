package algorithms;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

// Class to implement the Knuth-Morris-Pratt (KMP) pattern matching algorithm
// KMP avoids redundant comparisons by precomputing a failure function (LPS array)
// that tells how far to shift the pattern on a mismatch without re-examining text
// Time Complexity: O(n + m) | Space Complexity: O(m)
// n = length of text | m = length of pattern
public class KMPSearch {

    // ─────────────────────────────────────────────
    //  STEP 1 — BUILD LPS ARRAY (FAILURE FUNCTION)
    // ─────────────────────────────────────────────

    // Builds the Longest Proper Prefix which is also Suffix (LPS) array for the pattern
    // lps[i] = length of the longest proper prefix of pattern[0..i]
    //          that is also a suffix of pattern[0..i]
    // This array allows KMP to skip characters instead of restarting from scratch
    public static int[] buildLPS(String pattern) {

        int m   = pattern.length();
        int[] lps = new int[m];

        // lps[0] is always 0 — a single character has no proper prefix or suffix
        lps[0] = 0;

        // len tracks the length of the current longest prefix-suffix being evaluated
        int len = 0;

        // i starts from index 1 — we fill lps[1..m-1]
        int i = 1;

        while (i < m) {

            // Characters match — extend the current prefix-suffix by 1
            if (pattern.charAt(i) == pattern.charAt(len)) {
                len++;
                lps[i] = len;
                i++;

            } else {

                // Mismatch after some matches
                if (len != 0) {

                    // Fall back to the previous longest prefix-suffix
                    // Do NOT increment i — we re-examine pattern[i] against a shorter prefix
                    len = lps[len - 1];

                } else {

                    // No prefix-suffix possible for this position
                    lps[i] = 0;
                    i++;
                }
            }
        }

        return lps;
    }

    // ─────────────────────────────────────────────
    //  STEP 2 — KMP SEARCH
    // ─────────────────────────────────────────────

    // Searches for all occurrences of pattern in text using the KMP algorithm
    // Returns a list of all starting indices where the pattern is found
    public static List<Integer> kmpSearch(String text, String pattern) {

        List<Integer> matches = new ArrayList<>();

        int n = text.length();
        int m = pattern.length();

        // Edge case: pattern longer than text — no match possible
        if (m > n) return matches;

        // Precompute the LPS array for the pattern
        int[] lps = buildLPS(pattern);

        // i traverses the text | j traverses the pattern
        int i = 0;
        int j = 0;

        while (i < n) {

            // Characters match — advance both pointers
            if (text.charAt(i) == pattern.charAt(j)) {
                i++;
                j++;
            }

            // Full pattern matched — record the starting index
            if (j == m) {

                // Starting index of this match in the text
                matches.add(i - j);

                // Use LPS to find the next potential match without restarting
                // lps[j-1] gives the longest prefix-suffix to reuse
                j = lps[j - 1];
            }

            // Mismatch after some matches
            else if (i < n && text.charAt(i) != pattern.charAt(j)) {

                if (j != 0) {
                    // Shift pattern using LPS — skip already matched characters
                    // Do NOT move i — re-examine text[i] with a shorter pattern prefix
                    j = lps[j - 1];
                } else {
                    // No prefix to fall back to — just advance text pointer
                    i++;
                }
            }
        }

        return matches;
    }

    // ─────────────────────────────────────────────
    //  VERBOSE SEARCH — STEP BY STEP
    // ─────────────────────────────────────────────

    // Runs KMP search and prints every comparison and shift decision
    // Visualizes exactly where matches and mismatches occur in the text
    public static void kmpSearchVerbose(String text, String pattern) {

        int n   = text.length();
        int m   = pattern.length();
        int[] lps = buildLPS(pattern);

        System.out.println("\n─── KMP Search Steps ───");
        System.out.println("Text:    \"" + text + "\"");
        System.out.println("Pattern: \"" + pattern + "\"");
        System.out.println("LPS:     " + java.util.Arrays.toString(lps));
        System.out.println();

        int i       = 0;
        int j       = 0;
        int step    = 1;
        int matches = 0;

        while (i < n) {

            char tc = text.charAt(i);
            char pc = pattern.charAt(j);

            if (tc == pc) {

                // Print the match between text[i] and pattern[j]
                System.out.println("Step " + step++ + ": text[" + i + "]='" + tc
                        + "' == pattern[" + j + "]='" + pc + "'  →  match, advance both");
                i++;
                j++;

            }

            if (j == m) {

                // Full pattern found — report the match position
                int start = i - j;
                matches++;
                System.out.println("\n★ Match " + matches + " found at index "
                        + start + "  (text[" + start + ".." + (i - 1) + "])\n");

                // Shift pattern using LPS for the next potential match
                j = lps[j - 1];

            } else if (i < n && text.charAt(i) != pattern.charAt(j)) {

                if (j != 0) {
                    // Mismatch with fallback — shift pattern using LPS
                    System.out.println("Step " + step++ + ": text[" + i + "]='"
                            + text.charAt(i) + "' != pattern[" + j + "]='"
                            + pattern.charAt(j) + "'  →  mismatch, shift pattern to j="
                            + lps[j - 1] + " using LPS");
                    j = lps[j - 1];
                } else {
                    // Mismatch with no fallback — advance text pointer only
                    System.out.println("Step " + step++ + ": text[" + i + "]='"
                            + text.charAt(i) + "' != pattern[" + j + "]='"
                            + pattern.charAt(j) + "'  →  mismatch, advance text only");
                    i++;
                }
            }
        }

        if (matches == 0) {
            System.out.println("Pattern \"" + pattern + "\" not found in the text.");
        } else {
            System.out.println("Total matches found: " + matches);
        }
    }

    // ─────────────────────────────────────────────
    //  LPS TABLE PRINTER
    // ─────────────────────────────────────────────

    // Prints the LPS array with a visual explanation of each value
    // Helps understand how the failure function is precomputed
    public static void printLPSTable(String pattern) {

        int[] lps = buildLPS(pattern);

        System.out.println("\n─── LPS (Failure Function) Table ───");
        System.out.printf("%-10s %-10s %-10s %-20s%n",
                "Index", "Char", "LPS[i]", "Longest Prefix-Suffix");
        System.out.println("─".repeat(54));

        for (int i = 0; i < pattern.length(); i++) {
            String prefixSuffix = lps[i] == 0 ? "none"
                    : "\"" + pattern.substring(0, lps[i]) + "\"";
            System.out.printf("%-10d %-10c %-10d %-20s%n",
                    i, pattern.charAt(i), lps[i], prefixSuffix);
        }
    }

    // ─────────────────────────────────────────────
    //  MATCH HIGHLIGHTER
    // ─────────────────────────────────────────────

    // Prints the text with all pattern occurrences enclosed in brackets
    // Gives a clear visual of where each match appears in the full text
    public static void highlightMatches(String text, String pattern, List<Integer> matches) {

        if (matches.isEmpty()) {
            System.out.println("No matches to highlight.");
            return;
        }

        System.out.println("\n─── Matches Highlighted (brackets mark each match) ───");

        StringBuilder highlighted = new StringBuilder();
        int m   = pattern.length();
        int i   = 0;
        int idx = 0;

        // Walk through the text and insert brackets around each match position
        while (i < text.length()) {
            if (idx < matches.size() && i == matches.get(idx)) {
                highlighted.append("[").append(text, i, i + m).append("]");
                i += m;
                idx++;
            } else {
                highlighted.append(text.charAt(i));
                i++;
            }
        }

        System.out.println("Text:    " + highlighted);
        System.out.println("Pattern: \"" + pattern + "\"");
        System.out.println("Found at indices: " + matches);
        System.out.println("Total matches:    " + matches.size());
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
            System.out.println("\n─── KMP Pattern Matching Menu ───");
            System.out.println("1. Search for pattern in text");
            System.out.println("2. Search with step-by-step trace");
            System.out.println("3. Build and print LPS table for a pattern");
            System.out.println("4. Highlight all matches in text");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            int choice;

            try {
                choice = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                scanner.nextLine();
                System.out.println("  Invalid option. Please enter a number between 1 and 5.");
                continue;
            }

            switch (choice) {

                case 1:
                    // Perform KMP search and display all match positions
                    String text1    = readString(scanner, "Enter text:    ");
                    String pattern1 = readString(scanner, "Enter pattern: ");
                    List<Integer> matches1 = kmpSearch(text1, pattern1);

                    if (matches1.isEmpty()) {
                        System.out.println("Pattern \"" + pattern1
                                + "\" not found in the text.");
                    } else {
                        System.out.println("Pattern \"" + pattern1 + "\" found at indices: "
                                + matches1);
                        System.out.println("Total occurrences: " + matches1.size());
                    }
                    break;

                case 2:
                    // Run verbose KMP showing every comparison and shift step
                    String text2    = readString(scanner, "Enter text:    ");
                    String pattern2 = readString(scanner, "Enter pattern: ");
                    kmpSearchVerbose(text2, pattern2);
                    break;

                case 3:
                    // Build and display the LPS failure function for a given pattern
                    String pattern3 = readString(scanner, "Enter pattern: ");
                    printLPSTable(pattern3);
                    break;

                case 4:
                    // Search and highlight all matches within the full text
                    String text4    = readString(scanner, "Enter text:    ");
                    String pattern4 = readString(scanner, "Enter pattern: ");
                    List<Integer> matches4 = kmpSearch(text4, pattern4);
                    highlightMatches(text4, pattern4, matches4);
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