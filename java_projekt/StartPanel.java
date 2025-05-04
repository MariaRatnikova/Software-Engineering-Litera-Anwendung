
import javax.swing.*;
import java.awt.*;

public class StartPanel extends JPanel {
    public StartPanel(CardLayout cl, JPanel parent) {
        setLayout(new BorderLayout());
        JLabel label = new JLabel("Start Screen", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        add(label, BorderLayout.CENTER);

        JButton button = new JButton("Go to Search");
        button.addActionListener(e -> cl.show(parent, "search"));
        add(button, BorderLayout.SOUTH);
    }
}
