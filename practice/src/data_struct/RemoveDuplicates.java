package data_struct;

import java.util.Scanner;

// Class to remove duplicate elements from a sorted array in-place
public class RemoveDuplicates {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // Prompt the user to enter the number of elements in the array
        System.out.print("Enter number of elements: ");
        int n = scanner.nextInt();

        // Validate that the array is not empty before proceeding
        if (n == 0) {
            System.out.println("Array is empty.");
            return;
        }

        // Declare an integer array of size n
        int[] arr = new int[n];

        // Loop to read n sorted elements from the user and store them in the array
        System.out.println("Enter " + n + " sorted elements:");
        for (int i = 0; i < n; i++) {
            arr[i] = scanner.nextInt();
        }

        // Pointer j tracks the position of the last unique element found
        // It starts at index 0 since the first element is always unique
        int j = 0;

        // Iterate through the array starting from the second element
        for (int i = 1; i < n; i++) {

            // If the current element differs from the last unique element,
            // advance j and place the unique element at the new position
            if (arr[i] != arr[j]) {
                j++;
                arr[j] = arr[i];
            }
            // If arr[i] equals arr[j], it is a duplicate and is skipped
        }

        // The new logical size of the array without duplicates is j + 1
        // Print only the unique elements from index 0 to j (inclusive)
        System.out.println("Array after removing duplicates:");
        for (int i = 0; i <= j; i++) {
            System.out.print(arr[i] + " ");
        }

        // Close the scanner to release system resources
        scanner.close();
    }
}