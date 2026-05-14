package algorithms;

import java.util.InputMismatchException;
import java.util.Scanner;

// Class to implement Tower of Hanoi using recursion
// Moves n disks from the source peg to the destination peg using an auxiliary peg
// Total moves required = 2^n - 1
// Time Complexity: O(2^n) | Space Complexity: O(n) due to recursive call stack
public class TowerOfHanoi {

    // Counter to track the total number of moves made during the entire process
    static int moveCount = 0;

    // ─────────────────────────────────────────────
    //  RECURSIVE METHOD
    // ─────────────────────────────────────────────

    // Recursively solves the Tower of Hanoi for n disks
    // source  — the peg where the disks currently are
    // destination — the peg where all disks need to be moved
    // auxiliary   — the peg used as a helper during the process
    public static void hanoi(int n, char source, char destination, char auxiliary, int depth) {

        // Base case: only one disk to move
        // Move it directly from source to destination — no helper peg needed
        if (n == 1) {
            moveCount++;
            System.out.println("  ".repeat(depth)
                    + "Move " + moveCount + ": Disk 1 "
                    + source + " ──► " + destination);
            return;
        }

        // Step 1: Recursively move the top n-1 disks from source to auxiliary
        // Destination acts as the helper peg during this step
        System.out.println("  ".repeat(depth)
                + "Move top " + (n - 1) + " disks:  "
                + source + " ──► " + auxiliary
                + "  (using " + destination + " as helper)");

        hanoi(n - 1, source, auxiliary, destination, depth + 1);

        // Step 2: Move the largest remaining disk directly from source to destination
        // This disk is now free since all smaller disks are on the auxiliary peg
        moveCount++;
        System.out.println("  ".repeat(depth)
                + "Move " + moveCount + ": Disk " + n + " "
                + source + " ──► " + destination
                + "  ← largest free disk placed");

        // Step 3: Recursively move the n-1 disks from auxiliary to destination
        // Source acts as the helper peg during this step
        System.out.println("  ".repeat(depth)
                + "Move top " + (n - 1) + " disks:  "
                + auxiliary + " ──► " + destination
                + "  (using " + source + " as helper)");

        hanoi(n - 1, auxiliary, destination, source, depth + 1);
    }

    // ─────────────────────────────────────────────
    //  PEG STATE VISUALIZER
    // ─────────────────────────────────────────────

    // Simulates the peg state after every individual move
    // Uses three stacks to track which disks are on each peg at any moment
    public static void hanoiVisual(int n) {

        // Initialize the three pegs as integer stacks
        // Peg A starts with all n disks loaded from largest (bottom) to smallest (top)
        int[] pegA = new int[n];
        int[] pegB = new int[n];
        int[] pegC = new int[n];
        int   topA = n - 1;
        int   topB = -1;
        int   topC = -1;

        // Load all disks onto peg A in descending order (largest at index 0)
        for (int i = 0; i < n; i++) {
            pegA[i] = n - i;
        }

        System.out.println("\n─── Initial State ───");
        printPegs(pegA, topA, pegB, topB, pegC, topC, n);

        // Generate and replay all moves to animate the peg states
        // Moves are captured as source/destination pairs and replayed here
        int[] moveFrom = new int[(int) Math.pow(2, n)];
        int[] moveTo   = new int[(int) Math.pow(2, n)];
        int[] moveIdx  = {0};

        collectMoves(n, 0, 2, 1, moveFrom, moveTo, moveIdx);

        int visualMoveCount = 0;

        for (int m = 0; m < moveIdx[0]; m++) {
            int from = moveFrom[m];
            int to   = moveTo[m];

            // Pop the top disk from the source peg
            int disk;
            int[] fromPeg, toPeg;
            int   fromTop, toTop;

            // Determine which peg to pop from and push to based on peg indices
            if (from == 0) { disk = pegA[topA]; topA--; }
            else if (from == 1) { disk = pegB[topB]; topB--; }
            else { disk = pegC[topC]; topC--; }

            if (to == 0) { topA++; pegA[topA] = disk; }
            else if (to == 1) { topB++; pegB[topB] = disk; }
            else { topC++; pegC[topC] = disk; }

            visualMoveCount++;
            char srcChar = (from == 0) ? 'A' : (from == 1) ? 'B' : 'C';
            char dstChar = (to   == 0) ? 'A' : (to   == 1) ? 'B' : 'C';

            System.out.println("\n─── Move " + visualMoveCount
                    + ": Disk " + disk + "  " + srcChar + " ──► " + dstChar + " ───");
            printPegs(pegA, topA, pegB, topB, pegC, topC, n);
        }
    }

    // Collects all moves as source/destination peg index pairs without printing
    // Used exclusively to replay moves in the visual simulator
    public static void collectMoves(int n, int src, int dst, int aux,
                                    int[] moveFrom, int[] moveTo, int[] idx) {
        if (n == 1) {
            moveFrom[idx[0]] = src;
            moveTo[idx[0]]   = dst;
            idx[0]++;
            return;
        }
        collectMoves(n - 1, src, aux, dst, moveFrom, moveTo, idx);
        moveFrom[idx[0]] = src;
        moveTo[idx[0]]   = dst;
        idx[0]++;
        collectMoves(n - 1, aux, dst, src, moveFrom, moveTo, idx);
    }

    // Prints the current state of all three pegs as stacked disk representations
    // Each peg shows its disks from bottom to top with disk size as width
    public static void printPegs(int[] pegA, int topA,
                                 int[] pegB, int topB,
                                 int[] pegC, int topC, int n) {

        // Print each row from the bottom of the peg to the top
        for (int row = n - 1; row >= 0; row--) {
            String a = (row <= topA) ? diskLabel(pegA[row], n) : emptySlot(n);
            String b = (row <= topB) ? diskLabel(pegB[row], n) : emptySlot(n);
            String c = (row <= topC) ? diskLabel(pegC[row], n) : emptySlot(n);
            System.out.println("  " + a + "    " + b + "    " + c);
        }

        // Print the peg base labels
        int width = n * 2 + 1;
        String base = "─".repeat(width);
        System.out.println("  " + base + "    " + base + "    " + base);
        System.out.println("  " + center("A", width)
                + "    " + center("B", width)
                + "    " + center("C", width));
    }

    // Returns a visual representation of a disk of the given size
    // Wider disks represent larger disk numbers
    public static String diskLabel(int size, int maxSize) {
        int width     = maxSize * 2 + 1;
        int diskWidth = size * 2 - 1;
        int padding   = (width - diskWidth) / 2;
        return " ".repeat(padding) + "█".repeat(diskWidth) + " ".repeat(padding);
    }

    // Returns an empty slot on a peg — shown as a single vertical bar
    public static String emptySlot(int maxSize) {
        int width = maxSize * 2 + 1;
        return " ".repeat(maxSize) + "│" + " ".repeat(maxSize);
    }

    // Centers a string within the given total width by padding with spaces
    public static String center(String text, int width) {
        int padding = (width - text.length()) / 2;
        return " ".repeat(padding) + text + " ".repeat(padding);
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

    // Helper method to read a valid integer within a specific range
    // Rejects values outside the given minimum and maximum bounds
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
            System.out.println("\n─── Tower of Hanoi Menu ───");
            System.out.println("1. Print all moves with recursive trace");
            System.out.println("2. Visual peg simulation after each move");
            System.out.println("3. Show move count formula only");
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
                    // Print all moves with recursive call trace
                    int n1 = readIntInRange(scanner,
                            "Enter number of disks (1-10): ", 1, 10);
                    moveCount = 0;
                    System.out.println("\n─── Tower of Hanoi — " + n1
                            + " disk(s) — A ──► C (using B) ───");
                    hanoi(n1, 'A', 'C', 'B', 0);
                    System.out.println("\nTotal moves: " + moveCount
                            + "  (2^" + n1 + " - 1 = "
                            + ((int) Math.pow(2, n1) - 1) + ")");
                    break;

                case 2:
                    // Show visual peg state after every individual move
                    // Capped at 5 disks to keep output readable
                    int n2 = readIntInRange(scanner,
                            "Enter number of disks (1-5): ", 1, 5);
                    hanoiVisual(n2);
                    System.out.println("\nTotal moves: "
                            + ((int) Math.pow(2, n2) - 1));
                    break;

                case 3:
                    // Display the move count formula for any number of disks
                    int n3 = readIntInRange(scanner,
                            "Enter number of disks (1-20): ", 1, 20);
                    long totalMoves = (long) Math.pow(2, n3) - 1;
                    System.out.println("\nDisks : " + n3);
                    System.out.println("Formula: 2^" + n3 + " - 1 = " + totalMoves + " moves");
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