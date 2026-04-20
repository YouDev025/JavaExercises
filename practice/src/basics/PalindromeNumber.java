package basics;

import java.util.Scanner;

public class PalindromeNumber {

    public static boolean isPalindrome(int number) {
        // Negative numbers are NOT palindromes (e.g., -121 reversed is 121-)
        if (number < 0) return false;

        int original = number;
        int reversed = 0;

        while (number != 0) {
            int lastDigit = number % 10;        // Extract last digit
            reversed = reversed * 10 + lastDigit; // Build reversed number
            number /= 10;                        // Remove last digit
        }

        return original == reversed; // Palindrome if original == reversed
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a number: ");
        int number = scanner.nextInt();

        System.out.println("\n--- Result ---");
        System.out.println("Number  : " + number);

        if (isPalindrome(number)) {
            System.out.println("Result  : ✅ " + number + " IS a Palindrome!");
        } else {
            System.out.println("Result  : ❌ " + number + " is NOT a Palindrome.");
        }

        // Bonus: Test multiple known examples
        System.out.println("\n--- Palindrome Test Cases ---");
        int[] testCases = {121, 12321, 1001, 123, -121, 0, 9, 1221};
        for (int test : testCases) {
            System.out.printf("%-6d → %s%n", test, isPalindrome(test) ? "✅ Palindrome" : "❌ Not Palindrome");
        }

        scanner.close();
    }
}