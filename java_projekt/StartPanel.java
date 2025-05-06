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
 * Startbildschirm des Buchkatalogs („Willkommen“-Seite).
 * Zeigt Logo, Titel, Untertitel und eine Schaltfläche zum Starten.
 */
public class StartPanel extends JPanel {

    /**
     * Konstruktor – erstellt die Startseite mit Logo, Titel und Start-Button.
     * 
     * @param cl     CardLayout-Manager zum Wechseln der Panels
     * @param parent Übergeordnetes Panel mit CardLayout
     */
    public StartPanel(CardLayout cl, JPanel parent) {
        setLayout(new BorderLayout());                       // Hauptlayout: oben/unten/mitte
        setBackground(new Color(7, 31, 45));                 // Dunkler Hintergrund wie im Design

        // Zentrale Panel für Logo, Text und Button (vertikal angeordnet)
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS)); // vertikale Anordnung
        centerPanel.setOpaque(false); // Hintergrund bleibt durchsichtig (nimmt Elternfarbe)

        // Logo laden und skalieren
        ImageIcon logoIcon = new ImageIcon("images/Logo Weiss.png"); // Bildpfad ggf. anpassen
        Image scaledLogo = logoIcon.getImage().getScaledInstance(226, 161, Image.SCALE_SMOOTH);
        JLabel logoLabel = new JLabel(new ImageIcon(scaledLogo));
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // zentrieren

        // Haupttitel
        JLabel titleLabel = new JLabel("Litera Book Catalog");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 72));
        titleLabel.setForeground(Color.WHITE); // weiße Schrift
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Untertitel
        JLabel subtitleLabel = new JLabel("Explore. Discover. Read.");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 32));
        subtitleLabel.setForeground(Color.WHITE);
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Start-Button zum Wechseln auf die Bücherliste
        JButton startButton = new JButton("Start");
        startButton.setFont(new Font("Arial", Font.PLAIN, 32));
        startButton.setBackground(new Color(52, 121, 122)); // Button-Farbe
        startButton.setForeground(Color.WHITE);             // Textfarbe

        // Optische Einstellungen für benutzerdefiniertes Aussehen
        startButton.setOpaque(true);               // Zeichenfläche aktivieren
        startButton.setContentAreaFilled(false);   // verhindert Standard-Hintergrund
        startButton.setBorderPainted(false);       // keine Rahmenlinie
        startButton.setFocusPainted(false);        // kein Fokus-Rahmen

        // Größe und Ausrichtung des Buttons
        startButton.setPreferredSize(new Dimension(295, 75));
        startButton.setMaximumSize(new Dimension(295, 75));
        startButton.setMinimumSize(new Dimension(295, 75));
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Aktion: Wechsel zu „list“-Panel (Buchübersicht)
        startButton.addActionListener(e -> cl.show(parent, "list"));

        // Komponenten in centerPanel einfügen, mit vertikalen Abständen
        centerPanel.add(Box.createVerticalGlue());                      // Abstand nach oben
        centerPanel.add(logoLabel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 20)));     // Abstand zwischen Logo und Titel
        centerPanel.add(titleLabel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 10)));     // Abstand zwischen Titel und Untertitel
        centerPanel.add(subtitleLabel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 30)));     // Abstand vor dem Button
        centerPanel.add(startButton);
        centerPanel.add(Box.createVerticalGlue());                      // Abstand nach unten

        // Zentrales Panel in die Mitte des Hauptpanels einfügen
        add(centerPanel, BorderLayout.CENTER);
    }
}