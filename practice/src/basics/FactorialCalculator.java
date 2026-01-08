package basics;

import java.util.Locale;
import java.util.Scanner;

public class FactorialCalculator {
    public static void main(String[] args) {
        // Initialisation du scanner pour lire les entrées utilisateur
        Scanner scanner = new Scanner(System.in);
        // Configuration pour accepter les décimales avec un point
        scanner.useLocale(Locale.US);
        // Variable de contrôle pour la boucle
        boolean continuer = true;

        // Boucle principale du programme
        while (continuer) {
            System.out.println("\n============================================================");
            System.out.println("   Programme Java pour calculer la factorielle d'un nombre");
            System.out.println("============================================================\n");

            System.out.print("Entrez un nombre entier positif : ");

            // Vérifier si l'entrée est bien un entier
            if (!scanner.hasNextInt()) {
                System.out.println("Erreur : Veuillez entrer un nombre entier valide et positive (0 , 1 , 2 , 3 .... )!");
                scanner.next(); // Vider l'entrée incorrecte
                continue; // Retour au début de la boucle
            }

            int n = scanner.nextInt();

            // Vérification que le nombre est positif ou nul
            if (n < 0) {
                System.out.println("Erreur : Veuillez choisir une valeur positive ou nulle (0 , 1 , 2 , 3 .... )!");
            }
            // Cas particuliers : 0! = 1 et 1! = 1
            else if (n == 0 || n == 1) {
                System.out.println("Le factorielle de " + n + "! = 1");
            }
            // Calcul de la factorielle pour n >= 2
            else {
                int fact = 1;
                // Boucle pour multiplier tous les nombres de 1 à n
                for (int i = 1; i <= n; i++) {
                    fact *= i;
                }
                System.out.println("Le factorielle de " + n + "! = " + fact);
            }

            // Demander si l'utilisateur veut continuer
            System.out.print("\nVoulez-vous calculer une autre factorielle ? (oui/non) : ");
            String reponse = scanner.next();

            // Vérifier la réponse pour quitter ou continuer
            if (reponse.equalsIgnoreCase("non") || reponse.equalsIgnoreCase("n")) {
                continuer = false;
                System.out.println("\nMerci d'avoir utilisé le programme. Au revoir !");
            }
        }

        // Fermeture du scanner pour libérer les ressources
        scanner.close();
    }
}