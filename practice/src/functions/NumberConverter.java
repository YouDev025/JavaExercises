package functions;
import java.util.Scanner;

public class NumberConverter {

    // Convert decimal to binary
    public static String toBinary(int num) {
        return Integer.toBinaryString(num);
    }

    // Convert decimal to octal
    public static String toOctal(int num) {
        return Integer.toOctalString(num);
    }

    // Convert decimal to hexadecimal
    public static String toHexadecimal(int num) {
        return Integer.toHexString(num).toUpperCase();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Ask user for input
        System.out.print("Enter a decimal number: ");
        int number = scanner.nextInt();

        // Display results
        System.out.println("Binary: " + toBinary(number));
        System.out.println("Octal: " + toOctal(number));
        System.out.println("Hexadecimal: " + toHexadecimal(number));

        scanner.close();
    }
}