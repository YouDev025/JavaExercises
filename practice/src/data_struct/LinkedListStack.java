package data_struct;

import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Scanner;

// Class to simulate a Stack using Java's LinkedList
// Stack follows LIFO (Last In First Out) principle
// Push adds to the top, Pop removes from the top
// Time Complexity: push O(1) | pop O(1) | peek O(1)
public class LinkedListStack {

    // The LinkedList acts as the underlying data structure for the stack
    // The top of the stack is the head of the LinkedList
    private LinkedList<Integer> stack = new LinkedList<>();

    // Adds a new element to the top of the stack
    // addFirst() inserts at the head of the LinkedList
    public void push(int element) {
        stack.addFirst(element);
        System.out.println(element + " pushed to top of stack.");
    }

    // Removes and returns the element at the top of the stack
    // removeFirst() extracts from the head of the LinkedList
    // Throws an exception if the stack is empty before attempting removal
    public int pop() {
        if (isEmpty()) {
            throw new RuntimeException("Stack is empty. Cannot pop.");
        }
        int removed = stack.removeFirst();
        System.out.println(removed + " popped from top of stack.");
        return removed;
    }

    // Returns the element at the top of the stack without removing it
    // Throws an exception if the stack is empty before attempting peek
    public int peek() {
        if (isEmpty()) {
            throw new RuntimeException("Stack is empty. Cannot peek.");
        }
        return stack.getFirst();
    }

    // Returns true if the stack contains no elements
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    // Returns the number of elements currently in the stack
    public int size() {
        return stack.size();
    }

    // Displays all elements in the stack from top to bottom
    public void display() {
        if (isEmpty()) {
            System.out.println("Stack is empty.");
            return;
        }
        System.out.println("Stack (top -> bottom): " + stack);
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

        // Create a new LinkedListStack instance
        LinkedListStack linkedListStack = new LinkedListStack();

        // Interactive menu loop to test all stack operations
        boolean running = true;

        while (running) {
            System.out.println("\n--- Stack Menu ---");
            System.out.println("1. Push (add to top)");
            System.out.println("2. Pop  (remove from top)");
            System.out.println("3. Peek (view top element)");
            System.out.println("4. Display stack");
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
                    // Read the element to push and add it to the top of the stack
                    int element = readInt(scanner, "Enter element to push: ");
                    linkedListStack.push(element);
                    linkedListStack.display();
                    break;

                case 2:
                    // Pop the top element and handle the case where stack is empty
                    try {
                        linkedListStack.pop();
                        linkedListStack.display();
                    } catch (RuntimeException e) {
                        System.out.println("  Error: " + e.getMessage());
                    }
                    break;

                case 3:
                    // Peek at the top element without removing it
                    try {
                        System.out.println("Top element: " + linkedListStack.peek());
                    } catch (RuntimeException e) {
                        System.out.println("  Error: " + e.getMessage());
                    }
                    break;

                case 4:
                    // Display all current elements in the stack from top to bottom
                    linkedListStack.display();
                    break;

                case 5:
                    // Display the current number of elements in the stack
                    System.out.println("Stack size: " + linkedListStack.size());
                    break;

                case 6:
                    // Check and display whether the stack is currently empty
                    System.out.println("Stack is " + (linkedListStack.isEmpty() ? "empty." : "not empty."));
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