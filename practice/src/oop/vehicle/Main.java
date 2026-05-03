package oop.vehicle;

// ─── Main ────────────────────────────────────────────────────────────────────
public class Main {
    public static void main(String[] args) {
        Vehicle v = new Vehicle("Generic");
        v.honk();

        Car c = new Car("Toyota");
        c.honk();   // would call the overridden version — but this never compiles
    }
}