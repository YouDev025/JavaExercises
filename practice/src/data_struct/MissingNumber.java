package data_struct;

import java.util.Scanner;

// Class to find the missing number in an array containing numbers from 1 to n
// Time Complexity: O(n) | Space Complexity: O(1)
public class MissingNumber {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // Prompt the user to enter the value of n
        System.out.print("Enter the value of n: ");
        int n = scanner.nextInt();

        // Validate that n is at least 2 to have a meaningful missing number
        if (n < 2) {
            System.out.println("n must be at least 2.");
            scanner.close();
            return;
        }

        // Declare an integer array of size n-1 since one number is missing
        int[] arr = new int[n - 1];

        System.out.println("Enter " + (n - 1) + " elements (numbers from 1 to " + n + " with one missing):");

        // Loop to read n-1 elements with validation on each input
        for (int i = 0; i < n - 1; i++) {
            int input = scanner.nextInt();

            // Reject any element that falls outside the valid range 1 to n
            if (input < 1 || input > n) {
                System.out.println("Invalid input: " + input + ". All elements must be between 1 and " + n + ".");
                scanner.close();
                return;
            }

            arr[i] = input;
        }

        // Calculate the expected sum of all numbers from 1 to n using the formula n(n+1)/2
        int expectedSum = n * (n + 1) / 2;

        // Calculate the actual sum of the elements present in the array
        int actualSum = 0;
        for (int num : arr) {
            actualSum += num;
        }

        // The missing number is the difference between the expected sum and the actual sum
        int missingNumber = expectedSum - actualSum;

        // Display the missing number
        System.out.println("Missing number: " + missingNumber);

        // Close the scanner to release system resources
        scanner.close();
    }
}