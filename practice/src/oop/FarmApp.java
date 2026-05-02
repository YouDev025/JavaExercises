package oop;
import java.util.Scanner;

// ═══════════════════════════════════════════════════════
//  SUPERCLASS
// ═══════════════════════════════════════════════════════
class Animal {
    protected String name;
    protected int    age;
    protected String color;

    public Animal(String name, int age, String color) {
        this.name  = name;
        this.age   = age;
        this.color = color;
    }

    // Base method — meant to be overridden
    public void sound() {
        System.out.println("🐾  " + name + " makes a generic animal sound...");
    }

    public void displayInfo() {
        System.out.println("  Name  : " + name);
        System.out.println("  Age   : " + age + " years");
        System.out.println("  Color : " + color);
    }
}

// ═══════════════════════════════════════════════════════
//  DOG
// ═══════════════════════════════════════════════════════
class Dog extends Animal {
    private String breed;

    public Dog(String name, int age, String color, String breed) {
        super(name, age, color);
        this.breed = breed;
    }

    @Override
    public void sound() {
        System.out.println("🐶  " + name + " says: Woof! Woof! 🦴");
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("  Breed : " + breed);
    }
}

// ═══════════════════════════════════════════════════════
//  CAT
// ═══════════════════════════════════════════════════════
class Cat extends Animal {
    private boolean isIndoor;

    public Cat(String name, int age, String color, boolean isIndoor) {
        super(name, age, color);
        this.isIndoor = isIndoor;
    }

    @Override
    public void sound() {
        System.out.println("🐱  " + name + " says: Meow~ Purrrr... 🐟");
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("  Type  : " + (isIndoor ? "Indoor 🏠" : "Outdoor 🌿"));
    }
}

// ═══════════════════════════════════════════════════════
//  COW
// ═══════════════════════════════════════════════════════
class Cow extends Animal {
    private double milkPerDay; // liters

    public Cow(String name, int age, String color, double milkPerDay) {
        super(name, age, color);
        this.milkPerDay = milkPerDay;
    }

    @Override
    public void sound() {
        System.out.println("🐄  " + name + " says: Mooooo! 🌾");
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.printf("  Milk  : %.1f liters/day 🥛%n", milkPerDay);
    }
}

// ═══════════════════════════════════════════════════════
//  MAIN
// ═══════════════════════════════════════════════════════
public class FarmApp {

    static void header(String title, String emoji) {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.printf ("║  %s  %-32s║%n", emoji, title);
        System.out.println("╚══════════════════════════════════════╝");
    }

    static void divider() {
        System.out.println("  " + "─".repeat(34));
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║         🐾  FARM ANIMAL APP          ║");
        System.out.println("╚══════════════════════════════════════╝\n");

        // ── User info ─────────────────────────────────────────
        System.out.print("👤  Your name : ");
        String userName = sc.nextLine();
        System.out.println("\nWelcome, " + userName + "! 🌻 Let's meet your farm animals.\n");

        // ════════════════════
        //  DOG input
        // ════════════════════
        header("DOG", "🐶");
        System.out.print("  Name  : ");  String dName  = sc.nextLine();
        System.out.print("  Age   : ");  int    dAge   = Integer.parseInt(sc.nextLine().trim());
        System.out.print("  Color : ");  String dColor = sc.nextLine();
        System.out.print("  Breed : ");  String dBreed = sc.nextLine();

        Dog myDog = new Dog(dName, dAge, dColor, dBreed);

        // ════════════════════
        //  CAT input
        // ════════════════════
        header("CAT", "🐱");
        System.out.print("  Name      : ");  String cName  = sc.nextLine();
        System.out.print("  Age       : ");  int    cAge   = Integer.parseInt(sc.nextLine().trim());
        System.out.print("  Color     : ");  String cColor = sc.nextLine();
        System.out.print("  Indoor? (y/n) : ");
        boolean indoor = sc.nextLine().trim().equalsIgnoreCase("y");

        Cat myCat = new Cat(cName, cAge, cColor, indoor);

        // ════════════════════
        //  COW input
        // ════════════════════
        header("COW", "🐄");
        System.out.print("  Name          : ");  String wName  = sc.nextLine();
        System.out.print("  Age           : ");  int    wAge   = Integer.parseInt(sc.nextLine().trim());
        System.out.print("  Color         : ");  String wColor = sc.nextLine();
        System.out.print("  Milk (L/day)  : ");  double milk   = Double.parseDouble(sc.nextLine().trim());

        Cow myCow = new Cow(wName, wAge, wColor, milk);

        // ════════════════════
        //  ANIMAL PROFILES
        // ════════════════════
        header("ANIMAL PROFILES", "📋");

        System.out.println("\n🐶  Dog ──────────────────────────────");
        myDog.displayInfo();

        System.out.println("\n🐱  Cat ──────────────────────────────");
        myCat.displayInfo();

        System.out.println("\n🐄  Cow ──────────────────────────────");
        myCow.displayInfo();

        // ══════════════════════════════════════
        //  ⭐ POLYMORPHISM — Animal[] array
        // ══════════════════════════════════════
        header("⭐ POLYMORPHISM — All Animals Speak!", "🔊");

        Animal[] farm = { myDog, myCat, myCow };   // ← stored as Animal

        System.out.println("\n  All animals make their sound:\n");
        for (Animal animal : farm) {
            animal.sound();       // dynamic dispatch → correct override called
        }

        divider();
        System.out.println("\n  Runtime types in the array:");
        for (Animal animal : farm) {
            System.out.println("   → " + animal.getClass().getSimpleName()
                    + " \t| name: " + animal.name);
        }

        System.out.println("\n✅  Thanks for visiting the farm, " + userName + "! 🐾🌾");
        sc.close();
    }
}