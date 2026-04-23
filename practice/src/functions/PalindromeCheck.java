package functions;
import java.util.Scanner;

public class PalindromeCheck {

    public static boolean isPalindrome(String str) {
        // normalize: remove spaces & lowercase
        str = str.replaceAll("\\s+", "").toLowerCase();

        int left = 0;
        int right = str.length() - 1;

        while (left < right) {
            if (str.charAt(left) != str.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter a string: ");
        String userInput = input.nextLine();

        if (isPalindrome(userInput)) {
            System.out.println("It is a palindrome ✅");
        } else {
            System.out.println("It is NOT a palindrome ❌");
        }

        input.close();
    }
}