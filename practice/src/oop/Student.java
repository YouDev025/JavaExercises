package oop;
import java.util.Scanner;

public class Student {
    private int rollNumber;
    private String name;
    private double marks1;
    private double marks2;
    private double marks3;

    public Student(int rollNumber, String name, double marks1, double marks2, double marks3) {
        this.rollNumber = rollNumber;
        this.name = name;
        this.marks1 = marks1;
        this.marks2 = marks2;
        this.marks3 = marks3;
    }

    public int getRollNumber() { return rollNumber; }
    public String getName() { return name; }
    public double getMarks1() { return marks1; }
    public double getMarks2() { return marks2; }
    public double getMarks3() { return marks3; }

    public void setRollNumber(int rollNumber) { this.rollNumber = rollNumber; }
    public void setName(String name) { this.name = name; }
    public void setMarks1(double marks1) { this.marks1 = marks1; }
    public void setMarks2(double marks2) { this.marks2 = marks2; }
    public void setMarks3(double marks3) { this.marks3 = marks3; }

    public double calculateAverage() {
        return (marks1 + marks2 + marks3) / 3;
    }

    public String getGrade() {
        double avg = calculateAverage();
        if (avg >= 90) return "A+";
        else if (avg >= 80) return "A";
        else if (avg >= 70) return "B";
        else if (avg >= 60) return "C";
        else if (avg >= 50) return "D";
        else return "F";
    }

    public void displayReport() {
        System.out.println("\n======= Student Report =======");
        System.out.println("Roll Number : " + rollNumber);
        System.out.println("Name        : " + name);
        System.out.println("Subject 1   : " + marks1);
        System.out.println("Subject 2   : " + marks2);
        System.out.println("Subject 3   : " + marks3);
        System.out.printf( "Average     : %.2f%n", calculateAverage());
        System.out.println("Grade       : " + getGrade());
        System.out.println("==============================");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Student Information Entry ===");

        System.out.print("Enter Roll Number: ");
        int rollNumber = scanner.nextInt();
        scanner.nextLine(); // consume newline

        System.out.print("Enter Student Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Marks for Subject 1: ");
        double marks1 = scanner.nextDouble();

        System.out.print("Enter Marks for Subject 2: ");
        double marks2 = scanner.nextDouble();

        System.out.print("Enter Marks for Subject 3: ");
        double marks3 = scanner.nextDouble();

        scanner.close();

        Student student = new Student(rollNumber, name, marks1, marks2, marks3);
        student.displayReport();
    }
}