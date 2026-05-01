package oop;
import java.util.Scanner;

// ═══════════════════════════════════════════════════════
//  ABSTRACT BASE CLASS
// ═══════════════════════════════════════════════════════
abstract class BaseShape {
    protected String color;

    public BaseShape(String color) {
        this.color = color;
    }

    // Abstract methods — every subclass MUST implement these
    public abstract double area();
    public abstract double perimeter();

    // Concrete shared method
    public void displayInfo() {
        System.out.println("  Color     : " + color);
        System.out.printf ("  Area      : %.2f%n", area());
        System.out.printf ("  Perimeter : %.2f%n", perimeter());
    }
}

// ═══════════════════════════════════════════════════════
//  CIRCLE
// ═══════════════════════════════════════════════════════
class CircleGeo extends BaseShape {
    private double radius;

    public CircleGeo(String color, double radius) {
        super(color);
        this.radius = radius;
    }

    @Override
    public double area() {
        return Math.PI * radius * radius;         // π × r²
    }

    @Override
    public double perimeter() {
        return 2 * Math.PI * radius;              // 2 × π × r
    }

    @Override
    public void displayInfo() {
        System.out.println("  Radius    : " + radius);
        super.displayInfo();
    }
}

// ═══════════════════════════════════════════════════════
//  RECTANGLE
// ═══════════════════════════════════════════════════════
class RectGeo extends BaseShape {
    private double width, height;

    public RectGeo(String color, double width, double height) {
        super(color);
        this.width  = width;
        this.height = height;
    }

    @Override
    public double area() {
        return width * height;                    // w × h
    }

    @Override
    public double perimeter() {
        return 2 * (width + height);              // 2(w + h)
    }

    @Override
    public void displayInfo() {
        System.out.println("  Width     : " + width);
        System.out.println("  Height    : " + height);
        super.displayInfo();
    }
}

// ═══════════════════════════════════════════════════════
//  TRIANGLE  (Heron's formula)
// ═══════════════════════════════════════════════════════
class TriangleGeo extends BaseShape {
    private double a, b, c;   // three sides

    public TriangleGeo(String color, double a, double b, double c) {
        super(color);

        // Validate triangle inequality
        if (a + b <= c || a + c <= b || b + c <= a)
            throw new IllegalArgumentException(
                    "❌  Invalid triangle: sides " + a + ", " + b + ", " + c
            );

        this.a = a;
        this.b = b;
        this.c = c;
    }

    @Override
    public double area() {
        // ── Heron's formula ──────────────────────────────────
        // s = (a + b + c) / 2
        // Area = √( s(s−a)(s−b)(s−c) )
        double s = (a + b + c) / 2.0;
        return Math.sqrt(s * (s - a) * (s - b) * (s - c));
    }

    @Override
    public double perimeter() {
        return a + b + c;                         // a + b + c
    }

    @Override
    public void displayInfo() {
        System.out.printf("  Sides     : a=%.2f | b=%.2f | c=%.2f%n", a, b, c);
        super.displayInfo();
    }
}

// ═══════════════════════════════════════════════════════
//  MAIN
// ═══════════════════════════════════════════════════════
public class GeometryApp {

    // ── helper: print a section header ──────────────────
    static void header(String title, String emoji) {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.printf ("║  %s  %-32s║%n", emoji, title);
        System.out.println("╚══════════════════════════════════════╝");
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║       📐  SHAPE CALCULATOR APP       ║");
        System.out.println("╚══════════════════════════════════════╝\n");

        // ── User info ────────────────────────────────────
        System.out.println("👤  Your information:");
        System.out.print("   Name : ");
        String name = sc.nextLine();

        System.out.println("\nHello, " + name + "! Let's build your shapes. 🎨");

        // ════════════════════
        //  CIRCLE input
        // ════════════════════
        header("CIRCLE", "⭕");
        System.out.print("  Color  : ");
        String cColor = sc.nextLine();
        System.out.print("  Radius : ");
        double radius = Double.parseDouble(sc.nextLine().trim());

        CircleGeo circle = new CircleGeo(cColor, radius);

        // ════════════════════
        //  RECTANGLE input
        // ════════════════════
        header("RECTANGLE", "▭ ");
        System.out.print("  Color  : ");
        String rColor = sc.nextLine();
        System.out.print("  Width  : ");
        double width = Double.parseDouble(sc.nextLine().trim());
        System.out.print("  Height : ");
        double height = Double.parseDouble(sc.nextLine().trim());

        RectGeo rectangle = new RectGeo(rColor, width, height);

        // ════════════════════
        //  TRIANGLE input
        // ════════════════════
        header("TRIANGLE", "🔺");
        System.out.print("  Color  : ");
        String tColor = sc.nextLine();
        System.out.print("  Side a : ");
        double a = Double.parseDouble(sc.nextLine().trim());
        System.out.print("  Side b : ");
        double b = Double.parseDouble(sc.nextLine().trim());
        System.out.print("  Side c : ");
        double c = Double.parseDouble(sc.nextLine().trim());

        TriangleGeo triangle;
        try {
            triangle = new TriangleGeo(tColor, a, b, c);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            sc.close();
            return;
        }

        // ════════════════════
        //  RESULTS
        // ════════════════════
        System.out.println("\n\n╔══════════════════════════════════════╗");
        System.out.println("║          📊  RESULTS SUMMARY          ║");
        System.out.println("╚══════════════════════════════════════╝");

        System.out.println("\n⭕  Circle:");
        circle.displayInfo();

        System.out.println("\n▭   Rectangle:");
        rectangle.displayInfo();

        System.out.println("\n🔺  Triangle  (Heron's formula):");
        triangle.displayInfo();

        // ════════════════════
        //  POLYMORPHISM demo
        // ════════════════════
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║     🔁  POLYMORPHISM IN ACTION       ║");
        System.out.println("╚══════════════════════════════════════╝");

        BaseShape[] shapes = { circle, rectangle, triangle };
        String[] names = { "Circle", "Rectangle", "Triangle" };

        System.out.printf("%n%-12s  %-10s  %-10s%n", "Shape", "Area", "Perimeter");
        System.out.println("─".repeat(36));
        for (int i = 0; i < shapes.length; i++) {
            System.out.printf("%-12s  %-10.2f  %-10.2f%n",
                    names[i], shapes[i].area(), shapes[i].perimeter());
        }

        System.out.println("\n✅  Done! Thanks " + name + " 🎉");
        sc.close();
    }
}