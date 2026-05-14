import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.concurrent.atomic.AtomicBoolean;
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
                System.out.println("Value changed: " + newValue);
                
                boolean ok = listener.onSettingChanged(newValue);
                if (ok) {
                    valueField.setForeground(UIStyles.textColor);
                } else {
                    javax.swing.SwingUtilities.invokeLater(() -> {
                        isEdited.set(true);
                        //valueField.setText(value);
                        isEdited.set(false);
                        valueField.setForeground(UIStyles.invalidColor);
                    });
                }
                
            }
        });
    }
}
