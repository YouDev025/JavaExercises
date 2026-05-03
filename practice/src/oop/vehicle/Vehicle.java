package oop.vehicle;

public class Vehicle {

    protected String brand;

    public Vehicle(String brand) {
        this.brand = brand;
    }

    // final → no subclass is EVER allowed to override this method
    public final void honk() {
        System.out.println(brand + " goes: BEEP BEEP!");
    }
}
