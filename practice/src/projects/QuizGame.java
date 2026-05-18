package projects;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;

public class QuizGame {

    // ─────────────────────────────────────────────
    //  QUESTION CLASS
    // ─────────────────────────────────────────────
    static class Question {
        enum Difficulty { EASY, MEDIUM, HARD }

        String questionText;
        String[] options;
        int correctIndex;
        Difficulty difficulty;
        String category;
        String explanation;

        Question(String questionText, String[] options, int correctIndex,
                 Difficulty difficulty, String category, String explanation) {
            this.questionText = questionText;
            this.options = options;
            this.correctIndex = correctIndex;
            this.difficulty = difficulty;
            this.category = category;
            this.explanation = explanation;
        }

        char getCorrectLetter() {
            return (char) ('A' + correctIndex);
        }

        int getPoints() {
            switch (difficulty) {
                case EASY: return 10;
                case MEDIUM: return 20;
                case HARD: return 30;
                default: return 10;
            }
        }
    }

    // ─────────────────────────────────────────────
    //  PLAYER RESULT CLASS
    // ─────────────────────────────────────────────
    static class PlayerResult implements Comparable<PlayerResult> {
        String name;
        int score;
        int correct;
        int total;
        long timeTaken;
        String timestamp;

        PlayerResult(String name, int score, int correct, int total, long timeTaken) {
            this.name = name;
            this.score = score;
            this.correct = correct;
            this.total = total;
            this.timeTaken = timeTaken;
            this.timestamp = new Date().toString();
        }

        @Override
        public int compareTo(PlayerResult other) {
            return Integer.compare(other.score, this.score);
        }

        String toFileLine() {
            return name + "|" + score + "|" + correct + "|" + total + "|" + timeTaken + "|" + timestamp;
        }

        static PlayerResult fromFileLine(String line) {
            String[] p = line.split("\\|", -1);
            PlayerResult pr = new PlayerResult(p[0], Integer.parseInt(p[1]),
                    Integer.parseInt(p[2]), Integer.parseInt(p[3]), Long.parseLong(p[4]));
            pr.timestamp = p[5];
            return pr;
        }
    }

    // ─────────────────────────────────────────────
    //  QUESTION BANK (full list)
    // ─────────────────────────────────────────────
    static ArrayList<Question> loadQuestions() {
        ArrayList<Question> questions = new ArrayList<>();

        // Java Programming
        questions.add(new Question("Which keyword is used to create a class in Java?",
                new String[]{"struct", "define", "class", "object"}, 2,
                Question.Difficulty.EASY, "Java", "'class' is the keyword used to define a class in Java."));
        questions.add(new Question("What is the default value of an int in Java?",
                new String[]{"null", "1", "-1", "0"}, 3,
                Question.Difficulty.EASY, "Java", "Instance variables of type int are initialized to 0 by default."));
        questions.add(new Question("Which collection allows duplicate keys?",
                new String[]{"HashMap", "TreeMap", "LinkedHashMap", "None of these"}, 3,
                Question.Difficulty.MEDIUM, "Java", "No Map implementation allows duplicate keys — each key must be unique."));
        questions.add(new Question("What does JVM stand for?",
                new String[]{"Java Virtual Machine", "Java Visual Manager", "Java Variable Manager", "Java Verified Machine"}, 0,
                Question.Difficulty.EASY, "Java", "JVM stands for Java Virtual Machine — it executes Java bytecode."));
        questions.add(new Question("Which access modifier makes a member accessible only within its class?",
                new String[]{"protected", "default", "public", "private"}, 3,
                Question.Difficulty.MEDIUM, "Java", "'private' restricts access to within the declaring class only."));
        questions.add(new Question("What is the time complexity of HashMap.get() on average?",
                new String[]{"O(n)", "O(log n)", "O(1)", "O(n²)"}, 2,
                Question.Difficulty.HARD, "Java", "HashMap uses hashing for O(1) average-case lookup time."));

        // Data Structures
        questions.add(new Question("Which data structure follows LIFO order?",
                new String[]{"Queue", "Stack", "LinkedList", "Tree"}, 1,
                Question.Difficulty.EASY, "Data Structures", "A Stack follows Last In First Out."));
        questions.add(new Question("What is the worst-case time complexity of binary search?",
                new String[]{"O(1)", "O(n)", "O(log n)", "O(n log n)"}, 2,
                Question.Difficulty.MEDIUM, "Data Structures", "Binary search halves the search range each step giving O(log n)."));
        questions.add(new Question("Which sorting algorithm has O(n log n) time in all cases?",
                new String[]{"Quick Sort", "Bubble Sort", "Merge Sort", "Insertion Sort"}, 2,
                Question.Difficulty.MEDIUM, "Data Structures", "Merge Sort always divides and merges giving O(n log n)."));
        questions.add(new Question("In a min-heap, the root always contains the?",
                new String[]{"Largest element", "Median element", "Smallest element", "Random element"}, 2,
                Question.Difficulty.MEDIUM, "Data Structures", "A min-heap guarantees the root is always the smallest element."));
        questions.add(new Question("What is the space complexity of recursive binary search?",
                new String[]{"O(1)", "O(log n)", "O(n)", "O(n²)"}, 1,
                Question.Difficulty.HARD, "Data Structures", "Each recursive call adds a stack frame giving O(log n) space."));
        questions.add(new Question("Which traversal visits nodes in Left-Root-Right order?",
                new String[]{"Pre-order", "Post-order", "Level-order", "In-order"}, 3,
                Question.Difficulty.MEDIUM, "Data Structures", "In-order traversal visits left subtree, root, then right subtree."));

        // Algorithms
        questions.add(new Question("Which algorithm paradigm does merge sort use?",
                new String[]{"Greedy", "Dynamic Programming", "Divide and Conquer", "Backtracking"}, 2,
                Question.Difficulty.MEDIUM, "Algorithms", "Merge sort uses Divide and Conquer."));
        questions.add(new Question("What is the best-case time complexity of bubble sort?",
                new String[]{"O(n²)", "O(n log n)", "O(log n)", "O(n)"}, 3,
                Question.Difficulty.MEDIUM, "Algorithms", "With early-exit flag, bubble sort on a sorted array runs in O(n)."));
        questions.add(new Question("Which technique does KMP use to avoid redundant comparisons?",
                new String[]{"Hashing", "Failure function (LPS array)", "Binary search", "Two pointers"}, 1,
                Question.Difficulty.HARD, "Algorithms", "KMP precomputes the LPS array to skip re-comparisons."));

        // OOP
        questions.add(new Question("Which OOP principle hides internal implementation details?",
                new String[]{"Inheritance", "Polymorphism", "Encapsulation", "Abstraction"}, 2,
                Question.Difficulty.EASY, "OOP", "Encapsulation bundles data and restricts direct access."));
        questions.add(new Question("What does the 'super' keyword refer to in Java?",
                new String[]{"Current class instance", "Parent class", "Static context", "Interface"}, 1,
                Question.Difficulty.EASY, "OOP", "'super' refers to the immediate parent class."));
        questions.add(new Question("Which OOP concept allows a subclass to provide its own method implementation?",
                new String[]{"Overloading", "Encapsulation", "Overriding", "Abstraction"}, 2,
                Question.Difficulty.MEDIUM, "OOP", "Method overriding allows a subclass to redefine a parent class method."));

        // Databases
        questions.add(new Question("Which SQL clause filters rows after grouping?",
                new String[]{"WHERE", "ON", "HAVING", "FILTER"}, 2,
                Question.Difficulty.MEDIUM, "Databases", "HAVING filters groups produced by GROUP BY."));
        questions.add(new Question("What does ACID stand for in databases?",
                new String[]{"Atomicity Consistency Isolation Durability", "Accuracy Consistency Integrity Data",
                        "Atomicity Concurrency Isolation Distribution", "Accuracy Concurrency Integrity Durability"}, 0,
                Question.Difficulty.HARD, "Databases", "ACID: Atomicity, Consistency, Isolation, Durability."));

        return questions;
    }

    // ─────────────────────────────────────────────
    //  TIMER UTILITY
    // ─────────────────────────────────────────────
    static class QuestionTimer {
        private ScheduledExecutorService scheduler;
        private AtomicInteger timeLeft;
        private AtomicBoolean expired;
        private int totalSeconds;

        QuestionTimer(int seconds) {
            this.totalSeconds = seconds;
            this.timeLeft = new AtomicInteger(seconds);
            this.expired = new AtomicBoolean(false);
        }

        void start() {
            scheduler = Executors.newSingleThreadScheduledExecutor();
            scheduler.scheduleAtFixedRate(() -> {
                int remaining = timeLeft.decrementAndGet();
                if (remaining >= 0) {
                    System.out.print("\r  ⏱  Time remaining: " + remaining + "s   ");
                }
                if (remaining <= 0) {
                    expired.set(true);
                    scheduler.shutdown();
                }
            }, 1, 1, TimeUnit.SECONDS);
        }

        void stop() {
            if (scheduler != null && !scheduler.isShutdown()) {
                scheduler.shutdownNow();
            }
        }

        boolean isExpired() { return expired.get(); }
        int getTimeLeft() { return timeLeft.get(); }
    }

    // ─────────────────────────────────────────────
    //  LEADERBOARD
    // ─────────────────────────────────────────────
    static class Leaderboard {
        private ArrayList<PlayerResult> results = new ArrayList<>();
        private static final String FILE_PATH = "quiz_leaderboard.txt";

        void addResult(PlayerResult result) {
            results.add(result);
            Collections.sort(results);
        }

        void display() {
            if (results.isEmpty()) {
                System.out.println("  No scores recorded yet.");
                return;
            }
            System.out.println("\n" + "═".repeat(62));
            System.out.println("              🏆  LEADERBOARD — TOP 10");
            System.out.println("═".repeat(62));
            System.out.printf("  %-5s %-18s %-8s %-10s %-8s%n", "Rank", "Player", "Score", "Correct", "Time");
            System.out.println("─".repeat(62));
            int limit = Math.min(10, results.size());
            for (int i = 0; i < limit; i++) {
                PlayerResult r = results.get(i);
                String medal = i == 0 ? "🥇" : i == 1 ? "🥈" : i == 2 ? "🥉" : "  ";
                System.out.printf("  %-5s %-18s %-8d %-10s %-8s%n",
                        medal + (i + 1), r.name, r.score, r.correct + "/" + r.total, r.timeTaken + "s");
            }
            System.out.println("═".repeat(62));
        }

        void saveToFile() {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
                for (PlayerResult r : results) {
                    writer.write(r.toFileLine());
                    writer.newLine();
                }
            } catch (IOException e) {
                System.out.println("  Leaderboard save error: " + e.getMessage());
            }
        }

        void loadFromFile() {
            File file = new File(FILE_PATH);
            if (!file.exists()) return;
            results.clear();
            try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (!line.trim().isEmpty()) {
                        results.add(PlayerResult.fromFileLine(line));
                    }
                }
                Collections.sort(results);
            } catch (IOException e) {
                System.out.println("  Leaderboard load error: " + e.getMessage());
            }
        }
    }

    // ─────────────────────────────────────────────
    //  QUIZ ENGINE (fixed to accept both cases)
    // ─────────────────────────────────────────────
    static void runQuiz(Scanner scanner, ArrayList<Question> allQuestions,
                        Leaderboard leaderboard, String playerName,
                        int questionCount, int timePerQuestion,
                        String categoryFilter, String difficultyFilter) {

        ArrayList<Question> pool = new ArrayList<>();
        for (Question q : allQuestions) {
            boolean catMatch = categoryFilter.equals("ALL") || q.category.equalsIgnoreCase(categoryFilter);
            boolean diffMatch = difficultyFilter.equals("ALL") || q.difficulty.name().equalsIgnoreCase(difficultyFilter);
            if (catMatch && diffMatch) pool.add(q);
        }

        if (pool.isEmpty()) {
            System.out.println("  No questions match the selected filters.");
            return;
        }

        Collections.shuffle(pool);
        int count = Math.min(questionCount, pool.size());
        ArrayList<Question> selected = new ArrayList<>(pool.subList(0, count));

        int totalScore = 0;
        int correctCount = 0;
        int skippedCount = 0;
        long sessionStart = System.currentTimeMillis();
        ArrayList<String> reviewLog = new ArrayList<>();

        System.out.println("\n" + "═".repeat(60));
        System.out.println("  Quiz Starting!  Player: " + playerName);
        System.out.println("  Questions: " + count + "  |  Time per question: " + timePerQuestion + "s");
        System.out.println("  Category: " + categoryFilter + "  |  Difficulty: " + difficultyFilter);
        System.out.println("═".repeat(60));

        for (int qNum = 0; qNum < count; qNum++) {
            Question q = selected.get(qNum);

            System.out.println("\n  Question " + (qNum + 1) + " of " + count
                    + "  [" + q.difficulty + "]  [" + q.category + "]  +" + q.getPoints() + " pts");
            System.out.println("  " + "─".repeat(56));
            System.out.println("  " + q.questionText);
            System.out.println();

            char[] labels = {'A', 'B', 'C', 'D'};
            for (int i = 0; i < q.options.length; i++) {
                System.out.println("    " + labels[i] + ")  " + q.options[i]);
            }
            System.out.println();

            QuestionTimer timer = new QuestionTimer(timePerQuestion);
            timer.start();

            System.out.print("  Your answer (A/B/C/D or S to skip - case insensitive): ");

            AtomicBoolean answered = new AtomicBoolean(false);
            String input = "";

            try {
                long deadline = System.currentTimeMillis() + (timePerQuestion * 1000L);
                while (System.currentTimeMillis() < deadline && !timer.isExpired()) {
                    if (System.in.available() > 0) {
                        // Convert to UPPERCASE so both 'a' and 'A' work
                        input = scanner.nextLine().trim().toUpperCase();
                        answered.set(true);
                        timer.stop();
                        break;
                    }
                    Thread.sleep(100);
                }
            } catch (IOException | InterruptedException e) {
                timer.stop();
            }

            System.out.println();

            if (!answered.get() || timer.isExpired()) {
                System.out.println("\n  ⏰ Time's up!");
                System.out.println("  Correct answer: " + q.getCorrectLetter() + ") " + q.options[q.correctIndex]);
                System.out.println("  " + q.explanation);
                reviewLog.add("Q" + (qNum + 1) + ": TIME OUT    Correct: " + q.getCorrectLetter());
                skippedCount++;
            } else if (input.equals("S")) {
                System.out.println("  Skipped.");
                System.out.println("  Correct answer was: " + q.getCorrectLetter() + ") " + q.options[q.correctIndex]);
                reviewLog.add("Q" + (qNum + 1) + ": SKIPPED     Correct: " + q.getCorrectLetter());
                skippedCount++;
            } else if (input.length() == 1 && input.charAt(0) >= 'A' && input.charAt(0) <= 'D') {
                int chosenIndex = input.charAt(0) - 'A';
                if (chosenIndex == q.correctIndex) {
                    int pts = q.getPoints();
                    totalScore += pts;
                    correctCount++;
                    System.out.println("  ✓ Correct! +" + pts + " points");
                    System.out.println("  " + q.explanation);
                    reviewLog.add("Q" + (qNum + 1) + ": CORRECT ✓   +" + pts + " pts");
                } else {
                    System.out.println("  ✗ Wrong! You chose: " + input + ") " + q.options[chosenIndex]);
                    System.out.println("  Correct answer: " + q.getCorrectLetter() + ") " + q.options[q.correctIndex]);
                    System.out.println("  " + q.explanation);
                    reviewLog.add("Q" + (qNum + 1) + ": WRONG ✗     You: " + input + "  Correct: " + q.getCorrectLetter());
                }
            } else {
                System.out.println("  Invalid input — treated as wrong answer.");
                System.out.println("  Correct answer: " + q.getCorrectLetter() + ") " + q.options[q.correctIndex]);
                reviewLog.add("Q" + (qNum + 1) + ": INVALID INPUT  Correct: " + q.getCorrectLetter());
            }

            System.out.println("  Score so far: " + totalScore + " pts  |  " + correctCount + "/" + (qNum + 1) + " correct");
        }

        long timeTaken = (System.currentTimeMillis() - sessionStart) / 1000;
        int maxScore = selected.stream().mapToInt(Question::getPoints).sum();
        int pct = maxScore == 0 ? 0 : (int) ((totalScore * 100.0) / maxScore);

        System.out.println("\n" + "═".repeat(60));
        System.out.println("            QUIZ COMPLETE — " + playerName);
        System.out.println("═".repeat(60));
        System.out.printf("  Final Score:    %d / %d pts (%d%%)%n", totalScore, maxScore, pct);
        System.out.printf("  Correct:        %d / %d%n", correctCount, count);
        System.out.printf("  Skipped/Timed:  %d%n", skippedCount);
        System.out.printf("  Time Taken:     %d seconds%n", timeTaken);
        System.out.println("  Performance:    " + getPerformanceLabel(pct));
        System.out.println("─".repeat(60));
        System.out.println("  Answer Review:");
        for (String log : reviewLog) {
            System.out.println("    " + log);
        }
        System.out.println("═".repeat(60));

        PlayerResult result = new PlayerResult(playerName, totalScore, correctCount, count, timeTaken);
        leaderboard.addResult(result);
        leaderboard.saveToFile();
        System.out.println("  Result saved to leaderboard.");
    }

    static String getPerformanceLabel(int pct) {
        if (pct == 100) return "Perfect! Outstanding!";
        if (pct >= 80) return "Excellent!";
        if (pct >= 60) return "Good job!";
        if (pct >= 40) return "Keep practicing!";
        return "Better luck next time!";
    }

    // ─────────────────────────────────────────────
    //  INPUT HELPERS
    // ─────────────────────────────────────────────
    static int readInt(Scanner scanner, String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            try {
                int val = scanner.nextInt();
                scanner.nextLine();
                if (val >= min && val <= max) return val;
                System.out.println("  Please enter a number between " + min + " and " + max + ".");
            } catch (InputMismatchException e) {
                scanner.nextLine();
                System.out.println("  Invalid input. Please enter a number.");
            }
        }
    }

    static String readString(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String val = scanner.nextLine().trim();
            if (!val.isEmpty()) return val;
            System.out.println("  Input cannot be empty.");
        }
    }

    // ─────────────────────────────────────────────
    //  MAIN MENU
    // ─────────────────────────────────────────────
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Question> questions = loadQuestions();
        Leaderboard leaderboard = new Leaderboard();
        leaderboard.loadFromFile();

        System.out.println("═".repeat(60));
        System.out.println("              🎯  JAVA QUIZ GAME");
        System.out.println("═".repeat(60));

        boolean running = true;
        while (running) {
            System.out.println("\n─── Main Menu ───");
            System.out.println(" 1.  Start Quiz");
            System.out.println(" 2.  View Leaderboard");
            System.out.println(" 3.  Browse Questions");
            System.out.println(" 4.  Exit");
            System.out.print("Choose an option: ");

            int choice;
            try {
                choice = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                scanner.nextLine();
                System.out.println("  Invalid option.");
                continue;
            }

            switch (choice) {
                case 1:
                    String playerName = readString(scanner, "  Enter your name: ");
                    Set<String> categories = new LinkedHashSet<>();
                    categories.add("ALL");
                    for (Question q : questions) categories.add(q.category);
                    List<String> catList = new ArrayList<>(categories);
                    System.out.println("\n  Available categories:");
                    for (int i = 0; i < catList.size(); i++) {
                        System.out.println("  " + (i + 1) + ". " + catList.get(i));
                    }
                    int catChoice = readInt(scanner, "  Choose category: ", 1, catList.size());
                    String category = catList.get(catChoice - 1);

                    System.out.println("\n  Difficulty:");
                    System.out.println("  1. ALL\n  2. EASY\n  3. MEDIUM\n  4. HARD");
                    int diffChoice = readInt(scanner, "  Choose difficulty: ", 1, 4);
                    String[] diffs = {"ALL", "EASY", "MEDIUM", "HARD"};
                    String difficulty = diffs[diffChoice - 1];

                    int questionCount = readInt(scanner, "  Number of questions (1-20): ", 1, 20);
                    int timeLimit = readInt(scanner, "  Seconds per question (5-60): ", 5, 60);

                    runQuiz(scanner, questions, leaderboard, playerName, questionCount, timeLimit, category, difficulty);

                    System.out.print("\n  Play again? (Y/N): ");
                    String replay = scanner.nextLine().trim().toUpperCase();
                    if (replay.equals("Y")) {
                        runQuiz(scanner, questions, leaderboard, playerName, questionCount, timeLimit, category, difficulty);
                    }
                    break;

                case 2:
                    leaderboard.display();
                    break;

                case 3:
                    System.out.println("\n─── Question Browser ───");
                    System.out.println("  Categories available:");
                    Map<String, List<Question>> grouped = new LinkedHashMap<>();
                    for (Question q : questions) {
                        grouped.computeIfAbsent(q.category, k -> new ArrayList<>()).add(q);
                    }
                    List<String> cats = new ArrayList<>(grouped.keySet());
                    for (int i = 0; i < cats.size(); i++) {
                        System.out.println("  " + (i + 1) + ". " + cats.get(i) + " (" + grouped.get(cats.get(i)).size() + " questions)");
                    }
                    int browseChoice = readInt(scanner, "  Choose category to browse: ", 1, cats.size());
                    String browseCat = cats.get(browseChoice - 1);
                    List<Question> catQuestions = grouped.get(browseCat);

                    System.out.println("\n─── " + browseCat + " Questions ───");
                    for (int i = 0; i < catQuestions.size(); i++) {
                        Question q = catQuestions.get(i);
                        System.out.println("\n  Q" + (i + 1) + " [" + q.difficulty + "]  " + q.questionText);
                        char[] lbls = {'A', 'B', 'C', 'D'};
                        for (int j = 0; j < q.options.length; j++) {
                            String marker = (j == q.correctIndex) ? " ✓" : "";
                            System.out.println("    " + lbls[j] + ") " + q.options[j] + marker);
                        }
                        System.out.println("  Explanation: " + q.explanation);
                    }
                    break;

                case 4:
                    running = false;
                    System.out.println("  Thanks for playing! Goodbye!");
                    break;

                default:
                    System.out.println("  Invalid option. Choose 1–4.");
            }
        }
        scanner.close();
    }
}