package functions;

import java.util.Scanner;

public class Factorial {

    // Recursive function
    public static long factorialRecursive(int n) {
        if (n <= 1) return 1;
        return n * factorialRecursive(n - 1);
    }

    // Iterative version
    public static long factorialIterative(int n) {
        long result = 1;
        for (int i = 2; i <= n; i++) {
            result *= i;
        }
        return result;
    }

    // Shows the multiplication series recursively
    public static String seriesRecursive(int n) {
        if (n <= 1) return "1";
        return n + " × " + seriesRecursive(n - 1);
    }

    // Shows the multiplication series iteratively
    public static String seriesIterative(int n) {
        StringBuilder sb = new StringBuilder();
        for (int i = n; i >= 1; i--) {
            sb.append(i);
            if (i > 1) sb.append(" × ");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter your number n: ");
        int n = sc.nextInt();

        System.out.println("\n--- Recursive ---");
        System.out.println("Series : " + seriesRecursive(n));
        System.out.println("Result : " + factorialRecursive(n));

        System.out.println("\n--- Iterative ---");
        System.out.println("Series : " + seriesIterative(n));
        System.out.println("Result : " + factorialIterative(n));

        sc.close();
    }
}