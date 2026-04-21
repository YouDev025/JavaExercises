package basics;

import java.util.Scanner;

public class Calculator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("===========================");
        System.out.println("      Simple Calculator    ");
        System.out.println("===========================");
        System.out.print("Enter first number  : ");
        double a = scanner.nextDouble();

        System.out.print("Enter operator (+,-,*,/,%): ");
        char op = scanner.next().charAt(0);

        System.out.print("Enter second number : ");
        double b = scanner.nextDouble();

        System.out.println("---------------------------");

        double result;

        switch (op) {
            case '+':
                result = a + b;
                System.out.printf("  %.2f + %.2f = %.2f%n", a, b, result);
                break;

            case '-':
                result = a - b;
                System.out.printf("  %.2f - %.2f = %.2f%n", a, b, result);
                break;

            case '*':
                result = a * b;
                System.out.printf("  %.2f * %.2f = %.2f%n", a, b, result);
                break;

            case '/':
                // ✅ Handle division by zero
                if (b == 0) {
                    System.out.println("  ⚠️  Error: Division by zero is undefined!");
                } else {
                    result = a / b;
                    System.out.printf("  %.2f / %.2f = %.2f%n", a, b, result);
                }
                break;

            case '%':
                // ✅ Handle modulo by zero
                if (b == 0) {
                    System.out.println("  ⚠️  Error: Modulo by zero is undefined!");
                } else {
                    result = a % b;
                    System.out.printf("  %.2f %% %.2f = %.2f%n", a, b, result);
                }
                break;

            default:
                System.out.println("  ❌ Invalid operator! Use: + - * / %");
        }

        System.out.println("===========================");
        scanner.close();
    }
}