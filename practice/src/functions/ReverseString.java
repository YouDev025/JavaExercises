package functions;
import java.util.Scanner;

public class ReverseString {

    public static String reverse(String str) {
        char[] arr = str.toCharArray();

        int left = 0;
        int right = arr.length - 1;

        while (left < right) {
            char temp = arr[left];
            arr[left] = arr[right];
            arr[right] = temp;

            left++;
            right--;
        }

        return new String(arr);
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter a string: ");
        String userInput = input.nextLine();

        String reversed = reverse(userInput);
        System.out.println("Reversed string: " + reversed);

        input.close();
    }
}