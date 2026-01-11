package data_struct;
import java.util.Scanner;
public class MINArrayFinder {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("BIENVENUE DANS LE PROGRAMME MinArrayFinder !\n");
        boolean continuer = true;
        while (continuer) {
            System.out.print("Entrez le nombre de valeurs que vous voulez tester : ");
            int valeur = scanner.nextInt();
            int[] valeurs = new int[valeur];
            // Remplir le tableau d'abord
            for (int i = 0; i < valeurs.length; i++) {
                System.out.print("Entrez la valeur Tab[" + i + "] = ");
                valeurs[i] = scanner.nextInt();
            }
            int min = valeurs[0];
            for (int i = 1; i < valeurs.length; i++) {
                if (valeurs[i] < min) {
                    min = valeurs[i];
                }
            }
            System.out.print("La valeur minimum parmi les valeurs que vous avez saisies est : " + min);
            System.out.print("\nVoulez-vous tester d'autres valeurs (oui/non) : ");
            String reponse = scanner.next();
            if (reponse.equalsIgnoreCase("non") || reponse.equalsIgnoreCase("n")){
                continuer = false;
            }

        }
        scanner.close();
    }
}