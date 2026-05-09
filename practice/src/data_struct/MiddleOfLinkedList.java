package data_struct;

import java.util.InputMismatchException;
import java.util.Scanner;

// Class to find the middle element of a LinkedList in one pass
// Uses the slow and fast pointer technique (Tortoise and Hare)
// Time Complexity: O(n) | Space Complexity: O(1)
public class MiddleOfLinkedList {

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

    // Class to represent the LinkedList and its operations
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

        // Finds and returns the middle node using the slow and fast pointer technique
        // Slow pointer moves one step at a time
        // Fast pointer moves two steps at a time
        // When fast pointer reaches the end, slow pointer is at the middle
        public Node findMiddle() {

            // Cannot find middle of an empty list
            if (head == null) {
                return null;
            }

            // Both pointers start at the head of the LinkedList
            Node slow = head;
            Node fast = head;

            // Traverse the list with both pointers
            // Fast pointer moves twice as fast as slow pointer
            // When fast reaches the last node or goes beyond, slow is at the middle
            while (fast != null && fast.next != null) {
                slow = slow.next;        // Move slow one step forward
                fast = fast.next.next;   // Move fast two steps forward
            }

            // Slow pointer is now at the middle node
            return slow;
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
        System.out.println("Enter " + n + " elements:");
        for (int i = 0; i < n; i++) {
            int element = readInt(scanner, "  Element [" + (i + 1) + "]: ");
            list.add(element);
        }

        // Display the full LinkedList
        list.display();

        // Find the middle node using the slow and fast pointer technique
        Node middle = list.findMiddle();

        // Display the middle element
        if (middle != null) {
            System.out.println("Middle element: " + middle.data);
        }

        // Close the scanner to release system resources
        scanner.close();
    }
}