package functions;
import java.util.Scanner;

public class LCMCalculator {

    // Euclid's algorithm for GCD (needed for LCM formula)
    public static int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    // LCM using the formula: LCM(a,b) = (a × b) / GCD(a,b)
    public static long lcm(int a, int b) {
        if (a == 0 || b == 0) return 0;
        return (long) Math.abs(a) / gcd(Math.abs(a), Math.abs(b)) * Math.abs(b);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter first number  (a): ");
        int a = scanner.nextInt();

        System.out.print("Enter second number (b): ");
        int b = scanner.nextInt();

        int absA = Math.abs(a);
        int absB = Math.abs(b);
        int gcd  = gcd(absA, absB);
        long lcm = lcm(absA, absB);

        System.out.println("\n=============================");
        System.out.println("        LCM Calculator       ");
        System.out.println("=============================");
        System.out.println("  a            =  " + absA);
        System.out.println("  b            =  " + absB);
        System.out.println("  GCD(" + absA + ", " + absB + ")  =  " + gcd);
        System.out.println("-----------------------------");
        System.out.println("  Formula: LCM = (a × b) / GCD");
        System.out.println("          LCM = (" + absA + " × " + absB + ") / " + gcd);
        System.out.println("          LCM = " + ((long) absA * absB) + " / " + gcd);
        System.out.println("-----------------------------");
        System.out.println("  LCM(" + absA + ", " + absB + ")  =  " + lcm);
        System.out.println("=============================");

        scanner.close();
    }
}