package functions;

import java.util.Scanner;

public class ArmstrongNumber {

    /**
     * Function to check if a number is an Armstrong number
     * For a 3-digit number: sum of cubes of digits = the number itself
     * Example: 153 → 1³ + 5³ + 3³ = 153
     *
     * @param number the number to check
     * @return true if Armstrong, false otherwise
     */
    public static boolean isArmstrong(int number) {
        int original = number; // store original number
        int sum = 0;           // will store sum of cubes of digits

        // Loop through each digit
        while (number > 0) {
            int digit = number % 10;       // extract last digit
            sum += digit * digit * digit; // cube and add
            number /= 10;                 // remove last digit
        }

        // Check if sum equals original number
        return sum == original;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Ask user for input
        System.out.print("Enter a number: ");
        int num = sc.nextInt();

        // Call function and display result
        if (isArmstrong(num)) {
            System.out.println(num + " is an Armstrong number ✅");
        } else {
            System.out.println(num + " is NOT an Armstrong number ❌");
        }

        sc.close();
    }
}

/*
==========================
   COMMENTED EXAMPLE
==========================

Input: 153

Step-by-step:
digit = 3 → 3³ = 27
digit = 5 → 5³ = 125
digit = 1 → 1³ = 1

sum = 27 + 125 + 1 = 153

Since sum == original number → Armstrong number ✔

--------------------------------

Input: 123

digit = 3 → 27
digit = 2 → 8
digit = 1 → 1

sum = 27 + 8 + 1 = 36

Since sum ≠ 123 → NOT Armstrong ❌

==========================
*/