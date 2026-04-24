package functions;
import java.util.Scanner;

public class VowelConsonantCounter {

    public static int[] countVowelsAndConsonants(String str) {
        int vowels = 0;
        int consonants = 0;

        str = str.toLowerCase();

        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);

            if (ch >= 'a' && ch <= 'z') {
                if (ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u') {
                    vowels++;
                } else {
                    consonants++;
                }
            }
        }

        return new int[]{vowels, consonants};
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a string: ");
        String input = scanner.nextLine();

        int[] result = countVowelsAndConsonants(input);

        System.out.println("\nResults for: \"" + input + "\"");
        System.out.println("Vowels:     " + result[0]);
        System.out.println("Consonants: " + result[1]);

        scanner.close();
    }
}