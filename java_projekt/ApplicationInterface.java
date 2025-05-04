
import java.awt.*;
import javax.swing.*;

public class ApplicationInterface extends JFrame {
    private CardLayout cardLayout;
    private JPanel cardPanel;

    public ApplicationInterface() {
        setTitle("Litera Book Catalogue");
        setSize(1440, 960);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        cardPanel.add(new StartPanel(cardLayout, cardPanel), "start");
        cardPanel.add(new SearchPanel(cardLayout, cardPanel), "search");
        cardPanel.add(new BookListPanel(cardLayout, cardPanel), "list");
        cardPanel.add(new BookDetailsPanel(cardLayout, cardPanel), "details");
        cardPanel.add(new ReviewPanel(cardLayout, cardPanel), "reviews");

        add(cardPanel);
        cardLayout.show(cardPanel, "start");

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ApplicationInterface::new);
    }
}
