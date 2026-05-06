package data_struct;

import java.util.Scanner;

// Class that implements a dynamic resizable array similar to ArrayList
// The array doubles in size whenever it reaches full capacity
// Time Complexity: add O(1) amortized | get/set O(1) | remove O(n)
public class DynamicArray {

    // Internal array that holds the elements
    private int[] data;

    // Tracks the number of elements currently stored in the dynamic array
    private int size;

    // Tracks the total number of elements the internal array can hold before resizing
    private int capacity;

    // Constructor to initialize the dynamic array with a default capacity of 2
    public DynamicArray() {
        capacity = 2;
        data = new int[capacity];
        size = 0;
    }

    // Adds a new element to the end of the dynamic array
    // If the array is full, it resizes before inserting
    public void add(int element) {

        // Check if the array has reached its capacity
        if (size == capacity) {
            resize();
        }

        // Place the new element at the next available position and increment size
        data[size++] = element;
    }

    // Doubles the capacity of the internal array and copies all existing elements
    // This ensures that resizing happens infrequently as the array grows
    private void resize() {

        // Double the current capacity
        capacity = capacity * 2;

        // Create a new array with the updated capacity
        int[] newData = new int[capacity];

        // Copy all existing elements from the old array to the new array
        for (int i = 0; i < size; i++) {
            newData[i] = data[i];
        }

        // Replace the old internal array with the new larger array
        data = newData;

        System.out.println("  -- resized to capacity: " + capacity + " --");
    }

    // Returns the element at the specified index
    // Throws an exception if the index is out of valid range
    public int get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds for size " + size);
        }
        return data[index];
    }

    // Replaces the element at the specified index with a new value
    // Throws an exception if the index is out of valid range
    public void set(int index, int element) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds for size " + size);
        }
        data[index] = element;
    }

    // Removes the element at the specified index
    // Shifts all subsequent elements one position to the left to fill the gap
    // Throws an exception if the index is out of valid range
    public void remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds for size " + size);
        }

        // Shift all elements after the removed index one position to the left
        for (int i = index; i < size - 1; i++) {
            data[i] = data[i + 1];
        }

        // Decrement size to reflect the removal
        size--;
    }

    // Returns the number of elements currently stored in the dynamic array
    public int size() {
        return size;
    }

    // Returns the current maximum capacity of the internal array
    public int capacity() {
        return capacity;
    }

    // Prints all elements currently stored in the dynamic array
    public void print() {
        System.out.print("Array: [");
        for (int i = 0; i < size; i++) {
            System.out.print(data[i]);
            if (i < size - 1) System.out.print(", ");
        }
        System.out.println("]  size: " + size + "  capacity: " + capacity);
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // Create a new dynamic array instance with initial capacity of 2
        DynamicArray dynArr = new DynamicArray();

        // Interactive menu loop to test all dynamic array operations
        boolean running = true;

        while (running) {
            System.out.println("\n--- Dynamic Array Menu ---");
            System.out.println("1. Add element");
            System.out.println("2. Get element by index");
            System.out.println("3. Set element by index");
            System.out.println("4. Remove element by index");
            System.out.println("5. Print array");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();

            switch (choice) {

                case 1:
                    // Read the element to add and append it to the array
                    System.out.print("Enter element to add: ");
                    int element = scanner.nextInt();
                    dynArr.add(element);
                    System.out.println(element + " added.");
                    dynArr.print();
                    break;

                case 2:
                    // Read the index and retrieve the element stored at that position
                    System.out.print("Enter index to get: ");
                    int getIndex = scanner.nextInt();
                    try {
                        System.out.println("Element at index " + getIndex + ": " + dynArr.get(getIndex));
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case 3:
                    // Read the index and new value, then update the element at that position
                    System.out.print("Enter index to set: ");
                    int setIndex = scanner.nextInt();
                    System.out.print("Enter new value: ");
                    int newValue = scanner.nextInt();
                    try {
                        dynArr.set(setIndex, newValue);
                        System.out.println("Index " + setIndex + " updated.");
                        dynArr.print();
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case 4:
                    // Read the index and remove the element at that position
                    System.out.print("Enter index to remove: ");
                    int removeIndex = scanner.nextInt();
                    try {
                        dynArr.remove(removeIndex);
                        System.out.println("Element at index " + removeIndex + " removed.");
                        dynArr.print();
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case 5:
                    // Display all current elements along with size and capacity
                    dynArr.print();
                    break;

                case 6:
                    // Exit the menu loop
                    running = false;
                    System.out.println("Exiting.");
                    break;

                default:
                    System.out.println("Invalid option. Please choose between 1 and 6.");
            }
        }

        // Close the scanner to release system resources
        scanner.close();
    }
}