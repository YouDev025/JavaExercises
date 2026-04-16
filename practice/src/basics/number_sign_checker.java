package basics;
import  java.util.Scanner;
public class number_sign_checker {
    public static void main (String[] args){
        Scanner scanner = new Scanner(System.in);

        char choice;

        do {
            System.out.println("=== Number Sign Checker ===");

            try {
                System.out.print("Enter a number: ");
                double number = scanner.nextDouble();

                if (number > 0) {
                    System.out.println("The number is POSITIVE.");
                } else if (number < 0) {
                    System.out.println("The number is NEGATIVE.");
                } else {
                    System.out.println("The number is ZERO.");
                }

            } catch (Exception e) {
                System.out.println(" Invalid input! Please enter a valid number.");
                scanner.nextLine(); // clear buffer
            }

            System.out.print("\nDo you want to check another number? (y/n): ");
            choice = scanner.next().charAt(0);

            System.out.println();

        } while (choice == 'y' || choice == 'Y');

        System.out.println("Program exited. Goodbye!");
        scanner.close();
    }
}
