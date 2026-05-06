package data_struct;

import java.util.Scanner;

// Class to merge two sorted arrays into a single sorted array
// Time Complexity: O(n + m) | Space Complexity: O(n + m)
public class MergeSortedArrays {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // Prompt the user to enter the size of the first sorted array
        System.out.print("Enter size of first array: ");
        int n = scanner.nextInt();

        // Declare the first integer array of size n
        int[] arr1 = new int[n];

        // Loop to read n sorted elements for the first array
        System.out.println("Enter " + n + " sorted elements for first array:");
        for (int i = 0; i < n; i++) {
            arr1[i] = scanner.nextInt();
        }

        // Prompt the user to enter the size of the second sorted array
        System.out.print("Enter size of second array: ");
        int m = scanner.nextInt();

        // Declare the second integer array of size m
        int[] arr2 = new int[m];

        // Loop to read m sorted elements for the second array
        System.out.println("Enter " + m + " sorted elements for second array:");
        for (int i = 0; i < m; i++) {
            arr2[i] = scanner.nextInt();
        }

        // Declare the merged array with size n + m to hold all elements
        int[] merged = new int[n + m];

        // Pointer i tracks the current position in arr1
        // Pointer j tracks the current position in arr2
        // Pointer k tracks the current position in the merged array
        int i = 0, j = 0, k = 0;

        // Compare elements from both arrays one by one
        // Place the smaller element into the merged array first
        while (i < n && j < m) {

            if (arr1[i] <= arr2[j]) {
                // arr1 element is smaller or equal, place it in merged
                merged[k++] = arr1[i++];
            } else {
                // arr2 element is smaller, place it in merged
                merged[k++] = arr2[j++];
            }
        }

        // If any elements remain in arr1, copy them directly into merged
        // These elements are already sorted and all greater than the last merged element
        while (i < n) {
            merged[k++] = arr1[i++];
        }

        // If any elements remain in arr2, copy them directly into merged
        // These elements are already sorted and all greater than the last merged element
        while (j < m) {
            merged[k++] = arr2[j++];
        }

        // Display the final merged sorted array
        System.out.println("Merged sorted array:");
        for (int x = 0; x < merged.length; x++) {
            System.out.print(merged[x] + " ");
        }

        // Close the scanner to release system resources
        scanner.close();
    }
}