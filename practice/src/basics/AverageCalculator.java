package basics;

import java.util.Locale;
import java.util.Scanner;

public class AverageCalculator {
    public static void main(String[] args) {
        // Créer un scanner pour lire les entrées de l'utilisateur
        Scanner scanner = new Scanner(System.in);
        // Utiliser le point comme séparateur décimal
        scanner.useLocale(Locale.US);
/*
        -------------- La Méthode 1 -------------------
        System.out.print("Entrez le premier nombre : ");
        double a = scanner.nextDouble();
        System.out.print("Entrez le deuxième nombre : ");
        double b = scanner.nextDouble();
        System.out.print("Entrez le troisième nombre : ");
        double c = scanner.nextDouble();
        double somme = a + b + c;
        double moyenne = somme / 3;
        System.out.printf("La moyenne est : %.2f ", moyenne);
*/
//      -------------- La Méthode 2 -------------------
        // Variable pour contrôler la boucle principale
        boolean continuer = true;

        // Boucle pour permettre plusieurs calculs
        while (continuer) {
            // Demander le nombre de valeurs
            System.out.print("Entrez le nombre de valeurs dont vous voulez calculer la moyenne : ");
            int n = scanner.nextInt();

            // Vérifier que le nombre est valide
            if (n <= 0) {
                System.out.print("Veuillez choisir un nombre supérieur à 0 : ");
                n = scanner.nextInt();
            } else {
                // Créer un tableau pour stocker les valeurs
                float[] valeurs = new float[n];
                float somme = 0;

                // Lire chaque valeur et calculer la somme
                for (int i = 0; i < n; i++) {
                    System.out.print("Entrez la valeur " + (i + 1) + " : ");
                    valeurs[i] = scanner.nextFloat();
                    somme += valeurs[i];
                }

                // Calculer et afficher la moyenne
                float moyenne = somme / n;
                System.out.printf("La moyenne des %d valeurs est : %.2f\n", n, moyenne);

                // Demander si l'utilisateur veut continuer
                System.out.print("Voulez-vous calculer à nouveau d'autres valeurs (oui/non) : ");
                String reponse = scanner.next();

                // Vérifier la réponse
                if (reponse.equalsIgnoreCase("non") || reponse.equalsIgnoreCase("n")) {
                    continuer = false;
                    System.out.println("Merci ! Le programme est terminé.");
                }
            }
        }
        // Fermer le scanner
        scanner.close();
    }
}