package basics;
import java.util.Scanner;
import java.math.BigInteger;

public class FactorialDoWhile  {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n;

        // Handle invalid (non-integer) input
        while (true) {
            System.out.print("Enter a non-negative integer: ");
            if (sc.hasNextInt()) {
                n = sc.nextInt();
                break;
            } else {
                System.out.println("Invalid input! Please enter an integer.");
                sc.next(); // discard invalid input
            }
        }

        // Handle negative numbers
        if (n < 0) {
            System.out.println("Factorial is not defined for negative numbers.");
            return;
        }

        BigInteger factorial = BigInteger.ONE;
        int i = 1;

        // Handle 0! = 1 and general case
        if (n == 0) {
            factorial = BigInteger.ONE;
        } else {
            do {
                factorial = factorial.multiply(BigInteger.valueOf(i));
                i++;
            } while (i <= n);
        }

        System.out.println("Factorial of " + n + " is: " + factorial);
    }
}