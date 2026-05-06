package data_struct;

import java.util.HashSet;
import java.util.Scanner;

// Class to find the intersection of two arrays
// Intersection contains only the elements that exist in both arrays (no duplicates)
// Time Complexity: O(n + m) | Space Complexity: O(n)
public class ArrayIntersection {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // Prompt the user to enter the size of the first array
        System.out.print("Enter size of first array: ");
        int n = scanner.nextInt();

        // Declare the first integer array of size n
        int[] arr1 = new int[n];

        // Loop to read n elements for the first array
        System.out.println("Enter " + n + " elements for first array:");
        for (int i = 0; i < n; i++) {
            arr1[i] = scanner.nextInt();
        }

        // Prompt the user to enter the size of the second array
        System.out.print("Enter size of second array: ");
        int m = scanner.nextInt();

        // Declare the second integer array of size m
        int[] arr2 = new int[m];

        // Loop to read m elements for the second array
        System.out.println("Enter " + m + " elements for second array:");
        for (int i = 0; i < m; i++) {
            arr2[i] = scanner.nextInt();
        }

        // HashSet to store all unique elements from the first array
        // Allows O(1) average-time lookup when checking elements of the second array
        HashSet<Integer> setArr1 = new HashSet<>();

        // HashSet to track elements already added to the result
        // Prevents duplicate entries in the intersection output
        HashSet<Integer> seen = new HashSet<>();

        // Populate setArr1 with all elements from the first array
        for (int num : arr1) {
            setArr1.add(num);
        }

        System.out.println("Intersection of the two arrays:");

        // Flag to track whether at least one common element was found
        boolean found = false;

        // Iterate through each element in the second array
        for (int num : arr2) {

            // Check if the current element exists in the first array
            // and has not already been added to the result
            if (setArr1.contains(num) && !seen.contains(num)) {

                // Print the common element
                System.out.print(num + " ");

                // Mark this element as already included in the result
                seen.add(num);

                found = true;
            }
        }

        // Inform the user if the two arrays share no common elements
        if (!found) {
            System.out.println("No common elements found.");
        } else {
            System.out.println();
        }

        // Close the scanner to release system resources
        scanner.close();
    }
}