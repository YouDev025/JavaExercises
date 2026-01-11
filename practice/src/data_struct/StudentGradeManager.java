package data_struct;

import java.util.Scanner;

public class StudentGradeManager {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Vérification du nombre d'étudiants (entre 1 et 20)
        int n;
        do {
            System.out.print("Entrez le nombre d'étudiants (1-20) : ");
            n = sc.nextInt();
            if (n <= 0 || n > 20) {
                System.out.println("⚠️ Nombre invalide, veuillez entrer un nombre entre 1 et 20 !");
            }
        } while (n <= 0 || n > 20);

        sc.nextLine(); // consommer le saut de ligne

        String[] noms = new String[n];
        double[] notes = new double[n];

        // Saisie des noms et notes avec validation
        for (int i = 0; i < n; i++) {
            System.out.print("Entrez le nom de l'étudiant " + (i+1) + " : ");
            noms[i] = sc.nextLine();

            double note;
            do {
                System.out.print("Entrez la note de " + noms[i] + " (≤20) : ");
                note = sc.nextDouble();
                if (note > 20) {
                    System.out.println("⚠️ Note invalide, elle ne doit pas dépasser 20 !");
                }
            } while (note > 20);

            notes[i] = note;
            sc.nextLine(); // consommer le saut de ligne
        }

        // Variables pour statistiques
        double somme = 0;
        double max = notes[0];
        double min = notes[0];
        int indexMax = 0;
        int indexMin = 0;
        int admis = 0;

        System.out.println("\n=== GESTION DES NOTES ===");

        // Traitement
        for (int i = 0; i < n; i++) {
            somme += notes[i];

            if (notes[i] >= 10) {
                admis++;
            }

            if (notes[i] > max) {
                max = notes[i];
                indexMax = i;
            }

            if (notes[i] < min) {
                min = notes[i];
                indexMin = i;
            }

            String mention;
            if (notes[i] >= 16) {
                mention = "Excellent";
            } else if (notes[i] >= 14) {
                mention = "Bien";
            } else if (notes[i] >= 12) {
                mention = "Assez bien";
            } else if (notes[i] >= 10) {
                mention = "Passable";
            } else {
                mention = "Insuffisant";
            }

            System.out.printf("%s : %.1f/20 - %s\n", noms[i], notes[i], mention);
        }

        // Affichage des statistiques
        double moyenne = somme / n;
        System.out.printf("\nMoyenne de la classe : %.1f\n", moyenne);
        System.out.println("Meilleure note : " + max + " (" + noms[indexMax] + ")");
        System.out.println("Note la plus basse : " + min + " (" + noms[indexMin] + ")");


        // Afficher les étudiants réussis et non réussis
        System.out.println("\n--- Étudiants ayant réussi ---");
        for (int i = 0; i < n; i++) {
            if (notes[i] >= 10) {
                System.out.println(noms[i] + " (" + notes[i] + "/20)");
            }
        }

        System.out.println("\n--- Étudiants non admis ---");
        for (int i = 0; i < n; i++) {
            if (notes[i] < 10) {
                System.out.println(noms[i] + " (" + notes[i] + "/20)");
            }
        }

        sc.close();
    }
}
