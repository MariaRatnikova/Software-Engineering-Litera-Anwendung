/*
 * SPDX-License-Identifier: MPL-2.0
 *
 * ApplicationWindow.java
 * -----------------------
 * Zentrales Hauptfenster der Desktop-Applikation »Litera Book Catalogue«.
 * Über ein CardLayout werden hier die unterschiedlichen UI-Bildschirme
 * (Start-, Listen- und Detailansicht) verwaltet.
 */

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * Hauptfenster der Anwendung.
 * <p>
 *  • verwaltet alle Screens als „Karten“ in einem {@link CardLayout}<br>
 *  • hält eine Referenz auf das {@code ApplicationInterface} zur Weitergabe
 *    an die einzelnen Panels
 */
public final class ApplicationWindow extends JFrame {

  /** Empfohlene Startgröße für eine Full-HD-Desktopansicht. */
  private static final int WIDTH  = 1_440;
  private static final int HEIGHT = 900;

  /** Name der Startkarte (muss mit Eintrag in {@link #createScreens} übereinstimmen). */
  private static final String CARD_START = "start";

  /**
   * Konstruktor - erstellt Fenster, Screens und zeigt die Startkarte.
   *
   * @param controller zentrale Steuerschnittstelle der Anwendung
   */
  public ApplicationWindow(final ApplicationInterface controller) {
    super("Litera Book Catalogue");
    setSize(WIDTH, HEIGHT);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setLocationRelativeTo(null); // zentriert das Fenster

    /* --- CardLayout vorbereiten -------------------------------------- */
    final CardLayout cards = new CardLayout();
    final JPanel     root  = new JPanel(cards);

    createScreens(root, cards, controller);

    add(root);
    cards.show(root, CARD_START); // erste Ansicht wählen
    setVisible(true);
  }

  /**
   * Erzeugt und registriert alle UI-Screens im CardLayout.
   *
   * @param root       Container, der als „Kartenstapel“ dient
   * @param cards      das zugehörige {@code CardLayout}
   * @param controller zentrale Steuerschnittstelle
   */
  private static void createScreens(final JPanel root,
                                    final CardLayout cards,
                                    final ApplicationInterface controller) {
    root.add(new StartPanel(cards, root),                   CARD_START);
    root.add(new BookListPanel(cards, root, controller),    "list");
    root.add(new BookDetails(cards, root, controller),      "details");
  }

  /* ------------------------------------------------------------------ */
  /* Einstiegspunkt                                                    */
  /* ------------------------------------------------------------------ */

  /**
   * Main-Methode - startet die Swing-Anwendung thread-sicher.
   *
   * @param args Kommandozeilen-Argumente (werden nicht ausgewertet)
   */
  public static void main(final String[] args) {
    SwingUtilities.invokeLater(() -> {
      StorageService       storage    = new StorageService();
      ApplicationInterface controller = new Controller(storage);
      new ApplicationWindow(controller);
    });
  }
}
