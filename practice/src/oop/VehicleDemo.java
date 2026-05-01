package oop;
import java.util.Scanner;

// ─────────────────────────────────────────────
//  BASE CLASS
// ─────────────────────────────────────────────
abstract class Vehicle {
    protected String brand;
    protected String model;
    protected int year;

    public Vehicle(String brand, String model, int year) {
        this.brand = brand;
        this.model = model;
        this.year  = year;
    }

    // Method to be overridden by subclasses
    public void startEngine() {
        System.out.println("🔑 Starting a generic vehicle engine...");
    }

    public void displayInfo() {
        System.out.println("  Brand : " + brand);
        System.out.println("  Model : " + model);
        System.out.println("  Year  : " + year);
    }
}

// ─────────────────────────────────────────────
//  DERIVED CLASS – CAR
// ─────────────────────────────────────────────
class Car extends Vehicle {
    private int doors;

    public Car(String brand, String model, int year, int doors) {
        super(brand, model, year);
        this.doors = doors;
    }

    @Override
    public void startEngine() {
        System.out.println("🚗 Car engine started: VROOM VROOM! 💨");
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("  Doors : " + doors);
    }
}

// ─────────────────────────────────────────────
//  DERIVED CLASS – BIKE
// ─────────────────────────────────────────────
class Bike extends Vehicle {
    private String bikeType; // e.g. Mountain, Road, Electric

    public Bike(String brand, String model, int year, String bikeType) {
        super(brand, model, year);
        this.bikeType = bikeType;
    }

    @Override
    public void startEngine() {
        System.out.println("🏍️  Bike engine started: BRAAAP! 🔥");
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("  Type  : " + bikeType);
    }
}

// ─────────────────────────────────────────────
//  MAIN – User Input & Demo
// ─────────────────────────────────────────────
public class VehicleDemo {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║        🚀  VEHICLE DEMO APP          ║");
        System.out.println("╚══════════════════════════════════════╝\n");

        // ── Collect user personal info ──────────────────────────
        System.out.println("👤  Please enter your information:");
        System.out.print("   Full Name  : ");
        String name = sc.nextLine();

        System.out.print("   Age        : ");
        int age = Integer.parseInt(sc.nextLine().trim());

        System.out.print("   City       : ");
        String city = sc.nextLine();

        System.out.println("\nWelcome, " + name + " from " + city + "! 🎉");
        System.out.println("─".repeat(42));

        // ── Collect Car info ────────────────────────────────────
        System.out.println("\n🚗  Enter your CAR details:");
        System.out.print("   Brand : ");
        String carBrand = sc.nextLine();

        System.out.print("   Model : ");
        String carModel = sc.nextLine();

        System.out.print("   Year  : ");
        int carYear = Integer.parseInt(sc.nextLine().trim());

        System.out.print("   Doors : ");
        int doors = Integer.parseInt(sc.nextLine().trim());

        // ── Collect Bike info ───────────────────────────────────
        System.out.println("\n🏍️   Enter your BIKE details:");
        System.out.print("   Brand : ");
        String bikeBrand = sc.nextLine();

        System.out.print("   Model : ");
        String bikeModel = sc.nextLine();

        System.out.print("   Year  : ");
        int bikeYear = Integer.parseInt(sc.nextLine().trim());

        System.out.print("   Type (Mountain/Road/Electric) : ");
        String bikeType = sc.nextLine();

        // ── Build objects ───────────────────────────────────────
        Car  myCar  = new Car(carBrand, carModel, carYear, doors);
        Bike myBike = new Bike(bikeBrand, bikeModel, bikeYear, bikeType);

        // ── Display results ─────────────────────────────────────
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║            🛠️  YOUR VEHICLES          ║");
        System.out.println("╚══════════════════════════════════════╝");

        System.out.println("\n── 🚗  CAR ──────────────────────────────");
        myCar.displayInfo();
        myCar.startEngine();

        System.out.println("\n── 🏍️  BIKE ─────────────────────────────");
        myBike.displayInfo();
        myBike.startEngine();

        // ── Polymorphism demo ───────────────────────────────────
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║       🔁  POLYMORPHISM IN ACTION      ║");
        System.out.println("╚══════════════════════════════════════╝");

        Vehicle[] garage = { myCar, myBike };
        for (Vehicle v : garage) {
            System.out.println("\n→ " + v.brand + " " + v.model + ":");
            v.startEngine();   // dynamic dispatch – calls the correct override
        }

        System.out.println("\n✅  Thanks for using Vehicle Demo, " + name + "!");
        sc.close();
    }
}