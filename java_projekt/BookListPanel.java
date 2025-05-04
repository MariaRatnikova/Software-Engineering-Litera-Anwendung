import java.awt.*;
import javax.swing.*;

public class BookListPanel extends JPanel {

    public BookListPanel(CardLayout cl, JPanel parent) {
        // Setzt das Layout dieses Panels auf BorderLayout
        setLayout(new BorderLayout());

        // Erstellen der oberen Leiste (grünes Rechteck)
        JPanel topBarPanel = new JPanel();
        topBarPanel.setPreferredSize(new Dimension(1440, 175)); // Gesamtbreite des Fensters
        topBarPanel.setBackground(new Color(52, 121, 122)); // Grüner Farbton (aus dem Design)
        topBarPanel.setLayout(new FlowLayout(FlowLayout.LEFT)); // Elemente von links nach rechts platzieren

        // Laden und Skalieren des Logos
        ImageIcon originalIcon = new ImageIcon("java_projekt/images/LogoText.png");
        Image scaledImage = originalIcon.getImage().getScaledInstance(114, 123, Image.SCALE_SMOOTH); // Skalieren laut Figma
        JLabel logoLabel = new JLabel(new ImageIcon(scaledImage)); // Bild in JLabel setzen

        // Wrapper-Panel für das Logo, um Abstand (Padding) zu steuern
        JPanel logoWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0)); // Keine zusätzlichen Abstände
        logoWrapper.setPreferredSize(new Dimension(200, 160)); // Größe etwas größer für Abstand
        logoWrapper.setOpaque(false); // Hintergrund durchsichtig
        logoWrapper.setBorder(BorderFactory.createEmptyBorder(20, 70, 0, 0)); // Abstand: oben 20px, links 70px

        // Logo ins Wrapper-Panel einfügen
        logoWrapper.add(logoLabel);
        // Wrapper-Panel zur oberen Leiste hinzufügen
        topBarPanel.add(logoWrapper);

        // Obere Leiste im oberen Bereich (NORTH) des Hauptpanels platzieren
        add(topBarPanel, BorderLayout.NORTH);

        String[] suchoptionen = {"Title", "Author", "Publisher", "Genre"};
JComboBox<String> suchBox = new JComboBox<>(suchoptionen);

// Цвет, размер, шрифт
suchBox.setBackground(new Color(166, 221, 211)); // светло-зелёный
suchBox.setForeground(Color.BLACK);
suchBox.setFont(new Font("Arial", Font.PLAIN, 18));
suchBox.setPreferredSize(new Dimension(200, 50));

// Убираем стандартную рамку и добавляем внутренние отступы
suchBox.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
suchBox.setFocusable(false);

// Кастомизируем стрелку
suchBox.setUI(new javax.swing.plaf.basic.BasicComboBoxUI() {
    @Override
    protected JButton createArrowButton() {
        JButton button = new JButton("\u25BE"); // ▼
        button.setFont(new Font("Arial", Font.PLAIN, 18));
        button.setForeground(Color.BLACK);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        return button;
    }
});

// Добавляем в твою верхнюю панель
topBarPanel.add(suchBox);


// --- Suchfeld ---
        JTextField suchfeld = new JTextField();
        suchfeld.setPreferredSize(new Dimension(800, 40));
        suchfeld.setFont(new Font("Arial", Font.PLAIN, 16));

// --- Suchknopf с иконкой лупы ---
        ImageIcon suchIcon = new ImageIcon("java_projekt/images/search-icon.png");
        Image scaledSearch = suchIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        JButton suchButton = new JButton(new ImageIcon(scaledSearch));
        suchButton.setPreferredSize(new Dimension(50, 40));
        suchButton.setBackground(Color.WHITE);
        suchButton.setFocusPainted(false);

// --- Панель поиска (dropdown + textfield + button) ---
        JPanel searchBarPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));
        searchBarPanel.setOpaque(false); // прозрачный фон
        searchBarPanel.add(suchBox);
        searchBarPanel.add(suchfeld);
        searchBarPanel.add(suchButton);

// --- Добавить в верхнюю панель справа от логотипа ---
        topBarPanel.add(searchBarPanel);

        // Haupttitel in der Mitte (Platzhalter)
        JLabel label = new JLabel("Book List", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        add(label, BorderLayout.CENTER); // Mitte des Fensters

        // Button ganz unten zur Navigation (z.B. zu Buchdetails)
        JButton button = new JButton("Go to Details");
        button.addActionListener(e -> cl.show(parent, "details")); // Bei Klick: Ansicht wechseln
        add(button, BorderLayout.SOUTH); // Unten platzieren
    }
}