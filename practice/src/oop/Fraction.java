package oop;
import java.util.Scanner;
import java.util.Objects;

public class Fraction {

    // ── Private Fields ────────────────────────────────────────
    private int numerator;
    private int denominator;

    // ── No-Arg Constructor ────────────────────────────────────
    public Fraction() {
        this.numerator   = 0;
        this.denominator = 1;
    }

    // ── Parameterized Constructor ─────────────────────────────
    public Fraction(int numerator, int denominator) {
        if (denominator == 0) {
            throw new ArithmeticException("❌ Denominator cannot be zero.");
        }
        // Keep negative sign on numerator only
        if (denominator < 0) {
            numerator   = -numerator;
            denominator = -denominator;
        }
        this.numerator   = numerator;
        this.denominator = denominator;
        simplify();
    }

    // ── Getters ───────────────────────────────────────────────
    public int getNumerator()   { return numerator; }
    public int getDenominator() { return denominator; }

    // ── Setters ───────────────────────────────────────────────
    public void setNumerator(int numerator) {
        this.numerator = numerator;
        simplify();
    }

    public void setDenominator(int denominator) {
        if (denominator == 0) {
            System.out.println("❌ Denominator cannot be zero. Keeping previous value.");
            return;
        }
        this.denominator = denominator;
        simplify();
    }

    // ── GCD (Euclidean Algorithm) ─────────────────────────────
    private int gcd(int a, int b) {
        a = Math.abs(a);
        b = Math.abs(b);
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    // ── LCM ──────────────────────────────────────────────────
    private int lcm(int a, int b) {
        return Math.abs(a * b) / gcd(a, b);
    }

    // ── Simplify ─────────────────────────────────────────────
    public void simplify() {
        int common = gcd(Math.abs(numerator), denominator);
        numerator   /= common;
        denominator /= common;
    }

    // ── Add ──────────────────────────────────────────────────
    public Fraction add(Fraction other) {
        int lcmDenom = lcm(this.denominator, other.denominator);
        int newNumerator = (this.numerator   * (lcmDenom / this.denominator))
                + (other.numerator  * (lcmDenom / other.denominator));
        return new Fraction(newNumerator, lcmDenom);
    }

    // ── Subtract ─────────────────────────────────────────────
    public Fraction subtract(Fraction other) {
        int lcmDenom = lcm(this.denominator, other.denominator);
        int newNumerator = (this.numerator  * (lcmDenom / this.denominator))
                - (other.numerator * (lcmDenom / other.denominator));
        return new Fraction(newNumerator, lcmDenom);
    }

    // ── Multiply ─────────────────────────────────────────────
    public Fraction multiply(Fraction other) {
        return new Fraction(this.numerator * other.numerator,
                this.denominator * other.denominator);
    }

    // ── Divide ───────────────────────────────────────────────
    public Fraction divide(Fraction other) {
        if (other.numerator == 0) {
            throw new ArithmeticException("❌ Cannot divide by zero fraction.");
        }
        return new Fraction(this.numerator * other.denominator,
                this.denominator * other.numerator);
    }

    // ── Compare ──────────────────────────────────────────────
    public int compareTo(Fraction other) {
        int lhs = this.numerator  * other.denominator;
        int rhs = other.numerator * this.denominator;
        return Integer.compare(lhs, rhs);
    }

    public boolean isGreaterThan(Fraction other) { return compareTo(other) >  0; }
    public boolean isLessThan   (Fraction other) { return compareTo(other) <  0; }
    public boolean isEqualTo    (Fraction other) { return compareTo(other) == 0; }

    // ── ⋆ Challenge: Override equals() & hashCode() ──────────
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Fraction other = (Fraction) obj;
        // Both are already simplified in constructor, so direct compare is safe
        return this.numerator == other.numerator &&
                this.denominator == other.denominator;
    }

    @Override
    public int hashCode() {
        return Objects.hash(numerator, denominator);
    }

    // ── toString ─────────────────────────────────────────────
    @Override
    public String toString() {
        if (denominator == 1) return String.valueOf(numerator); // whole number
        return numerator + "/" + denominator;
    }

    // ── Display Detail ────────────────────────────────────────
    public void displayInfo() {
        System.out.println("\n====== Fraction Details ======");
        System.out.println("Fraction   : " + this);
        System.out.printf( "Decimal    : %.4f%n", (double) numerator / denominator);
        System.out.println("==============================");
    }

    // ── Main ─────────────────────────────────────────────────
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("╔══════════════════════════════╗");
        System.out.println("║     ➗ Fraction Calculator    ║");
        System.out.println("╚══════════════════════════════╝");

        // ── Input Fraction 1 ──────────────────────────────────
        System.out.println("\n--- Enter First Fraction ---");
        System.out.print("Numerator   : ");
        int n1 = scanner.nextInt();
        System.out.print("Denominator : ");
        int d1 = scanner.nextInt();

        // ── Input Fraction 2 ──────────────────────────────────
        System.out.println("\n--- Enter Second Fraction ---");
        System.out.print("Numerator   : ");
        int n2 = scanner.nextInt();
        System.out.print("Denominator : ");
        int d2 = scanner.nextInt();

        scanner.close();

        // ── Create Fractions ──────────────────────────────────
        Fraction f1 = new Fraction(n1, d1);
        Fraction f2 = new Fraction(n2, d2);

        f1.displayInfo();
        f2.displayInfo();

        // ── Operations ────────────────────────────────────────
        System.out.println("\n======= Operations =======");
        System.out.println("  " + f1 + "  +  " + f2 + "  =  " + f1.add(f2));
        System.out.println("  " + f1 + "  -  " + f2 + "  =  " + f1.subtract(f2));
        System.out.println("  " + f1 + "  ×  " + f2 + "  =  " + f1.multiply(f2));
        System.out.println("  " + f1 + "  ÷  " + f2 + "  =  " + f1.divide(f2));

        // ── Comparison ────────────────────────────────────────
        System.out.println("\n======= Comparison =======");
        System.out.println("  " + f1 + " > "  + f2 + " : " + f1.isGreaterThan(f2));
        System.out.println("  " + f1 + " < "  + f2 + " : " + f1.isLessThan(f2));
        System.out.println("  " + f1 + " == " + f2 + " : " + f1.isEqualTo(f2));

        // ── ⋆ Challenge ───────────────────────────────────────
        System.out.println("\n====== equals() & hashCode() ======");
        System.out.println("  equals()   : " + f1.equals(f2));
        System.out.println("  hashCode() f1 : " + f1.hashCode());
        System.out.println("  hashCode() f2 : " + f2.hashCode());
    }
}