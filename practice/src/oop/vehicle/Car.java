package oop.vehicle;

// ─── Derived class ────────────────────────────────────────────────────────────
public class Car extends Vehicle {

    public Car(String brand) {
        super(brand);
    }

    // ✅ honk() is inherited from Vehicle as-is — final means no override allowed
}