package oop.Resizable;

import java.util.Scanner;

// ─── Main ────────────────────────────────────────────────────────────────────
public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("=== Rectangle Resizer ===\n");

        System.out.print("Enter width  : ");
        double width = Double.parseDouble(sc.nextLine().trim());

        System.out.print("Enter height : ");
        double height = Double.parseDouble(sc.nextLine().trim());

        // Create rectangle via the interface reference → polymorphism
        Resizable shape = new GeoRectangle(width, height);

        System.out.println("\nInitial state → " + shape);

        System.out.print("\nEnter resize factor (e.g. 2 to double, 0.5 to halve) : ");
        double factor = Double.parseDouble(sc.nextLine().trim());

        shape.resize(factor);

        System.out.println("After resize  → " + shape);

        sc.close();
    }
}