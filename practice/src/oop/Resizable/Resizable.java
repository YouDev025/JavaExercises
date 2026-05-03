package oop;

// ─── Interface ───────────────────────────────────────────────────────────────
public interface Resizable {

    /**
     * Resizes the shape by the given factor.
     * factor > 1  → grows
     * factor < 1  → shrinks
     * factor = 1  → unchanged
     */
    void resize(double factor);
}