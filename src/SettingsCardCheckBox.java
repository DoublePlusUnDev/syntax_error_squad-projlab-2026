
import javax.swing.JCheckBox;

public class SettingsCardCheckBox extends SettingsCardBase {
    private JCheckBox checkBox;

    public SettingsCardCheckBox(String title, boolean initialValue, SettingChangeListener listener) {
        super(title);
        checkBox = new JCheckBox();
        checkBox.setSelected(initialValue);
        checkBox.setBackground(UIStyles.buttonBackgroundColor);
        checkBox.setForeground(UIStyles.textColor);
        this.add(checkBox, java.awt.BorderLayout.EAST);
        checkBox.addChangeListener(e -> {
            boolean newValue = checkBox.isSelected();
            if (listener != null && !listener.onSettingChanged(Boolean.toString(newValue))) {
                checkBox.setSelected(!newValue);
            }
        });
    }
    
}
