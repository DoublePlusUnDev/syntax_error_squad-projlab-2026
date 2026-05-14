import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class VehiclePanel extends JPanel {
    public VehiclePanel() {
        setLayout(new BorderLayout());
        setBackground(UIStyles.backgroundColor);

        JLabel label = new JLabel("Vehicle info will be displayed here.");
        label.setHorizontalAlignment(JLabel.CENTER);
        add(label, BorderLayout.CENTER);
    }
    
}
