/*
 * BookDetails.java
 * ------------------------------------------------------------------
 * Detailed view for a single book:
 *   • Basic data (title, author, publisher, genres, description)
 *   • Scrollable list of all existing reviews
 *   • “Write review” button for adding new reviews
 */

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Date;
import java.util.List;

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

/**
 * Panel that displays the details of a book along with its reviews.
 */
public final class BookDetails extends JPanel {

    /* ---------------------------------------------------------------
 * Color scheme & layout boundaries
 * -------------------------------------------------------------- */
    private static final Color COLOR_BG         = new Color(8, 25, 40);
    private static final Color COLOR_ACCENT     = new Color(166, 221, 211);
    private static final Color COLOR_CARD_BG    = new Color(19, 41, 59);
    private static final Color COLOR_BUTTON_FG  = Color.WHITE;
    private static final int   WIDTH_MAX        = 1_250;

  /* ---------------------------------------------------------------
 * Navigation & logic
 * -------------------------------------------------------------- */

    private final CardLayout           navigator;
    private final JPanel               parent;
    private final ApplicationInterface controller;

/* ---------------------------------------------------------------
 * State
 * -------------------------------------------------------------- */
    private Buch currentBook;

   /* ---------------------------------------------------------------
 * UI elements – Book data
 * -------------------------------------------------------------- */
    private final JLabel    titleLabel  = label("", 32, Font.BOLD, Color.WHITE);
    private final JLabel    authorLabel = label("", 20, Font.PLAIN, Color.WHITE);
    private final JLabel    publLabel   = label("", 16, Font.PLAIN, COLOR_ACCENT);
    private final JLabel    genreLabel  = label("", 16, Font.PLAIN, COLOR_ACCENT);
    private final JTextArea descArea    = descriptionArea();
/* ---------------------------------------------------------------
 * UI elements – Reviews
 * -------------------------------------------------------------- */

    private final JPanel     reviewListPanel = new JPanel();
    private final JScrollPane reviewPane;
/* ---------------------------------------------------------------
 * Constructor
 * -------------------------------------------------------------- */
    public BookDetails(CardLayout navigator, JPanel parent,
            ApplicationInterface controller) {

        super(new BorderLayout());
        this.navigator  = navigator;
        this.parent     = parent;
        this.controller = controller;

        setBackground(COLOR_BG);

        /* Top bar with back button -------------------------------- */
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setOpaque(false);
        topBar.setBorder(new EmptyBorder(20, 30, 0, 30));

        JButton backBtn = textButton("←");
        backBtn.addActionListener(evt -> navigator.show(parent, "list"));
        topBar.add(backBtn, BorderLayout.WEST);
        add(topBar, BorderLayout.NORTH);

        /* Central vertical column ----------------------------------- */
        JPanel column = new JPanel();
        column.setOpaque(false);
        column.setLayout(new BoxLayout(column, BoxLayout.Y_AXIS));
        column.setBorder(new EmptyBorder(20, 80, 40, 80));
        add(column, BorderLayout.CENTER);

        /* Book basic information ------------------------------------ */
        JPanel infoBox = new JPanel();
        infoBox.setOpaque(false);
        infoBox.setLayout(new BoxLayout(infoBox, BoxLayout.Y_AXIS));
        infoBox.setAlignmentX(LEFT_ALIGNMENT);

        infoBox.add(titleLabel);
        infoBox.add(Box.createVerticalStrut(6));
        infoBox.add(authorLabel);
        infoBox.add(Box.createVerticalStrut(8));
        infoBox.add(publLabel);
        infoBox.add(Box.createVerticalStrut(4));
        infoBox.add(genreLabel);
        infoBox.add(Box.createVerticalStrut(4));
        infoBox.add(label("Description:", 16, Font.BOLD, COLOR_ACCENT));
        infoBox.add(Box.createVerticalStrut(2));
        infoBox.add(descArea);

        infoBox.setMaximumSize(new Dimension(WIDTH_MAX, Integer.MAX_VALUE));
        column.add(infoBox);

       /* Heading “Reviews” + Button ------------------------------ */
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.setAlignmentX(LEFT_ALIGNMENT);
        header.setBorder(new EmptyBorder(10, 0, 20, 0));

        JLabel reviewsLabel = label("Reviews", 42, Font.BOLD, Color.WHITE);
        header.add(reviewsLabel, BorderLayout.WEST);

        JButton writeBtn = textButton("Write review");
        writeBtn.addActionListener(evt -> openAddDialog(currentBook));
        header.add(writeBtn, BorderLayout.EAST);

        column.add(header);

      /* Scrollable review list ------------------------------------- */
        reviewListPanel.setOpaque(false);
        reviewListPanel.setLayout(new BoxLayout(reviewListPanel, BoxLayout.Y_AXIS));

        reviewPane = new JScrollPane(reviewListPanel,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        reviewPane.setBorder(null);
        reviewPane.setOpaque(false);
        reviewPane.getViewport().setOpaque(false);
        reviewPane.setAlignmentX(LEFT_ALIGNMENT);
        reviewPane.setPreferredSize(new Dimension(WIDTH_MAX, 400));
        reviewPane.setMaximumSize(new Dimension(WIDTH_MAX, 600));
        column.add(reviewPane);
    }

   /* ---------------------------------------------------------------
 * Public API
 * -------------------------------------------------------------- */

   /**
 * Displays the data of the given book.
 *
 * @param book Book to be displayed
 */

    public void showBook(Buch book) {
        currentBook = book;

        titleLabel.setText(book.getTitle());
        authorLabel.setText("Author: " + book.getAuthor());

        String publisher = book.getPublisher();
        publLabel.setText("Publisher: " + (publisher == null || publisher.isBlank()
                                           ? "—" : publisher));

        List<String> genres = book.getGenres();
        genreLabel.setText("Genres: " + (genres == null || genres.isEmpty()
                                         ? "—" : String.join(", ", genres)));

        String description = book.getDescription();
        descArea.setText(description == null || description.isBlank()
                         ? "(no description)" : description);

       /* Limit height of the description --------------------------- */
        int visibleLines = Math.min(descArea.getLineCount(), 6);
        descArea.setRows(visibleLines);
        Dimension size = descArea.getPreferredSize();
        descArea.setMaximumSize(new Dimension(WIDTH_MAX, size.height));

        loadReviews();
    }

   /* ---------------------------------------------------------------
 * Private helper methods
 * -------------------------------------------------------------- */

    private void loadReviews() {
        reviewListPanel.removeAll();
        if (currentBook == null) {
            return;
        }

        List<Rezension> reviews = controller.showRezensionen(currentBook.getBookId());
        if (reviews.isEmpty()) {
            reviewListPanel.add(label("No reviews yet", 14, Font.PLAIN, Color.LIGHT_GRAY));
        } else {
            for (Rezension review : reviews) {
                reviewListPanel.add(render(review));
                reviewListPanel.add(Box.createVerticalStrut(10));
            }
        }
        reviewListPanel.revalidate();
        reviewListPanel.repaint();
    }

    private JPanel render(Rezension review) {
        JPanel card = new JPanel();
        card.setBackground(COLOR_CARD_BG);
        card.setOpaque(true);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(15, 15, 15, 15));
        card.setAlignmentX(LEFT_ALIGNMENT);
        card.setMaximumSize(new Dimension(WIDTH_MAX, Integer.MAX_VALUE));

        card.add(label(starLine(review.getBewertung()), 18, Font.PLAIN, COLOR_ACCENT));
        card.add(Box.createVerticalStrut(6));

        JTextArea body = descriptionArea();
        body.setText(review.getKommentar());
        card.add(body);

        return card;
    }

    private void openAddDialog(Buch book) {
        if (book == null) {
            return;
        }

        JPanel pane = new JPanel(new java.awt.BorderLayout(5, 5));
        JTextField ratingField = new JTextField();
        JTextArea commentField = new JTextArea(5, 25);
        commentField.setLineWrap(true);
        commentField.setWrapStyleWord(true);

        pane.add(new JLabel("Rating (1-5):"), java.awt.BorderLayout.NORTH);
        pane.add(ratingField, java.awt.BorderLayout.CENTER);
        pane.add(new JScrollPane(commentField), java.awt.BorderLayout.SOUTH);

        int result = JOptionPane.showConfirmDialog(
                this, pane, "Write review", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            try {
                int stars = Integer.parseInt(ratingField.getText().trim());
                if (stars < 1 || stars > 5) {
                    throw new NumberFormatException();
                }

                Rezension newReview = new Rezension();
                newReview.setBewertung(stars);
                newReview.setKommentar(commentField.getText());
                newReview.setDatum(new Date());

                controller.reviewHinzufuegen(book.getBookId(), newReview);
                loadReviews();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(
                        this,
                        "Rating must be a number from 1 to 5",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

   /* ---------------------------------------------------------------
 * Mini-utilities
 * -------------------------------------------------------------- */
    private static JLabel label(String text, int size, int style, Color color) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Arial", style, size));
        lbl.setForeground(color);
        lbl.setAlignmentX(LEFT_ALIGNMENT);
        return lbl;
    }

    private static JTextArea descriptionArea() {
        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.setOpaque(false);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setForeground(Color.WHITE);
        area.setFont(new Font("Arial", Font.PLAIN, 16));
        area.setAlignmentX(LEFT_ALIGNMENT);
        return area;
    }

    private static JButton textButton(String caption) {
        JButton btn = new JButton(caption);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setOpaque(false);
        btn.setForeground(COLOR_BUTTON_FG);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Arial", Font.BOLD, 16));
        return btn;
    }

    private static String starLine(int rating) {
        StringBuilder sb = new StringBuilder(5);
        for (int i = 0; i < 5; i++) {
            sb.append(i < rating ? '★' : '☆');
        }
        return sb.toString();
    }
}
