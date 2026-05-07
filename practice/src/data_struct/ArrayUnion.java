package data_struct;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

// Class to find the union of two arrays without duplicates
// Union contains all unique elements that appear in either array
// Time Complexity: O(n + m) | Space Complexity: O(n + m)
public class ArrayUnion {

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

        // HashSet to track elements already added to the union
        // Guarantees no duplicates appear in the final result
        HashSet<Integer> seen = new HashSet<>();

        // ArrayList to preserve the insertion order of unique elements
        // Maintains the order in which elements were first encountered
        ArrayList<Integer> union = new ArrayList<>();

        // Iterate through each element in the first array
        for (int num : arr1) {

            // Add to union only if this element has not been seen before
            if (seen.add(num)) {
                union.add(num);
            }
        }

        // Iterate through each element in the second array
        for (int num : arr2) {

            // Add to union only if this element has not been seen before
            // This also handles duplicates that exist across both arrays
            if (seen.add(num)) {
                union.add(num);
            }
        }

        // Display all unique elements that form the union of both arrays
        System.out.println("Union of the two arrays:");
        System.out.println(union);

        // Close the scanner to release system resources
        scanner.close();
    }
}