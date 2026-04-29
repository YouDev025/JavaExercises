package oop;

import java.util.Scanner;

public class Time {
    private int hours;
    private int minutes;
    private int seconds;

    public Time(int hours, int minutes, int seconds) {
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
    }

    public int getHours() { return hours; }
    public int getMinutes() { return minutes; }
    public int getSeconds() { return seconds; }

    public void setHours(int hours) { this.hours = hours; }
    public void setMinutes(int minutes) { this.minutes = minutes; }
    public void setSeconds(int seconds) { this.seconds = seconds; }

    public String displayTime() {
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    private static int readField(Scanner scanner, String fieldName, int min, int max) {
        while (true) {
            try {
                System.out.print("Enter " + fieldName + " (" + min + "-" + max + "): ");
                int value = scanner.nextInt();
                if (value < min || value > max) {
                    System.out.println("Error: " + fieldName + " must be between " + min + " and " + max + ". Try again.");
                } else {
                    return value;
                }
            } catch (java.util.InputMismatchException e) {
                System.out.println("Error: Invalid input. Please enter a whole number. Try again.");
                scanner.nextLine();
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int h = readField(scanner, "hours",   0, 23);
        int m = readField(scanner, "minutes", 0, 59);
        int s = readField(scanner, "seconds", 0, 59);

        Time time = new Time(h, m, s);
        System.out.println("Time: " + time.displayTime());

        scanner.close();
    }
}