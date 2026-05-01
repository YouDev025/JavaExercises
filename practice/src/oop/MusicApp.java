package oop;
import java.util.Scanner;

// ═══════════════════════════════════════════════════════
//  INTERFACE
// ═══════════════════════════════════════════════════════
interface Playable {
    void play();                          // abstract by default
    void stop();                          // bonus method
    String getInstrumentName();           // identify the instrument
}

// ═══════════════════════════════════════════════════════
//  GUITAR
// ═══════════════════════════════════════════════════════
class Guitar implements Playable {
    private String brand;
    private String guitarType;   // Acoustic / Electric / Bass

    public Guitar(String brand, String guitarType) {
        this.brand      = brand;
        this.guitarType = guitarType;
    }

    @Override
    public void play() {
        System.out.println("🎸  [" + brand + " Guitar] Strumming chords... ♩♪♫");
        System.out.println("    Type: " + guitarType);
    }

    @Override
    public void stop() {
        System.out.println("🎸  [" + brand + " Guitar] Strings fading out... 🤫");
    }

    @Override
    public String getInstrumentName() {
        return brand + " " + guitarType + " Guitar";
    }
}

// ═══════════════════════════════════════════════════════
//  PIANO
// ═══════════════════════════════════════════════════════
class Piano implements Playable {
    private String brand;
    private String pianoType;    // Grand / Upright / Digital

    public Piano(String brand, String pianoType) {
        this.brand     = brand;
        this.pianoType = pianoType;
    }

    @Override
    public void play() {
        System.out.println("🎹  [" + brand + " Piano] Playing keys... ♩♪♫");
        System.out.println("    Type: " + pianoType);
    }

    @Override
    public void stop() {
        System.out.println("🎹  [" + brand + " Piano] Notes fading out... 🤫");
    }

    @Override
    public String getInstrumentName() {
        return brand + " " + pianoType + " Piano";
    }
}

// ═══════════════════════════════════════════════════════
//  MAIN
// ═══════════════════════════════════════════════════════
public class MusicApp {

    static void header(String title, String emoji) {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.printf ("║  %s  %-32s║%n", emoji, title);
        System.out.println("╚══════════════════════════════════════╝");
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║       🎵  MUSIC INSTRUMENT APP       ║");
        System.out.println("╚══════════════════════════════════════╝\n");

        // ── User info ─────────────────────────────────────────
        System.out.println("👤  Your information:");
        System.out.print("   Name : ");
        String name = sc.nextLine();

        System.out.println("\nWelcome, " + name + "! 🎶 Let's set up your instruments.\n");

        // ════════════════════
        //  GUITAR input
        // ════════════════════
        header("GUITAR SETUP", "🎸");
        System.out.print("  Brand : ");
        String gBrand = sc.nextLine();

        System.out.println("  Type  : 1) Acoustic   2) Electric   3) Bass");
        System.out.print("  Choice (1/2/3) : ");
        String gChoice = sc.nextLine().trim();
        String gType = switch (gChoice) {
            case "1" -> "Acoustic";
            case "2" -> "Electric";
            case "3" -> "Bass";
            default  -> "Acoustic";
        };

        Guitar myGuitar = new Guitar(gBrand, gType);

        // ════════════════════
        //  PIANO input
        // ════════════════════
        header("PIANO SETUP", "🎹");
        System.out.print("  Brand : ");
        String pBrand = sc.nextLine();

        System.out.println("  Type  : 1) Grand   2) Upright   3) Digital");
        System.out.print("  Choice (1/2/3) : ");
        String pChoice = sc.nextLine().trim();
        String pType = switch (pChoice) {
            case "1" -> "Grand";
            case "2" -> "Upright";
            case "3" -> "Digital";
            default  -> "Grand";
        };

        Piano myPiano = new Piano(pBrand, pType);

        // ════════════════════
        //  CONCERT — play!
        // ════════════════════
        header("🎼  CONCERT TIME", "🎵");
        System.out.println("\n  Instruments registered:");
        System.out.println("   • " + myGuitar.getInstrumentName());
        System.out.println("   • " + myPiano.getInstrumentName());

        // ── Polymorphism: both stored as Playable ────────────
        Playable[] band = { myGuitar, myPiano };

        System.out.println("\n▶️   Playing all instruments...\n");
        for (Playable instrument : band) {
            instrument.play();
            System.out.println();
        }

        System.out.println("⏹️   Stopping all instruments...\n");
        for (Playable instrument : band) {
            instrument.stop();
        }

        System.out.println("\n✅  Concert over! Thanks for playing, " + name + " 🎉");
        sc.close();
    }
}