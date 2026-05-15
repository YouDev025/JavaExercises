package algorithms;

import java.util.InputMismatchException;
import java.util.Scanner;

// Class to compute the Greatest Common Divisor (GCD) using the Euclidean Algorithm
// The GCD of two numbers is the largest number that divides both without a remainder
// Euclidean principle: GCD(a, b) = GCD(b, a % b) until b becomes 0
// Time Complexity: O(log(min(a,b))) | Space Complexity: O(log(min(a,b))) call stack
public class EuclideanGCD {

    // ─────────────────────────────────────────────
    //  APPROACH 1 — RECURSIVE GCD
    // ─────────────────────────────────────────────

    // Recursively computes GCD(a, b) using the Euclidean algorithm
    // Negative numbers are handled by converting to absolute values
    public static int gcd(int a, int b) {

        // Ensure both values are non-negative
        // GCD is always defined on positive integers
        a = Math.abs(a);
        b = Math.abs(b);

        // Base case: when b becomes 0 the GCD is a
        // GCD(a, 0) = a because every number divides 0
        if (b == 0) {
            return a;
        }

        // Recursive case: replace a with b and b with a % b
        // The remainder shrinks b toward 0 with every call
        return gcd(b, a % b);
    }

    // ─────────────────────────────────────────────
    //  APPROACH 2 — ITERATIVE GCD
    // ─────────────────────────────────────────────

    // Computes GCD(a, b) iteratively using the same Euclidean principle
    // Avoids call stack overhead — preferred for very large numbers
    // Time Complexity: O(log(min(a,b))) | Space Complexity: O(1)
    public static int gcdIterative(int a, int b) {

        // Ensure both values are non-negative
        a = Math.abs(a);
        b = Math.abs(b);

        // Repeatedly replace (a, b) with (b, a % b) until b reaches 0
        while (b != 0) {
            int remainder = a % b;

            // Slide the window forward — b becomes the new a
            a = b;

            // The remainder becomes the new b
            b = remainder;
        }

        // a now holds the GCD
        return a;
    }

    // ─────────────────────────────────────────────
    //  LCM USING GCD
    // ─────────────────────────────────────────────

    // Computes the Least Common Multiple of a and b using the GCD
    // LCM(a, b) = |a * b| / GCD(a, b)
    // Dividing before multiplying prevents integer overflow for large values
    public static long lcm(int a, int b) {

        // Special case: if either value is 0 the LCM is 0
        if (a == 0 || b == 0) return 0;

        // Cast to long before multiplying to avoid integer overflow
        return (long) Math.abs(a) / gcd(a, b) * Math.abs(b);
    }

    // ─────────────────────────────────────────────
    //  VERBOSE RECURSIVE GCD
    // ─────────────────────────────────────────────

    // Computes GCD(a, b) recursively while printing every step of the reduction
    // Visualizes how the call stack builds down and unwinds back up
    public static int gcdVerbose(int a, int b, int depth) {

        String indent = "  ".repeat(depth);

        // Ensure both values are non-negative
        a = Math.abs(a);
        b = Math.abs(b);

        // Base case: b is 0 — GCD found
        if (b == 0) {
            System.out.println(indent + "GCD(" + a + ", 0) = " + a
                    + "  ← base case  b = 0");
            return a;
        }

        // Print the current call showing the reduction step
        System.out.println(indent + "GCD(" + a + ", " + b + ")  →  "
                + a + " % " + b + " = " + (a % b));

        // Recurse with (b, a % b) and capture the result
        int result = gcdVerbose(b, a % b, depth + 1);

        // Print the resolved return value as the call stack unwinds
        System.out.println(indent + "GCD(" + a + ", " + b + ") = " + result);

        return result;
    }

    // ─────────────────────────────────────────────
    //  GCD OF AN ARRAY
    // ─────────────────────────────────────────────

    // Computes the GCD of all elements in an array by applying GCD pairwise
    // GCD(a, b, c) = GCD(GCD(a, b), c) — associative property of GCD
    public static int gcdArray(int[] arr) {

        // Start with the first element as the running GCD
        int result = arr[0];

        // Combine the running GCD with each subsequent element
        for (int i = 1; i < arr.length; i++) {
            result = gcd(result, arr[i]);

            // Early exit: GCD of 1 cannot be reduced further
            if (result == 1) return 1;
        }

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
            System.out.println("\n─── Euclidean GCD Menu ───");
            System.out.println("1. Compute GCD (recursive)");
            System.out.println("2. Compute GCD (iterative)");
            System.out.println("3. Compute GCD with step-by-step trace");
            System.out.println("4. Compute LCM of two numbers");
            System.out.println("5. Compute GCD of an array of numbers");
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
                    // Compute GCD using recursive Euclidean algorithm
                    int a1 = readInt(scanner, "Enter first number:  ");
                    int b1 = readInt(scanner, "Enter second number: ");
                    System.out.println("GCD(" + a1 + ", " + b1 + ") = " + gcd(a1, b1));
                    break;

                case 2:
                    // Compute GCD using iterative Euclidean algorithm
                    int a2 = readInt(scanner, "Enter first number:  ");
                    int b2 = readInt(scanner, "Enter second number: ");
                    System.out.println("GCD(" + a2 + ", " + b2 + ") = "
                            + gcdIterative(a2, b2));
                    break;

                case 3:
                    // Show every recursive reduction step with indentation
                    int a3 = readInt(scanner, "Enter first number:  ");
                    int b3 = readInt(scanner, "Enter second number: ");
                    System.out.println("\n─── Recursive Trace ───");
                    int result3 = gcdVerbose(Math.abs(a3), Math.abs(b3), 0);
                    System.out.println("\nGCD(" + a3 + ", " + b3 + ") = " + result3);
                    break;

                case 4:
                    // Compute LCM using the GCD relationship
                    int a4 = readInt(scanner, "Enter first number:  ");
                    int b4 = readInt(scanner, "Enter second number: ");
                    long lcmResult = lcm(a4, b4);
                    System.out.println("GCD(" + a4 + ", " + b4 + ") = " + gcd(a4, b4));
                    System.out.println("LCM(" + a4 + ", " + b4 + ") = " + lcmResult);
                    System.out.println("Verified: " + Math.abs(a4) + " * " + Math.abs(b4)
                            + " / GCD = " + ((long) Math.abs(a4) * Math.abs(b4))
                            + " / " + gcd(a4, b4) + " = " + lcmResult);
                    break;

                case 5:
                    // Compute GCD of a user-defined array of integers
                    int n = readPositiveInt(scanner, "Enter number of elements: ");
                    int[] arr = new int[n];
                    System.out.println("Enter " + n + " integers:");
                    for (int i = 0; i < n; i++) {
                        arr[i] = readInt(scanner, "  Element [" + (i + 1) + "]: ");
                    }
                    System.out.println("GCD of all elements = " + gcdArray(arr));
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