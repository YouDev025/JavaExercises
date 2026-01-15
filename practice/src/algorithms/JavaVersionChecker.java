package algorithms;
public class JavaVersionChecker {
    public static void main(String[] args) {
        System.out.println("=== Vérification de l'installation de Java ===\n");

        // Afficher la version de Java
        String javaVersion = System.getProperty("java.version");
        System.out.println("Version de Java : " + javaVersion);

        // Afficher le fournisseur de Java
        String javaVendor = System.getProperty("java.vendor");
        System.out.println("Fournisseur : " + javaVendor);

        // Afficher le chemin d'installation de Java
        String javaHome = System.getProperty("java.home");
        System.out.println("Chemin d'installation (JAVA_HOME) : " + javaHome);

        // Afficher la version du JRE
        String javaRuntimeVersion = System.getProperty("java.runtime.version");
        System.out.println("Version du Runtime : " + javaRuntimeVersion);

        // Afficher le nom du système d'exploitation
        String osName = System.getProperty("os.name");
        System.out.println("Système d'exploitation : " + osName);

        // Afficher l'architecture
        String osArch = System.getProperty("os.arch");
        System.out.println("Architecture : " + osArch);

        System.out.println("\n✓ Java est correctement installé et fonctionnel !");
    }
}