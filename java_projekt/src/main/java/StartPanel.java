/*
 * StartPanel.java
 * ---------------------------------------------------------------------
 * Welcome/start screen of the desktop application "Litera Book Catalog".
 */

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Start/welcome screen.<br>
 * Displays logo, title, subtitle, and a button that switches to the book list.
 */
public final class StartPanel extends JPanel {

    /* ------------------------------------------------------------------
     * Constants – Colors / Sizes
     * ----------------------------------------------------------------- */
    private static final Color COLOR_BACKGROUND = new Color(7, 31, 45);
    private static final Color COLOR_ACCENT     = new Color(52, 121, 122);

    private static final Dimension BTN_SIZE = new Dimension(295, 75);

    /**
     * Creates the start screen.
     *
     * @param layout  shared {@link CardLayout}
     * @param parent  parent panel containing the {@code CardLayout}
     */
    public StartPanel(final CardLayout layout, final JPanel parent) {
        super(new BorderLayout());
        setBackground(COLOR_BACKGROUND);

        /* ---------- Central vertical panel ------------------------ */
        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setOpaque(false); // transparent background

        /* --------------------------- Logo ----------------------------- */
        ImageIcon rawLogo  = new ImageIcon("src/main/java/images/Logo Weiss.png");
        Image     imgLogo  = rawLogo.getImage()
                                    .getScaledInstance(226, 161, Image.SCALE_SMOOTH);
        JLabel    logoLbl  = new JLabel(new ImageIcon(imgLogo));
        logoLbl.setAlignmentX(Component.CENTER_ALIGNMENT);

        /* ------------------------ Main title -------------------------- */
        JLabel titleLbl = new JLabel("Litera Book Catalog");
        titleLbl.setFont(new Font("Arial", Font.BOLD, 72));
        titleLbl.setForeground(Color.WHITE);
        titleLbl.setAlignmentX(Component.CENTER_ALIGNMENT);

        /* ------------------------ Subtitle ---------------------------- */
        JLabel subtitleLbl = new JLabel("Explore. Discover. Read.");
        subtitleLbl.setFont(new Font("Arial", Font.PLAIN, 32));
        subtitleLbl.setForeground(Color.WHITE);
        subtitleLbl.setAlignmentX(Component.CENTER_ALIGNMENT);

        /* -------------------------- Button ---------------------------- */
        JButton startBtn = new JButton("Start");
        startBtn.setFont(new Font("Arial", Font.PLAIN, 32));
        startBtn.setBackground(COLOR_ACCENT);
        startBtn.setForeground(Color.WHITE);
        startBtn.setOpaque(true);
        startBtn.setContentAreaFilled(false); // no default Swing background
        startBtn.setBorderPainted(false);
        startBtn.setFocusPainted(false);
        startBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        startBtn.setPreferredSize(BTN_SIZE);
        startBtn.setMaximumSize(BTN_SIZE);
        startBtn.setMinimumSize(BTN_SIZE);

        /* ---- Action: switch to book list ---------------------------- */
        startBtn.addActionListener(evt -> layout.show(parent, "list"));

        /* ---- Arrange components ------------------------------------- */
        center.add(Box.createVerticalGlue());
        center.add(logoLbl);
        center.add(Box.createRigidArea(new Dimension(0, 20)));
        center.add(titleLbl);
        center.add(Box.createRigidArea(new Dimension(0, 10)));
        center.add(subtitleLbl);
        center.add(Box.createRigidArea(new Dimension(0, 30)));
        center.add(startBtn);
        center.add(Box.createVerticalGlue());

        add(center, BorderLayout.CENTER);
    }
}