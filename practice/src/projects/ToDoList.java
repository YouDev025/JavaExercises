package applications;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

// A menu-driven To-Do List application with file persistence
// Features: add, remove, complete, search, filter, save and load tasks
// Concepts: ArrayList, File I/O, BufferedReader/Writer, LocalDateTime
public class ToDoList {

    // ─────────────────────────────────────────────
    //  TASK CLASS
    // ─────────────────────────────────────────────

    // Represents a single task with its metadata
    // Each task has a title, priority, completion status, and timestamps
    static class Task {

        private static int idCounter = 1;   // Auto-incrementing ID for each new task

        int     id;           // Unique identifier for this task
        String  title;        // The task description entered by the user
        String  priority;     // Priority level: HIGH | MEDIUM | LOW
        boolean completed;    // true if the task has been marked as done
        String  createdAt;    // Timestamp when the task was created
        String  completedAt;  // Timestamp when the task was marked complete

        // Formatter for consistent timestamp display across all tasks
        static final DateTimeFormatter FORMATTER =
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        // Constructor for creating a brand new task
        Task(String title, String priority) {
            this.id          = idCounter++;
            this.title       = title;
            this.priority    = priority;
            this.completed   = false;
            this.createdAt   = LocalDateTime.now().format(FORMATTER);
            this.completedAt = "";
        }

        // Constructor for loading a task from a saved file line
        Task(int id, String title, String priority,
             boolean completed, String createdAt, String completedAt) {
            this.id          = id;
            this.title       = title;
            this.priority    = priority;
            this.completed   = completed;
            this.createdAt   = createdAt;
            this.completedAt = completedAt;

            // Ensure idCounter stays ahead of any loaded IDs to avoid duplicates
            if (id >= idCounter) idCounter = id + 1;
        }

        // Marks this task as completed and records the completion timestamp
        void markComplete() {
            this.completed   = true;
            this.completedAt = LocalDateTime.now().format(FORMATTER);
        }

        // Serializes the task to a pipe-delimited string for file storage
        // Format: id|title|priority|completed|createdAt|completedAt
        String toFileLine() {
            return id + "|" + title + "|" + priority + "|"
                    + completed + "|" + createdAt + "|" + completedAt;
        }

        // Deserializes a pipe-delimited file line back into a Task object
        static Task fromFileLine(String line) {
            String[] parts = line.split("\\|", -1);
            return new Task(
                    Integer.parseInt(parts[0]),  // id
                    parts[1],                    // title
                    parts[2],                    // priority
                    Boolean.parseBoolean(parts[3]), // completed
                    parts[4],                    // createdAt
                    parts[5]                     // completedAt
            );
        }

        // Returns a formatted single-line string representation of this task
        @Override
        public String toString() {
            String status    = completed ? "✓" : "○";
            String doneAt    = completed ? "  Done: " + completedAt : "";
            String priorityTag = "[" + priority + "]";
            return String.format("#%-4d %s %-8s %-40s  Created: %s%s",
                    id, status, priorityTag, title, createdAt, doneAt);
        }
    }

    // ─────────────────────────────────────────────
    //  TASK MANAGER
    // ─────────────────────────────────────────────

    // Manages all tasks and handles add, remove, complete, search, filter,
    // and file save/load operations using an ArrayList as the backing store
    static class TaskManager {

        // In-memory list of all tasks — persisted to file on save
        private ArrayList<Task> tasks = new ArrayList<>();

        // File path where tasks are saved and loaded from
        private static final String FILE_PATH = "tasks.txt";

        // ── ADD ──

        // Creates a new task and adds it to the list
        public void addTask(String title, String priority) {
            tasks.add(new Task(title, priority));
            System.out.println("  Task added successfully.");
        }

        // ── REMOVE ──

        // Finds a task by its ID and removes it from the list
        // Returns true if the task was found and removed, false otherwise
        public boolean removeTask(int id) {
            for (int i = 0; i < tasks.size(); i++) {
                if (tasks.get(i).id == id) {
                    String title = tasks.get(i).title;
                    tasks.remove(i);
                    System.out.println("  Removed: \"" + title + "\"");
                    return true;
                }
            }
            System.out.println("  Task #" + id + " not found.");
            return false;
        }

        // ── MARK COMPLETE ──

        // Finds a task by ID and marks it as completed
        // Returns true if the task was found and updated, false otherwise
        public boolean markComplete(int id) {
            for (Task task : tasks) {
                if (task.id == id) {
                    if (task.completed) {
                        System.out.println("  Task #" + id
                                + " is already completed.");
                        return false;
                    }
                    task.markComplete();
                    System.out.println("  Marked complete: \"" + task.title + "\"");
                    return true;
                }
            }
            System.out.println("  Task #" + id + " not found.");
            return false;
        }

        // ── LIST ALL ──

        // Displays all tasks in the list with a count summary
        public void listAll() {
            if (tasks.isEmpty()) {
                System.out.println("  No tasks found.");
                return;
            }

            System.out.println("\n" + "─".repeat(90));
            System.out.printf("  %-6s %-2s %-8s %-40s  %-18s%n",
                    "ID", "S", "Priority", "Title", "Created");
            System.out.println("─".repeat(90));

            for (Task task : tasks) {
                System.out.println("  " + task);
            }

            System.out.println("─".repeat(90));
            long done    = tasks.stream().filter(t -> t.completed).count();
            long pending = tasks.size() - done;
            System.out.println("  Total: " + tasks.size()
                    + "  ✓ Done: " + done
                    + "  ○ Pending: " + pending);
        }

        // ── FILTER ──

        // Displays only tasks that match the given filter criteria
        // filterType: "pending" | "completed" | "HIGH" | "MEDIUM" | "LOW"
        public void listFiltered(String filterType) {
            ArrayList<Task> filtered = new ArrayList<>();

            for (Task task : tasks) {
                switch (filterType.toUpperCase()) {
                    case "PENDING":
                        if (!task.completed) filtered.add(task);
                        break;
                    case "COMPLETED":
                        if (task.completed) filtered.add(task);
                        break;
                    default:
                        // Filter by priority level
                        if (task.priority.equalsIgnoreCase(filterType)) {
                            filtered.add(task);
                        }
                }
            }

            if (filtered.isEmpty()) {
                System.out.println("  No tasks found for filter: " + filterType);
                return;
            }

            System.out.println("\n─── Filter: " + filterType.toUpperCase()
                    + " (" + filtered.size() + " tasks) ───");
            for (Task task : filtered) {
                System.out.println("  " + task);
            }
        }

        // ── SEARCH ──

        // Searches all tasks for those whose title contains the query string
        // Search is case-insensitive
        public void search(String query) {
            ArrayList<Task> results = new ArrayList<>();

            for (Task task : tasks) {
                if (task.title.toLowerCase().contains(query.toLowerCase())) {
                    results.add(task);
                }
            }

            if (results.isEmpty()) {
                System.out.println("  No tasks matching \"" + query + "\".");
                return;
            }

            System.out.println("\n─── Search: \"" + query + "\""
                    + " (" + results.size() + " result(s)) ───");
            for (Task task : results) {
                System.out.println("  " + task);
            }
        }

        // ── CLEAR COMPLETED ──

        // Removes all tasks that have been marked as completed from the list
        public void clearCompleted() {
            int before = tasks.size();
            tasks.removeIf(t -> t.completed);
            int removed = before - tasks.size();
            System.out.println("  Removed " + removed + " completed task(s).");
        }

        // ── SAVE TO FILE ──

        // Serializes all tasks to a plain text file using pipe-delimited format
        // Overwrites any existing file at FILE_PATH
        public void saveToFile() {
            try (BufferedWriter writer = new BufferedWriter(
                    new FileWriter(FILE_PATH))) {

                // Write each task as one line in the file
                for (Task task : tasks) {
                    writer.write(task.toFileLine());
                    writer.newLine();
                }

                System.out.println("  Saved " + tasks.size()
                        + " task(s) to \"" + FILE_PATH + "\".");

            } catch (IOException e) {
                System.out.println("  Error saving file: " + e.getMessage());
            }
        }

        // ── LOAD FROM FILE ──

        // Reads all tasks from the save file and restores them into the list
        // Clears any existing in-memory tasks before loading
        public void loadFromFile() {
            File file = new File(FILE_PATH);

            // If no save file exists yet, notify the user and return
            if (!file.exists()) {
                System.out.println("  No save file found at \""
                        + FILE_PATH + "\". Starting fresh.");
                return;
            }

            tasks.clear();

            try (BufferedReader reader = new BufferedReader(
                    new FileReader(FILE_PATH))) {

                String line;
                int    loaded = 0;

                // Read each line and deserialize it back into a Task object
                while ((line = reader.readLine()) != null) {
                    if (!line.trim().isEmpty()) {
                        tasks.add(Task.fromFileLine(line));
                        loaded++;
                    }
                }

                System.out.println("  Loaded " + loaded
                        + " task(s) from \"" + FILE_PATH + "\".");

            } catch (IOException e) {
                System.out.println("  Error loading file: " + e.getMessage());
            }
        }

        // Returns the total number of tasks currently in the list
        public int size() {
            return tasks.size();
        }
    }

    // ─────────────────────────────────────────────
    //  INPUT HELPERS
    // ─────────────────────────────────────────────

    // Helper method to read a valid integer from the user
    public static int readInt(Scanner scanner, String prompt) {
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

    // Helper method to read a non-empty string from the user
    public static String readString(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = scanner.nextLine().trim();
            if (!value.isEmpty()) return value;
            System.out.println("  Invalid input. Please enter a non-empty value.");
        }
    }

    // Prompts the user to choose a priority level and validates the input
    // Keeps prompting until a valid choice of HIGH, MEDIUM, or LOW is made
    public static String readPriority(Scanner scanner) {
        while (true) {
            System.out.print("  Priority (1=HIGH, 2=MEDIUM, 3=LOW): ");
            try {
                int choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1: return "HIGH";
                    case 2: return "MEDIUM";
                    case 3: return "LOW";
                    default:
                        System.out.println("  Please enter 1, 2, or 3.");
                }
            } catch (InputMismatchException e) {
                scanner.nextLine();
                System.out.println("  Invalid input. Please enter 1, 2, or 3.");
            }
        }
    }

    // ─────────────────────────────────────────────
    //  MAIN MENU
    // ─────────────────────────────────────────────

    public static void main(String[] args) {

        Scanner     scanner = new Scanner(System.in);
        TaskManager manager = new TaskManager();

        // Automatically load saved tasks at startup
        System.out.println("═".repeat(60));
        System.out.println("        ✓  TO-DO LIST APPLICATION");
        System.out.println("═".repeat(60));
        manager.loadFromFile();

        boolean running = true;

        while (running) {
            System.out.println("\n─── Main Menu ───");
            System.out.println(" 1.  Add task");
            System.out.println(" 2.  Remove task");
            System.out.println(" 3.  Mark task as complete");
            System.out.println(" 4.  List all tasks");
            System.out.println(" 5.  Filter tasks");
            System.out.println(" 6.  Search tasks");
            System.out.println(" 7.  Clear completed tasks");
            System.out.println(" 8.  Save tasks to file");
            System.out.println(" 9.  Load tasks from file");
            System.out.println(" 10. Exit");
            System.out.print("Choose an option: ");

            int choice;

            try {
                choice = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                scanner.nextLine();
                System.out.println("  Invalid option. Please enter a number between 1 and 10.");
                continue;
            }

            switch (choice) {

                case 1:
                    // Read title and priority then add the new task
                    String title    = readString(scanner, "  Task title: ");
                    String priority = readPriority(scanner);
                    manager.addTask(title, priority);
                    break;

                case 2:
                    // Read a task ID and remove that task from the list
                    manager.listAll();
                    int removeId = readInt(scanner, "  Enter task ID to remove: ");
                    manager.removeTask(removeId);
                    break;

                case 3:
                    // Read a task ID and mark it as completed
                    manager.listAll();
                    int completeId = readInt(scanner, "  Enter task ID to mark complete: ");
                    manager.markComplete(completeId);
                    break;

                case 4:
                    // Display all tasks with status and priority information
                    manager.listAll();
                    break;

                case 5:
                    // Filter tasks by status or priority level
                    System.out.println("  Filter by:");
                    System.out.println("  1. Pending");
                    System.out.println("  2. Completed");
                    System.out.println("  3. HIGH priority");
                    System.out.println("  4. MEDIUM priority");
                    System.out.println("  5. LOW priority");
                    int filterChoice = readInt(scanner, "  Choose filter: ");
                    String[] filters = {"PENDING", "COMPLETED", "HIGH", "MEDIUM", "LOW"};
                    if (filterChoice >= 1 && filterChoice <= 5) {
                        manager.listFiltered(filters[filterChoice - 1]);
                    } else {
                        System.out.println("  Invalid filter choice.");
                    }
                    break;

                case 6:
                    // Search tasks by keyword in their title
                    String query = readString(scanner, "  Enter search keyword: ");
                    manager.search(query);
                    break;

                case 7:
                    // Remove all completed tasks from the list in one operation
                    manager.clearCompleted();
                    break;

                case 8:
                    // Persist all current tasks to the save file
                    manager.saveToFile();
                    break;

                case 9:
                    // Reload tasks from the save file — replaces current in-memory tasks
                    manager.loadFromFile();
                    break;

                case 10:
                    // Automatically save before exiting so no data is lost
                    System.out.println("  Auto-saving before exit...");
                    manager.saveToFile();
                    running = false;
                    System.out.println("  Goodbye!");
                    break;

                default:
                    System.out.println("  Invalid option. Please choose between 1 and 10.");
            }
        }

        // Close the scanner to release system resources
        scanner.close();
    }
}