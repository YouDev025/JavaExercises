package oop;

import java.util.Scanner;

public class ComplexNumber {
    private double real;
    private double imaginary;

    public ComplexNumber(double real, double imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    public double getReal() { return real; }
    public double getImaginary() { return imaginary; }

    public ComplexNumber add(ComplexNumber other) {
        return new ComplexNumber(
                this.real + other.real,
                this.imaginary + other.imaginary
        );
    }

    public ComplexNumber subtract(ComplexNumber other) {
        return new ComplexNumber(
                this.real - other.real,
                this.imaginary - other.imaginary
        );
    }

    public ComplexNumber multiply(ComplexNumber other) {
        return new ComplexNumber(
                this.real * other.real - this.imaginary * other.imaginary,
                this.real * other.imaginary + this.imaginary * other.real
        );
    }

    public String toString() {
        if (imaginary >= 0)
            return String.format("%.2f + %.2fi", real, imaginary);
        else
            return String.format("%.2f - %.2fi", real, Math.abs(imaginary));
    }

    private static double readDouble(Scanner scanner, String fieldName) {
        while (true) {
            try {
                System.out.print("  Enter " + fieldName + ": ");
                double value = scanner.nextDouble();
                return value;
            } catch (java.util.InputMismatchException e) {
                System.out.println("  Error: Invalid input. Please enter a number. Try again.");
                scanner.nextLine();
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("--- First complex number ---");
        double r1 = readDouble(scanner, "real part");
        double i1 = readDouble(scanner, "imaginary part");

        System.out.println("--- Second complex number ---");
        double r2 = readDouble(scanner, "real part");
        double i2 = readDouble(scanner, "imaginary part");

        ComplexNumber c1 = new ComplexNumber(r1, i1);
        ComplexNumber c2 = new ComplexNumber(r2, i2);

        System.out.println("\nC1 = " + c1);
        System.out.println("C2 = " + c2);
        System.out.println("\nC1 + C2 = " + c1.add(c2));
        System.out.println("C1 - C2 = " + c1.subtract(c2));
        System.out.println("C1 × C2 = " + c1.multiply(c2));

        scanner.close();
    }
}
