import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 * Steuerungsklasse für den Konsolen-Workflow des Bücherkatalogs.
 */
public class Controller implements ApplicationInterface {
    private final Buchkatalog buchkatalog;

    public Controller() {
        StorageService storageService = new StorageService();
        this.buchkatalog = new Buchkatalog(storageService);
    }

    @Override
    public List<Buch> buchsuche(String titel) {
        return buchkatalog.buchsuche(titel);
    }

    @Override
    public List<Buch> sucheNachAutor(String autorName) {
        return buchkatalog.sucheNachAutor(autorName);
    }

    @Override
    public List<Buch> sucheNachGenre(String genreName) {
        return buchkatalog.sucheNachGenre(genreName);
    }

    @Override
    public List<Buch> sucheNachVerlag(String verlagName) {
        return buchkatalog.sucheNachVerlag(verlagName);
    }

    @Override
    public Buch buchdetails(String bookId) {
        return buchkatalog.buchDetails(bookId);
    }

    @Override
    public List<Rezension> showRezensionen(String bookId) {
        return buchkatalog.showRezensionen(bookId);
    }

    @Override
    public void reviewHinzufuegen(String bookId, Rezension rezension) {
        buchkatalog.reviewHinzufuegen(bookId, rezension);
    }

    public void runConsole() {
        Scanner sc = new Scanner(System.in);
        System.out.println("=== Willkommen im Bücherkatalog ===");

        while (true) {
            System.out.println("\nMenü:");
            System.out.println("1: Titel suchen");
            System.out.println("2: Autor suchen");
            System.out.println("3: Genre suchen");
            System.out.println("4: Verlag suchen");
            System.out.println("5: Buch-Details");
            System.out.println("6: Rezension hinzufügen");
            System.out.println("7: Rezensionen anzeigen");
            System.out.println("0: Beenden");
            System.out.print("Auswahl: ");

            String wahl = sc.nextLine().trim();
            if ("0".equals(wahl)) break;

            switch (wahl) {
                case "1":
                    System.out.print("Titel (Teilstring): ");
                    for (Buch b : buchsuche(sc.nextLine())) {
                        System.out.printf("%s: %s%n", b.getBookId(), b.getTitle());
                    }
                    break;
                case "2":
                    System.out.print("Autor (Teilstring): ");
                    for (Buch b : sucheNachAutor(sc.nextLine())) {
                        System.out.printf("%s: %s%n", b.getBookId(), b.getTitle());
                    }
                    break;
                case "3":
                    System.out.print("Genre (Teilstring): ");
                    for (Buch b : sucheNachGenre(sc.nextLine())) {
                        System.out.printf("%s: %s%n", b.getBookId(), b.getTitle());
                    }
                    break;
                case "4":
                    System.out.print("Verlag (Teilstring): ");
                    for (Buch b : sucheNachVerlag(sc.nextLine())) {
                        System.out.printf("%s: %s%n", b.getBookId(), b.getTitle());
                    }
                    break;
                case "5":
                    System.out.print("BookId für Details: ");
                    String bid = sc.nextLine();
                    Buch buch = buchdetails(bid);
                    System.out.println(buch != null ? buch : "Buch nicht gefunden.");
                    break;
                case "6":
                    System.out.print("BookId für Rezension: ");
                    String rbid = sc.nextLine();
                    if (buchdetails(rbid) != null) {
                        System.out.print("Kommentar: ");
                        String text = sc.nextLine();
                        System.out.print("Bewertung (1–5): ");
                        int rating = Integer.parseInt(sc.nextLine());
                        Rezension rez = new Rezension();
                        rez.setKommentar(text);
                        rez.setBewertung(rating);
                        rez.setDatum(new Date());
                        reviewHinzufuegen(rbid, rez);
                        System.out.println("Rezension gespeichert.");
                    } else {
                        System.out.println("Ungültige BookId.");
                    }
                    break;
                case "7":
                    System.out.print("BookId für Rezensionen: ");
                    for (Rezension r : showRezensionen(sc.nextLine())) {
                        System.out.printf("%d Sterne: %s (Datum: %s)%n",
                            r.getBewertung(), r.getKommentar(), r.getDatum());
                    }
                    break;
                default:
                    System.out.println("Ungültige Auswahl.");
            }
        }

        System.out.println("Programm beendet.");
        sc.close();
    }

    public static void main(String[] args) {
        new Controller().runConsole();
    }
}
