package data_struct;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

// Class to sort an ArrayList of integers using Collections.sort()
// Demonstrates both natural ascending order and custom descending Comparator
public class SortArrayList {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // Create a new ArrayList to hold integer elements
        ArrayList<Integer> list = new ArrayList<>();

        // Prompt the user to enter the number of elements
        System.out.print("Enter number of elements: ");
        int n = scanner.nextInt();

        // Loop to read n integers from the user and add them to the ArrayList
        System.out.println("Enter " + n + " integers:");
        for (int i = 0; i < n; i++) {
            list.add(scanner.nextInt());
        }

        // Display the original unsorted list
        System.out.println("\nOriginal list:    " + list);

        // Sort the ArrayList in natural ascending order using Collections.sort()
        // This uses the natural ordering of Integer (smallest to largest)
        Collections.sort(list);
        System.out.println("Ascending order:  " + list);

        // Sort the ArrayList in descending order using a custom Comparator
        // The Comparator reverses the natural order by comparing b against a
        // instead of the usual a against b
        Collections.sort(list, new Comparator<Integer>() {
            @Override
            public int compare(Integer a, Integer b) {
                // Returning b - a places larger values before smaller ones
                return b - a;
            }
        });
        System.out.println("Descending order: " + list);

        // Sort using a lambda expression — a concise alternative to the anonymous Comparator class above
        // Both approaches produce the same descending result
        list.sort((a, b) -> a - b);
        System.out.println("Lambda ascending: " + list);

        // Reverse the list using Collections.reverse() as another approach
        // Useful when the list is already sorted and only needs to be flipped
        Collections.reverse(list);
        System.out.println("After reverse:    " + list);

        // Sort using Comparator.reverseOrder() — the most readable built-in approach
        // for descending order without writing any manual comparison logic
        list.sort(Comparator.reverseOrder());
        System.out.println("reverseOrder():   " + list);

        // Close the scanner to release system resources
        scanner.close();
    }
}