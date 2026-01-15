package algorithms;
import java.util.Scanner;

public class BinaryToDecimal {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String binary;
        boolean valid = false;

        // Boucle jusqu'à ce que l'utilisateur entre un binaire correct
        while (!valid) {
            System.out.print("Entrez un nombre binaire : ");
            binary = scanner.nextLine();

            // Vérification : uniquement des 0 et 1
            if (binary.matches("[01]+")) {
                // Conversion en décimal
                int decimal = Integer.parseInt(binary, 2);
                System.out.println("Le nombre décimal est : " + decimal);
                valid = true; // sortie de la boucle
            } else {
                System.out.println("Erreur : '" + binary + "' n'est pas un nombre binaire valide !");
            }
        }

        scanner.close();
    }
}
