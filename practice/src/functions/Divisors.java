package functions;
import java.util.Scanner;

public class Divisors {

    // Function to print all divisors of a number
    public static void printDivisors(int n) {
        if (n <= 0) {
            System.out.println("Please enter a positive number.");
            return;
        }

        System.out.println("Divisors of " + n + " are:");

        for (int i = 1; i <= n; i++) {
            if (n % i == 0) {
                System.out.print(i + " ");
            }
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter a number: ");
        int num = sc.nextInt();

        printDivisors(num);

        sc.close();
    }
}