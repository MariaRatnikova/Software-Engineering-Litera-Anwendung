/*
 * BookDetails.java
 * ------------------------------------------------------------------
 * Detailed view for a single book.
 *
 *  • One global JScrollPane (“pageScroll”) scrolls the entire page.
 *  • Review block height is dynamic:
 *      – 60 px if there are no reviews
 *      – min(real-height, 400 px) if reviews exist
 *  • Similar-books gallery lives at the bottom.
 *
 
 */



import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.util.Date;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 * Panel that shows a single {@link Buch} together with:
 * <ul>
 *   <li>basic metadata (title, author, publisher, genres, description, cover)</li>
 *   <li>a (dynamically sized) list of reviews</li>
 *   <li>a horizontal carousel of similar books</li>
 * </ul>
 *
 * <p>The whole page is wrapped in one {@code JScrollPane}, so the mouse wheel
 * always scrolls the full view instead of nested panes.</p>
 */
public final class BookDetails extends JPanel {

    /* === Palette and layout constants =================================== */

    private static final Color COLOR_BG       = new Color(8, 25, 40);
    private static final Color COLOR_ACCENT   = new Color(166, 221, 211);
    private static final Color COLOR_CARD_BG  = new Color(19, 41, 59);
    private static final Color COLOR_BTN_FG   = Color.WHITE;

    /** Maximum logical width of the page (px) – never changed at run-time. */
    private static final int WIDTH_MAX = 1250;

    /* === Navigation / controller hooks ================================== */

    private final CardLayout navigator;
    private final JPanel     parent;
    private final ApplicationInterface controller;

    /* === Mutable state =================================================== */

    private Buch currentBook;

    /* === UI widgets ====================================================== */

    /*— Labels in the header —*/
    private final JLabel titleLabel  = createLabel("", 34, Font.BOLD,  Color.WHITE);
    private final JLabel authorLabel = createLabel("", 20, Font.PLAIN, Color.WHITE);
    private final JLabel publLabel   = createLabel("", 18, Font.PLAIN, Color.WHITE);
    private final JLabel genreLabel  = createLabel("", 18, Font.PLAIN, Color.WHITE);
    private final JTextArea  descArea   = createTextArea();

    /*— Cover image (right-hand side) —*/
    private final JLabel coverLabel = new JLabel();

    /*— Review pane (dynamic height) —*/
    private final JPanel      reviewList = new JPanel();
    private final JScrollPane reviewPane;

    /*— Similar books gallery —*/
    private final SimilarBooks similarBooks;

    /* ===================================================================== */

    /**
     * Constructs the detail panel and wires UI to the given controller.
     *
     * @param nav        parent CardLayout for back-navigation
     * @param parentCard parent container that owns {@code nav}
     * @param ctl        application controller / service façade
     */
    public BookDetails(CardLayout nav, JPanel parentCard,
                       ApplicationInterface ctl) {

        super(new BorderLayout());

        this.navigator  = nav;
        this.parent     = parentCard;
        this.controller = ctl;

        setBackground(COLOR_BG);

        /* ---------- top row with “back” button -------------------------- */

        JButton back = createButton("←");
        back.addActionListener(e -> navigator.show(parent, "list"));

        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);
        top.setBorder(new EmptyBorder(20, 30, 0, 30));
        top.add(back, BorderLayout.WEST);
        add(top, BorderLayout.NORTH);

        /* ---------- column that hosts all content ---------------------- */

        JPanel column = new JPanel();
        column.setOpaque(false);
        column.setLayout(new BoxLayout(column, BoxLayout.Y_AXIS));
        column.setBorder(new EmptyBorder(20, 80, 40, 80));
        column.setMaximumSize(new Dimension(WIDTH_MAX, Integer.MAX_VALUE));

        /* Wrap the column – now wheel scrolls the whole page. */
        JScrollPane pageScroll = new JScrollPane(
            column,
            JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );
        pageScroll.setBorder(null);
        pageScroll.setOpaque(false);
        pageScroll.getViewport().setOpaque(false);
        add(pageScroll, BorderLayout.CENTER);

        /* ---------- header block: metadata + cover --------------------- */

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.setAlignmentX(LEFT_ALIGNMENT);
        header.setMaximumSize(new Dimension(WIDTH_MAX, 300));

        /* Left side – textual metadata. */
        JPanel info = new JPanel();
        info.setOpaque(false);
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.setBorder(new EmptyBorder(0, 0, 0, 30));

        info.add(titleLabel);
        info.add(Box.createVerticalStrut(10));
        info.add(authorLabel);
        info.add(Box.createVerticalStrut(6));
        info.add(publLabel);
        info.add(Box.createVerticalStrut(6));
        info.add(genreLabel);
        info.add(Box.createVerticalStrut(10));
        info.add(createLabel(
                 "<html>" + green("Description:") + "</html>",
                 18, Font.BOLD, Color.WHITE));
        info.add(Box.createVerticalStrut(4));
        info.add(descArea);

        header.add(info, BorderLayout.CENTER);

        /* Right side – cover image. */
        coverLabel.setAlignmentY(TOP_ALIGNMENT);
        header.add(coverLabel, BorderLayout.EAST);

        column.add(header);

        /* ---------- “Reviews” header row ------------------------------ */

        JPanel revHdr = new JPanel(new BorderLayout());
        revHdr.setOpaque(false);
        revHdr.setAlignmentX(LEFT_ALIGNMENT);
        revHdr.setBorder(new EmptyBorder(25, 0, 10, 0));

        revHdr.add(createLabel("Reviews", 32, Font.BOLD, Color.WHITE),
                   BorderLayout.WEST);

        JButton addReview = createButton("Write review");
        addReview.addActionListener(e -> openAddDialog(currentBook));
        revHdr.add(addReview, BorderLayout.EAST);

        column.add(revHdr);

        /* ---------- review list (no inner scroll) --------------------- */

        reviewList.setOpaque(false);
        reviewList.setLayout(new BoxLayout(reviewList, BoxLayout.Y_AXIS));

        reviewPane = new JScrollPane(
            reviewList,
            JScrollPane.VERTICAL_SCROLLBAR_NEVER,    // never scroll inside
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );
        reviewPane.setBorder(null);
        reviewPane.setOpaque(false);
        reviewPane.getViewport().setOpaque(false);
        reviewPane.setAlignmentX(LEFT_ALIGNMENT);
        column.add(reviewPane);

        /* ---------- similar books gallery ----------------------------- */

        column.add(Box.createVerticalStrut(30));
        similarBooks = new SimilarBooks(controller, navigator, parent, this);
        similarBooks.setAlignmentX(LEFT_ALIGNMENT);
        column.add(similarBooks);
    }

    /* == Public API ====================================================== */

    /**
     * Populates the panel with the given {@link Buch}.
     *
     * @param b book to show
     */
    public void showBook(Buch b) {
        currentBook = b;

        titleLabel.setText(b.getTitle());
        authorLabel.setText(htmlPair("Author:",    b.getAuthor()));
        publLabel .setText(htmlPair("Publisher:",  dash(b.getPublisher())));
        genreLabel.setText(htmlPair("Genres:",     genres(b.getGenres())));

        descArea.setText(
            b.getDescription() == null || b.getDescription().isBlank()
            ? "(no description)" : b.getDescription()
        );
        descArea.setMaximumSize(new Dimension(WIDTH_MAX, Short.MAX_VALUE));

        /* Cover image (scaled). */
        if (b.getImage() != null && !b.getImage().isBlank()) {
            ImageIcon raw = new ImageIcon(getClass().getClassLoader().getResource(b.getImage()));
            Image scaled = raw.getImage().getScaledInstance(
                               160, 240, Image.SCALE_SMOOTH);
            coverLabel.setIcon(new ImageIcon(scaled));
            coverLabel.setToolTipText(b.getTitle());
            coverLabel.setText(null);
        } else {
            coverLabel.setIcon(null);
            coverLabel.setText("no image");
            coverLabel.setForeground(Color.LIGHT_GRAY);
        }

        similarBooks.showFor(b.getBookId());
        loadReviews();
    }

    /* == Review handling =============================================== */

    /** Reloads reviews and resizes {@code reviewPane}. */
    private void loadReviews() {
        reviewList.removeAll();

        if (currentBook == null) {
            return;
        }

        List<Rezension> data =
            controller.showRezensionen(currentBook.getBookId());

        if (data.isEmpty()) {
            reviewList.add(createLabel(
                "No reviews yet", 16, Font.PLAIN, Color.LIGHT_GRAY));
        } else {
            for (Rezension r : data) {
                reviewList.add(renderReview(r));
                reviewList.add(Box.createVerticalStrut(12));
            }
        }

        /* Dynamically set block height. */
        int height = data.isEmpty()
                     ? 60
                     : Math.min(reviewList.getPreferredSize().height + 12, 400);

        reviewPane.setPreferredSize(new Dimension(WIDTH_MAX, height));
        reviewList.revalidate();
        reviewList.repaint();
    }

    /**
     * Creates a single “review card”.
     *
     * @param r review DTO
     *
     * @return panel ready to insert
     */
    private JPanel renderReview(Rezension r) {
        JPanel card = new JPanel();
        card.setBackground(COLOR_CARD_BG);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(15, 15, 15, 15));
        card.setAlignmentX(LEFT_ALIGNMENT);
        card.setMaximumSize(new Dimension(WIDTH_MAX, Integer.MAX_VALUE));

        card.add(createLabel(
            starLine(r.getBewertung()), 18, Font.PLAIN, COLOR_ACCENT));
        card.add(Box.createVerticalStrut(6));

        JTextArea body = createTextArea();
        body.setText(r.getKommentar());
        card.add(body);

        return card;
    }

    /* == “Write review” pop-up dialog =================================== */

    private void openAddDialog(Buch book) {
        if (book == null) {
            return;
        }

        JPanel form = new JPanel(new BorderLayout(5, 5));
        JTextField ratingField = new JTextField();
        JTextArea  textField   = new JTextArea(5, 25);

        textField.setLineWrap(true);
        textField.setWrapStyleWord(true);

        form.add(new JLabel("Rating (1-5):"), BorderLayout.NORTH);
        form.add(ratingField,                BorderLayout.CENTER);
        form.add(new JScrollPane(textField), BorderLayout.SOUTH);

        int rc = JOptionPane.showConfirmDialog(
            this, form, "Write review", JOptionPane.OK_CANCEL_OPTION);

        if (rc != JOptionPane.OK_OPTION) {
            return;
        }

        try {
            int stars = Integer.parseInt(ratingField.getText().trim());
            if (stars < 1 || stars > 5) {
                throw new NumberFormatException();
            }

            Rezension rev = new Rezension();
            rev.setBewertung(stars);
            rev.setKommentar(textField.getText());
            rev.setDatum(new Date());

            controller.reviewHinzufuegen(book.getBookId(), rev);
            loadReviews();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                this, "Rating must be 1-5", "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    /* == Helpers / factory methods ====================================== */

    private static JLabel createLabel(String txt,
                                      int size,
                                      int style,
                                      Color fg) {
        JLabel l = new JLabel(txt);
        l.setFont(new Font("Arial", style, size));
        l.setForeground(fg);
        l.setAlignmentX(LEFT_ALIGNMENT);
        l.setHorizontalAlignment(SwingConstants.LEFT);
        return l;
    }

    private static JTextArea createTextArea() {
        JTextArea ta = new JTextArea();
        ta.setEditable(false);
        ta.setOpaque(false);
        ta.setLineWrap(true);
        ta.setWrapStyleWord(true);
        ta.setForeground(Color.WHITE);
        ta.setFont(new Font("Arial", Font.PLAIN, 16));
        ta.setAlignmentX(LEFT_ALIGNMENT);
        return ta;
    }

    private static JButton createButton(String caption) {
        JButton b = new JButton(caption);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setContentAreaFilled(false);
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        b.setOpaque(false);
        b.setForeground(COLOR_BTN_FG);
        b.setFont(new Font("Arial", Font.BOLD, 16));
        return b;
    }

    /** Returns “★☆☆☆☆” style string for a 1-5 rating. */
    private static String starLine(int rating) {
        StringBuilder sb = new StringBuilder(5);
        for (int i = 0; i < 5; i++) {
            sb.append(i < rating ? '★' : '☆');
        }
        return sb.toString();
    }

    /* == Tiny HTML helpers ============================================== */

    private static final String ACCENT_HEX = String.format(
        "#%02x%02x%02x",
        COLOR_ACCENT.getRed(),
        COLOR_ACCENT.getGreen(),
        COLOR_ACCENT.getBlue());

    private static String green(String txt) {
        return "<font color='" + ACCENT_HEX + "'>" + txt + "</font>";
    }

    private static String htmlPair(String key, String val) {
        return "<html>" + green(key) + ' ' + val + "</html>";
    }

    private static String dash(String s) {
        return (s == null || s.isBlank()) ? "—" : s;
    }

    private static String genres(List<String> g) {
        return (g == null || g.isEmpty()) ? "—" : String.join(", ", g);
    }
}
