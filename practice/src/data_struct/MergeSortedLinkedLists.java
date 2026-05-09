package data_struct;

import java.util.InputMismatchException;
import java.util.Scanner;

// Class to merge two sorted LinkedLists into one sorted LinkedList
// Compares nodes from both lists one by one and links them in sorted order
// Time Complexity: O(n + m) | Space Complexity: O(1)
public class MergeSortedLinkedLists {

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

    // Class to represent a sorted LinkedList and its operations
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

        // Displays all elements in the LinkedList from head to tail
        public void display(String label) {
            if (head == null) {
                System.out.println(label + "empty");
                return;
            }

            System.out.print(label);
            Node current = head;

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

    // Merges two sorted LinkedLists into one sorted LinkedList
    // Uses a dummy head node to simplify the merging logic
    // The dummy node acts as a placeholder so the result list always has a head
    // to attach nodes to without special casing the first node
    public static Node mergeSortedLists(Node head1, Node head2) {

        // Dummy node to serve as the starting anchor of the merged list
        // The actual merged list begins at dummy.next
        Node dummy = new Node(0);

        // Tail always points to the last node added to the merged list
        // New nodes are attached after tail to build the result
        Node tail = dummy;

        // Pointer for traversing the first list
        Node current1 = head1;

        // Pointer for traversing the second list
        Node current2 = head2;

        // Compare nodes from both lists and attach the smaller one to the merged list
        while (current1 != null && current2 != null) {

            if (current1.data <= current2.data) {

                // Node from list1 is smaller or equal — attach it to merged list
                tail.next = current1;

                // Advance the pointer in list1
                current1 = current1.next;

            } else {

                // Node from list2 is smaller — attach it to merged list
                tail.next = current2;

                // Advance the pointer in list2
                current2 = current2.next;
            }

            // Advance the tail to the newly attached node
            tail = tail.next;
        }

        // If list1 still has remaining nodes, attach them all directly
        // These nodes are already sorted and greater than the last merged node
        if (current1 != null) {
            tail.next = current1;
        }

        // If list2 still has remaining nodes, attach them all directly
        // These nodes are already sorted and greater than the last merged node
        if (current2 != null) {
            tail.next = current2;
        }

        // Return the head of the merged list, which is the node after the dummy
        return dummy.next;
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

        // Create the first sorted LinkedList
        LinkedList list1 = new LinkedList();

        // Read a valid positive integer for the size of the first list
        int n = readPositiveInt(scanner, "Enter number of elements for first list: ");

        // Loop to read n sorted integers for the first list
        System.out.println("Enter " + n + " sorted elements for first list:");
        for (int i = 0; i < n; i++) {
            int element = readInt(scanner, "  Element [" + (i + 1) + "]: ");
            list1.add(element);
        }

        // Create the second sorted LinkedList
        LinkedList list2 = new LinkedList();

        // Read a valid positive integer for the size of the second list
        int m = readPositiveInt(scanner, "\nEnter number of elements for second list: ");

        // Loop to read m sorted integers for the second list
        System.out.println("Enter " + m + " sorted elements for second list:");
        for (int i = 0; i < m; i++) {
            int element = readInt(scanner, "  Element [" + (i + 1) + "]: ");
            list2.add(element);
        }

        // Display both lists before merging
        System.out.println();
        list1.display("List 1:        ");
        list2.display("List 2:        ");

        // Merge both sorted lists into one sorted LinkedList
        Node mergedHead = mergeSortedLists(list1.head, list2.head);

        // Wrap the merged result in a LinkedList for display
        LinkedList mergedList = new LinkedList();
        mergedList.head = mergedHead;

        // Display the final merged sorted LinkedList
        mergedList.display("Merged list:   ");

        // Close the scanner to release system resources
        scanner.close();
    }
}