package algorithms;
import java.util.Scanner;
import java.util.HashMap;

public class TwoSum {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Étape 1 : Demander la taille du tableau
        System.out.println("=== EXERCICE TWO SUM ===");
        System.out.println();

        int taille = 0;
        boolean tailleValide = false;

        while (!tailleValide) {
            System.out.print("Entrez la taille du tableau (minimum 2) : ");

            if (scanner.hasNextInt()) {
                taille = scanner.nextInt();

                if (taille >= 2) {
                    tailleValide = true;
                } else {
                    System.out.println("Erreur : La taille doit être au moins 2 !");
                }
            } else {
                System.out.println("Erreur : Veuillez entrer un nombre entier !");
                scanner.next(); // consommer l'entrée invalide
            }
        }

        // Étape 2 : Remplir le tableau
        int[] nums = new int[taille];
        System.out.println();
        System.out.println("Entrez les " + taille + " éléments du tableau :");

        for (int i = 0; i < taille; i++) {
            boolean elementValide = false;

            while (!elementValide) {
                System.out.print("Élément [" + i + "] : ");

                if (scanner.hasNextInt()) {
                    nums[i] = scanner.nextInt();
                    elementValide = true;
                } else {
                    System.out.println("Erreur : Veuillez entrer un nombre entier !");
                    scanner.next();
                }
            }
        }

        // Étape 3 : Demander la cible (target)
        int target = 0;
        boolean targetValide = false;

        System.out.println();
        while (!targetValide) {
            System.out.print("Entrez la valeur cible (target) : ");

            if (scanner.hasNextInt()) {
                target = scanner.nextInt();
                targetValide = true;
            } else {
                System.out.println("Erreur : Veuillez entrer un nombre entier !");
                scanner.next();
            }
        }

        // Étape 4 : Afficher les données entrées
        System.out.println();
        System.out.println("=== DONNÉES ENTRÉES ===");
        System.out.print("Tableau : [");
        for (int i = 0; i < nums.length; i++) {
            System.out.print(nums[i]);
            if (i < nums.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
        System.out.println("Target : " + target);
        System.out.println();

        // Étape 5 : Résoudre avec l'algorithme Two Sum
        System.out.println("=== RECHERCHE EN COURS ===");
        int[] resultat = twoSum(nums, target);

        // Étape 6 : Afficher le résultat
        System.out.println();
        if (resultat != null) {
            System.out.println("✓ SOLUTION TROUVÉE !");
            System.out.println("Indices : [" + resultat[0] + ", " + resultat[1] + "]");
            System.out.println("Valeurs : " + nums[resultat[0]] + " + " + nums[resultat[1]] + " = " + target);
        } else {
            System.out.println("✗ Aucune solution trouvée.");
        }

        scanner.close();
    }

    /**
     * Algorithme Two Sum optimisé avec HashMap
     * Complexité temporelle : O(n)
     * Complexité spatiale : O(n)
     */
    public static int[] twoSum(int[] nums, int target) {
        // Vérification si le tableau est valide
        if (nums == null || nums.length < 2) {
            return null;
        }

        // HashMap pour stocker : valeur -> indice
        HashMap<Integer, Integer> map = new HashMap<>();

        // Parcourir le tableau
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];

            System.out.println("Étape " + (i + 1) + " : nums[" + i + "] = " + nums[i] +
                    ", complément recherché = " + complement);

            // Vérifier si le complément existe déjà dans la HashMap
            if (map.containsKey(complement)) {
                System.out.println("  → Complément trouvé à l'indice " + map.get(complement));
                return new int[] {map.get(complement), i};
            } else {
                // Ajouter l'élément courant dans la HashMap
                map.put(nums[i], i);
                System.out.println("  → Ajout de " + nums[i] + " dans la HashMap");
            }
        }

        // Aucune solution trouvée
        return null;
    }
}