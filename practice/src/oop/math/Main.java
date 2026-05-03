package oop.math;

import java.util.Scanner;

// ─── Main ─────────────────────────────────────────────────────────────────────
public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter a number: ");
        double number = Double.parseDouble(sc.nextLine().trim());
        int    intNum = (int) number;

        System.out.println("\n── Results ──────────────────────────");
        System.out.printf("  Number  : %.2f%n",   number);
        System.out.printf("  Square  : %.2f%n",   MathOperations.square(number));
        System.out.printf("  Cube    : %.2f%n",   MathOperations.cube(number));
        System.out.printf("  isEven  : %b%n",     MathOperations.isEven(intNum));
        System.out.println("─────────────────────────────────────");

        sc.close();
    }
}