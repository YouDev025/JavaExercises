package basics;

import java.util.Scanner;
public class Calculatrice {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        char continuer;

        do {
            // Saisie des nombres
            System.out.print("Donner une valeur A : ");
            int A = scanner.nextInt();
            System.out.print("Donner une valeur B : ");
            int B = scanner.nextInt();

            // Saisie de l'opération
            System.out.print("Choisissez une opération (+, -, *, /, %) : ");
            char op = scanner.next().charAt(0);

            // Calcul avec switch
            switch (op) {
                case '+':
                    System.out.println("La somme de A + B = " + (A + B));
                    break;
                case '-':
                    System.out.println("La soustraction de A - B = " + (A - B));
                    break;
                case '*':
                    System.out.println("La multiplication de A * B = " + (A * B));
                    break;
                case '/':
                    if (B != 0)
                        System.out.println("La division de A / B = " + ((double) A / B));
                    else
                        System.out.println("Erreur : division par zéro impossible !");
                    break;
                case '%':
                    if (B != 0)
                        System.out.println("Le reste de la division A % B = " + (A % B));
                    else
                        System.out.println("Erreur : division par zéro impossible !");
                    break;
                default:
                    System.out.println("Opération invalide ! Veuillez choisir +, -, *, / ou %.");
            }

            // Demander si l'utilisateur veut continuer
            System.out.print("Voulez-vous recalculer ? (O/N) : ");
            continuer = scanner.next().charAt(0);

        } while (continuer == 'O' || continuer == 'o');

        System.out.println("Merci d'avoir utilisé la calculatrice !");
        scanner.close(); // Bonne pratique : fermer le scanner
    }
}