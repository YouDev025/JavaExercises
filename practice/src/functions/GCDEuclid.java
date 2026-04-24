package functions;
import java.util.Scanner;

public class GCDEuclid {

    // Recursive implementation of Euclid's algorithm
    public static int gcdRecursive(int a, int b) {
        if (b == 0) {
            return a;
        }
        return gcdRecursive(b, a % b);
    }

    // Iterative implementation of Euclid's algorithm
    public static int gcdIterative(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter first number  (a): ");
        int a = scanner.nextInt();

        System.out.print("Enter second number (b): ");
        int b = scanner.nextInt();

        // Handle negative numbers
        a = Math.abs(a);
        b = Math.abs(b);

        int gcd = gcdRecursive(a, b);

        System.out.println("\n--- Euclid's Algorithm Steps ---");
        printSteps(a, b);
        System.out.println("--------------------------------");
        System.out.println("GCD(" + a + ", " + b + ") = " + gcd);

        scanner.close();
    }

    // Shows each step of the algorithm
    public static void printSteps(int a, int b) {
        while (b != 0) {
            System.out.println("GCD(" + a + ", " + b + ") → GCD(" + b + ", " + a + "%" + b + "=" + (a % b) + ")");
            int temp = b;
            b = a % b;
            a = temp;
        }
        System.out.println("GCD(" + a + ", 0) = " + a);
    }
}