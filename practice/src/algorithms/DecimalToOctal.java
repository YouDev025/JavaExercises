package algorithms;
import java.util.Scanner;

public class DecimalToOctal {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int decimal = 0;
        boolean valid = false;

        // Boucle jusqu'à ce que l'utilisateur entre une valeur correcte
        while (!valid) {
            System.out.print("Entrez un nombre décimal : ");
            String input = scanner.nextLine();

            try {
                decimal = Integer.parseInt(input); // tentative de conversion
                valid = true; // si ça marche, on sort de la boucle
            } catch (NumberFormatException e) {
                System.out.println("Erreur : '" + input + "' n'est pas un entier valide !");
                // la boucle continue
            }
        }

        // Conversion en octal
        String octal = Integer.toOctalString(decimal);
        System.out.println("Le nombre octal est : " + octal);

        scanner.close();
    }
}
