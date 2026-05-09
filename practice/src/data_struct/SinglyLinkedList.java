package data_struct;

import java.util.InputMismatchException;
import java.util.Scanner;

// Class that implements a custom singly LinkedList from scratch
// Supports add (at end, at index), remove (by value, by index), search, and display
// Time Complexity: add at end O(n) | add at index O(n) | remove O(n) | display O(n)
public class SinglyLinkedList {

    // Node inner class represents a single element in the LinkedList
    // Each node stores a value and a reference to the next node in the chain
    static class Node {
        int data;   // The value stored in this node
        Node next;  // Reference to the next node, null if this is the last node

        // Constructor to initialize a node with a given value
        // next is set to null by default since new nodes have no successor yet
        Node(int data) {
            this.data = data;
            this.next = null;
        }
    }

    // Head points to the first node in the LinkedList
    // null when the list is empty
    private Node head;

    // Tracks the total number of nodes currently in the LinkedList
    private int size;

    // Constructor initializes an empty LinkedList
    public SinglyLinkedList() {
        head = null;
        size = 0;
    }

    // ─────────────────────────────────────────────
    //  ADD OPERATIONS
    // ─────────────────────────────────────────────

    // Adds a new node with the given value at the end of the LinkedList
    public void addAtEnd(int data) {
        Node newNode = new Node(data);

        // If the list is empty, the new node becomes the head
        if (head == null) {
            head = newNode;
        } else {

            // Traverse to the last node and attach the new node after it
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }

        size++;
        System.out.println(data + " added at end.");
    }

    // Adds a new node with the given value at the beginning of the LinkedList
    // The new node becomes the new head and points to the old head
    public void addAtBeginning(int data) {
        Node newNode = new Node(data);

        // Point the new node to the current head before replacing it
        newNode.next = head;
        head = newNode;
        size++;
        System.out.println(data + " added at beginning.");
    }

    // Adds a new node with the given value at the specified index (zero-based)
    // Throws an exception if the index is out of the valid range
    public void addAtIndex(int index, int data) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds for size " + size);
        }

        // Delegate to addAtBeginning if inserting at the head position
        if (index == 0) {
            addAtBeginning(data);
            return;
        }

        Node newNode = new Node(data);
        Node current = head;

        // Traverse to the node just before the target index
        for (int i = 0; i < index - 1; i++) {
            current = current.next;
        }

        // Insert the new node between current and current.next
        newNode.next = current.next;
        current.next = newNode;
        size++;
        System.out.println(data + " added at index " + index + ".");
    }

    // ─────────────────────────────────────────────
    //  REMOVE OPERATIONS
    // ─────────────────────────────────────────────

    // Removes the first node that contains the given value
    // Returns true if the value was found and removed, false otherwise
    public boolean removeByValue(int data) {

        // Cannot remove from an empty list
        if (head == null) {
            System.out.println("List is empty. Nothing to remove.");
            return false;
        }

        // If the head node holds the target value, update head to the next node
        if (head.data == data) {
            head = head.next;
            size--;
            System.out.println(data + " removed from the list.");
            return true;
        }

        // Traverse the list to find the node just before the target value
        Node current = head;
        while (current.next != null) {

            if (current.next.data == data) {

                // Skip the target node by linking current directly to the node after it
                current.next = current.next.next;
                size--;
                System.out.println(data + " removed from the list.");
                return true;
            }
            current = current.next;
        }

        // Value was not found anywhere in the list
        System.out.println(data + " not found in the list.");
        return false;
    }

    // Removes the node at the specified index (zero-based)
    // Throws an exception if the index is out of the valid range
    public void removeAtIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds for size " + size);
        }

        // If removing the head node, update head to the next node
        if (index == 0) {
            System.out.println(head.data + " removed from index " + index + ".");
            head = head.next;
            size--;
            return;
        }

        // Traverse to the node just before the target index
        Node current = head;
        for (int i = 0; i < index - 1; i++) {
            current = current.next;
        }

        // Skip the target node by linking current directly to the node after it
        System.out.println(current.next.data + " removed from index " + index + ".");
        current.next = current.next.next;
        size--;
    }

    // ─────────────────────────────────────────────
    //  SEARCH AND UTILITY OPERATIONS
    // ─────────────────────────────────────────────

    // Searches for the first occurrence of a value and returns its index
    // Returns -1 if the value is not found in the list
    public int search(int data) {
        Node current = head;
        int index = 0;

        while (current != null) {
            if (current.data == data) {
                return index;
            }
            current = current.next;
            index++;
        }

        return -1;
    }

    // Returns the number of nodes currently in the LinkedList
    public int size() {
        return size;
    }

    // Returns true if the LinkedList contains no nodes
    public boolean isEmpty() {
        return size == 0;
    }

    // ─────────────────────────────────────────────
    //  DISPLAY OPERATION
    // ─────────────────────────────────────────────

    // Displays all elements in the LinkedList from head to tail
    // Shows each node's index alongside its value for clarity
    public void display() {
        if (head == null) {
            System.out.println("List is empty.");
            return;
        }

        Node current = head;
        int index = 0;

        System.out.print("LinkedList [size=" + size + "]: ");

        while (current != null) {
            System.out.print("[" + index + "]" + current.data);

            // Print arrow between nodes, not after the last one
            if (current.next != null) {
                System.out.print(" -> ");
            }
            current = current.next;
            index++;
        }
        System.out.println();
    }

    // ─────────────────────────────────────────────
    //  HELPER INPUT METHODS
    // ─────────────────────────────────────────────

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

    // Helper method to read a valid non-negative integer (zero or greater)
    // Used for index inputs where 0 is a valid position
    public static int readNonNegativeInt(Scanner scanner, String prompt) {
        while (true) {
            int value = readInt(scanner, prompt);
            if (value >= 0) {
                return value;
            }
            System.out.println("  Invalid input. Please enter a non-negative number.");
        }
    }

    // ─────────────────────────────────────────────
    //  MAIN MENU
    // ─────────────────────────────────────────────

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // Create a new SinglyLinkedList instance
        SinglyLinkedList list = new SinglyLinkedList();

        // Interactive menu loop to test all LinkedList operations
        boolean running = true;

        while (running) {
            System.out.println("\n─── Singly LinkedList Menu ───");
            System.out.println("1.  Add at end");
            System.out.println("2.  Add at beginning");
            System.out.println("3.  Add at index");
            System.out.println("4.  Remove by value");
            System.out.println("5.  Remove at index");
            System.out.println("6.  Search for value");
            System.out.println("7.  Display list");
            System.out.println("8.  Check size");
            System.out.println("9.  Check if empty");
            System.out.println("10. Exit");
            System.out.print("Choose an option: ");

            int choice;

            try {
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                // Clear the invalid token and prompt the user to try again
                scanner.nextLine();
                System.out.println("  Invalid option. Please enter a number between 1 and 10.");
                continue;
            }

            switch (choice) {

                case 1:
                    // Read the element and add it at the end of the list
                    int endElement = readInt(scanner, "Enter element to add at end: ");
                    list.addAtEnd(endElement);
                    list.display();
                    break;

                case 2:
                    // Read the element and add it at the beginning of the list
                    int beginElement = readInt(scanner, "Enter element to add at beginning: ");
                    list.addAtBeginning(beginElement);
                    list.display();
                    break;

                case 3:
                    // Read the index and element, then insert at the specified position
                    int insertIndex = readNonNegativeInt(scanner, "Enter index to insert at: ");
                    int insertElement = readInt(scanner, "Enter element to insert: ");
                    try {
                        list.addAtIndex(insertIndex, insertElement);
                        list.display();
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("  Error: " + e.getMessage());
                    }
                    break;

                case 4:
                    // Read the value and remove its first occurrence from the list
                    int removeValue = readInt(scanner, "Enter value to remove: ");
                    list.removeByValue(removeValue);
                    list.display();
                    break;

                case 5:
                    // Read the index and remove the node at that position
                    int removeIndex = readNonNegativeInt(scanner, "Enter index to remove: ");
                    try {
                        list.removeAtIndex(removeIndex);
                        list.display();
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("  Error: " + e.getMessage());
                    }
                    break;

                case 6:
                    // Search for a value and display its index if found
                    int searchValue = readInt(scanner, "Enter value to search: ");
                    int foundIndex = list.search(searchValue);
                    if (foundIndex == -1) {
                        System.out.println(searchValue + " not found in the list.");
                    } else {
                        System.out.println(searchValue + " found at index " + foundIndex + ".");
                    }
                    break;

                case 7:
                    // Display all elements currently in the list
                    list.display();
                    break;

                case 8:
                    // Display the current number of nodes in the list
                    System.out.println("List size: " + list.size());
                    break;

                case 9:
                    // Check and display whether the list is currently empty
                    System.out.println("List is " + (list.isEmpty() ? "empty." : "not empty."));
                    break;

                case 10:
                    // Exit the menu loop
                    running = false;
                    System.out.println("Exiting.");
                    break;

                default:
                    System.out.println("  Invalid option. Please choose between 1 and 10.");
            }
        }

        // Close the scanner to release system resources
        scanner.close();
    }
}