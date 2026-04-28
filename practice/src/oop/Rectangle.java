package oop;
import java.util.Scanner;

public class Rectangle {
    private double length;
    private double width;

    public Rectangle(double length, double width) {
        this.length = length;
        this.width = width;
    }

    public double getLength() { return length; }
    public double getWidth() { return width; }
    public void setLength(double length) { this.length = length; }
    public void setWidth(double width) { this.width = width; }

    public double calculateArea() {
        return length * width;
    }

    public double calculatePerimeter() {
        return 2 * (length + width);
    }

    @Override
    public String toString() {
        return String.format("Rectangle[length=%.2f, width=%.2f]", length, width);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Rectangle Calculator ===");

        System.out.print("Enter the length: ");
        double length = scanner.nextDouble();

        System.out.print("Enter the width: ");
        double width = scanner.nextDouble();

        scanner.close();

        Rectangle rect = new Rectangle(length, width);

        System.out.println("\n--- Results ---");
        System.out.println(rect);
        System.out.printf("Area:      %.2f%n", rect.calculateArea());
        System.out.printf("Perimeter: %.2f%n", rect.calculatePerimeter());
    }
}