package data_struct;

import java.util.HashSet;
import java.util.Scanner;

// Class to find all pairs in an array whose sum equals a given target
// Time Complexity: O(n) | Space Complexity: O(n)
public class PairSum {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // Prompt the user to enter the number of elements in the array
        System.out.print("Enter number of elements: ");
        int n = scanner.nextInt();

        // Declare an integer array of size n
        int[] arr = new int[n];

        // Loop to read n elements from the user and store them in the array
        System.out.println("Enter " + n + " elements:");
        for (int i = 0; i < n; i++) {
            arr[i] = scanner.nextInt();
        }

        // Prompt the user to enter the target sum to search for
        System.out.print("Enter target sum: ");
        int target = scanner.nextInt();

        // HashSet to store elements that have been visited during iteration
        // Allows O(1) average-time lookup to check if a complement exists
        HashSet<Integer> seen = new HashSet<>();

        // HashSet to store the smaller element of each pair already printed
        // Prevents printing the same pair more than once
        HashSet<Integer> printed = new HashSet<>();

        System.out.println("Pairs with sum " + target + ":");

        // Flag to track whether at least one valid pair was found
        boolean found = false;

        // Iterate through each element in the array
        for (int num : arr) {

            // Calculate the complement needed to reach the target sum
            int complement = target - num;

            // Check if the complement has already been seen
            // and that this pair has not been printed before
            if (seen.contains(complement) && !printed.contains(Math.min(num, complement))) {

                // Print the valid pair
                System.out.println("(" + complement + ", " + num + ")");

                // Record the smaller element of this pair to avoid printing it again
                printed.add(Math.min(num, complement));

                found = true;
            }

            // Add the current element to the seen set for future complement lookups
            seen.add(num);
        }

        // Inform the user if no pairs were found that match the target sum
        if (!found) {
            System.out.println("No pairs found.");
        }

        // Close the scanner to release system resources
        scanner.close();
    }
}