package data_struct;
import java.util.Scanner;
public class AsciiValue  {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("BIENVENUE DANS LE PROGRAMME DES AsciiValue !\n");
        boolean continuer = true;
        while (continuer) {
            System.out.print("Entrez votre caractère ici : ");
            char caractere = scanner.next().charAt(0);
            int ascii = (int)caractere;
            System.out.print("Le ASCII de "+caractere+" est : "+ascii);
            System.out.print("\nVoulez-vous connaître d'autres ASCII (oui/non) : ");
            String reponse = scanner.next();
            if (reponse.equalsIgnoreCase("non") || reponse.equalsIgnoreCase("n"))
            {
                continuer = false;
            }
        }
        scanner.close();
    }
}