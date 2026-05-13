import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JTextField;

public class SettingsCardValueField extends SettingsCardBase {
    private JTextField valueField;

    public SettingsCardValueField(String title, String value, SettingChangeListener listener) {
        super(title);
        valueField = new JTextField(value);
        valueField.setEditable(true);
        valueField.setBackground(UIStyles.buttonBackgroundColor);
        valueField.setForeground(UIStyles.textColor);
        valueField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        valueField.setFont(valueField.getFont().deriveFont(14.0f));
        valueField.setHorizontalAlignment(JTextField.LEFT);
        valueField.setPreferredSize(new Dimension(80, 30));
        this.add(valueField, BorderLayout.EAST);
        valueField.addActionListener(e -> {
            String newValue = valueField.getText();
            if (listener != null && !listener.onSettingChanged(newValue)) {
                valueField.setText(value);
                valueField.setForeground(UIStyles.textColor);
            }
            else{
                valueField.setForeground(UIStyles.invalidColor);
            }
        });
    }
}
