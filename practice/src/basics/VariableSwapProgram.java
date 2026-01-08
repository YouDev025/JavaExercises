package basics;

import java.util.Locale;
import java.util.Scanner;

public class VariableSwapProgram {
    public static void main(String[] args) {
        // Initialisation du scanner pour lire les entrées utilisateur
        Scanner scanner = new Scanner(System.in);
        // Configuration pour accepter les décimales avec un point (ex: 3.14)
        scanner.useLocale(Locale.US);
        // Variable de contrôle pour la boucle
        boolean continuer = true;

        // Boucle principale du programme
        while (continuer) {
            // Affichage du titre
            System.out.println("\n============================================================");
            System.out.println("   Programme Java pour permuter deux variables");
            System.out.println("============================================================\n");

            // Lecture de la première valeur
            System.out.print("Entrez la 1ère valeur : ");
            double a = scanner.nextDouble();

            // Lecture de la deuxième valeur
            System.out.print("Entrez la 2ème valeur : ");
            double b = scanner.nextDouble();

            // Affichage des valeurs avant permutation
            System.out.println("\n--- Avant permutation ---");
            System.out.println("Variable 1 : " + a);
            System.out.println("Variable 2 : " + b);

            // Permutation des valeurs avec une variable temporaire
            double temp = a;  // Sauvegarde de la valeur de 'a'
            a = b;            // 'a' prend la valeur de 'b'
            b = temp;         // 'b' prend l'ancienne valeur de 'a'

            // Affichage des valeurs après permutation
            System.out.println("\n--- Après permutation ---");
            System.out.println("Variable 1 : " + a);
            System.out.println("Variable 2 : " + b);

            // Demander à l'utilisateur s'il veut continuer
            System.out.print("\nVoulez-vous continuer ? (oui/non) : ");
            String reponse = scanner.next();

            // Vérifier la réponse (accepte "non" ou "n", majuscules ou minuscules)
            if (reponse.equalsIgnoreCase("non") || reponse.equalsIgnoreCase("n")) {
                continuer = false;
                System.out.println("\nMerci d'avoir utilisé le programme. Au revoir !");
            }
        }

        // Fermeture du scanner pour libérer les ressources
        scanner.close();
    }
}