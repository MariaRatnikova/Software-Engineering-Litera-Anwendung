import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

// Die Klasse BookDetails stellt eine GUI-Ansicht dar, die Buchdetails und Benutzerrezensionen anzeigt.
public class BookDetails extends JPanel {

    /* === Farben für Hintergrund, Akzente, Buttons etc. === */
    private static final Color BACK    = new Color(8, 25, 40);         // Dunkler Hintergrund
    private static final Color ACCENT  = new Color(166, 221, 211);     // Akzentfarbe
    private static final Color LIST_BG = new Color(19, 41, 59);        // Hintergrund für Rezensionen
    private static final Color BTN_BG  = new Color(52, 121, 122);      // Button-Hintergrund

    private static final int MAX_W = 1250; // Maximale Breite der Textbereiche

    // Verweise auf Layout und Controller
    private final CardLayout cl;
    private final JPanel parent;
    private final ApplicationInterface ctrl;

    private Buch current; // Aktuell angezeigtes Buch

    // GUI-Komponenten für Buchdetails
    private final JLabel titleL  = lbl("", 32, Font.BOLD , Color.WHITE);
    private final JLabel authorL = lbl("", 20, Font.PLAIN, Color.WHITE);
    private final JLabel publL   = lbl("", 16, Font.PLAIN, ACCENT);
    private final JLabel genreL  = lbl("", 16, Font.PLAIN, ACCENT);
    private final JTextArea descA = txtArea();

    // Panel für Benutzerrezensionen
    private final JPanel reviewList = new JPanel();
    private final JScrollPane reviewPane;

    // Konstruktor: initialisiert UI und Layout
    public BookDetails(CardLayout cl, JPanel parent, ApplicationInterface ctrl) {
        this.cl = cl;
        this.parent = parent;
        this.ctrl = ctrl;

        setLayout(new BorderLayout());
        setBackground(BACK);

        /* === obere Leiste mit Zurück-Button und Schreibbutton === */
        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);
        top.setBorder(new EmptyBorder(15,15,0,15));

        JButton back  = btn("←");
        back.addActionListener(e -> cl.show(parent,"list")); // Zurück zur Liste
        JButton write = btn("Write review");
        write.addActionListener(e -> openAddDialog(current));

        top.add(back , BorderLayout.WEST);
        top.add(write, BorderLayout.EAST);
        add(top, BorderLayout.NORTH);

        /* === Hauptinhalt: vertikale Spalte === */
        JPanel column = new JPanel();
        column.setOpaque(false);
        column.setLayout(new BoxLayout(column, BoxLayout.Y_AXIS));
        column.setBorder(new EmptyBorder(5,60,5,60)); // Allgemeiner Innenabstand
        add(column, BorderLayout.CENTER);

        /* === Buchinformationen === */
        JPanel info = new JPanel();
        info.setOpaque(false);
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.setAlignmentX(LEFT_ALIGNMENT);
        info.setBorder(new EmptyBorder(0,0,0,0));

        info.add(titleL);
        info.add(Box.createVerticalStrut(8));
        info.add(authorL);
        info.add(Box.createVerticalStrut(12));
        info.add(publL);
        info.add(Box.createVerticalStrut(5));
        info.add(genreL);
        info.add(Box.createVerticalStrut(2));
        info.add(lbl("Description:",16,Font.BOLD,ACCENT));
        info.add(Box.createVerticalStrut(4));

        descA.setRows(6); // Feste Höhe für Beschreibung
        Dimension d = descA.getPreferredSize();
        descA.setMaximumSize(new Dimension(MAX_W, d.height));
        info.add(descA);

        info.setMaximumSize(new Dimension(MAX_W, Integer.MAX_VALUE));
        column.add(info);

        /* === Überschrift für Rezensionen + Button === */
        JPanel head = new JPanel(new BorderLayout());
        head.setOpaque(false);
        head.setAlignmentX(LEFT_ALIGNMENT);
        head.setBorder(new EmptyBorder(5,0,5,0));

        JLabel revH = lbl("Reviews", 42, Font.BOLD, Color.WHITE);
        head.add(revH, BorderLayout.WEST);
        head.add(write, BorderLayout.EAST); // derselbe Button

        column.add(head);

        /* === Scrollbare Liste von Rezensionen === */
        reviewList.setOpaque(false);
        reviewList.setLayout(new BoxLayout(reviewList, BoxLayout.Y_AXIS));

        reviewPane = new JScrollPane(
            reviewList,
            JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );
        reviewPane.setBorder(null);
        reviewPane.setOpaque(false);
        reviewPane.getViewport().setOpaque(false);
        reviewPane.setAlignmentX(LEFT_ALIGNMENT);
        column.add(reviewPane);
    }

    // Zeigt Details für ein bestimmtes Buch an
    public void showBook(Buch b) {
        current = b;
        titleL.setText(b.getTitle());
        authorL.setText("Author: " + b.getAuthor());
        publL.setText("Publisher: " + (b.getPublisher() == null || b.getPublisher().isBlank() ? "—" : b.getPublisher()));
        List<String> g = b.getGenres();
        genreL.setText("Genres: " + (g == null || g.isEmpty() ? "—" : String.join(", ", g)));
        descA.setText(b.getDescription() == null || b.getDescription().isBlank() ? "(no description)" : b.getDescription());

        loadReviews();
    }

    // Lädt und zeigt Rezensionen für das aktuelle Buch
    private void loadReviews() {
        reviewList.removeAll();
        if (current == null) return;

        List<Rezension> rez = ctrl.showRezensionen(current.getBookId());
        if (rez.isEmpty()) {
            reviewList.add(lbl("No reviews yet",14,Font.PLAIN,Color.LIGHT_GRAY));
        } else {
            for (Rezension r : rez) {
                JPanel p = render(r);
                reviewList.add(p);
                reviewList.add(Box.createVerticalStrut(10)); // Abstand
            }
        }
        reviewList.revalidate();
        reviewList.repaint();
    }

    // Rendert eine einzelne Rezension
    private JPanel render(Rezension r){
        JPanel p = new JPanel();
        p.setOpaque(true);
        p.setBackground(LIST_BG);
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBorder(new EmptyBorder(15, 15, 15, 15));
        p.setAlignmentX(LEFT_ALIGNMENT);
        p.setMaximumSize(new Dimension(MAX_W, Integer.MAX_VALUE));

        p.add(lbl(stars(r.getBewertung()), 18, Font.PLAIN, ACCENT));
        p.add(Box.createVerticalStrut(6));
        JTextArea body = txtArea();
        body.setText(r.getKommentar());
        p.add(body);
        return p;
    }

    // Öffnet ein Dialogfenster zum Hinzufügen einer Rezension
    private void openAddDialog(Buch b){
        if (b == null) return;
        JPanel pane = new JPanel(new BorderLayout(5, 5));
        JTextField rate = new JTextField();
        JTextArea txt = new JTextArea(5, 25);
        txt.setLineWrap(true);
        txt.setWrapStyleWord(true);

        pane.add(new JLabel("Rating (1-5):"), BorderLayout.NORTH);
        pane.add(rate, BorderLayout.CENTER);
        pane.add(new JScrollPane(txt), BorderLayout.SOUTH);

        if (JOptionPane.showConfirmDialog(this, pane, "Write review",
                JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            try {
                int s = Integer.parseInt(rate.getText().trim());
                if (s < 1 || s > 5) throw new NumberFormatException();

                Rezension r = new Rezension();
                r.setBewertung(s);
                r.setKommentar(txt.getText());
                r.setDatum(new Date());
                ctrl.reviewHinzufuegen(b.getBookId(), r);
                loadReviews();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Rating must be 1–5",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /* === Hilfsmethoden zum Erstellen von Komponenten ===================== */
    private static JLabel lbl(String t, int size, int style, Color c) {
        JLabel l = new JLabel(t);
        l.setFont(new Font("Arial", style, size));
        l.setForeground(c);
        l.setAlignmentX(LEFT_ALIGNMENT);
        return l;
    }

    private static JTextArea txtArea() {
        JTextArea a = new JTextArea();
        a.setEditable(false);
        a.setOpaque(false);
        a.setLineWrap(true);
        a.setWrapStyleWord(true);
        a.setForeground(Color.WHITE);
        a.setFont(new Font("Arial", Font.PLAIN, 16));
        a.setAlignmentX(LEFT_ALIGNMENT);
        return a;
    }

    private static JButton btn(String t) {
        JButton b = new JButton(t);
        b.setBackground(BTN_BG);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setFont(new Font("Arial", Font.BOLD, 16));
        b.setBorder(BorderFactory.createEmptyBorder(6, 18, 6, 18));
        return b;
    }

    private static String stars(int n) {
        StringBuilder sb = new StringBuilder(5);
        for (int i = 0; i < 5; i++) sb.append(i < n ? '★' : '☆');
        return sb.toString();
    }
}