package oop;
import java.util.Scanner;

// ═══════════════════════════════════════════════════════
//  SUPERCLASS — StaffMember
// ═══════════════════════════════════════════════════════
abstract class StaffMember {
    protected String name;
    protected int    id;
    protected double baseSalary;
    protected int    yearsOfExperience;

    public StaffMember(String name, int id, double baseSalary, int yearsOfExperience) {
        this.name              = name;
        this.id                = id;
        this.baseSalary        = baseSalary;
        this.yearsOfExperience = yearsOfExperience;
    }

    public abstract double calculateBonus();

    public double calculateTotalCompensation() {
        return baseSalary + calculateBonus();
    }

    public void displayInfo() {
        System.out.println("  Name         : " + name);
        System.out.println("  ID           : EMP-" + id);
        System.out.printf ("  Base Salary  : $%.2f%n",  baseSalary);
        System.out.println("  Experience   : " + yearsOfExperience + " years");
        System.out.printf ("  Bonus        : $%.2f  (%.0f%%)%n",
                calculateBonus(), getBonusRate() * 100);
        System.out.printf ("  Total Comp.  : $%.2f%n", calculateTotalCompensation());
    }

    protected abstract double getBonusRate();
}

// ═══════════════════════════════════════════════════════
//  SUBCLASS — Manager
// ═══════════════════════════════════════════════════════
class Manager extends StaffMember {
    private String department;
    private int    teamSize;

    private static final double BASE_BONUS_RATE = 0.20;

    public Manager(String name, int id, double baseSalary,
                   int yearsOfExperience, String department, int teamSize) {
        super(name, id, baseSalary, yearsOfExperience);
        this.department = department;
        this.teamSize   = teamSize;
    }

    @Override
    public double calculateBonus() {
        double rate = BASE_BONUS_RATE + (teamSize * 0.01) + (yearsOfExperience * 0.005);
        return baseSalary * rate;
    }

    @Override
    protected double getBonusRate() {
        return BASE_BONUS_RATE + (teamSize * 0.01) + (yearsOfExperience * 0.005);
    }

    @Override
    public void displayInfo() {
        System.out.println("  Role         : 👔 Manager");
        System.out.println("  Department   : " + department);
        System.out.println("  Team Size    : " + teamSize + " people");
        super.displayInfo();
    }
}

// ═══════════════════════════════════════════════════════
//  SUBCLASS — Developer
// ═══════════════════════════════════════════════════════
class Developer extends StaffMember {
    private String programmingLanguage;
    private String level;

    private static final double BASE_BONUS_RATE = 0.10;

    public Developer(String name, int id, double baseSalary,
                     int yearsOfExperience, String programmingLanguage, String level) {
        super(name, id, baseSalary, yearsOfExperience);
        this.programmingLanguage = programmingLanguage;
        this.level               = level;
    }

    @Override
    public double calculateBonus() {
        double levelBonus = switch (level.toLowerCase()) {
            case "senior" -> 0.08;
            case "mid"    -> 0.04;
            default       -> 0.00;
        };
        return baseSalary * (BASE_BONUS_RATE + levelBonus + (yearsOfExperience * 0.003));
    }

    @Override
    protected double getBonusRate() {
        double levelBonus = switch (level.toLowerCase()) {
            case "senior" -> 0.08;
            case "mid"    -> 0.04;
            default       -> 0.00;
        };
        return BASE_BONUS_RATE + levelBonus + (yearsOfExperience * 0.003);
    }

    @Override
    public void displayInfo() {
        System.out.println("  Role         : 💻 Developer");
        System.out.println("  Level        : " + level);
        System.out.println("  Language     : " + programmingLanguage);
        super.displayInfo();
    }
}

// ═══════════════════════════════════════════════════════
//  MAIN
// ═══════════════════════════════════════════════════════
public class CompanyApp {

    static Scanner sc = new Scanner(System.in);

    // ══════════════════════════════════════════════════════
    //  INPUT VALIDATION HELPERS
    // ══════════════════════════════════════════════════════

    /** Non-empty text — loops until user types something */
    static String readString(String prompt) {
        while (true) {
            System.out.print(prompt);
            String val = sc.nextLine().trim();
            if (!val.isEmpty()) return val;
            System.out.println("  ⚠️  Cannot be empty. Please try again.");
        }
    }

    /** Strictly positive integer (e.g. ID, team size) */
    static int readPositiveInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                int val = Integer.parseInt(sc.nextLine().trim());
                if (val > 0) return val;
                System.out.println("  ⚠️  Must be greater than 0. Please try again.");
            } catch (NumberFormatException e) {
                System.out.println("  ⚠️  Invalid — enter a whole number (e.g. 5).");
            }
        }
    }

    /** Non-negative integer (e.g. years of experience can be 0) */
    static int readNonNegativeInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                int val = Integer.parseInt(sc.nextLine().trim());
                if (val >= 0) return val;
                System.out.println("  ⚠️  Cannot be negative. Please try again.");
            } catch (NumberFormatException e) {
                System.out.println("  ⚠️  Invalid — enter a whole number (e.g. 3).");
            }
        }
    }

    /** Strictly positive decimal (e.g. salary) */
    static double readPositiveDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                double val = Double.parseDouble(sc.nextLine().trim());
                if (val > 0) return val;
                System.out.println("  ⚠️  Must be greater than 0. Please try again.");
            } catch (NumberFormatException e) {
                System.out.println("  ⚠️  Invalid — enter a number (e.g. 2500.50).");
            }
        }
    }

    /** Accepts only "1", "2", or "3" */
    static String readChoice(String prompt) {
        while (true) {
            System.out.print(prompt);
            String val = sc.nextLine().trim();
            if (val.equals("1") || val.equals("2") || val.equals("3")) return val;
            System.out.println("  ⚠️  Invalid choice — please enter 1, 2, or 3.");
        }
    }

    // ══════════════════════════════════════════════════════
    //  LAYOUT HELPERS
    // ══════════════════════════════════════════════════════

    static void header(String title, String emoji) {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.printf ("║  %s  %-32s║%n", emoji, title);
        System.out.println("╚══════════════════════════════════════╝");
    }

    static void divider() { System.out.println("  " + "─".repeat(34)); }

    // ══════════════════════════════════════════════════════
    //  ENTRY POINT
    // ══════════════════════════════════════════════════════

    public static void main(String[] args) {

        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║       🏢  COMPANY HR SYSTEM          ║");
        System.out.println("╚══════════════════════════════════════╝\n");

        String userName = readString("👤  Your name : ");
        System.out.println("\nWelcome, " + userName + "! Let's register staff members.\n");

        // ════════════════════
        //  MANAGER input
        // ════════════════════
        header("MANAGER REGISTRATION", "👔");
        String mName = readString         ("  Full Name     : ");
        int    mId   = readPositiveInt    ("  Staff ID      : ");
        double mSal  = readPositiveDouble ("  Base Salary $ : ");
        int    mExp  = readNonNegativeInt ("  Years of Exp  : ");
        String mDept = readString         ("  Department    : ");
        int    mTeam = readPositiveInt    ("  Team Size     : ");

        Manager manager = new Manager(mName, mId, mSal, mExp, mDept, mTeam);

        // ════════════════════
        //  DEVELOPER input
        // ════════════════════
        header("DEVELOPER REGISTRATION", "💻");
        String dName = readString         ("  Full Name     : ");
        int    dId   = readPositiveInt    ("  Staff ID      : ");
        double dSal  = readPositiveDouble ("  Base Salary $ : ");
        int    dExp  = readNonNegativeInt ("  Years of Exp  : ");
        String dLang = readString         ("  Language      : ");
        System.out.println("  Level         : 1) Junior   2) Mid   3) Senior");
        String dLevel = switch (readChoice("  Choice (1/2/3) : ")) {
            case "2" -> "Mid";
            case "3" -> "Senior";
            default  -> "Junior";
        };

        Developer developer = new Developer(dName, dId, dSal, dExp, dLang, dLevel);

        // ════════════════════
        //  STAFF PROFILES
        // ════════════════════
        header("STAFF PROFILES", "📋");

        System.out.println("\n👔  Manager ───────────────────────────");
        manager.displayInfo();

        System.out.println("\n💻  Developer ─────────────────────────");
        developer.displayInfo();

        // ════════════════════════════════════════
        //  ⭐ POLYMORPHISM — StaffMember[] array
        // ════════════════════════════════════════
        header("PAYROLL SUMMARY — Polymorphism", "💰");

        StaffMember[] team = { manager, developer };

        System.out.println();
        System.out.printf("  %-14s %-10s %-12s %-12s%n",
                "Name", "Role", "Bonus", "Total Comp.");
        divider();

        double totalBonus = 0, totalComp = 0;
        for (StaffMember e : team) {
            String role = (e instanceof Manager) ? "Manager" : "Developer";
            System.out.printf("  %-14s %-10s $%-11.2f $%-11.2f%n",
                    e.name, role, e.calculateBonus(), e.calculateTotalCompensation());
            totalBonus += e.calculateBonus();
            totalComp  += e.calculateTotalCompensation();
        }
        divider();
        System.out.printf("  %-25s $%-11.2f $%-11.2f%n", "TOTALS", totalBonus, totalComp);

        header("BONUS BREAKDOWN", "📊");
        System.out.printf("%n  👔 Manager   bonus rate : %.1f%%%n", manager.getBonusRate()   * 100);
        System.out.printf("  💻 Developer bonus rate : %.1f%%%n",  developer.getBonusRate() * 100);
        System.out.printf("%n  📈 Manager earns $%.2f more in bonus.%n",
                manager.calculateBonus() - developer.calculateBonus());

        System.out.println("\n✅  Payroll complete! Thanks, " + userName + " 🎉");
        sc.close();
    }
}