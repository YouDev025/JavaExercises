package oop.Resizable;


// ─── Rectangle implements Resizable ─────────────────────────────────────────
public class GeoRectangle implements Resizable {

    private double width;
    private double height;

    public GeoRectangle(double width, double height) {
        this.width  = width;
        this.height = height;
    }

    // ── Interface method ─────────────────────────────────────────────────────
    @Override
    public void resize(double factor) {
        if (factor <= 0) {
            throw new IllegalArgumentException("Resize factor must be positive.");
        }
        width  *= factor;
        height *= factor;
        System.out.printf("Resized by x%.1f  →  width = %.2f, height = %.2f%n",
                factor, width, height);
    }

    // ── Helpers ──────────────────────────────────────────────────────────────
    public double area()      { return width * height; }
    public double perimeter() { return 2 * (width + height); }

    @Override
    public String toString() {
        return String.format("Rectangle [ width=%.2f, height=%.2f, area=%.2f, perimeter=%.2f ]",
                width, height, area(), perimeter());
    }

    // ── Getters / Setters ────────────────────────────────────────────────────
    public double getWidth()        { return width; }
    public double getHeight()       { return height; }
    public void   setWidth(double w)  { this.width  = w; }
    public void   setHeight(double h) { this.height = h; }
}