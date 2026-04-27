package functions;
import java.util.Scanner;

public class PowerCalculator {

    // Function to compute power using fast exponentiation
    public static long power(int base, int exponent) {

        long result = 1; // This will store the final result

        // Loop until exponent becomes 0
        while (exponent > 0) {

            // If exponent is odd, multiply result by current base
            if (exponent % 2 == 1) {
                result *= base;
            }

            // Square the base (base = base^2)
            base *= base;

            // Divide exponent by 2
            exponent /= 2;
        }

        return result; // Return the computed power
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // Ask the user to enter the base
        System.out.print("Enter base: ");
        int base = sc.nextInt();

        // Ask the user to enter the exponent
        System.out.print("Enter exponent: ");
        int exponent = sc.nextInt();

        // Call the function and store the result
        long result = power(base, exponent);

        // Display the result
        System.out.println("Result: " + result);

        sc.close(); // Close scanner to avoid resource leak
    }
}