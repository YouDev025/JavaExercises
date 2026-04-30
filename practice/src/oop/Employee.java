package oop;
import java.util.Scanner;

public class Employee {

    // ── Private Fields (JavaBeans convention) ─────────────────
    private String employeeId;
    private String name;
    private double salary;

    // ── No-Arg Constructor ────────────────────────────────────
    public Employee() {
        this.employeeId = "N/A";
        this.name       = "Unknown";
        this.salary     = 0.0;
    }

    // ── Parameterized Constructor ─────────────────────────────
    public Employee(String employeeId, String name, double salary) {
        setEmployeeId(employeeId);
        setName(name);
        setSalary(salary);
    }

    // ── Getters ───────────────────────────────────────────────
    public String getEmployeeId() { return employeeId; }
    public String getName()       { return name; }
    public double getSalary()     { return salary; }

    // ── Setters (with validation) ─────────────────────────────
    public void setEmployeeId(String employeeId) {
        if (employeeId == null || employeeId.trim().isEmpty()) {
            System.out.println("❌ Employee ID cannot be empty.");
            return;
        }
        this.employeeId = employeeId.trim();
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            System.out.println("❌ Name cannot be empty.");
            return;
        }
        this.name = name.trim();
    }

    public void setSalary(double salary) {
        if (salary < 0) {
            System.out.println("❌ Salary cannot be negative.");
            return;
        }
        this.salary = salary;
    }

    // ── Apply Raise ───────────────────────────────────────────
    public void applyRaise(double percentage) {
        if (percentage <= 0) {
            System.out.println("❌ Raise percentage must be greater than zero.");
            return;
        }
        if (percentage > 100) {
            System.out.println("⚠️  Warning: Raise percentage exceeds 100%. Proceeding anyway.");
        }
        double raiseAmount = salary * (percentage / 100);
        double oldSalary   = salary;
        salary += raiseAmount;

        System.out.println("\n🎉 Raise Applied Successfully!");
        System.out.println("══════════════════════════════");
        System.out.printf("  Previous Salary : %-10s%n", String.format("$%,.2f", oldSalary));
        System.out.printf("  Raise (%%)       : %.2f%%%n", percentage);
        System.out.printf("  Raise Amount    : %-10s%n", String.format("$%,.2f", raiseAmount));
        System.out.printf("  New Salary      : %-10s%n", String.format("$%,.2f", salary));
        System.out.println("══════════════════════════════");
    }

    // ── Apply Fixed Bonus ─────────────────────────────────────
    public void applyBonus(double bonusAmount) {
        if (bonusAmount <= 0) {
            System.out.println("❌ Bonus amount must be greater than zero.");
            return;
        }
        double oldSalary = salary;
        salary += bonusAmount;

        System.out.println("\n💰 Bonus Applied Successfully!");
        System.out.println("══════════════════════════════");
        System.out.printf("  Previous Salary : %-10s%n", String.format("$%,.2f", oldSalary));
        System.out.printf("  Bonus Amount    : %-10s%n", String.format("$%,.2f", bonusAmount));
        System.out.printf("  New Salary      : %-10s%n", String.format("$%,.2f", salary));
        System.out.println("══════════════════════════════");
    }

    // ── Display Info ──────────────────────────────────────────  ✅ FIXED
    public void displayInfo() {
        String formattedSalary = String.format("$%,.2f", salary);  // pre-format first
        System.out.println("\n╔══════════════════════════════╗");
        System.out.println("║      Employee Details        ║");
        System.out.println("╠══════════════════════════════╣");
        System.out.printf( "║  ID     : %-20s║%n", employeeId);
        System.out.printf( "║  Name   : %-20s║%n", name);
        System.out.printf( "║  Salary : %-20s║%n", formattedSalary); // ✅ use %s
        System.out.println("╚══════════════════════════════╝");
    }

    @Override
    public String toString() {
        return String.format("Employee{id='%s', name='%s', salary=%s}",
                employeeId, name, String.format("$%,.2f", salary));
    }

    // ── Main ─────────────────────────────────────────────────
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("╔══════════════════════════════╗");
        System.out.println("║     👔 Employee Manager      ║");
        System.out.println("╚══════════════════════════════╝");

        System.out.print("Enter Employee ID   : ");
        String id = scanner.nextLine();

        System.out.print("Enter Employee Name : ");
        String name = scanner.nextLine();

        System.out.print("Enter Base Salary   : $");
        double salary = scanner.nextDouble();

        Employee emp = new Employee(id, name, salary);
        emp.displayInfo();

        boolean running = true;
        while (running) {
            System.out.println("\n╔══════════════════════════════╗");
            System.out.println("║           Main Menu          ║");
            System.out.println("╠══════════════════════════════╣");
            System.out.println("║  1. Apply Raise (%)          ║");
            System.out.println("║  2. Apply Bonus ($)          ║");
            System.out.println("║  3. Update Name              ║");
            System.out.println("║  4. Update Salary            ║");
            System.out.println("║  5. Display Employee Info    ║");
            System.out.println("║  6. Exit                     ║");
            System.out.println("╚══════════════════════════════╝");
            System.out.print("Choose an option (1-6): ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter raise percentage : ");
                    double percent = scanner.nextDouble();
                    emp.applyRaise(percent);
                    break;

                case 2:
                    System.out.print("Enter bonus amount : $");
                    double bonus = scanner.nextDouble();
                    emp.applyBonus(bonus);
                    break;

                case 3:
                    System.out.print("Enter new name : ");
                    emp.setName(scanner.nextLine());
                    System.out.println("✅ Name updated successfully!");
                    break;

                case 4:
                    System.out.print("Enter new salary : $");
                    emp.setSalary(scanner.nextDouble());
                    System.out.println("✅ Salary updated successfully!");
                    break;

                case 5:
                    emp.displayInfo();
                    break;

                case 6:
                    System.out.println("\n👋 Exiting Employee Manager. Goodbye!");
                    running = false;
                    break;

                default:
                    System.out.println("❌ Invalid option. Please choose between 1 and 6.");
            }
        }

        scanner.close();
    }
}