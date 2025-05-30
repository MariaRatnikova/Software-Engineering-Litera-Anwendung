/*
 * AboutPanel.java
 * ----------------------------------------------------------------------
 * Scrollable “About Litera” screen that plugs into the application’s
 * global CardLayout.  Uses the same dark-green theme as the rest of the
 * UI and offers a single “← Back” button that returns to the panel
 * tagged "start".
 */

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

/**
 * A modeless, scroll-enabled panel that presents company information
 * about Litera.
 */
public final class AboutPanel extends JPanel {

    /*--------------------------------------------------------------------
     * Palette / style constants
     *------------------------------------------------------------------*/
    private static final Color COLOR_BG        = new Color(8, 37, 40);
    private static final Color COLOR_ACCENT    = new Color(166, 221, 211);
    private static final Color COLOR_BUTTON_BG = new Color(52, 121, 122);
    private static final Font  UI_FONT         = new Font("Arial", Font.PLAIN, 18);

    /*--------------------------------------------------------------------
     * Navigation
     *------------------------------------------------------------------*/
    private final CardLayout navigator;
    private final JPanel     root;

    /*--------------------------------------------------------------------
     * Construction
     *------------------------------------------------------------------*/
    /**
     * @param nav   the shared CardLayout used by the root container
     * @param root  the container that owns {@code nav}
     */
    public AboutPanel(final CardLayout nav, final JPanel root) {
        super(new BorderLayout());
        this.navigator = nav;
        this.root      = root;

        setBackground(COLOR_BG);

        add(buildHeader(), BorderLayout.NORTH);
        add(buildScrollPane(), BorderLayout.CENTER);
    }

    /*====================================================================
     * Public API
     *==================================================================*/

    /**
     * Moves the CardLayout back to the “start” card.
     */
    public void goBack() {
        navigator.show(root, "start");
    }

    /*====================================================================
     * Private helpers
     *==================================================================*/

    /** Creates the fixed top bar with the back button and the logo. */
    private JComponent buildHeader() {
        JPanel bar = new JPanel();
        bar.setOpaque(false);
        bar.setLayout(new BoxLayout(bar, BoxLayout.X_AXIS));
        bar.setBorder(new EmptyBorder(15, 20, 5, 20));

        JButton back = new JButton("← Back");
        back.setFont(UI_FONT.deriveFont(Font.BOLD, 16f));
        back.setForeground(Color.WHITE);
        back.setBackground(COLOR_BUTTON_BG);
        back.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        back.setFocusPainted(false);
        back.setBorderPainted(false);
        back.setOpaque(true);
        back.addActionListener(e -> goBack());
        bar.add(back);

        bar.add(Box.createHorizontalGlue());

        /* --- Litera logo (aspect-ratio aware) --------------------- */
        ImageIcon rawIcon = new ImageIcon("src/main/java/images/Litera.png");
        JLabel logo;
        if (rawIcon.getIconWidth() > 0) {
            final int targetWidth = 160;
            int targetHeight = rawIcon.getIconHeight() * targetWidth
                               / rawIcon.getIconWidth();
            Image scaled = rawIcon.getImage()
                                  .getScaledInstance(targetWidth,
                                                     targetHeight,
                                                     Image.SCALE_SMOOTH);
            logo = new JLabel(new ImageIcon(scaled));
        } else {
            /* Fallback placeholder if the file is missing */
            logo = makeLabel("Litera", 24, Font.BOLD, COLOR_ACCENT);
        }
        bar.add(logo);

        return bar;
    }

    /** Wraps the content column in a JScrollPane. */
    private JScrollPane buildScrollPane() {
        JScrollPane scroll = new JScrollPane(buildContentColumn(),
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setBorder(null);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        return scroll;
    }

    /** Builds the vertically-stacked information column. */
    private JComponent buildContentColumn() {
        JPanel col = new JPanel();
        col.setOpaque(false);
        col.setLayout(new BoxLayout(col, BoxLayout.Y_AXIS));
        col.setBorder(new EmptyBorder(30, 60, 40, 60));

        col.add(title("Litera – Your World of Stories"));
        col.add(vGap(25));

        col.add(pair("Founded:",        "2009"));
        col.add(pair("Founder:",        "Mark Stolz"));
        col.add(pair("Managing Board:", "Mark Stolz & Edwin Hartmann"));
        col.add(pair("Employees:",      "50"));
        col.add(pair("Headquarters:",   "Dresden"));
        col.add(pair("Locations:",
                     "Dresden, Berlin, Hamburg, Frankfurt, Munich, Bremen"));
        col.add(vGap(20));

        col.add(section("Philosophy"));
        col.add(wrap(
            "Litera offers high-quality books across many genres – "
          + "from modern classics to romantic comedies – while creating a "
          + "welcoming atmosphere that inspires a love of reading."));
        col.add(vGap(20));

        col.add(section("Target Customers"));
        col.add(bullet("Literature lovers"));
        col.add(bullet("Students"));
        col.add(bullet("Families"));
        col.add(bullet("Schools"));
        col.add(vGap(20));

        col.add(section("Milestones"));
        col.add(bullet("2009 – First Litera store opens in Dresden"));
        col.add(bullet("2012 – Expansion to Berlin and Frankfurt"));
        col.add(bullet("2023 – Partnership with regional publishers "
                     + "for exclusive titles"));
        col.add(vGap(20));

        col.add(section("Awards"));
        col.add(bullet("2018 – Regional Literature Promotion Award"));
        col.add(bullet("2023 – Dresden Cultural Award"));

        return col;
    }

    /*--------------------------------------------------------------------
     * Tiny label factory helpers
     *------------------------------------------------------------------*/
    private static JLabel title(final String txt) {
        return makeLabel(txt, 34, Font.BOLD, Color.WHITE);
    }

    private static JLabel section(final String txt) {
        return makeLabel(txt, 28, Font.BOLD, COLOR_ACCENT);
    }

    private static JLabel pair(final String key, final String val) {
        String html = "<html><span style='color:#a6ddd3;'>" + key
                    + "</span> " + val + "</html>";
        return makeLabel(html, 18, Font.PLAIN, Color.WHITE);
    }

    private static JLabel bullet(final String txt) {
        return makeLabel("• " + txt, 18, Font.PLAIN, Color.WHITE);
    }

    private static JLabel wrap(final String txt) {
        String html = "<html><div style='width:560px;'>" + txt + "</div></html>";
        return makeLabel(html, 18, Font.PLAIN, Color.WHITE);
    }

    private static JLabel makeLabel(final String txt,
                                    final int size,
                                    final int style,
                                    final Color fg) {

        JLabel label = new JLabel(txt);
        label.setFont(UI_FONT.deriveFont(style, size));
        label.setForeground(fg);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    private static Component vGap(final int px) {
        return Box.createVerticalStrut(px);
    }
}
