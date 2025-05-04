
import javax.swing.*;
import java.awt.*;

public class BookDetailsPanel extends JPanel {
    public BookDetailsPanel(CardLayout cl, JPanel parent) {
        setLayout(new BorderLayout());
        JLabel label = new JLabel("Book Details", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        add(label, BorderLayout.CENTER);

        JButton button = new JButton("Go to Reviews");
        button.addActionListener(e -> cl.show(parent, "reviews"));
        add(button, BorderLayout.SOUTH);
    }
}
