package data_struct;

import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Scanner;

// Class to simulate a Queue using Java's LinkedList
// Queue follows FIFO (First In First Out) principle
// Enqueue adds to the rear, Dequeue removes from the front
// Time Complexity: enqueue O(1) | dequeue O(1) | peek O(1)
public class LinkedListQueue {

    // The LinkedList acts as the underlying data structure for the queue
    // The front of the queue is the head of the LinkedList
    // The rear  of the queue is the tail of the LinkedList
    private LinkedList<Integer> queue = new LinkedList<>();

    // Adds a new element to the rear of the queue
    // addLast() inserts at the tail of the LinkedList
    public void enqueue(int element) {
        queue.addLast(element);
        System.out.println(element + " enqueued to rear of queue.");
    }

    // Removes and returns the element at the front of the queue
    // removeFirst() extracts from the head of the LinkedList
    // Throws an exception if the queue is empty before attempting removal
    public int dequeue() {
        if (isEmpty()) {
            throw new RuntimeException("Queue is empty. Cannot dequeue.");
        }
        int removed = queue.removeFirst();
        System.out.println(removed + " dequeued from front of queue.");
        return removed;
    }

    // Returns the element at the front of the queue without removing it
    // Throws an exception if the queue is empty before attempting peek
    public int peek() {
        if (isEmpty()) {
            throw new RuntimeException("Queue is empty. Cannot peek.");
        }
        return queue.getFirst();
    }

    // Returns true if the queue contains no elements
    public boolean isEmpty() {
        return queue.isEmpty();
    }

    // Returns the number of elements currently in the queue
    public int size() {
        return queue.size();
    }

    // Displays all elements in the queue from front to rear
    public void display() {
        if (isEmpty()) {
            System.out.println("Queue is empty.");
            return;
        }
        System.out.println("Queue (front -> rear): " + queue);
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

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // Create a new LinkedListQueue instance
        LinkedListQueue linkedListQueue = new LinkedListQueue();

        // Interactive menu loop to test all queue operations
        boolean running = true;

        while (running) {
            System.out.println("\n--- Queue Menu ---");
            System.out.println("1. Enqueue (add to rear)");
            System.out.println("2. Dequeue (remove from front)");
            System.out.println("3. Peek (view front element)");
            System.out.println("4. Display queue");
            System.out.println("5. Check size");
            System.out.println("6. Check if empty");
            System.out.println("7. Exit");
            System.out.print("Choose an option: ");

            int choice;

            try {
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                // Clear the invalid token and prompt the user to try again
                scanner.nextLine();
                System.out.println("  Invalid option. Please enter a number between 1 and 7.");
                continue;
            }

            switch (choice) {

                case 1:
                    // Read the element to enqueue and add it to the rear of the queue
                    int element = readInt(scanner, "Enter element to enqueue: ");
                    linkedListQueue.enqueue(element);
                    linkedListQueue.display();
                    break;

                case 2:
                    // Dequeue the front element and handle the case where queue is empty
                    try {
                        linkedListQueue.dequeue();
                        linkedListQueue.display();
                    } catch (RuntimeException e) {
                        System.out.println("  Error: " + e.getMessage());
                    }
                    break;

                case 3:
                    // Peek at the front element without removing it
                    try {
                        System.out.println("Front element: " + linkedListQueue.peek());
                    } catch (RuntimeException e) {
                        System.out.println("  Error: " + e.getMessage());
                    }
                    break;

                case 4:
                    // Display all current elements in the queue from front to rear
                    linkedListQueue.display();
                    break;

                case 5:
                    // Display the current number of elements in the queue
                    System.out.println("Queue size: " + linkedListQueue.size());
                    break;

                case 6:
                    // Check and display whether the queue is currently empty
                    System.out.println("Queue is " + (linkedListQueue.isEmpty() ? "empty." : "not empty."));
                    break;

                case 7:
                    // Exit the menu loop
                    running = false;
                    System.out.println("Exiting.");
                    break;

                default:
                    System.out.println("  Invalid option. Please choose between 1 and 7.");
            }
        }

        // Close the scanner to release system resources
        scanner.close();
    }
}