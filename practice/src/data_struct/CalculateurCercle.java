package data_struct;
import java.util.Locale;
import java.util.Scanner;

public class CalculateurCercle {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        scanner.useLocale(Locale.US);  // Accepte le point comme séparateur


        boolean continuer = true;
        while (continuer) {
            System.out.print("Entrer le rayon du cercle : ");
            double rayon = scanner.nextDouble();


            if (rayon >= 0) {
                double perimetre = 2 * Math.PI * rayon;
                double aire = Math.PI * Math.pow(rayon, 2);
                System.out.printf("\nLe rayon vous avez choisi r = %.2f\n", rayon);
                System.out.printf("Le périmètre du cercle P = %.2f\n", perimetre);
                System.out.printf("L'aire du cercle A = %.2f\n", aire);
                System.out.print("Voulez-vous calculer un autre cercle ? (oui/non) : ");
                String reponse = scanner.next();
                if (reponse.equalsIgnoreCase("non") || reponse.equalsIgnoreCase("n")) {
                    continuer = false;
                }
            } else {
                System.out.println("Erreur : La valeur du rayon doit etre POSITIVE ");
            }
        }
        System.out.print("Merci");
        scanner.close();
    }
}
