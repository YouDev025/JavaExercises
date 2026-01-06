package basics;

import java.util.Arrays;
import java.util.Scanner;

public class TemperatureAnalyzer {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // Déclaration des tableaux
        String[] jours = {"Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi", "Dimanche"};
        int[] temperatures = new int[7];

        // ===== SAISIE DES TEMPÉRATURES =====
        System.out.println("=== SAISIE DES TEMPÉRATURES DE LA SEMAINE ===");
        System.out.println();

        for (int i = 0; i < jours.length; i++) {
            System.out.print("Température du " + jours[i] + " (en °C) : ");
            temperatures[i] = scanner.nextInt();
        }

        System.out.println();
        System.out.println("Merci ! Analyse en cours...");
        System.out.println();

        // ===== SECTION 1 : AFFICHAGE DES TEMPÉRATURES =====
        System.out.println("=== ANALYSE DES TEMPÉRATURES ===");
        for (int i = 0; i < jours.length; i++) {
            System.out.printf("%-8s : %d°C%n", jours[i], temperatures[i]);
        }
        System.out.println();

        // ===== SECTION 2 : CALCULS STATISTIQUES =====

        // Calcul de la moyenne
        int somme = 0;
        for (int temp : temperatures) {
            somme += temp;
        }
        double moyenne = (double) somme / temperatures.length;
        System.out.printf("Température moyenne : %.1f°C%n", moyenne);

        // Trouver la température maximale
        int tempMax = temperatures[0];
        int indexMax = 0;
        for (int i = 1; i < temperatures.length; i++) {
            if (temperatures[i] > tempMax) {
                tempMax = temperatures[i];
                indexMax = i;
            }
        }
        System.out.printf("Température max : %d°C (%s)%n", tempMax, jours[indexMax]);

        // Trouver la température minimale
        int tempMin = temperatures[0];
        int indexMin = 0;
        for (int i = 1; i < temperatures.length; i++) {
            if (temperatures[i] < tempMin) {
                tempMin = temperatures[i];
                indexMin = i;
            }
        }
        System.out.printf("Température min : %d°C (%s)%n", tempMin, jours[indexMin]);

        // Compter les jours au-dessus de la moyenne
        int joursAuDessus = 0;
        for (int temp : temperatures) {
            if (temp > moyenne) {
                joursAuDessus++;
            }
        }
        System.out.println("Jours au-dessus de la moyenne : " + joursAuDessus);
        System.out.println();

        // ===== SECTION 3 : GRAPHIQUE =====
        System.out.println("=== GRAPHIQUE ===");
        for (int i = 0; i < jours.length; i++) {
            System.out.printf("%-8s : ", jours[i]);
            // Afficher autant d'étoiles que la température
            for (int j = 0; j < temperatures[i]; j++) {
                System.out.print("*");
            }
            System.out.println();
        }
        System.out.println();

        // ===== SECTION 4 : TEMPÉRATURES INVERSÉES =====
        System.out.println("=== TEMPÉRATURES INVERSÉES ===");
        for (int i = temperatures.length - 1; i >= 0; i--) {
            System.out.printf("%-8s : %d°C%n", jours[i], temperatures[i]);
        }
        System.out.println();

        // ===== SECTION 5 : TEMPÉRATURES TRIÉES =====
        System.out.println("=== TEMPÉRATURES TRIÉES ===");
        // Créer une copie pour ne pas modifier l'original
        int[] temperaturesCopie = Arrays.copyOf(temperatures, temperatures.length);
        // Trier la copie
        Arrays.sort(temperaturesCopie);

        // Afficher avec des virgules
        for (int i = 0; i < temperaturesCopie.length; i++) {
            System.out.print(temperaturesCopie[i] + "°C");
            if (i < temperaturesCopie.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println();

        // Fermer le scanner
        scanner.close();
    }
}
