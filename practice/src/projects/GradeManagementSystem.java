package projects;

import java.io.*;
import java.util.*;

// Student Grade Management System
// Features: add students, record grades, compute statistics,
//           assign letter grades, sort, filter, and generate reports
// Concepts: HashMap, ArrayList, sorting, file I/O, statistics
public class GradeManagementSystem {

    // ─────────────────────────────────────────────
    //  STUDENT CLASS
    // ─────────────────────────────────────────────

    // Represents a single student with their ID, name, and list of grades
    // All statistical computations are derived from the grades ArrayList
    static class Student {

        private static int idCounter = 1000; // IDs start from 1001

        int               id;       // Unique student identifier
        String            name;     // Full name of the student
        ArrayList<Double> grades;   // All recorded grades for this student

        // Constructor to create a new student with an auto-assigned ID
        Student(String name) {
            this.id     = ++idCounter;
            this.name   = name;
            this.grades = new ArrayList<>();
        }

        // Constructor used when loading a student from a save file
        Student(int id, String name, ArrayList<Double> grades) {
            this.id     = id;
            this.name   = name;
            this.grades = grades;
            if (id >= idCounter) idCounter = id;
        }

        // ── GRADE STATISTICS ──

        // Returns the arithmetic mean of all recorded grades
        // Returns 0.0 if the student has no grades yet
        double getAverage() {
            if (grades.isEmpty()) return 0.0;
            double sum = 0;
            for (double g : grades) sum += g;
            return sum / grades.size();
        }

        // Returns the highest grade recorded for this student
        // Returns 0.0 if the student has no grades yet
        double getHighest() {
            if (grades.isEmpty()) return 0.0;
            double max = grades.get(0);
            for (double g : grades) if (g > max) max = g;
            return max;
        }

        // Returns the lowest grade recorded for this student
        // Returns 0.0 if the student has no grades yet
        double getLowest() {
            if (grades.isEmpty()) return 0.0;
            double min = grades.get(0);
            for (double g : grades) if (g < min) min = g;
            return min;
        }

        // Maps the student's average to a letter grade using standard grading scale
        String getLetterGrade() {
            return toLetterGrade(getAverage());
        }

        // Returns a pass/fail status based on whether the average meets the threshold
        String getPassFail() {
            return getAverage() >= 50.0 ? "PASS" : "FAIL";
        }

        // Adds a new grade to this student's record after validating the range
        // Returns true if the grade was valid and added, false otherwise
        boolean addGrade(double grade) {
            if (grade < 0 || grade > 100) return false;
            grades.add(grade);
            return true;
        }

        // Removes the grade at the given index (zero-based) from the grade list
        // Returns true if the index was valid and the grade was removed
        boolean removeGrade(int index) {
            if (index < 0 || index >= grades.size()) return false;
            grades.remove(index);
            return true;
        }

        // Serializes this student to a pipe-delimited line for file storage
        // Format: id|name|grade1,grade2,...
        String toFileLine() {
            StringBuilder gradesStr = new StringBuilder();
            for (int i = 0; i < grades.size(); i++) {
                gradesStr.append(grades.get(i));
                if (i < grades.size() - 1) gradesStr.append(",");
            }
            return id + "|" + name + "|" + gradesStr;
        }

        // Deserializes a pipe-delimited file line back into a Student object
        static Student fromFileLine(String line) {
            String[] parts  = line.split("\\|", -1);
            int      id     = Integer.parseInt(parts[0]);
            String   name   = parts[1];
            ArrayList<Double> grades = new ArrayList<>();
            if (!parts[2].isEmpty()) {
                for (String g : parts[2].split(",")) {
                    grades.add(Double.parseDouble(g));
                }
            }
            return new Student(id, name, grades);
        }
    }

    // ─────────────────────────────────────────────
    //  GRADE MANAGER
    // ─────────────────────────────────────────────

    // Core business logic for the grade management system
    // Uses a HashMap keyed by student ID for O(1) average-case lookups
    static class GradeManager {

        // Primary data store — maps student ID to Student object
        private HashMap<Integer, Student> students = new HashMap<>();

        // Ordered list of IDs to maintain insertion order for display
        private ArrayList<Integer> insertionOrder = new ArrayList<>();

        // File path for persistent storage
        private static final String FILE_PATH = "grades.txt";

        // ── ADD STUDENT ──

        // Creates a new student and registers them in the system
        // Returns the new student's auto-assigned ID
        public int addStudent(String name) {
            // Check for duplicate names (case-insensitive) before adding
            for (Student s : students.values()) {
                if (s.name.equalsIgnoreCase(name)) {
                    System.out.println("  Student \"" + name
                            + "\" already exists with ID #" + s.id);
                    return -1;
                }
            }
            Student student = new Student(name);
            students.put(student.id, student);
            insertionOrder.add(student.id);
            System.out.println("  Added: " + name + "  (ID: #" + student.id + ")");
            return student.id;
        }

        // ── REMOVE STUDENT ──

        // Removes a student and all their grade records from the system
        public boolean removeStudent(int id) {
            Student s = students.remove(id);
            if (s == null) {
                System.out.println("  Student #" + id + " not found.");
                return false;
            }
            insertionOrder.remove(Integer.valueOf(id));
            System.out.println("  Removed student: " + s.name);
            return true;
        }

        // ── ADD GRADE ──

        // Records a grade for the student with the given ID
        // Validates both the student ID and the grade range (0–100)
        public boolean addGrade(int id, double grade) {
            Student s = students.get(id);
            if (s == null) {
                System.out.println("  Student #" + id + " not found.");
                return false;
            }
            if (!s.addGrade(grade)) {
                System.out.println("  Invalid grade. Must be between 0 and 100.");
                return false;
            }
            System.out.printf("  Grade %.1f added for %s  (average now: %.2f %s)%n",
                    grade, s.name, s.getAverage(), s.getLetterGrade());
            return true;
        }

        // ── REMOVE GRADE ──

        // Removes a specific grade entry by its index from a student's record
        public boolean removeGrade(int id, int gradeIndex) {
            Student s = students.get(id);
            if (s == null) {
                System.out.println("  Student #" + id + " not found.");
                return false;
            }
            if (s.grades.isEmpty()) {
                System.out.println("  No grades recorded for " + s.name);
                return false;
            }
            // Display grades so the user can choose which index to remove
            System.out.println("  Grades for " + s.name + ":");
            for (int i = 0; i < s.grades.size(); i++) {
                System.out.printf("    [%d] %.1f%n", i, s.grades.get(i));
            }
            if (!s.removeGrade(gradeIndex)) {
                System.out.println("  Invalid grade index.");
                return false;
            }
            System.out.println("  Grade at index " + gradeIndex + " removed.");
            return true;
        }

        // ── VIEW STUDENT ──

        // Displays full details for one student including all grades and statistics
        public void viewStudent(int id) {
            Student s = students.get(id);
            if (s == null) {
                System.out.println("  Student #" + id + " not found.");
                return;
            }
            System.out.println("\n" + "─".repeat(50));
            System.out.println("  Student ID:    #" + s.id);
            System.out.println("  Name:          " + s.name);
            System.out.println("  Grades:        " + formatGrades(s.grades));
            System.out.printf ("  Average:       %.2f%n", s.getAverage());
            System.out.printf ("  Highest:       %.1f%n", s.getHighest());
            System.out.printf ("  Lowest:        %.1f%n", s.getLowest());
            System.out.println("  Letter Grade:  " + s.getLetterGrade());
            System.out.println("  Status:        " + s.getPassFail());
            System.out.println("─".repeat(50));
        }

        // ── LIST ALL STUDENTS ──

        // Displays a summary table of all students with key statistics
        public void listAll() {
            if (students.isEmpty()) {
                System.out.println("  No students registered.");
                return;
            }

            System.out.println("\n" + "═".repeat(80));
            System.out.printf("  %-6s %-22s %-8s %-8s %-8s %-6s %-6s%n",
                    "ID", "Name", "Avg", "High", "Low", "Grade", "Status");
            System.out.println("─".repeat(80));

            for (int id : insertionOrder) {
                Student s = students.get(id);
                System.out.printf("  #%-5d %-22s %-8.2f %-8.1f %-8.1f %-6s %-6s%n",
                        s.id, s.name, s.getAverage(), s.getHighest(),
                        s.getLowest(), s.getLetterGrade(), s.getPassFail());
            }
            System.out.println("═".repeat(80));
            System.out.println("  Total students: " + students.size());
        }

        // ── SORT AND DISPLAY ──

        // Sorts and displays all students according to the chosen criteria
        // sortBy: "name" | "average" | "grade" | "id"
        public void listSorted(String sortBy) {
            if (students.isEmpty()) {
                System.out.println("  No students to sort.");
                return;
            }

            ArrayList<Student> sorted = new ArrayList<>(students.values());

            switch (sortBy.toLowerCase()) {
                case "name":
                    // Sort alphabetically by student name (A to Z)
                    sorted.sort(Comparator.comparing(s -> s.name));
                    break;
                case "average":
                    // Sort by average grade from highest to lowest
                    sorted.sort((a, b) -> Double.compare(b.getAverage(), a.getAverage()));
                    break;
                case "grade":
                    // Sort by letter grade (A before B before C etc.)
                    sorted.sort(Comparator.comparing(Student::getLetterGrade));
                    break;
                default:
                    // Sort by student ID (ascending)
                    sorted.sort(Comparator.comparingInt(s -> s.id));
            }

            System.out.println("\n─── Sorted by: " + sortBy.toUpperCase() + " ───");
            System.out.printf("  %-6s %-22s %-10s %-8s %-6s%n",
                    "Rank", "Name", "Average", "ID", "Grade");
            System.out.println("─".repeat(56));
            for (int i = 0; i < sorted.size(); i++) {
                Student s = sorted.get(i);
                System.out.printf("  %-6d %-22s %-10.2f %-8d %-6s%n",
                        i + 1, s.name, s.getAverage(), s.id, s.getLetterGrade());
            }
        }

        // ── FILTER ──

        // Displays students that match the given letter grade filter
        public void filterByGrade(String letterGrade) {
            ArrayList<Student> filtered = new ArrayList<>();
            for (Student s : students.values()) {
                if (s.getLetterGrade().equals(letterGrade.toUpperCase())) {
                    filtered.add(s);
                }
            }
            if (filtered.isEmpty()) {
                System.out.println("  No students with grade " + letterGrade + ".");
                return;
            }
            System.out.println("\n─── Students with Grade " + letterGrade.toUpperCase()
                    + " (" + filtered.size() + ") ───");
            for (Student s : filtered) {
                System.out.printf("  #%-5d %-22s %.2f%n",
                        s.id, s.name, s.getAverage());
            }
        }

        // ── CLASS STATISTICS ──

        // Computes and displays aggregate statistics across all students
        public void classStatistics() {
            if (students.isEmpty()) {
                System.out.println("  No students to compute statistics for.");
                return;
            }

            // Collect all averages into an array for processing
            double[] averages = new double[students.size()];
            int      idx      = 0;
            for (Student s : students.values()) {
                averages[idx++] = s.getAverage();
            }

            // Compute class-wide statistics
            double classAvg  = 0;
            double classHigh = averages[0];
            double classLow  = averages[0];

            for (double avg : averages) {
                classAvg  += avg;
                if (avg > classHigh) classHigh = avg;
                if (avg < classLow)  classLow  = avg;
            }
            classAvg /= averages.length;

            // Compute standard deviation to measure grade spread
            double variance = 0;
            for (double avg : averages) {
                variance += Math.pow(avg - classAvg, 2);
            }
            double stdDev = Math.sqrt(variance / averages.length);

            // Compute median by sorting a copy of the averages array
            double[] sorted = averages.clone();
            Arrays.sort(sorted);
            double median = sorted.length % 2 == 0
                    ? (sorted[sorted.length / 2 - 1] + sorted[sorted.length / 2]) / 2.0
                    : sorted[sorted.length / 2];

            // Count students in each letter grade bracket
            Map<String, Integer> gradeCounts = new LinkedHashMap<>();
            for (String g : new String[]{"A", "B", "C", "D", "F"}) {
                gradeCounts.put(g, 0);
            }
            int passing = 0;
            int failing = 0;
            for (Student s : students.values()) {
                String lg = s.getLetterGrade();
                gradeCounts.put(lg, gradeCounts.getOrDefault(lg, 0) + 1);
                if (s.getAverage() >= 50) passing++; else failing++;
            }

            System.out.println("\n" + "═".repeat(50));
            System.out.println("         CLASS STATISTICS REPORT");
            System.out.println("═".repeat(50));
            System.out.println("  Total Students:    " + students.size());
            System.out.printf ("  Class Average:     %.2f  (%s)%n",
                    classAvg, toLetterGrade(classAvg));
            System.out.printf ("  Median Average:    %.2f%n", median);
            System.out.printf ("  Highest Average:   %.2f%n", classHigh);
            System.out.printf ("  Lowest Average:    %.2f%n", classLow);
            System.out.printf ("  Std Deviation:     %.2f%n", stdDev);
            System.out.println("─".repeat(50));
            System.out.println("  Passing:           " + passing
                    + " (" + pct(passing, students.size()) + "%)");
            System.out.println("  Failing:           " + failing
                    + " (" + pct(failing, students.size()) + "%)");
            System.out.println("─".repeat(50));
            System.out.println("  Grade Distribution:");
            for (Map.Entry<String, Integer> e : gradeCounts.entrySet()) {
                int    count = e.getValue();
                String bar   = "█".repeat(count);
                System.out.printf("    %-4s %3d  %s%n",
                        e.getKey(), count, bar);
            }
            System.out.println("═".repeat(50));
        }

        // ── GENERATE REPORT ──

        // Writes a full class report to a text file including all student data
        // and class-wide statistics
        public void generateReport() {
            String reportPath = "grade_report.txt";

            try (BufferedWriter writer = new BufferedWriter(
                    new FileWriter(reportPath))) {

                writer.write("═".repeat(60));
                writer.newLine();
                writer.write("         STUDENT GRADE MANAGEMENT REPORT");
                writer.newLine();
                writer.write("  Generated: " + new Date());
                writer.newLine();
                writer.write("═".repeat(60));
                writer.newLine();
                writer.newLine();

                writer.write(String.format("  %-6s %-22s %-10s %-8s %-6s %-6s%n",
                        "ID", "Name", "Average", "Grades", "Grade", "Status"));
                writer.write("─".repeat(60));
                writer.newLine();

                for (int id : insertionOrder) {
                    Student s = students.get(id);
                    writer.write(String.format("  #%-5d %-22s %-10.2f %-8d %-6s %-6s%n",
                            s.id, s.name, s.getAverage(),
                            s.grades.size(), s.getLetterGrade(), s.getPassFail()));
                }

                writer.newLine();
                writer.write("═".repeat(60));
                writer.newLine();
                writer.write("  DETAILED BREAKDOWN");
                writer.newLine();
                writer.write("═".repeat(60));
                writer.newLine();

                for (int id : insertionOrder) {
                    Student s = students.get(id);
                    writer.write(String.format(
                            "%n  #%d  %s%n  Grades: %s%n  Avg: %.2f  High: %.1f  Low: %.1f  Grade: %s  %s%n",
                            s.id, s.name, formatGrades(s.grades),
                            s.getAverage(), s.getHighest(),
                            s.getLowest(), s.getLetterGrade(), s.getPassFail()));
                }

                System.out.println("  Report saved to \"" + reportPath + "\".");

            } catch (IOException e) {
                System.out.println("  Error generating report: " + e.getMessage());
            }
        }

        // ── SAVE TO FILE ──

        // Persists all student records to the data file in pipe-delimited format
        public void saveToFile() {
            try (BufferedWriter writer = new BufferedWriter(
                    new FileWriter(FILE_PATH))) {
                for (int id : insertionOrder) {
                    writer.write(students.get(id).toFileLine());
                    writer.newLine();
                }
                System.out.println("  Saved " + students.size()
                        + " student(s) to \"" + FILE_PATH + "\".");
            } catch (IOException e) {
                System.out.println("  Error saving: " + e.getMessage());
            }
        }

        // ── LOAD FROM FILE ──

        // Restores all student records from the data file
        public void loadFromFile() {
            File file = new File(FILE_PATH);
            if (!file.exists()) {
                System.out.println("  No save file found. Starting fresh.");
                return;
            }
            students.clear();
            insertionOrder.clear();

            try (BufferedReader reader = new BufferedReader(
                    new FileReader(FILE_PATH))) {
                String line;
                int    loaded = 0;
                while ((line = reader.readLine()) != null) {
                    if (!line.trim().isEmpty()) {
                        Student s = Student.fromFileLine(line);
                        students.put(s.id, s);
                        insertionOrder.add(s.id);
                        loaded++;
                    }
                }
                System.out.println("  Loaded " + loaded
                        + " student(s) from \"" + FILE_PATH + "\".");
            } catch (IOException e) {
                System.out.println("  Error loading: " + e.getMessage());
            }
        }

        // Returns true if the system has at least one registered student
        public boolean hasStudents() {
            return !students.isEmpty();
        }

        // Returns the student with the given ID, or null if not found
        public Student getStudent(int id) {
            return students.get(id);
        }
    }

    // ─────────────────────────────────────────────
    //  UTILITY METHODS
    // ─────────────────────────────────────────────

    // Maps a numeric average to a letter grade using a standard scale
    static String toLetterGrade(double avg) {
        if (avg >= 90) return "A";
        if (avg >= 80) return "B";
        if (avg >= 70) return "C";
        if (avg >= 60) return "D";
        return "F";
    }

    // Formats a list of grades as a comma-separated string rounded to 1 decimal
    static String formatGrades(ArrayList<Double> grades) {
        if (grades.isEmpty()) return "No grades yet";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < grades.size(); i++) {
            sb.append(String.format("%.1f", grades.get(i)));
            if (i < grades.size() - 1) sb.append(", ");
        }
        return sb.toString();
    }

    // Computes and returns a percentage rounded to one decimal place
    static String pct(int part, int total) {
        return String.format("%.1f", total == 0 ? 0.0 : (part * 100.0 / total));
    }

    // ─────────────────────────────────────────────
    //  INPUT HELPERS
    // ─────────────────────────────────────────────

    // Reads a valid integer from the user — re-prompts on invalid input
    static int readInt(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                int value = scanner.nextInt();
                scanner.nextLine();
                return value;
            } catch (InputMismatchException e) {
                scanner.nextLine();
                System.out.println("  Invalid input. Please enter a whole number.");
            }
        }
    }

    // Reads a valid double from the user — re-prompts on invalid input
    static double readDouble(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                double value = scanner.nextDouble();
                scanner.nextLine();
                return value;
            } catch (InputMismatchException e) {
                scanner.nextLine();
                System.out.println("  Invalid input. Please enter a number.");
            }
        }
    }

    // Reads a non-empty string from the user — re-prompts on blank input
    static String readString(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = scanner.nextLine().trim();
            if (!value.isEmpty()) return value;
            System.out.println("  Invalid input. Please enter a non-empty value.");
        }
    }

    // ─────────────────────────────────────────────
    //  MAIN MENU
    // ─────────────────────────────────────────────

    public static void main(String[] args) {

        Scanner      scanner = new Scanner(System.in);
        GradeManager manager = new GradeManager();

        System.out.println("═".repeat(60));
        System.out.println("     STUDENT GRADE MANAGEMENT SYSTEM");
        System.out.println("═".repeat(60));
        manager.loadFromFile();

        boolean running = true;

        while (running) {
            System.out.println("\n─── Main Menu ───");
            System.out.println(" 1.  Add student");
            System.out.println(" 2.  Remove student");
            System.out.println(" 3.  Add grade to student");
            System.out.println(" 4.  Remove grade from student");
            System.out.println(" 5.  View student details");
            System.out.println(" 6.  List all students");
            System.out.println(" 7.  Sort students");
            System.out.println(" 8.  Filter by letter grade");
            System.out.println(" 9.  Class statistics");
            System.out.println(" 10. Generate report file");
            System.out.println(" 11. Save data");
            System.out.println(" 12. Load data");
            System.out.println(" 13. Exit");
            System.out.print("Choose an option: ");

            int choice;
            try {
                choice = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                scanner.nextLine();
                System.out.println("  Invalid option. Please enter 1–13.");
                continue;
            }

            switch (choice) {

                case 1:
                    // Register a new student by name
                    String name = readString(scanner, "  Student name: ");
                    manager.addStudent(name);
                    break;

                case 2:
                    // Remove a student by ID
                    manager.listAll();
                    int removeId = readInt(scanner, "  Enter student ID to remove: ");
                    manager.removeStudent(removeId);
                    break;

                case 3:
                    // Add a grade to a specific student's record
                    manager.listAll();
                    int gradeId  = readInt(scanner,    "  Enter student ID: ");
                    double grade = readDouble(scanner,  "  Enter grade (0-100): ");
                    manager.addGrade(gradeId, grade);
                    break;

                case 4:
                    // Remove a specific grade entry from a student's record
                    int removeGradeId    = readInt(scanner, "  Enter student ID: ");
                    int removeGradeIndex = readInt(scanner, "  Enter grade index to remove: ");
                    manager.removeGrade(removeGradeId, removeGradeIndex);
                    break;

                case 5:
                    // View full details for one student
                    manager.listAll();
                    int viewId = readInt(scanner, "  Enter student ID to view: ");
                    manager.viewStudent(viewId);
                    break;

                case 6:
                    // Display all students in a summary table
                    manager.listAll();
                    break;

                case 7:
                    // Sort and display students by the chosen criteria
                    System.out.println("  Sort by:");
                    System.out.println("  1. Name");
                    System.out.println("  2. Average (highest first)");
                    System.out.println("  3. Letter Grade");
                    System.out.println("  4. ID");
                    int sortChoice = readInt(scanner, "  Choose: ");
                    String[] sortKeys = {"name", "average", "grade", "id"};
                    if (sortChoice >= 1 && sortChoice <= 4) {
                        manager.listSorted(sortKeys[sortChoice - 1]);
                    } else {
                        System.out.println("  Invalid choice.");
                    }
                    break;

                case 8:
                    // Filter and display students by letter grade
                    System.out.print("  Enter letter grade to filter (A/B/C/D/F): ");
                    String letterGrade = scanner.nextLine().trim().toUpperCase();
                    if (letterGrade.matches("[ABCDF]")) {
                        manager.filterByGrade(letterGrade);
                    } else {
                        System.out.println("  Invalid grade. Use A, B, C, D, or F.");
                    }
                    break;

                case 9:
                    // Compute and display class-wide statistics
                    manager.classStatistics();
                    break;

                case 10:
                    // Write a detailed grade report to a text file
                    manager.generateReport();
                    break;

                case 11:
                    // Persist all current data to the save file
                    manager.saveToFile();
                    break;

                case 12:
                    // Reload data from the save file
                    manager.loadFromFile();
                    break;

                case 13:
                    // Auto-save and exit the application
                    System.out.println("  Auto-saving before exit...");
                    manager.saveToFile();
                    running = false;
                    System.out.println("  Goodbye!");
                    break;

                default:
                    System.out.println("  Invalid option. Please choose 1–13.");
            }
        }

        scanner.close();
    }
}