package data_struct;

import java.util.Scanner;

// Class to find the second largest element in an array using a single pass
public class SecondLargest {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // Prompt the user to enter the number of elements in the array
        System.out.print("Enter number of elements: ");
        int n = scanner.nextInt();

        // Validate that the array has at least 2 elements to have a second largest
        if (n < 2) {
            System.out.println("Array must have at least 2 elements.");
            return;
        }

        // Declare an integer array of size n
        int[] arr = new int[n];

        // Loop to read n elements from the user and store them in the array
        System.out.println("Enter " + n + " elements:");
        for (int i = 0; i < n; i++) {
            arr[i] = scanner.nextInt();
        }

        // Initialize largest and secondLargest to the smallest possible integer value
        int largest = Integer.MIN_VALUE;
        int secondLargest = Integer.MIN_VALUE;

        // Iterate through each element in the array in a single pass
        for (int num : arr) {

            // If the current element is greater than the current largest,
            // shift largest to secondLargest and update largest
            if (num > largest) {
                secondLargest = largest;
                largest = num;

                // If the current element is not equal to largest but greater than secondLargest,
                // update secondLargest
            } else if (num > secondLargest && num != largest) {
                secondLargest = num;
            }
        }

        // If secondLargest was never updated, all elements are equal and no second largest exists
        if (secondLargest == Integer.MIN_VALUE) {
            System.out.println("No second largest element (all values may be equal).");
        } else {
            // Display the second largest element found in the array
            System.out.println("Second largest element: " + secondLargest);
        }

        // Close the scanner to release system resources
        scanner.close();
    }
}