package ui;
import java.awt.Color;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class UIFactory {
    private UIFactory() {
        // Private constructor to prevent instantiation
    }

    public static JButton createButton(String text, ActionListener action) {
        JButton button = new JButton(text);
        button.setBackground(UIStyles.buttonBackgroundColor);
        button.setForeground(UIStyles.textColor);
        button.setBorder(BorderFactory.createLineBorder(UIStyles.borderColor, 2));
        button.addActionListener(action);
        return button;
    }

    public static JButton createButton(String text, float fontSize, int width, int height, float alignmentX, ActionListener action) {
        JButton button = createButton(text, action);
        button.setFont(button.getFont().deriveFont(fontSize));
        button.setMaximumSize(new java.awt.Dimension(width, height));
        button.setPreferredSize(new java.awt.Dimension(width, height));
        button.setAlignmentX(alignmentX);
        return button;
    }

    public static JLabel createLabel(String text, float fontSize) {
        JLabel label = new JLabel(text);
        label.setBackground(UIStyles.backgroundColor);
        label.setForeground(UIStyles.textColor);
        label.setFont(label.getFont().deriveFont(fontSize));
        return label;
    }
    
    public static JLabel createLabel(String text, float fontSize, Color textColor, float alignmentX) {
        JLabel label = createLabel(text, fontSize);
        label.setForeground(textColor);
        label.setAlignmentX(alignmentX);
        return label;
    }

    public static JTextField createTextField(int columns) {
        JTextField textField = new JTextField(columns);
        textField.setBackground(UIStyles.buttonBackgroundColor);
        textField.setForeground(UIStyles.textColor);
        textField.setBorder(BorderFactory.createLineBorder(UIStyles.borderColor, 2));
        return textField;
    }
}
