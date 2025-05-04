
import javax.swing.*;
import java.awt.*;

public class ReviewPanel extends JPanel {
    public ReviewPanel(CardLayout cl, JPanel parent) {
        setLayout(new BorderLayout());
        JLabel label = new JLabel("Reviews", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        add(label, BorderLayout.CENTER);

        JButton button = new JButton("Back to Start");
        button.addActionListener(e -> cl.show(parent, "start"));
        add(button, BorderLayout.SOUTH);
    }
}
