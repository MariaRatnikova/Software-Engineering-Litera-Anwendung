/*
 * SPDX-License-Identifier: MPL-2.0
 *
 * ApplicationWindow.java
 * -----------------------
 * Central main window of the desktop application »Litera Book Catalogue«.
 * A CardLayout is used to manage the different UI screens
 * (start view, list view, and details view).
 */

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * Main window of the application.
 * <p>
 *  • manages all screens as "cards" in a {@link CardLayout}<br>
 *  • holds a reference to the {@code ApplicationInterface} for passing it
 *    to the individual panels
 */
public final class ApplicationWindow extends JFrame {

  /** Recommended startup size for a Full-HD desktop view. */
  private static final int WIDTH  = 1_440;
  private static final int HEIGHT = 900;

  /** Name of the start card (must match entry in {@link #createScreens}). */
  private static final String CARD_START = "start";

  /**
   * Constructor – creates the window, screens and displays the start card.
   *
   * @param controller central control interface of the application
   */
  public ApplicationWindow(final ApplicationInterface controller) {
    super("Litera Book Catalogue");
    setSize(WIDTH, HEIGHT);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setLocationRelativeTo(null); // centers the window

    /* --- Prepare CardLayout ----------------------------------------- */
    final CardLayout cards = new CardLayout();
    final JPanel     root  = new JPanel(cards);

    createScreens(root, cards, controller);

    add(root);
    cards.show(root, CARD_START); // select first view
    setVisible(true);
  }

  /**
   * Creates and registers all UI screens in the CardLayout.
   *
   * @param root       Container serving as the "card stack"
   * @param cards      the corresponding {@code CardLayout}
   * @param controller central control interface
   */
  private static void createScreens(final JPanel root,
                                    final CardLayout cards,
                                    final ApplicationInterface controller) {
    root.add(new StartPanel(cards, root),                   CARD_START);
    root.add(new BookListPanel(cards, root, controller),    "list");
    root.add(new AboutPanel(cards, root),   "about"); 
    root.add(new BookDetails(cards, root, controller),      "details");
  }

  /* ------------------------------------------------------------------ */
  /* Entry point                                                       */
  /* ------------------------------------------------------------------ */

  /**
   * Main method – launches the Swing application in a thread-safe way.
   *
   * @param args Command-line arguments (not evaluated)
   */
  public static void main(final String[] args) {
    SwingUtilities.invokeLater(() -> {
      StorageService       storage    = new StorageService();
      ApplicationInterface controller = new Controller(storage);
      new ApplicationWindow(controller);
    });
  }
}
