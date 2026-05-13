import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public abstract class SettingsCardBase extends JPanel {
    public interface SettingChangeListener {
        boolean onSettingChanged(String newValue);
    }



    public SettingsCardBase(String title) {
        setBackground(UIStyles.buttonBackgroundColor);
        setBorder(BorderFactory.createLineBorder(UIStyles.buttonBorderColor, 2));
        setPreferredSize(new Dimension(240, 40));

        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel(title);

        add(titleLabel, BorderLayout.WEST);
    }
}
