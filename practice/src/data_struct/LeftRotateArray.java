package data_struct;

import java.util.Scanner;

// Class to left rotate an array by k positions using the reversal algorithm
// Time Complexity: O(n) | Space Complexity: O(1)
public class LeftRotateArray {

    // Helper method to reverse a portion of the array from index 'start' to 'end' (inclusive)
    static void reverse(int[] arr, int start, int end) {
        while (start < end) {

            // Swap the elements at the start and end positions
            int temp = arr[start];
            arr[start] = arr[end];
            arr[end] = temp;

            // Move both pointers toward the center
            start++;
            end--;
        }
    }

    // Method to left rotate the array by k positions using three reversals
    static void leftRotate(int[] arr, int k, int n) {

        // Normalize k in case it is greater than the array size
        // A rotation by n is the same as no rotation
        k = k % n;

        // If k is 0, the array is already in its final state
        if (k == 0) return;

        // Step 1: Reverse the first k elements
        // This prepares the left segment for its final position
        reverse(arr, 0, k - 1);

        // Step 2: Reverse the remaining n-k elements
        // This prepares the right segment for its final position
        reverse(arr, k, n - 1);

        // Step 3: Reverse the entire array
        // Combining both reversed segments produces the final rotated result
        reverse(arr, 0, n - 1);
    }

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

        // Prompt the user to enter the number of positions to rotate left
        System.out.print("Enter number of positions to rotate (k): ");
        int k = scanner.nextInt();

        // Perform the left rotation using the reversal algorithm
        leftRotate(arr, k, n);

        // Display the array after rotation
        System.out.println("Array after left rotation by " + k + " positions:");
        for (int i = 0; i < n; i++) {
            System.out.print(arr[i] + " ");
        }

        // Close the scanner to release system resources
        scanner.close();
    }
}