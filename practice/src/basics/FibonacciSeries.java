package basics;

import java.util.Scanner;

public class FibonacciSeries {
    public static void main(String[] args) {
        // Create a Scanner object to read input from the user
        Scanner scanner = new Scanner(System.in);

        // Prompt the user to enter the number of terms
        System.out.print("Enter the number of terms for Fibonacci series: ");
        int n = scanner.nextInt(); // Read and store the number of terms

        // Close the scanner to prevent resource leak
        scanner.close();

        // Print header for the output
        System.out.println("Fibonacci series up to " + n + " terms:");

        // Call the method to print Fibonacci series using while loop
        printFibonacciUsingWhile(n);
    }

    /**
     * This method prints the Fibonacci series up to n terms using a while loop
     * Fibonacci series: 0, 1, 1, 2, 3, 5, 8, 13, 21, ...
     * Each number is the sum of the two preceding numbers
     *
     * @param n The number of terms to print
     */
    public static void printFibonacciUsingWhile(int n) {
        // Handle edge case: if n is 0 or negative
        if (n <= 0) {
            System.out.println("Please enter a positive number greater than 0.");
            return; // Exit the method early
        }

        // Initialize the first two terms of Fibonacci series
        int firstTerm = 0;  // First term (F0)
        int secondTerm = 1; // Second term (F1)

        // Counter variable to track how many terms we've printed
        int count = 0;

        // WHILE LOOP: Continue until we've printed 'n' terms
        while (count < n) {
            // Print the current first term
            System.out.print(firstTerm + " ");

            // Calculate the next term by adding the current two terms
            int nextTerm = firstTerm + secondTerm;

            // Update for the next iteration:
            // Shift the terms: second becomes first, next becomes second
            firstTerm = secondTerm;
            secondTerm = nextTerm;

            // Increment the counter to move to the next term
            count++;
        }

        // Print a new line at the end for better formatting
        System.out.println();
    }
}