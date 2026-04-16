package basics;

import java.util.Scanner;

public class largest_of_three_numbers {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        char choice;

        do {
            System.out.println("=== Largest of Three Numbers ===");

            try {
                System.out.print("Enter first number: ");
                double num1 = scanner.nextDouble();

                System.out.print("Enter second number: ");
                double num2 = scanner.nextDouble();

                System.out.print("Enter third number: ");
                double num3 = scanner.nextDouble();

                double largest;

                if (num1 >= num2 && num1 >= num3) {
                    largest = num1;
                } else if (num2 >= num1 && num2 >= num3) {
                    largest = num2;
                } else {
                    largest = num3;
                }

                System.out.println("Largest number is: " + largest);

            } catch (Exception e) {
                System.out.println(" Invalid input! Please enter numeric values.");
                scanner.nextLine(); // clear buffer
            }

            System.out.print("\nDo you want to try again? (y/n): ");
            choice = scanner.next().charAt(0);

            System.out.println();

        } while (choice == 'y' || choice == 'Y');

        System.out.println("Program exited. Goodbye!");
        scanner.close();
    }
}
