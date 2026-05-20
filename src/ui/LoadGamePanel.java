package ui;

import java.nio.file.Paths;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import utils.CommandInterpreter;
import utils.Logger;

public class LoadGamePanel extends JPanel {
    JButton loadGameButton;
    JTextField loadFileField;

    public LoadGamePanel(GameWindow gameWindow, CommandInterpreter commandInterpreter) {
        

        setBackground(UIStyles.backgroundColor);
        loadGameButton = UIFactory.createButton("Betöltés", e -> {
            String savePath = loadFileField.getText();
            if (savePath != null && !savePath.trim().isEmpty()) {
                try (Scanner fileScanner = new Scanner(Paths.get(savePath).toFile())) {
                    while (fileScanner.hasNextLine()) {
                        String line = fileScanner.nextLine().trim();
                        
                        if (line.isBlank() || line.startsWith("#")) {
                            continue; // Skip empty lines and comments
                        }

                        commandInterpreter.execute(line);
                        gameWindow.showGamePanel();
                    }
                        } catch (Exception ex) {
                            Logger.logError("Error loading game: " + ex.getMessage());
                        }
            }
        });
        loadFileField = UIFactory.createTextField(20);

        add(loadFileField);
        add(loadGameButton);
    }    
}
