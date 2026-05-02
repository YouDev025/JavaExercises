package oop;
import java.util.Scanner;

// ═══════════════════════════════════════════════════════
//  CALCULATOR CLASS — Method Overloading
// ═══════════════════════════════════════════════════════
class Calculator {

    // ── Overload 1 : add(int, int) ───────────────────────
    public int add(int a, int b) {
        return a + b;
    }

    // ── Overload 2 : add(int, int, int) ─────────────────
    public int add(int a, int b, int c) {
        return a + b + c;
    }

    // ── Overload 3 : add(double, double) ────────────────
    public double add(double a, double b) {
        return a + b;
    }

    // ── Bonus overloads ──────────────────────────────────
    public double subtract(double a, double b) { return a - b; }
    public double multiply(double a, double b) { return a * b; }
    public double divide(double a, double b) {
        if (b == 0) throw new ArithmeticException("❌ Cannot divide by zero!");
        return a / b;
    }
}

// ═══════════════════════════════════════════════════════
//  MAIN
// ═══════════════════════════════════════════════════════
public class CalculatorApp {

    static void header(String title, String emoji) {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.printf ("║  %s  %-32s║%n", emoji, title);
        System.out.println("╚══════════════════════════════════════╝");
    }

    static void divider() { System.out.println("  " + "─".repeat(34)); }

    public static void main(String[] args) {
        Scanner sc   = new Scanner(System.in);
        Calculator calc = new Calculator();

        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║       🧮  CALCULATOR APP             ║");
        System.out.println("╚══════════════════════════════════════╝\n");

        // ── User info ─────────────────────────────────────
        System.out.print("👤  Your name : ");
        String name = sc.nextLine();
        System.out.println("\nHello, " + name + "! Let's crunch some numbers. 💡\n");

        // ════════════════════════════════════════
        //  OVERLOAD 1 — add(int, int)
        // ════════════════════════════════════════
        header("ADD — Two Integers", "➕");
        System.out.print("  Enter integer A : ");
        int i1 = Integer.parseInt(sc.nextLine().trim());
        System.out.print("  Enter integer B : ");
        int i2 = Integer.parseInt(sc.nextLine().trim());

        int result1 = calc.add(i1, i2);
        System.out.println("\n  ✅  add(" + i1 + ", " + i2 + ")  =  " + result1);

        // ════════════════════════════════════════
        //  OVERLOAD 2 — add(int, int, int)
        // ════════════════════════════════════════
        header("ADD — Three Integers", "➕");
        System.out.print("  Enter integer A : ");
        int j1 = Integer.parseInt(sc.nextLine().trim());
        System.out.print("  Enter integer B : ");
        int j2 = Integer.parseInt(sc.nextLine().trim());
        System.out.print("  Enter integer C : ");
        int j3 = Integer.parseInt(sc.nextLine().trim());

        int result2 = calc.add(j1, j2, j3);
        System.out.println("\n  ✅  add(" + j1 + ", " + j2 + ", " + j3 + ")  =  " + result2);

        // ════════════════════════════════════════
        //  OVERLOAD 3 — add(double, double)
        // ════════════════════════════════════════
        header("ADD — Two Doubles", "➕");
        System.out.print("  Enter decimal A : ");
        double d1 = Double.parseDouble(sc.nextLine().trim());
        System.out.print("  Enter decimal B : ");
        double d2 = Double.parseDouble(sc.nextLine().trim());

        double result3 = calc.add(d1, d2);
        System.out.printf("%n  ✅  add(%.2f, %.2f)  =  %.2f%n", d1, d2, result3);

        // ════════════════════════════════════════
        //  BONUS — subtract, multiply, divide
        // ════════════════════════════════════════
        header("BONUS OPERATIONS", "🔢");
        System.out.print("  Enter number X : ");
        double x = Double.parseDouble(sc.nextLine().trim());
        System.out.print("  Enter number Y : ");
        double y = Double.parseDouble(sc.nextLine().trim());

        System.out.printf("%n  ➕  add      (%.2f, %.2f) = %.2f%n", x, y, calc.add(x, y));
        System.out.printf("  ➖  subtract (%.2f, %.2f) = %.2f%n", x, y, calc.subtract(x, y));
        System.out.printf("  ✖️   multiply (%.2f, %.2f) = %.2f%n", x, y, calc.multiply(x, y));

        try {
            System.out.printf("  ➗  divide   (%.2f, %.2f) = %.2f%n", x, y, calc.divide(x, y));
        } catch (ArithmeticException e) {
            System.out.println("  " + e.getMessage());
        }

        // ════════════════════════════════════════
        //  SUMMARY TABLE
        // ════════════════════════════════════════
        header("SUMMARY — Overloaded Methods", "📋");
        System.out.println();
        System.out.printf("  %-30s  %s%n", "Method Signature", "Result");
        divider();
        System.out.printf("  %-30s  %d%n",    "add(int, int)",         result1);
        System.out.printf("  %-30s  %d%n",    "add(int, int, int)",    result2);
        System.out.printf("  %-30s  %.2f%n",  "add(double, double)",   result3);

        System.out.println("\n✅  Done! Thanks, " + name + " 🎉");
        sc.close();
    }
}