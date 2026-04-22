package functions;
import java.util.Scanner;

public class MaxFunction {

    public static int max(int a, int b) {
        return (a > b) ? a : b;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter first number: ");
        int a = sc.nextInt();

        System.out.print("Enter second number: ");
        int b = sc.nextInt();

        int result = max(a, b);

        System.out.println("Maximum is: " + result);

        sc.close();
    }
}