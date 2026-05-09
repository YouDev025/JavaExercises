package data_struct;

import java.util.InputMismatchException;
import java.util.Scanner;

// Class to detect a cycle in a LinkedList and find the node where the cycle starts
// Uses Floyd's Cycle Detection Algorithm (Tortoise and Hare)
// Time Complexity: O(n) | Space Complexity: O(1)
public class DetectCycle {

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

    // Class to represent the LinkedList and its cycle detection operations
    static class LinkedList {

        // Head points to the first node in the LinkedList
        Node head;

        // Stores all added nodes in order to allow cycle creation by index
        Node[] nodeRefs;
        int size;

        // Constructor initializes the node reference array with a fixed capacity
        LinkedList(int capacity) {
            nodeRefs = new Node[capacity];
            size = 0;
        }

        // Adds a new node with the given value at the end of the LinkedList
        // Also stores a reference to the node for later cycle creation
        public void add(int data) {
            Node newNode = new Node(data);
            nodeRefs[size++] = newNode;

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

        // Creates a cycle by connecting the last node to the node at cycleIndex
        // cycleIndex is zero-based, referring to the position in the list
        public void createCycle(int cycleIndex) {
            if (cycleIndex < 0 || cycleIndex >= size) {
                System.out.println("  Invalid cycle index. No cycle created.");
                return;
            }

            // Connect the tail node back to the node at cycleIndex
            nodeRefs[size - 1].next = nodeRefs[cycleIndex];
            System.out.println("  Cycle created: last node points back to node at index "
                    + cycleIndex + " (value: " + nodeRefs[cycleIndex].data + ")");
        }

        // Phase 1: Detects whether a cycle exists using slow and fast pointers
        // Returns the meeting node inside the cycle, or null if no cycle exists
        private Node detectCycle() {

            Node slow = head;
            Node fast = head;

            // Move slow one step and fast two steps at a time
            // If they meet, a cycle exists
            while (fast != null && fast.next != null) {
                slow = slow.next;
                fast = fast.next.next;

                // Cycle detected — return the meeting point inside the cycle
                if (slow == fast) {
                    return slow;
                }
            }

            // Fast pointer reached the end — no cycle exists
            return null;
        }

        // Phase 2: Finds the exact node where the cycle starts
        // Reset one pointer to head while keeping the other at the meeting point
        // Both move one step at a time — they will meet exactly at the cycle start node
        private Node findCycleStart(Node meetingPoint) {
            Node pointer1 = head;
            Node pointer2 = meetingPoint;

            // Advance both pointers one step at a time until they meet
            while (pointer1 != pointer2) {
                pointer1 = pointer1.next;
                pointer2 = pointer2.next;
            }

            // Both pointers now point to the start of the cycle
            return pointer1;
        }

        // Combines Phase 1 and Phase 2 to detect and locate the cycle
        public void analyzeCycle() {

            // Phase 1: check if a cycle exists and find the meeting point
            Node meetingPoint = detectCycle();

            if (meetingPoint == null) {
                System.out.println("No cycle detected in the LinkedList.");
                return;
            }

            System.out.println("Cycle detected in the LinkedList.");

            // Phase 2: find the exact node where the cycle begins
            Node cycleStart = findCycleStart(meetingPoint);
            System.out.println("Cycle starts at node with value: " + cycleStart.data);
        }

        // Displays all elements in the LinkedList from head to tail
        // Stops at the last node before the cycle to avoid infinite loop
        public void display() {
            if (head == null) {
                System.out.println("List is empty.");
                return;
            }

            Node current = head;
            System.out.print("LinkedList: ");

            // Traverse safely using the stored node references
            for (int i = 0; i < size; i++) {
                System.out.print(nodeRefs[i].data);
                if (i < size - 1) {
                    System.out.print(" -> ");
                }
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

        // Read a valid positive integer for the number of elements
        int n = readPositiveInt(scanner, "Enter number of elements: ");

        // Create the LinkedList with a capacity matching the number of elements
        LinkedList list = new LinkedList(n);

        // Loop to read n integers and add them to the LinkedList
        System.out.println("Enter " + n + " elements:");
        for (int i = 0; i < n; i++) {
            int element = readInt(scanner, "  Element [" + (i + 1) + "]: ");
            list.add(element);
        }

        // Display the full LinkedList before any cycle is created
        list.display();

        // Ask the user whether to create a cycle for testing purposes
        System.out.print("\nDo you want to create a cycle? (1 = Yes, 0 = No): ");
        int createCycle;

        try {
            createCycle = scanner.nextInt();
        } catch (InputMismatchException e) {
            scanner.nextLine();
            createCycle = 0;
        }

        if (createCycle == 1) {
            // Read the index of the node where the tail should point to form a cycle
            int cycleIndex = readInt(scanner,
                    "Enter index (0-based) of the node to connect the tail to (0 to " + (n - 1) + "): ");
            list.createCycle(cycleIndex);
        }

        // Analyze the LinkedList for a cycle and report the findings
        System.out.println();
        list.analyzeCycle();

        // Close the scanner to release system resources
        scanner.close();
    }
}