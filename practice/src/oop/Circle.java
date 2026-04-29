package oop;

import java.util.Scanner;
import java.util.InputMismatchException;

public class Circle {
    private double radius;

    public Circle(double radius) {
        this.radius = radius;
    }

    public double getArea() {
        return Math.PI * radius * radius;
    }

    public double getCircumference() {
        return 2 * Math.PI * radius;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double r = 0;
        boolean valid = false;

        while (!valid) {
            try {
                System.out.print("Enter the radius: ");
                r = scanner.nextDouble();

                if (r < 0) {
                    System.out.println("Radius cannot be negative.");
                } else {
                    valid = true; // correct input
                }

            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a numeric value.");
                scanner.next(); // clear invalid input
            }
        }

        Circle circle = new Circle(r);

        System.out.printf("Area          : %.4f%n", circle.getArea());
        System.out.printf("Circumference : %.4f%n", circle.getCircumference());

        scanner.close();
    }
}