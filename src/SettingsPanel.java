import java.awt.FlowLayout;
import javax.swing.JPanel;

public class SettingsPanel extends JPanel {
    public SettingsPanel() {
        this.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
            this.setBackground(UIStyles.backgroundColor);

            add(new SettingsCardCheckBox("Turn screen: ", false, null));
            add(new SettingsCardValueField("Node min: ", "10", null));
            add(new SettingsCardValueField("Node max: ", "20", null));
            add(new SettingsCardValueField("Main lanes: ", "2", null));
            add(new SettingsCardValueField("Small nodes min: ", "2", null));
            add(new SettingsCardValueField("Small nodes max: ", "5", null));
            add(new SettingsCardValueField("Small node lanes: ", "1", null));
            add(new SettingsCardValueField("Small node extra roads: ", "1", null));
            add(new SettingsCardValueField("Big nodes min: ", "1", null));
            add(new SettingsCardValueField("Big nodes max: ", "3", null));
            add(new SettingsCardValueField("Big node lanes: ", "3", null));
            add(new SettingsCardValueField("Big node extra roads: ", "2", null));
            add(new SettingsCardValueField("Bus stops min: ", "2", null));
            add(new SettingsCardValueField("Bus stops max: ", "5", null));
            add(new SettingsCardValueField("Work places min: ", "2", null));
            add(new SettingsCardValueField("Work places max: ", "5", null));
            add(new SettingsCardValueField("Apartments min: ", "2", null));
            add(new SettingsCardValueField("Apartments max: ", "5", null));
    }
}
