package functions;

import java.util.Scanner;

public class fibonacci {

    /**
     * Returns the nth Fibonacci number using iterative approach.
     * F(0) = 0, F(1) = 1, F(n) = F(n-1) + F(n-2)
     */
    public static long getNth(int n) {
        if (n < 0) throw new IllegalArgumentException("n must be non-negative, got: " + n);
        if (n == 0) return 0;
        if (n == 1) return 1;

        long prev = 0, curr = 1;
        for (int i = 2; i <= n; i++) {
            long next = prev + curr;
            prev = curr;
            curr = next;
        }
        return curr;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter n (position in Fibonacci sequence): ");
        int n = sc.nextInt();

        try {
            long result = getNth(n);
            System.out.println("F(" + n + ") = " + result);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }

        sc.close();
    }
}