package data_struct;

import java.util.ArrayList;
import java.util.Scanner;

// Interactive ArrayList Demo
public class ArrayListDemo {

    public static void main(String[] args) {

        // Create Scanner object for user input
        Scanner scanner = new Scanner(System.in);

        // Create ArrayList
        ArrayList<String> list = new ArrayList<>();

        // Ask user how many elements to add
        System.out.print("How many elements do you want to add? ");
        int n = scanner.nextInt();
        scanner.nextLine(); // consume newline

        // Add elements entered by the user
        for (int i = 0; i < n; i++) {
            System.out.print("Enter element " + (i + 1) + ": ");
            String item = scanner.nextLine();
            list.add(item);
        }

        // Display original list
        System.out.println("\nOriginal list: " + list);

        // Ask user for index to remove
        System.out.print("Enter the index of the element to remove: ");
        int index = scanner.nextInt();

        // Check if index is valid
        if (index >= 0 && index < list.size()) {

            // Remove element
            String removed = list.remove(index);

            // Display removed element
            System.out.println("Removed element: " + removed);

            // Display updated list
            System.out.println("List after removal: " + list);

        } else {
            System.out.println("Invalid index!");
        }

        // Close scanner
        scanner.close();
    }
}