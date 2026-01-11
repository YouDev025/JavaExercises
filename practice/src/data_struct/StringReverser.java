package data_struct;
import java.util.Scanner;
public class StringReverser {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean continuer = true;
        while (continuer) {
            System.out.println("---------- Bonjour dans le programme d'inversion des chaînes de caractères : ----------");
            System.out.print("Entrez votre chaîne de caractères ici : ");
            String str = scanner.next();
            StringBuilder res = new StringBuilder();
            for (int i = str.length() - 1; i >= 0; i--) {
                res.append(str.charAt(i));
            }
            System.out.print("Votre chaîne de caractères inversée est : " + res);

            System.out.print("\nVoulez-vous inverser d'autres chaînes (oui/non) : ");
            String reponse = scanner.next();
            if(reponse.equalsIgnoreCase("non") || reponse.equalsIgnoreCase("n")) {
                continuer = false;
            }
        }
        scanner.close();
    }
}