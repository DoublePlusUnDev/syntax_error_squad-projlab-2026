package ui;

import java.io.File;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import utils.Logger;

public class SavePanel extends JPanel {
    JButton saveButton;
    JTextField saveNameField;

    public SavePanel() {
        
        setBackground(UIStyles.backgroundColor);   
        saveButton = UIFactory.createButton("Játék mentése", e -> {
            File saveFile = new File(saveNameField.getText());
            Logger.saveGameState(saveFile.toPath());
        });
        saveButton.setAlignmentX(LEFT_ALIGNMENT);
        saveNameField = UIFactory.createTextField(20);
        saveNameField.setAlignmentX(LEFT_ALIGNMENT);

        add(saveButton);
        add(saveNameField);
    }
}
