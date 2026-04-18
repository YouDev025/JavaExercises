package basics;

public class EvenNumbers {
    public static void main(String[] args) {
        // Print a header message to inform the user what the program does
        System.out.println("Even numbers from 1 to 100:");

        // Approach 1: More efficient - start at 2 and increment by 2
        // This loop runs 50 times instead of 100 times
        // Initialize i = 2 (first even number)
        // Continue loop while i <= 100
        // After each iteration, add 2 to i (i += 2 means i = i + 2)
        for (int i = 2; i <= 100; i += 2) {
            // Print the current even number followed by a space
            // Using print() instead of println() to keep numbers on the same line
            System.out.print(i + " ");
        }

        // Alternative approach (commented out but explained)
        /*
        // Approach 2: Using modulus operator - checks every number
        // This loop runs 100 times
        for (int i = 1; i <= 100; i++) {
            // Check if the number is divisible by 2 with no remainder
            // % is the modulus operator - it returns the remainder after division
            if (i % 2 == 0) {
                // If remainder is 0, the number is even
                System.out.print(i + " ");
            }
        }
        */
    }
}