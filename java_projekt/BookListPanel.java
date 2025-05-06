import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

/**
 * Panel, das eine Liste von Büchern anzeigt (bestehend aus Suchfeld und Ergebnissen).
 */
public class BookListPanel extends JPanel {

    // Verweis auf die Hauptlogik der Anwendung
    private final ApplicationInterface ctrl;

    // CardLayout zum Wechseln zwischen Panels
    private final CardLayout cl;

    // Übergeordnetes Panel, das mehrere Panels enthält
    private final JPanel parent;

    // UI-Komponenten für Filterung und Suche
    private JComboBox<String> filterBox;
    private JTextField searchField;
    private JPanel resultsPanel;

    /**
     * Konstruktor für das Panel mit Buchliste.
     * Initialisiert das Layout, die Farben und baut die UI-Komponenten.
     */
    public BookListPanel(CardLayout cl, JPanel parent, ApplicationInterface c) {
        this.ctrl = c;
        this.cl = cl;
        this.parent = parent;

        setLayout(new BorderLayout()); // Layout mit Kopfbereich (Norden) und Inhalt (Zentrum)
        setBackground(new Color(8, 25, 40)); // Dunkler Hintergrund

        buildHeader();      // Erstellt Kopfzeile mit Logo, Filter und Suchfeld
        buildResultArea();  // Erstellt Bereich für Suchergebnisse

        print(ctrl.buchsuche("")); // Zeigt beim Start alle Bücher an
    }

    /**
     * Erstellt den oberen Teil des Panels:
     * Logo, Dropdown-Filter, Suchfeld, Such-Button und Überschrift "Books:"
     */
    private void buildHeader() {

        // Obere Leiste mit grünlichem Hintergrund
        JPanel topBar = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 20));
        topBar.setPreferredSize(new Dimension(1440, 170));
        topBar.setBackground(new Color(52, 121, 122));

        // Logo einfügen und skalieren
        ImageIcon icon = new ImageIcon("images/LogoText.png");
        Image scaledLogo = icon.getImage().getScaledInstance(113, 123, Image.SCALE_SMOOTH);
        JLabel logoLabel = new JLabel(new ImageIcon(scaledLogo));
        topBar.add(logoLabel);

        // Dropdown-Menü für Filterkategorie (z. B. nach Titel oder Autor)
        filterBox = new JComboBox<>(new String[] { "Title", "Author", "Publisher", "Genre" });
        filterBox.setBackground(new Color(166, 221, 211)); // Mintfarben
        filterBox.setFont(new Font("Arial", Font.PLAIN, 18));
        filterBox.setPreferredSize(new Dimension(200, 45));
        filterBox.setFocusable(false);
        filterBox.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        filterBox.setOpaque(true); // Wichtig für Darstellung auf dunklem Hintergrund
        topBar.add(filterBox);

        // Suchfeld zur Eingabe der Suchbegriffe
        searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(600, 45));
        searchField.setFont(new Font("Arial", Font.PLAIN, 18));
        searchField.setBackground(Color.WHITE);
        searchField.setForeground(Color.BLACK);
        searchField.setCaretColor(Color.BLACK);
        searchField.setOpaque(true);
        topBar.add(searchField);

        // Such-Button mit der Beschriftung "Search"
        JButton go = new JButton("Search");
        go.setPreferredSize(new Dimension(120, 45));
        go.setForeground(Color.WHITE);
        go.setFont(new Font("Arial", Font.BOLD, 16));
        go.setFocusPainted(false);
        go.setContentAreaFilled(false); // Kein Hintergrund
        go.setBorderPainted(false);     // Keine Rahmenlinie
        go.setOpaque(false);            // Komplett transparent
        topBar.add(go);

        // Ein gemeinsamer ActionListener für Button und Enter-Taste im Suchfeld
        ActionListener performSearch = e -> refresh();
        go.addActionListener(performSearch);
        searchField.addActionListener(performSearch);

        // Überschrift „Books:“
        JLabel booksLabel = new JLabel("Books:");
        booksLabel.setFont(new Font("Arial", Font.BOLD, 28));
        booksLabel.setForeground(Color.WHITE);
        booksLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));

        // Panel zur Ausrichtung der Überschrift
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setBackground(new Color(8, 25, 40));
        headerPanel.add(booksLabel);

        // Container-Panel für die gesamte Kopfzeile
        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBackground(new Color(8, 25, 40));
        header.add(topBar);
        header.add(booksLabel);

        add(header, BorderLayout.NORTH);
    }

    /**
     * Erstellt den Bereich, in dem die Suchergebnisse (Bücher) angezeigt werden.
     * Dieser Bereich ist scrollbar.
     */
    private void buildResultArea() {
        resultsPanel = new JPanel();
        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));
        resultsPanel.setBackground(new Color(8, 25, 40));
        resultsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 0, 0)); // Abstand oben

        JScrollPane sp = new JScrollPane(resultsPanel,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        sp.setBorder(null); // Keine Rahmenlinie

        add(sp, BorderLayout.CENTER);
    }

    /**
     * Aktualisiert die Suchergebnisse entsprechend dem eingegebenen Suchbegriff
     * und dem gewählten Filter (Kategorie).
     */
    private void refresh() {
        String query = searchField.getText();                       // Eingabetext
        String category = (String) filterBox.getSelectedItem();     // Gewählter Filter

        List<Buch> list;

        // Auswahl der Suchmethode je nach Filterkategorie
        switch (category) {
            case "Author":
                list = ctrl.sucheNachAutor(query);
                break;
            case "Publisher":
                list = ctrl.sucheNachVerlag(query);
                break;
            case "Genre":
                list = ctrl.sucheNachGenre(query);
                break;
            default:
                list = ctrl.buchsuche(query);
                break;
        }

        print(list); // Ergebnisse anzeigen
    }

    /**
     * Zeigt eine Liste von Büchern im resultsPanel an.
     * Wenn keine Bücher gefunden wurden, wird eine entsprechende Nachricht gezeigt.
     */
    private void print(List<Buch> books) {
        resultsPanel.removeAll(); // Vorherige Ergebnisse löschen

        if (books.isEmpty()) {
            JLabel none = new JLabel("Nothing found");
            none.setForeground(Color.LIGHT_GRAY);
            none.setBorder(BorderFactory.createEmptyBorder(30, 60, 0, 0));
            resultsPanel.add(none);
        }

        // Für jedes Buch ein Panel mit Titel und Autor
        for (Buch b : books) {
            JPanel row = new JPanel(new BorderLayout());
            row.setPreferredSize(new Dimension(1000, 80));
            row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
            row.setBackground(new Color(19, 41, 59));
            row.setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 25));

            // Titel anzeigen
            JLabel title = new JLabel(b.getTitle());
            title.setFont(new Font("Arial", Font.BOLD, 20));
            title.setForeground(Color.WHITE);

            // Autor anzeigen
            JLabel author = new JLabel(b.getAuthor());
            author.setForeground(new Color(173, 199, 208));

            // Vertikale Anordnung von Titel und Autor
            JPanel text = new JPanel();
            text.setOpaque(false);
            text.setLayout(new BoxLayout(text, BoxLayout.Y_AXIS));
            text.add(title);
            text.add(author);

            row.add(text, BorderLayout.CENTER);

            // Beim Klicken: zur Detailansicht des Buchs wechseln
            row.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            row.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    for (Component c : parent.getComponents()) {
                        if (c instanceof BookDetails) {
                            BookDetails details = (BookDetails) c;
                            details.showBook(b); // Buchdetails anzeigen
                            break;
                        }
                    }
                    cl.show(parent, "details"); // Zum Detail-Panel wechseln
                }
            });

            resultsPanel.add(row);                          // Buchzeile hinzufügen
            resultsPanel.add(Box.createVerticalStrut(15));  // Abstand zwischen den Büchern
        }

        resultsPanel.revalidate(); // Layout neu berechnen
        resultsPanel.repaint();    // UI aktualisieren
    }
}