package basics;

import java.util.Scanner;

public class EOFReader {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Demander un entier
        System.out.print("Entrez un entier : ");
        int entier = scanner.nextInt();

        // Demander un nombre décimal
        System.out.print("Entrez un nombre décimal : ");
        double decimal = scanner.nextDouble();

        // Demander une chaîne de caractères
        scanner.nextLine(); // vider le buffer
        System.out.print("Entrez votre nom : ");
        String nom = scanner.nextLine();

        // Afficher les valeurs saisies
        System.out.println("\n--- Résultats ---");
        System.out.println("Entier = " + entier);
        System.out.println("Décimal = " + decimal);
        System.out.println("Nom = " + nom);

        scanner.close();
    }
}
