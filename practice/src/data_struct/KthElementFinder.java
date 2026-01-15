package data_struct;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Collections;
import java.util.Scanner;

public class KthElementFinder {

    // Méthode 1: Utilisant le tri (Simple mais O(n log n))
    public static int kthSmallestBySort(int[] arr, int k) {
        if (k < 1 || k > arr.length) {
            throw new IllegalArgumentException("k doit être entre 1 et " + arr.length);
        }

        int[] sortedArr = arr.clone();
        Arrays.sort(sortedArr);
        return sortedArr[k - 1];
    }

    public static int kthLargestBySort(int[] arr, int k) {
        if (k < 1 || k > arr.length) {
            throw new IllegalArgumentException("k doit être entre 1 et " + arr.length);
        }

        int[] sortedArr = arr.clone();
        Arrays.sort(sortedArr);
        return sortedArr[arr.length - k];
    }

    // Méthode 2: Utilisant une file de priorité (Heap) - O(n log k)
    public static int kthSmallestByHeap(int[] arr, int k) {
        if (k < 1 || k > arr.length) {
            throw new IllegalArgumentException("k doit être entre 1 et " + arr.length);
        }

        // Max heap pour garder les k plus petits éléments
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());

        for (int num : arr) {
            maxHeap.offer(num);
            if (maxHeap.size() > k) {
                maxHeap.poll();
            }
        }

        return maxHeap.peek();
    }

    public static int kthLargestByHeap(int[] arr, int k) {
        if (k < 1 || k > arr.length) {
            throw new IllegalArgumentException("k doit être entre 1 et " + arr.length);
        }

        // Min heap pour garder les k plus grands éléments
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();

        for (int num : arr) {
            minHeap.offer(num);
            if (minHeap.size() > k) {
                minHeap.poll();
            }
        }

        return minHeap.peek();
    }

    // Méthode 3: Quickselect (Optimal - O(n) en moyenne)
    public static int kthSmallestByQuickSelect(int[] arr, int k) {
        if (k < 1 || k > arr.length) {
            throw new IllegalArgumentException("k doit être entre 1 et " + arr.length);
        }

        int[] workArr = arr.clone();
        return quickSelect(workArr, 0, workArr.length - 1, k - 1);
    }

    public static int kthLargestByQuickSelect(int[] arr, int k) {
        if (k < 1 || k > arr.length) {
            throw new IllegalArgumentException("k doit être entre 1 et " + arr.length);
        }

        int[] workArr = arr.clone();
        return quickSelect(workArr, 0, workArr.length - 1, workArr.length - k);
    }

    private static int quickSelect(int[] arr, int left, int right, int k) {
        if (left == right) {
            return arr[left];
        }

        int pivotIndex = partition(arr, left, right);

        if (k == pivotIndex) {
            return arr[k];
        } else if (k < pivotIndex) {
            return quickSelect(arr, left, pivotIndex - 1, k);
        } else {
            return quickSelect(arr, pivotIndex + 1, right, k);
        }
    }

    private static int partition(int[] arr, int left, int right) {
        int pivot = arr[right];
        int i = left;

        for (int j = left; j < right; j++) {
            if (arr[j] <= pivot) {
                swap(arr, i, j);
                i++;
            }
        }

        swap(arr, i, right);
        return i;
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    // Programme principal pour démonstration
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = 0;
        int[] tableau = null;
        int k = 0;

        try {
            // Demander la taille du tableau
            System.out.print("Entrez la taille du tableau: ");
            while (!scanner.hasNextInt()) {
                System.out.println("Erreur: Veuillez entrer un nombre entier valide.");
                System.out.print("Entrez la taille du tableau: ");
                scanner.next();
            }
            n = scanner.nextInt();

            if (n <= 0) {
                System.out.println("Erreur: La taille doit être supérieure à 0.");
                scanner.close();
                return;
            }

            tableau = new int[n];

            // Demander les éléments du tableau
            System.out.println("Entrez les " + n + " éléments du tableau:");
            for (int i = 0; i < n; i++) {
                System.out.print("Élément " + (i + 1) + ": ");
                while (!scanner.hasNextInt()) {
                    System.out.println("Erreur: Veuillez entrer un nombre entier valide.");
                    System.out.print("Élément " + (i + 1) + ": ");
                    scanner.next();
                }
                tableau[i] = scanner.nextInt();
            }

            // Demander la valeur de k
            System.out.print("\nEntrez la valeur de k (entre 1 et " + n + "): ");
            while (!scanner.hasNextInt()) {
                System.out.println("Erreur: Veuillez entrer un nombre entier valide.");
                System.out.print("Entrez la valeur de k (entre 1 et " + n + "): ");
                scanner.next();
            }
            k = scanner.nextInt();

            if (k < 1 || k > n) {
                System.out.println("Erreur: k doit être entre 1 et " + n);
                scanner.close();
                return;
            }

            System.out.println("\nTableau: " + Arrays.toString(tableau));
            System.out.println("k = " + k);
            System.out.println();

            // Test des différentes méthodes pour le kème plus petit
            System.out.println("=== " + k + "ème élément le plus PETIT ===");
            try {
                System.out.println("Méthode Tri: " + kthSmallestBySort(tableau, k));
                System.out.println("Méthode Heap: " + kthSmallestByHeap(tableau, k));
                System.out.println("Méthode QuickSelect: " + kthSmallestByQuickSelect(tableau, k));
            } catch (IllegalArgumentException e) {
                System.out.println("Erreur: " + e.getMessage());
            }
            System.out.println();

            // Test des différentes méthodes pour le kème plus grand
            System.out.println("=== " + k + "ème élément le plus GRAND ===");
            try {
                System.out.println("Méthode Tri: " + kthLargestBySort(tableau, k));
                System.out.println("Méthode Heap: " + kthLargestByHeap(tableau, k));
                System.out.println("Méthode QuickSelect: " + kthLargestByQuickSelect(tableau, k));
            } catch (IllegalArgumentException e) {
                System.out.println("Erreur: " + e.getMessage());
            }
            System.out.println();

            // Vérification avec le tableau trié
            int[] triee = tableau.clone();
            Arrays.sort(triee);
            System.out.println("Tableau trié: " + Arrays.toString(triee));
            System.out.println("(pour vérification)");

        } catch (Exception e) {
            System.out.println("Une erreur s'est produite: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}