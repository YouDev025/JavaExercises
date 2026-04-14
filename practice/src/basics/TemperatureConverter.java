package basics;
import java.util.Scanner;
import java.util.InputMismatchException;

/**
 * Temperature Conversion Program
 * Converts between Celsius, Fahrenheit, and Kelvin scales
 */
public class TemperatureConverter {

    // Conversion formulas as constants for better maintainability
    private static final double FREEZING_POINT_DIFFERENCE = 32.0;
    private static final double CELSIUS_TO_FAHRENHEIT_RATIO = 9.0 / 5.0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean continueRunning = true;

        System.out.println("=" .repeat(60));
        System.out.println("🌡️  TEMPERATURE CONVERSION CALCULATOR");
        System.out.println("=" .repeat(60));

        while (continueRunning) {
            // Display menu
            System.out.println("\n📋 What would you like to convert?");
            System.out.println("1. Celsius to Fahrenheit");
            System.out.println("2. Celsius to Kelvin");
            System.out.println("3. Fahrenheit to Celsius");
            System.out.println("4. Fahrenheit to Kelvin");
            System.out.println("5. Kelvin to Celsius");
            System.out.println("6. Kelvin to Fahrenheit");
            System.out.println("7. Convert ALL (Celsius → Fahrenheit & Kelvin)");
            System.out.println("8. Compare multiple temperatures");
            System.out.println("9. Exit");
            System.out.print("\nEnter your choice (1-9): ");

            int choice = 0;
            try {
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("❌ Invalid input! Please enter a number between 1 and 9.");
                scanner.next(); // Clear invalid input
                continue;
            }

            switch (choice) {
                case 1:
                    celsiusToFahrenheit(scanner);
                    break;
                case 2:
                    celsiusToKelvin(scanner);
                    break;
                case 3:
                    fahrenheitToCelsius(scanner);
                    break;
                case 4:
                    fahrenheitToKelvin(scanner);
                    break;
                case 5:
                    kelvinToCelsius(scanner);
                    break;
                case 6:
                    kelvinToFahrenheit(scanner);
                    break;
                case 7:
                    convertAllCelsius(scanner);
                    break;
                case 8:
                    compareTemperatures(scanner);
                    break;
                case 9:
                    continueRunning = false;
                    System.out.println("\n👋 Thank you for using Temperature Converter!");
                    break;
                default:
                    System.out.println("❌ Invalid choice! Please select 1-9.");
            }
        }
        scanner.close();
    }

    // Method 1: Celsius to Fahrenheit
    // Formula: °F = (°C × 9/5) + 32
    private static void celsiusToFahrenheit(Scanner scanner) {
        System.out.println("\n--- Celsius to Fahrenheit Conversion ---");
        double celsius = getValidTemperature(scanner, "Enter temperature in Celsius (°C): ");

        // Using double for precise calculation
        double fahrenheit = (celsius * CELSIUS_TO_FAHRENHEIT_RATIO) + FREEZING_POINT_DIFFERENCE;

        System.out.printf("\n✅ Conversion Result:%n");
        System.out.printf("   %.2f°C = %.2f°F%n", celsius, fahrenheit);
        displayWeatherTip(fahrenheit);
    }

    // Method 2: Celsius to Kelvin
    // Formula: K = °C + 273.15
    private static void celsiusToKelvin(Scanner scanner) {
        System.out.println("\n--- Celsius to Kelvin Conversion ---");
        double celsius = getValidTemperature(scanner, "Enter temperature in Celsius (°C): ");

        double kelvin = celsius + 273.15;

        System.out.printf("\n✅ Conversion Result:%n");
        System.out.printf("   %.2f°C = %.2f K%n", celsius, kelvin);

        if (kelvin < 0) {
            System.out.println("⚠️  Warning: Temperature below absolute zero (0 K) is impossible!");
        }
    }

    // Method 3: Fahrenheit to Celsius
    // Formula: °C = (°F - 32) × 5/9
    private static void fahrenheitToCelsius(Scanner scanner) {
        System.out.println("\n--- Fahrenheit to Celsius Conversion ---");
        double fahrenheit = getValidTemperature(scanner, "Enter temperature in Fahrenheit (°F): ");

        double celsius = (fahrenheit - FREEZING_POINT_DIFFERENCE) * 5.0 / 9.0;

        System.out.printf("\n✅ Conversion Result:%n");
        System.out.printf("   %.2f°F = %.2f°C%n", fahrenheit, celsius);
        displayTemperatureFeel(celsius);
    }

    // Method 4: Fahrenheit to Kelvin
    // Formula: K = (°F - 32) × 5/9 + 273.15
    private static void fahrenheitToKelvin(Scanner scanner) {
        System.out.println("\n--- Fahrenheit to Kelvin Conversion ---");
        double fahrenheit = getValidTemperature(scanner, "Enter temperature in Fahrenheit (°F): ");

        double kelvin = (fahrenheit - FREEZING_POINT_DIFFERENCE) * 5.0 / 9.0 + 273.15;

        System.out.printf("\n✅ Conversion Result:%n");
        System.out.printf("   %.2f°F = %.2f K%n", fahrenheit, kelvin);

        if (kelvin < 0) {
            System.out.println("⚠️  Warning: Temperature below absolute zero (0 K) is impossible!");
        }
    }

    // Method 5: Kelvin to Celsius
    // Formula: °C = K - 273.15
    private static void kelvinToCelsius(Scanner scanner) {
        System.out.println("\n--- Kelvin to Celsius Conversion ---");
        double kelvin = getValidTemperature(scanner, "Enter temperature in Kelvin (K): ");

        if (kelvin < 0) {
            System.out.println("❌ Error: Kelvin cannot be negative (absolute zero is 0K)!");
            return;
        }

        double celsius = kelvin - 273.15;

        System.out.printf("\n✅ Conversion Result:%n");
        System.out.printf("   %.2f K = %.2f°C%n", kelvin, celsius);
    }

    // Method 6: Kelvin to Fahrenheit
    // Formula: °F = (K - 273.15) × 9/5 + 32
    private static void kelvinToFahrenheit(Scanner scanner) {
        System.out.println("\n--- Kelvin to Fahrenheit Conversion ---");
        double kelvin = getValidTemperature(scanner, "Enter temperature in Kelvin (K): ");

        if (kelvin < 0) {
            System.out.println("❌ Error: Kelvin cannot be negative (absolute zero is 0K)!");
            return;
        }

        double fahrenheit = (kelvin - 273.15) * CELSIUS_TO_FAHRENHEIT_RATIO + FREEZING_POINT_DIFFERENCE;

        System.out.printf("\n✅ Conversion Result:%n");
        System.out.printf("   %.2f K = %.2f°F%n", kelvin, fahrenheit);
    }

    // Method 7: Convert Celsius to both Fahrenheit and Kelvin (Challenge requirement)
    private static void convertAllCelsius(Scanner scanner) {
        System.out.println("\n--- Complete Celsius Conversion (→ Fahrenheit & Kelvin) ---");
        double celsius = getValidTemperature(scanner, "Enter temperature in Celsius (°C): ");

        // Calculate both conversions using double variables
        double fahrenheit = (celsius * CELSIUS_TO_FAHRENHEIT_RATIO) + FREEZING_POINT_DIFFERENCE;
        double kelvin = celsius + 273.15;

        System.out.println("\n" + "=".repeat(50));
        System.out.println("📊 COMPLETE CONVERSION RESULTS:");
        System.out.println("=".repeat(50));
        System.out.printf("   %-15s : %10.2f°C%n", "Original", celsius);
        System.out.printf("   %-15s : %10.2f°F%n", "Fahrenheit", fahrenheit);
        System.out.printf("   %-15s : %10.2f K%n", "Kelvin", kelvin);
        System.out.println("=".repeat(50));

        // Show common reference points
        displayReferencePoints(celsius);
    }

    // Method 8: Compare multiple temperatures
    private static void compareTemperatures(Scanner scanner) {
        System.out.println("\n--- Temperature Comparison Tool ---");
        System.out.print("How many temperatures do you want to convert? ");

        int count = 0;
        try {
            count = scanner.nextInt();
            if (count <= 0) {
                System.out.println("❌ Please enter a positive number.");
                return;
            }
        } catch (InputMismatchException e) {
            System.out.println("❌ Invalid input!");
            scanner.next();
            return;
        }

        double[] celsiusValues = new double[count];

        for (int i = 0; i < count; i++) {
            System.out.printf("\nTemperature %d:%n", i + 1);
            celsiusValues[i] = getValidTemperature(scanner, "Enter temperature in Celsius (°C): ");
        }

        System.out.println("\n" + "=".repeat(70));
        System.out.printf("%-15s %-15s %-15s %-15s%n", "Celsius (°C)", "Fahrenheit (°F)", "Kelvin (K)", "Description");
        System.out.println("=".repeat(70));

        for (int i = 0; i < count; i++) {
            double f = (celsiusValues[i] * CELSIUS_TO_FAHRENHEIT_RATIO) + FREEZING_POINT_DIFFERENCE;
            double k = celsiusValues[i] + 273.15;
            String description = getTemperatureDescription(celsiusValues[i]);

            System.out.printf("%-15.2f %-15.2f %-15.2f %-15s%n",
                    celsiusValues[i], f, k, description);
        }
        System.out.println("=".repeat(70));
    }

    // Helper method to get valid temperature input
    private static double getValidTemperature(Scanner scanner, String prompt) {
        double temperature = 0;
        while (true) {
            try {
                System.out.print(prompt);
                temperature = scanner.nextDouble();
                return temperature;
            } catch (InputMismatchException e) {
                System.out.println("❌ Invalid input! Please enter a numeric value.");
                scanner.next(); // Clear invalid input
            }
        }
    }

    // Helper method to provide weather tips
    private static void displayWeatherTip(double fahrenheit) {
        System.out.println("\n💡 Weather Tip:");
        if (fahrenheit >= 100) {
            System.out.println("   🔥 Very hot! Stay hydrated and avoid direct sunlight.");
        } else if (fahrenheit >= 80) {
            System.out.println("   ☀️  Warm weather. Perfect for outdoor activities!");
        } else if (fahrenheit >= 50) {
            System.out.println("   🌤️  Mild temperature. Comfortable weather.");
        } else if (fahrenheit >= 32) {
            System.out.println("   🧥 Cool. Bring a jacket if going outside.");
        } else {
            System.out.println("   ❄️  Freezing! Bundle up and stay warm!");
        }
    }

    // Helper method to describe temperature feel
    private static void displayTemperatureFeel(double celsius) {
        System.out.println("\n💡 How it feels:");
        if (celsius >= 40) {
            System.out.println("   🔴 Extremely hot!");
        } else if (celsius >= 30) {
            System.out.println("   🟠 Hot");
        } else if (celsius >= 20) {
            System.out.println("   🟡 Warm");
        } else if (celsius >= 10) {
            System.out.println("   🟢 Cool");
        } else if (celsius >= 0) {
            System.out.println("   🔵 Cold");
        } else {
            System.out.println("   🔵❄️  Freezing cold!");
        }
    }

    // Helper method to get temperature description
    private static String getTemperatureDescription(double celsius) {
        if (celsius >= 100) return "Boiling Point";
        if (celsius >= 40) return "Very Hot";
        if (celsius >= 30) return "Hot Day";
        if (celsius >= 20) return "Room Temp";
        if (celsius >= 10) return "Cool";
        if (celsius >= 0) return "Cold";
        if (celsius >= -10) return "Freezing";
        if (celsius >= -20) return "Very Cold";
        if (celsius >= -273.14) return "Extreme Cold";
        return "Below Absolute Zero";
    }

    // Helper method to display reference points
    private static void displayReferencePoints(double celsius) {
        System.out.println("\n📌 Reference Points:");
        System.out.printf("   • Water freezes at 0°C (32°F / 273.15K)%n");
        System.out.printf("   • Water boils at 100°C (212°F / 373.15K)%n");

        if (Math.abs(celsius - 0) < 0.01) {
            System.out.println("   🧊 Your temperature is exactly the freezing point of water!");
        } else if (Math.abs(celsius - 100) < 0.01) {
            System.out.println("   💨 Your temperature is exactly the boiling point of water!");
        } else if (Math.abs(celsius - 37) < 0.5) {
            System.out.println("   👤 Your temperature is close to normal human body temperature!");
        }
    }
}