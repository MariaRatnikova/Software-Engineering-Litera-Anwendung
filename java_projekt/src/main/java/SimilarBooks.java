/*
 * SimilarBooks.java
 * ------------------------------------------------------------------
 * Horizontal thumbnail gallery that shows books “similar” to the one
 * currently displayed on the {@link BookDetails} page.
 *
 *  • One single-row panel wrapped in a JScrollPane (horizontal bar only)
 *  • Each tile: 120 × 180 cover + centred title (max two lines)
 *  • Clicking a tile replaces the content of BookDetails with that book
 *
 */

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

/** Horizontal gallery of book thumbnails (“Similar Books” section). */
public final class SimilarBooks extends JPanel {

    /* ------------------------------------------------------------------
     *  Constants
     * ---------------------------------------------------------------- */
    private static final int  TILE_W   = 130;  // logical tile width
    private static final int  TILE_H   = 220;  // logical tile height
    private static final int  GAP_H    = 15;   // gap between tiles
    private static final int  GAP_V    = 10;   // vertical padding in FlowLayout
    private static final int  VIEW_W   = 1_250;  // same limit as BookDetails.WIDTH_MAX
    private static final int  VIEW_H   = TILE_H + 2 * GAP_V;

    private static final Color COLOR_TEXT = Color.WHITE;

    /* ------------------------------------------------------------------
     *  Dependencies
     * ---------------------------------------------------------------- */
    private final ApplicationInterface controller;
    private final BookDetails          bookDetails;

    /* ------------------------------------------------------------------
     *  C-tor
     * ---------------------------------------------------------------- */
    public SimilarBooks(ApplicationInterface ctl,
                        CardLayout navigator,
                        JPanel     parent,
                        BookDetails details) {

        this.controller  = ctl;
        this.bookDetails = details;

        setLayout(new BorderLayout());
        setOpaque(false);
    }

    /* ------------------------------------------------------------------
     *  Public API
     * ---------------------------------------------------------------- */
    /** Refreshes the gallery for the given reference book. */
    public void showFor(String bookId) {
        removeAll();

        /* --- section headline ------------------------------------- */
        JLabel head = new JLabel("Similar Books");
        head.setFont(new Font("Arial", Font.BOLD, 32));
        head.setForeground(COLOR_TEXT);
        head.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        add(head, BorderLayout.NORTH);

        /* --- flow row with tiles ---------------------------------- */
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, GAP_H, GAP_V));
        row.setOpaque(false);

        List<Buch> similar = controller.aehnlich(bookId);
        similar.stream()
               .filter(b -> !b.getBookId().equals(bookId))
               .forEach(b -> row.add(createTile(b)));

        /* --- scroll wrapper (horizontal only) --------------------- */
        JScrollPane scroll = new JScrollPane(
                row,
                JScrollPane.VERTICAL_SCROLLBAR_NEVER,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setBorder(null);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.getHorizontalScrollBar().setUnitIncrement(16);

        /* ►►  FIX: constrain the viewport so BoxLayout in BookDetails
         *      cannot stretch it; when row > VIEW_W the h-bar appears   */
        scroll.setPreferredSize(new Dimension(VIEW_W, VIEW_H));
        scroll.setMaximumSize  (new Dimension(VIEW_W, VIEW_H));
        scroll.setMinimumSize  (new Dimension(VIEW_W, VIEW_H));

        add(scroll, BorderLayout.CENTER);

        revalidate();
        repaint();
    }

    /* ------------------------------------------------------------------
     *  Helpers
     * ---------------------------------------------------------------- */
    /** Builds one 120 × 180 thumbnail tile. */
    private JPanel createTile(Buch b) {

        /* cover ----------------------------------------------------- */
        ImageIcon raw = new ImageIcon(b.getImage());
        Image scaled  = raw.getImage()
                           .getScaledInstance(120, 180, Image.SCALE_SMOOTH);
        JLabel cover  = new JLabel(new ImageIcon(scaled));
        cover.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        cover.setAlignmentX(CENTER_ALIGNMENT);

        /* title ----------------------------------------------------- */
        String html =
            "<html><div style='text-align:center;width:118px'>" +
            b.getTitle() + "</div></html>";
        JLabel title = new JLabel(html, SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.PLAIN, 14));
        title.setForeground(COLOR_TEXT);
        title.setAlignmentX(CENTER_ALIGNMENT);

        /* tile container ------------------------------------------- */
        JPanel tile = new JPanel();
        tile.setOpaque(false);
        tile.setLayout(new BoxLayout(tile, BoxLayout.Y_AXIS));
        tile.add(cover);
        tile.add(Box.createVerticalStrut(4));
        tile.add(title);

        tile.setPreferredSize(new Dimension(TILE_W, TILE_H));
        tile.setMaximumSize  (new Dimension(TILE_W, TILE_H));

        /* click → load in BookDetails ------------------------------ */
        MouseAdapter click = new MouseAdapter() {
            @Override public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 1) {
                    bookDetails.showBook(b);
                }
            }
        };
        cover .addMouseListener(click);
        title .addMouseListener(click);
        tile  .setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        return tile;
    }
}