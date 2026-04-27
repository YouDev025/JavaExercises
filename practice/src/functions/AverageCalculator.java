package functions;
import java.util.Scanner;

public class AverageCalculator {

    // Function that takes a variable number of arguments
    public static double average(int... numbers) {

        // Check if no numbers are passed
        if (numbers.length == 0) {
            return 0;
        }

        int sum = 0;

        // Loop through all numbers and calculate the sum
        for (int num : numbers) {
            sum += num;
        }

        // Return average as double
        return (double) sum / numbers.length;
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // Ask user how many numbers they want to enter
        System.out.print("How many numbers do you want to input? ");
        int n = sc.nextInt();

        // Create an array to store user input
        int[] values = new int[n];

        // Read numbers from user
        for (int i = 0; i < n; i++) {
            System.out.print("Enter number " + (i + 1) + ": ");
            values[i] = sc.nextInt();
        }

        // Call the varargs function using the array
        double result = average(values);

        // Display result
        System.out.println("Average: " + result);

        sc.close();
    }
}