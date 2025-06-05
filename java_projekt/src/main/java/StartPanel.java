/*
 * StartPanel.java
 * -------------------------------------------------------------------
 * Landing screen that shows the Litera logo, a title, a subtitle,
 * and two primary navigation buttons (“Start” / “About”).
 *
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
 * First screen that shows the logo, title and the two main buttons.
 */
public final class StartPanel extends JPanel {

    /*--------------------------------------------------------------------
     * Constants
     *------------------------------------------------------------------*/
    private static final Color      COLOR_BACKGROUND = new Color(7, 31, 45);
    private static final Color      COLOR_ACCENT     = new Color(52, 121, 122);
    private static final Dimension  BUTTON_SIZE      = new Dimension(295, 75);

    /*--------------------------------------------------------------------
     * Constructor
     *------------------------------------------------------------------*/
    /**
     * Creates the start screen and wires the navigation callbacks.
     *
     * @param layout the shared {@link CardLayout} used for navigation
     * @param parent the parent panel containing the {@code CardLayout}
     */
    public StartPanel(final CardLayout layout, final JPanel parent) {
        super(new BorderLayout());
        setBackground(COLOR_BACKGROUND);

        /* --- centre column ----------------------------------------- */
        JPanel column = new JPanel();
        column.setOpaque(false);
        column.setLayout(new BoxLayout(column, BoxLayout.Y_AXIS));

        /* --- logo --------------------------------------------------- */
        ImageIcon rawLogo = new ImageIcon(getClass().getClassLoader().getResource("images/Logo Weiss.png"));
        Image     imgLogo = rawLogo.getImage()
                                   .getScaledInstance(226, 161, Image.SCALE_SMOOTH);
        JLabel logoLabel = new JLabel(new ImageIcon(imgLogo));
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        /* --- titles ------------------------------------------------- */
        JLabel titleLabel = new JLabel("Litera Book Catalog");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 72));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitleLabel = new JLabel("Explore. Discover. Read.");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 32));
        subtitleLabel.setForeground(Color.WHITE);
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        /* --- buttons ------------------------------------------------ */
        JButton startButton = new JButton("Start");
        startButton.setFont(new Font("Arial", Font.PLAIN, 32));
        styleButton(startButton, BUTTON_SIZE);
        startButton.addActionListener(e -> layout.show(parent, "list"));

        JButton aboutButton = new JButton("About");
        aboutButton.setFont(new Font("Arial", Font.PLAIN, 32));
        styleButton(aboutButton, BUTTON_SIZE);
        aboutButton.addActionListener(e -> layout.show(parent, "about"));

        /* --- compose ----------------------------------------------- */
        column.add(Box.createVerticalGlue());
        column.add(logoLabel);
        column.add(Box.createRigidArea(new Dimension(0, 20)));
        column.add(titleLabel);
        column.add(Box.createRigidArea(new Dimension(0, 10)));
        column.add(subtitleLabel);
        column.add(Box.createRigidArea(new Dimension(0, 30)));
        column.add(startButton);
        column.add(Box.createRigidArea(new Dimension(0, 20)));
        column.add(aboutButton);
        column.add(Box.createVerticalGlue());

        add(column, BorderLayout.CENTER);
    }

    /*--------------------------------------------------------------------
     * Helper methods
     *------------------------------------------------------------------*/
    /**
     * Applies consistent styling to a navigation button.
     *
     * @param button the button to style
     * @param size   the preferred/fixed size
     */
    private static void styleButton(final JButton button,
                                    final Dimension size) {

        button.setBackground(COLOR_ACCENT);
        button.setForeground(Color.WHITE);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setPreferredSize(size);
        button.setMaximumSize(size);
        button.setMinimumSize(size);
    }
}





