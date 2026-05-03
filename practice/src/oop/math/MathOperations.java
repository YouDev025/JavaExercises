package oop.math;

// ─── Utility class — all methods are static (no instance needed) ──────────────
public class MathOperations {

    // Private constructor prevents instantiation: MathOperations m = new MathOperations() ❌
    private MathOperations() {}

    // ── Square ────────────────────────────────────────────────────────────────
    public static double square(double n) {
        return n * n;
    }

    // ── Cube ──────────────────────────────────────────────────────────────────
    public static double cube(double n) {
        return n * n * n;
    }

    // ── isEven ────────────────────────────────────────────────────────────────
    public static boolean isEven(int n) {
        return n % 2 == 0;
    }
}