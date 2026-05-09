package data_struct;

import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Scanner;

// Class to remove duplicate elements from an unsorted LinkedList
// Uses a HashSet to track seen values for O(1) lookup per node
// Time Complexity: O(n) | Space Complexity: O(n)
public class RemoveDuplicatesLinkedList {

    // Node class represents a single element in the LinkedList
    // Each node holds an integer value and a reference to the next node
    static class Node {
        int data;
        Node next;

        // Constructor to initialize a node with a given value
        Node(int data) {
            this.data = data;
            this.next = null;
        }
    }

    // Class to represent the LinkedList and its duplicate removal operation
    static class LinkedList {

        // Head points to the first node in the LinkedList
        Node head;

        // Adds a new node with the given value at the end of the LinkedList
        public void add(int data) {
            Node newNode = new Node(data);

            // If the list is empty, the new node becomes the head
            if (head == null) {
                head = newNode;
                return;
            }

            // Traverse to the last node and attach the new node after it
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }

        // Removes all duplicate nodes from the LinkedList in a single pass
        // A HashSet tracks which values have already been seen
        // When a duplicate is found, it is skipped by adjusting the next pointer
        public void removeDuplicates() {

            // Cannot remove duplicates from an empty list
            if (head == null) {
                System.out.println("List is empty.");
                return;
            }

            // HashSet to store values that have already been encountered
            HashSet<Integer> seen = new HashSet<>();

            // Current node being examined
            Node current = head;

            // Previous node used to re-link the list when a duplicate is removed
            Node previous = null;

            // Counter to track the total number of duplicates removed
            int removedCount = 0;

            // Traverse every node in the LinkedList
            while (current != null) {

                // Check if the current node's value has been seen before
                if (seen.contains(current.data)) {

                    // Duplicate found — skip the current node by linking
                    // the previous node directly to the node after current
                    previous.next = current.next;
                    removedCount++;

                } else {

                    // First occurrence — record this value as seen
                    seen.add(current.data);

                    // Advance the previous pointer to the current node
                    previous = current;
                }

                // Move to the next node regardless of whether current was removed
                current = current.next;
            }

            System.out.println("Duplicates removed: " + removedCount);
        }

        // Displays all elements in the LinkedList from head to tail
        public void display() {
            if (head == null) {
                System.out.println("List is empty.");
                return;
            }

            Node current = head;
            System.out.print("LinkedList: ");

            while (current != null) {
                System.out.print(current.data);

                // Print arrow between nodes, not after the last one
                if (current.next != null) {
                    System.out.print(" -> ");
                }
                current = current.next;
            }
            System.out.println();
        }
    }

    // Helper method to read a valid integer from the user
    // Keeps prompting until the user enters a proper integer value
    public static int readInt(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                int value = scanner.nextInt();
                return value;
            } catch (InputMismatchException e) {
                // Clear the invalid token from the scanner buffer before retrying
                scanner.nextLine();
                System.out.println("  Invalid input. Please enter a whole number.");
            }
        }
    }

    // Helper method to read a valid positive integer greater than zero
    // Rejects non-integers and values that are zero or negative
    public static int readPositiveInt(Scanner scanner, String prompt) {
        while (true) {
            int value = readInt(scanner, prompt);
            if (value > 0) {
                return value;
            }
            System.out.println("  Invalid input. Please enter a number greater than 0.");
        }
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // Create a new LinkedList instance
        LinkedList list = new LinkedList();

        // Read a valid positive integer for the number of elements
        int n = readPositiveInt(scanner, "Enter number of elements: ");

        // Loop to read n integers and add them to the LinkedList
        System.out.println("Enter " + n + " elements (duplicates allowed):");
        for (int i = 0; i < n; i++) {
            int element = readInt(scanner, "  Element [" + (i + 1) + "]: ");
            list.add(element);
        }

        // Display the original LinkedList before removing duplicates
        System.out.println("\nBefore:");
        list.display();

        // Remove all duplicate nodes from the LinkedList
        list.removeDuplicates();

        // Display the LinkedList after duplicates have been removed
        System.out.println("After:");
        list.display();

        // Close the scanner to release system resources
        scanner.close();
    }
}