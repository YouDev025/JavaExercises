package basics;

import java.util.Scanner;
import java.util.InputMismatchException;

/**
 * A program that demonstrates all Java primitive data types
 * by taking user input for each type and showing default values
 */
public class PrimitiveDataTypesDemo {

    // Class-level variables (default values are automatically assigned)
    static byte defaultByte;
    static short defaultShort;
    static int defaultInt;
    static long defaultLong;
    static float defaultFloat;
    static double defaultDouble;
    static char defaultChar;
    static boolean defaultBoolean;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Display program header
        System.out.println("=" .repeat(50));
        System.out.println("JAVA PRIMITIVE DATA TYPES DEMONSTRATION");
        System.out.println("=" .repeat(50));

        // Display default values first
        System.out.println("\n📌 DEFAULT VALUES (uninitialized class-level variables):");
        System.out.println("----------------------------------------");
        System.out.println("byte    : " + defaultByte);
        System.out.println("short   : " + defaultShort);
        System.out.println("int     : " + defaultInt);
        System.out.println("long    : " + defaultLong);
        System.out.println("float   : " + defaultFloat);
        System.out.println("double  : " + defaultDouble);
        System.out.println("char    : '" + defaultChar + "' (Unicode null)");
        System.out.println("boolean : " + defaultBoolean);

        System.out.println("\n🎯 Now let's enter our own values!\n");

        // Variables to store user input
        byte byteValue = 0;
        short shortValue = 0;
        int intValue = 0;
        long longValue = 0;
        float floatValue = 0.0f;
        double doubleValue = 0.0;
        char charValue = ' ';
        boolean booleanValue = false;

        // Input for byte (-128 to 127)
        while (true) {
            try {
                System.out.print("Enter a byte value (-128 to 127): ");
                byteValue = scanner.nextByte();
                break;
            } catch (InputMismatchException e) {
                System.out.println("❌ Invalid input! Please enter a number between -128 and 127.");
                scanner.next(); // Clear the invalid input
            }
        }

        // Input for short (-32,768 to 32,767)
        while (true) {
            try {
                System.out.print("Enter a short value (-32,768 to 32,767): ");
                shortValue = scanner.nextShort();
                break;
            } catch (InputMismatchException e) {
                System.out.println("❌ Invalid input! Please enter a number within the short range.");
                scanner.next();
            }
        }

        // Input for int (-2,147,483,648 to 2,147,483,647)
        while (true) {
            try {
                System.out.print("Enter an int value: ");
                intValue = scanner.nextInt();
                break;
            } catch (InputMismatchException e) {
                System.out.println("❌ Invalid input! Please enter a valid integer.");
                scanner.next();
            }
        }

        // Input for long
        while (true) {
            try {
                System.out.print("Enter a long value (add 'L' at the end if needed): ");
                longValue = scanner.nextLong();
                break;
            } catch (InputMismatchException e) {
                System.out.println("❌ Invalid input! Please enter a valid long number.");
                scanner.next();
            }
        }

        // Input for float
        while (true) {
            try {
                System.out.print("Enter a float value (add 'f' at the end if needed): ");
                floatValue = scanner.nextFloat();
                break;
            } catch (InputMismatchException e) {
                System.out.println("❌ Invalid input! Please enter a valid decimal number for float.");
                scanner.next();
            }
        }

        // Input for double
        while (true) {
            try {
                System.out.print("Enter a double value: ");
                doubleValue = scanner.nextDouble();
                break;
            } catch (InputMismatchException e) {
                System.out.println("❌ Invalid input! Please enter a valid decimal number for double.");
                scanner.next();
            }
        }

        // Input for char (single character)
        while (true) {
            try {
                System.out.print("Enter a char value (single character): ");
                String input = scanner.next();
                if (input.length() == 1) {
                    charValue = input.charAt(0);
                    break;
                } else {
                    System.out.println("❌ Invalid input! Please enter exactly ONE character.");
                }
            } catch (Exception e) {
                System.out.println("❌ Invalid input! Please enter a single character.");
                scanner.next();
            }
        }

        // Input for boolean (true/false)
        while (true) {
            try {
                System.out.print("Enter a boolean value (true/false): ");
                booleanValue = scanner.nextBoolean();
                break;
            } catch (InputMismatchException e) {
                System.out.println("❌ Invalid input! Please enter either 'true' or 'false'.");
                scanner.next();
            }
        }

        // Display all entered values
        System.out.println("\n" + "=".repeat(50));
        System.out.println("📊 YOUR ENTERED VALUES:");
        System.out.println("=".repeat(50));
        System.out.printf("%-10s : %d (size: 1 byte, range: -128 to 127)%n", "byte", byteValue);
        System.out.printf("%-10s : %d (size: 2 bytes, range: -32,768 to 32,767)%n", "short", shortValue);
        System.out.printf("%-10s : %d (size: 4 bytes, range: -2³¹ to 2³¹-1)%n", "int", intValue);
        System.out.printf("%-10s : %d (size: 8 bytes, range: -2⁶³ to 2⁶³-1)%n", "long", longValue);
        System.out.printf("%-10s : %.2f (size: 4 bytes, ~7 decimal digits precision)%n", "float", floatValue);
        System.out.printf("%-10s : %.2f (size: 8 bytes, ~16 decimal digits precision)%n", "double", doubleValue);
        System.out.printf("%-10s : '%c' (size: 2 bytes, Unicode character)%n", "char", charValue);
        System.out.printf("%-10s : %b (size: 1 bit, but JVM dependent)%n", "boolean", booleanValue);

        // Additional information about memory usage
        System.out.println("\n" + "=".repeat(50));
        System.out.println("💡 QUICK REFERENCE - PRIMITIVE DATA TYPES:");
        System.out.println("=".repeat(50));
        System.out.println("• byte   : 8-bit signed integer");
        System.out.println("• short  : 16-bit signed integer");
        System.out.println("• int    : 32-bit signed integer (most commonly used)");
        System.out.println("• long   : 64-bit signed integer (use 'L' suffix)");
        System.out.println("• float  : 32-bit floating point (use 'f' suffix)");
        System.out.println("• double : 64-bit floating point (default for decimals)");
        System.out.println("• char   : 16-bit Unicode character (use single quotes)");
        System.out.println("• boolean: true or false only");

        scanner.close();
    }
}