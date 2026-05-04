package data_struct;

import java.util.Scanner;

// Class to find the maximum and minimum elements in an array
public class MaxMinArray {
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

        // Initialize max and min with the first element of the array
        int max = arr[0];
        int min = arr[0];

        // Iterate through the array starting from the second element
        for (int i = 1; i < n; i++) {

            // Update max if the current element is greater than the current max
            if (arr[i] > max) {
                max = arr[i];
            }

            // Update min if the current element is less than the current min
            if (arr[i] < min) {
                min = arr[i];
            }
        }

        // Display the maximum element found in the array
        System.out.println("Maximum element: " + max);

        // Display the minimum element found in the array
        System.out.println("Minimum element: " + min);

        // Close the scanner to release system resources
        scanner.close();
    }
}