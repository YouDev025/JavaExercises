package basics;

import java.util.Scanner;

public class LoopsI_TableMultiplication {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Donner un nombre : ");
        int N = sc.nextInt();

        for (int i = 1; i <= 10; i++) {
            System.out.println(N + " x " + i + " = " + (N * N));
        }

        sc.close();
    }
}
