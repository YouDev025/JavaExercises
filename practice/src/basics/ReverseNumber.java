package basics;
import java.util.Scanner;

public class ReverseNumber {

    public static int reverseNumber(int number) {
        int reversed = 0;
        boolean isNegative = number < 0;
        number = Math.abs(number); // Handle negative numbers

        while (number != 0) {
            int lastDigit = number % 10;   // Extract last digit
            reversed = reversed * 10 + lastDigit; // Build reversed number
            number /= 10;                  // Remove last digit
        }

        return isNegative ? -reversed : reversed;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a number: ");
        int number = scanner.nextInt();

        int result = reverseNumber(number);

        System.out.println("\n--- Result ---");
        System.out.println("Original Number : " + number);
        System.out.println("Reversed Number : " + result);

        scanner.close();
    }
}