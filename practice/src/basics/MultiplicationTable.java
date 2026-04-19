package basics;
import java.util.Scanner;

public class MultiplicationTable {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n;

        // Handle invalid input
        while (true) {
            System.out.print("Enter an integer: ");
            if (sc.hasNextInt()) {
                n = sc.nextInt();
                break;
            } else {
                System.out.println("Invalid input! Please enter an integer.");
                sc.next(); // discard invalid input
            }
        }

        // Print multiplication table (1 to 10)
        int i = 1;

        System.out.println("Multiplication table of " + n + ":");

        do {
            System.out.println(n + " x " + i + " = " + (n * i));
            i++;
        } while (i <= 10);
    }
}