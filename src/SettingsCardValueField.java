import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.swing.BorderFactory;
import javax.swing.JTextField;

public class SettingsCardValueField extends SettingsCardBase {
    private JTextField valueField;
    private String latestValidValue;

    public SettingsCardValueField(String title, int value, SettingChangeListener listener) {
        super(title);
        valueField = new JTextField();
        latestValidValue = String.valueOf(value);
        valueField.setEditable(true);
        valueField.setBackground(UIStyles.buttonBackgroundColor);
        valueField.setForeground(UIStyles.textColor);
        valueField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        valueField.setFont(valueField.getFont().deriveFont(14.0f));
        valueField.setHorizontalAlignment(JTextField.LEFT);
        valueField.setPreferredSize(new Dimension(80, 30));
        this.add(valueField, BorderLayout.EAST);
        final AtomicBoolean isEdited = new AtomicBoolean(false);
        
        valueField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                update();
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                update();
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                update();
            }

            private void update() {
                if (isEdited.get() || listener == null) return;

                String newValue = valueField.getText();
                
                boolean valid = listener.onSettingChanged(newValue);
                if (valid) {
                    valueField.setForeground(UIStyles.textColor);
                    latestValidValue = newValue;
                } else {
                    valueField.setForeground(UIStyles.invalidColor);
                }
            }
        });

        valueField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (isEdited.get())
                    return;
                
                isEdited.set(true);
                valueField.setText(latestValidValue);
                valueField.setForeground(UIStyles.textColor);
                isEdited.set(false);
            }
        });

        valueField.setText(String.valueOf(value));
    }
}
