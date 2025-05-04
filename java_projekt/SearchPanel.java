
import javax.swing.*;
import java.awt.*;

public class SearchPanel extends JPanel {
    public SearchPanel(CardLayout cl, JPanel parent) {
        setLayout(new BorderLayout());
        JLabel label = new JLabel("Search Screen", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        add(label, BorderLayout.CENTER);

        JButton button = new JButton("Go to List");
        button.addActionListener(e -> cl.show(parent, "list"));
        add(button, BorderLayout.SOUTH);
    }
}
