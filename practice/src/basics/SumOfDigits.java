package basics;
import java.util.Scanner;

public class SumOfDigits {

    // Method 1: Using a loop
    public static int sumOfDigits(int number) {
        number = Math.abs(number); // Handle negative numbers
        int sum = 0;

        while (number > 0) {
            sum += number % 10;   // Extract last digit
            number /= 10;         // Remove last digit
        }

        return sum;
    }

    // Method 2: Using recursion
    public static int sumOfDigitsRecursive(int number) {
        number = Math.abs(number);
        if (number == 0) return 0;
        return (number % 10) + sumOfDigitsRecursive(number / 10);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a number: ");
        int number = scanner.nextInt();

        System.out.println("\n--- Results ---");
        System.out.println("Number        : " + number);
        System.out.println("Sum (Loop)    : " + sumOfDigits(number));
        System.out.println("Sum (Recursion): " + sumOfDigitsRecursive(number));

        scanner.close();
    }
}