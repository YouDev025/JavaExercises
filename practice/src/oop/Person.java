package oop;
import java.util.Scanner;

public class Person {

    // ── Private Fields (JavaBeans convention) ─────────────────
    private String name;
    private int age;
    private String address;

    // ── No-Arg Constructor ────────────────────────────────────
    public Person() {
        this.name    = "Unknown";
        this.age     = 0;
        this.address = "Not Provided";
    }

    // ── Parameterized Constructor ─────────────────────────────
    public Person(String name, int age, String address) {
        setName(name);       // reuse setters for validation
        setAge(age);
        setAddress(address);
    }

    // ── Copy Constructor ──────────────────────────────────────
    public Person(Person other) {
        this(other.name, other.age, other.address);
    }

    // ── Getters ───────────────────────────────────────────────
    public String getName()    { return name; }
    public int    getAge()     { return age; }
    public String getAddress() { return address; }

    // ── Setters (with validation) ─────────────────────────────
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            System.out.println("❌ Name cannot be empty. Keeping previous value.");
            return;
        }
        this.name = name.trim();
    }

    public void setAge(int age) {
        if (age < 0 || age > 150) {
            System.out.println("❌ Invalid age. Must be between 0 and 150. Keeping previous value.");
            return;
        }
        this.age = age;
    }

    public void setAddress(String address) {
        if (address == null || address.trim().isEmpty()) {
            System.out.println("❌ Address cannot be empty. Keeping previous value.");
            return;
        }
        this.address = address.trim();
    }

    // ── Display ───────────────────────────────────────────────
    public void displayInfo() {
        System.out.println("\n======= Person Details =======");
        System.out.println("Name    : " + name);
        System.out.println("Age     : " + age + " years old");
        System.out.println("Address : " + address);
        System.out.println("==============================");
    }

    @Override
    public String toString() {
        return String.format("Person{name='%s', age=%d, address='%s'}", name, age, address);
    }

    // ── Main ──────────────────────────────────────────────────
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("╔══════════════════════════════╗");
        System.out.println("║      👤 Person Registry      ║");
        System.out.println("╚══════════════════════════════╝");

        // ── Collect Input ──────────────────────────────────────
        System.out.print("Enter Name    : ");
        String name = scanner.nextLine();

        System.out.print("Enter Age     : ");
        int age = scanner.nextInt();
        scanner.nextLine(); // consume newline

        System.out.print("Enter Address : ");
        String address = scanner.nextLine();

        // ── Create Person ──────────────────────────────────────
        Person person = new Person(name, age, address);
        person.displayInfo();

        // ── Update Menu ────────────────────────────────────────
        boolean running = true;
        while (running) {
            System.out.println("\n╔══════════════════════════════╗");
            System.out.println("║          Update Menu         ║");
            System.out.println("╠══════════════════════════════╣");
            System.out.println("║  1. Update Name              ║");
            System.out.println("║  2. Update Age               ║");
            System.out.println("║  3. Update Address           ║");
            System.out.println("║  4. Display Info             ║");
            System.out.println("║  5. Exit                     ║");
            System.out.println("╚══════════════════════════════╝");
            System.out.print("Choose an option (1-5): ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter new Name    : ");
                    person.setName(scanner.nextLine());
                    System.out.println("✅ Name updated successfully!");
                    break;

                case 2:
                    System.out.print("Enter new Age     : ");
                    person.setAge(scanner.nextInt());
                    scanner.nextLine();
                    System.out.println("✅ Age updated successfully!");
                    break;

                case 3:
                    System.out.print("Enter new Address : ");
                    person.setAddress(scanner.nextLine());
                    System.out.println("✅ Address updated successfully!");
                    break;

                case 4:
                    person.displayInfo();
                    break;

                case 5:
                    System.out.println("\n👋 Exiting Person Registry. Goodbye!");
                    running = false;
                    break;

                default:
                    System.out.println("❌ Invalid option. Please choose between 1 and 5.");
            }
        }

        scanner.close();
    }
}