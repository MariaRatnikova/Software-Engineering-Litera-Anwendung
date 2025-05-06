import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * Hauptfenster der Anwendung.
 * Verwaltet die verschiedenen Bildschirme über ein CardLayout.
 */
public class ApplicationWindow extends JFrame {

    /**
     * Konstruktor – Initialisiert das Fenster und die Panels.
     * @param controller Das Haupt-Interface für die Steuerung der App
     */
    public ApplicationWindow(ApplicationInterface controller) {
        super("Litera Book Catalogue");  // Fenstertitel
        setSize(1440, 960);              // Fenstergröße
        setDefaultCloseOperation(EXIT_ON_CLOSE); // Schließen bei "X"-Klick
        setLocationRelativeTo(null);     // Zentriert das Fenster auf dem Bildschirm

        /* 2) Layout-Manager für das Umschalten zwischen Panels */
        CardLayout cl = new CardLayout(); // Ermöglicht das Umschalten von „Karten“
        JPanel cards  = new JPanel(cl);   // Root-Panel, in dem alle „Karten“ liegen

        /* 3) Erstellen und Hinzufügen der verschiedenen Panels */
        cards.add(new StartPanel(cl, cards),              "start");    // Startseite
        cards.add(new BookListPanel(cl, cards, controller), "list");   // Liste der Bücher
        cards.add(new BookDetails(cl, cards, controller),   "details"); // Detailansicht eines Buches

        add(cards);                // Panels zum Fenster hinzufügen
        cl.show(cards, "start");   // Startansicht anzeigen

        setVisible(true);          // Fenster sichtbar machen
    }

    /**
     * Einstiegspunkt der Anwendung.
     * Hier wird alles gestartet.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Erstellt das Speicher-Service-Objekt (z. B. Datenbank)
            StorageService storage = new StorageService();

            // Erstellt den Controller, der die Logik enthält
            ApplicationInterface controller = new Controller(storage);

            // Startet die Anwendung
            new ApplicationWindow(controller);
        });
    }
}