package basics;

import java.util.Scanner;

public class LoopsII_Series {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int t = in.nextInt();

        for (int i = 0; i < t; i++) {
            int a = in.nextInt();
            int b = in.nextInt();
            int n = in.nextInt();

            int sum = a;
            int term = b;

            for (int j = 0; j < n; j++) {
                sum += term;
                System.out.print(sum + " ");
                term *= 2;
            }
            System.out.println();
        }

        in.close();
    }
}
