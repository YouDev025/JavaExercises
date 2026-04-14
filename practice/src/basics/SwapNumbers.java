package basics;

import java.util.Scanner;
import java.util.InputMismatchException;

/**
 * Program to swap two numbers without using a third variable
 * Demonstrates 4 different methods:
 * 1. Arithmetic (Addition & Subtraction)
 * 2. Arithmetic (Multiplication & Division)
 * 3. Bitwise XOR operator
 * 4. Single-line swapping
 */
public class SwapNumbers {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean continueRunning = true;

        System.out.println("=" .repeat(60));
        System.out.println("🔄 SWAP TWO NUMBERS WITHOUT THIRD VARIABLE");
        System.out.println("=" .repeat(60));

        while (continueRunning) {
            // Get input numbers from user
            double num1 = getValidNumber(scanner, "Enter first number: ");
            double num2 = getValidNumber(scanner, "Enter second number: ");

            // Display menu for swapping method
            System.out.println("\n📋 Choose swapping method:");
            System.out.println("1. Arithmetic (Addition/Subtraction)");
            System.out.println("2. Arithmetic (Multiplication/Division)");
            System.out.println("3. Bitwise XOR (for integers only)");
            System.out.println("4. Single-line swapping (magic formula)");
            System.out.println("5. Show ALL methods");
            System.out.println("6. Exit");
            System.out.print("\nEnter your choice (1-6): ");

            int choice = 0;
            try {
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("❌ Invalid input! Please enter a number between 1 and 6.");
                scanner.next();
                continue;
            }

            System.out.println("\n" + "=".repeat(60));
            System.out.println("📊 ORIGINAL VALUES:");
            System.out.printf("   First number  : %.4f%n", num1);
            System.out.printf("   Second number : %.4f%n", num2);
            System.out.println("=".repeat(60));

            switch (choice) {
                case 1:
                    swapUsingAddSub(num1, num2);
                    break;
                case 2:
                    swapUsingMulDiv(num1, num2);
                    break;
                case 3:
                    if (isInteger(num1) && isInteger(num2)) {
                        swapUsingXOR((int)num1, (int)num2);
                    } else {
                        System.out.println("\n⚠️  Bitwise XOR works only with integers!");
                        System.out.println("   Your numbers have decimal parts. Using arithmetic method instead:\n");
                        swapUsingAddSub(num1, num2);
                    }
                    break;
                case 4:
                    swapSingleLine(num1, num2);
                    break;
                case 5:
                    showAllMethods(num1, num2);
                    break;
                case 6:
                    continueRunning = false;
                    System.out.println("\n👋 Thank you for using Swap Program!");
                    break;
                default:
                    System.out.println("❌ Invalid choice! Please select 1-6.");
            }

            if (choice != 6 && continueRunning) {
                System.out.print("\n\nDo you want to try again? (yes/no): ");
                String tryAgain = scanner.next().toLowerCase();
                if (!tryAgain.equals("yes") && !tryAgain.equals("y")) {
                    continueRunning = false;
                    System.out.println("\n👋 Goodbye!");
                } else {
                    System.out.println("\n" + "🔄".repeat(30) + "\n");
                }
            }
        }
        scanner.close();
    }

    // METHOD 1: Using Addition and Subtraction
    // Works for all numeric types (integers and decimals)
    // Formula: a = a + b; b = a - b; a = a - b;
    private static void swapUsingAddSub(double a, double b) {
        System.out.println("\n🔧 METHOD 1: Arithmetic (Addition/Subtraction)");
        System.out.println("   Steps:");
        System.out.println("   1. a = a + b");
        System.out.println("   2. b = a - b");
        System.out.println("   3. a = a - b");

        System.out.println("\n   Before swap: a = " + a + ", b = " + b);

        a = a + b;  // a becomes sum of both
        b = a - b;  // b gets original a (sum - b)
        a = a - b;  // a gets original b (sum - new b)

        System.out.println("   After swap : a = " + a + ", b = " + b);
        System.out.println("\n✅ Swapped successfully!");
        System.out.println("⚠️  Note: May cause overflow for very large numbers.");
    }

    // METHOD 2: Using Multiplication and Division
    // Works for non-zero numbers
    // Formula: a = a * b; b = a / b; a = a / b;
    private static void swapUsingMulDiv(double a, double b) {
        System.out.println("\n🔧 METHOD 2: Arithmetic (Multiplication/Division)");
        System.out.println("   Steps:");
        System.out.println("   1. a = a * b");
        System.out.println("   2. b = a / b");
        System.out.println("   3. a = a / b");

        if (a == 0 || b == 0) {
            System.out.println("\n⚠️  Warning: One of the numbers is zero!");
            System.out.println("   Multiplication/division method works best with non-zero numbers.");
            System.out.println("   Using addition/subtraction method instead:\n");
            swapUsingAddSub(a, b);
            return;
        }

        System.out.println("\n   Before swap: a = " + a + ", b = " + b);

        a = a * b;  // a becomes product
        b = a / b;  // b gets original a (product / b)
        a = a / b;  // a gets original b (product / new b)

        System.out.println("   After swap : a = " + a + ", b = " + b);
        System.out.println("\n✅ Swapped successfully!");
        System.out.println("⚠️  Note: May cause overflow or precision loss with decimals.");
    }

    // METHOD 3: Using Bitwise XOR (^)
    // Only works for integers
    // Formula: a = a ^ b; b = a ^ b; a = a ^ b;
    private static void swapUsingXOR(int a, int b) {
        System.out.println("\n🔧 METHOD 3: Bitwise XOR Operator (^)");
        System.out.println("   XOR Truth Table:");
        System.out.println("   0 ^ 0 = 0  |  1 ^ 1 = 0");
        System.out.println("   0 ^ 1 = 1  |  1 ^ 0 = 1");

        System.out.println("\n   Steps:");
        System.out.println("   1. a = a ^ b");
        System.out.println("   2. b = a ^ b");
        System.out.println("   3. a = a ^ b");

        System.out.println("\n   Binary representation:");
        System.out.printf("   a = %d (binary: %8s)%n", a, Integer.toBinaryString(a));
        System.out.printf("   b = %d (binary: %8s)%n", b, Integer.toBinaryString(b));

        System.out.println("\n   Before swap: a = " + a + ", b = " + b);

        a = a ^ b;  // a becomes a^b
        b = a ^ b;  // b becomes original a
        a = a ^ b;  // a becomes original b

        System.out.println("   After swap : a = " + a + ", b = " + b);

        System.out.println("\n   Verification:");
        System.out.printf("   a = %d (binary: %8s)%n", a, Integer.toBinaryString(a));
        System.out.printf("   b = %d (binary: %8s)%n", b, Integer.toBinaryString(b));

        System.out.println("\n✅ Swapped successfully!");
        System.out.println("💡 Advantage: No overflow issues and very fast!");
        System.out.println("⚠️  Note: Works only for integers.");
    }

    // METHOD 4: Single-line swapping (using tuple-like behavior)
    // Works in some languages, Java requires a trick
    private static void swapSingleLine(double a, double b) {
        System.out.println("\n🔧 METHOD 4: Single-line Swapping (Using expression)");
        System.out.println("   Formula: b = (a + b) - (a = b);");

        System.out.println("\n   Before swap: a = " + a + ", b = " + b);

        // This single line swaps the values
        b = (a + b) - (a = b);

        System.out.println("   After swap : a = " + a + ", b = " + b);
        System.out.println("\n✅ Swapped successfully!");
        System.out.println("💡 This works because the expression (a = b) assigns b to a and returns the old value of b.");
    }

    // Show all methods for demonstration
    private static void showAllMethods(double a, double b) {
        System.out.println("\n🎯 DEMONSTRATING ALL SWAPPING METHODS");
        System.out.println("=".repeat(60));

        // Method 1: Addition/Subtraction
        System.out.println("\n1️⃣ ADDITION/SUBTRACTION METHOD:");
        double a1 = a, b1 = b;
        a1 = a1 + b1;
        b1 = a1 - b1;
        a1 = a1 - b1;
        System.out.printf("   Original: (%.4f, %.4f) → Swapped: (%.4f, %.4f)%n", a, b, a1, b1);

        // Method 2: Multiplication/Division (if non-zero)
        System.out.println("\n2️⃣ MULTIPLICATION/DIVISION METHOD:");
        if (a != 0 && b != 0) {
            double a2 = a, b2 = b;
            a2 = a2 * b2;
            b2 = a2 / b2;
            a2 = a2 / b2;
            System.out.printf("   Original: (%.4f, %.4f) → Swapped: (%.4f, %.4f)%n", a, b, a2, b2);
        } else {
            System.out.println("   ⚠️  Skipped (division by zero would occur)");
        }

        // Method 3: XOR (only for integers)
        System.out.println("\n3️⃣ BITWISE XOR METHOD:");
        if (isInteger(a) && isInteger(b)) {
            int a3 = (int)a, b3 = (int)b;
            a3 = a3 ^ b3;
            b3 = a3 ^ b3;
            a3 = a3 ^ b3;
            System.out.printf("   Original: (%.0f, %.0f) → Swapped: (%d, %d)%n", a, b, a3, b3);
        } else {
            System.out.println("   ⚠️  Skipped (XOR works only with integers)");
        }

        // Method 4: Single line
        System.out.println("\n4️⃣ SINGLE-LINE METHOD:");
        double a4 = a, b4 = b;
        b4 = (a4 + b4) - (a4 = b4);
        System.out.printf("   Original: (%.4f, %.4f) → Swapped: (%.4f, %.4f)%n", a, b, a4, b4);

        // Performance comparison
        System.out.println("\n📊 PERFORMANCE COMPARISON:");
        System.out.println("   • Addition/Subtraction : Fast, works with decimals, risk of overflow");
        System.out.println("   • Multiplication/Division : Fast, works with non-zero decimals");
        System.out.println("   • XOR                   : Fastest, no overflow, integers only");
        System.out.println("   • Single-line          : Compact, works with decimals");
    }

    // Helper method to get valid number input
    private static double getValidNumber(Scanner scanner, String prompt) {
        double number = 0;
        while (true) {
            try {
                System.out.print(prompt);
                number = scanner.nextDouble();
                return number;
            } catch (InputMismatchException e) {
                System.out.println("❌ Invalid input! Please enter a valid number.");
                scanner.next(); // Clear invalid input
            }
        }
    }

    // Helper method to check if a number is an integer
    private static boolean isInteger(double number) {
        return number == (int)number;
    }
}