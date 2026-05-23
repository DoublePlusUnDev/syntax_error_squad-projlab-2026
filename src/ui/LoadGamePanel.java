package ui;

import java.nio.file.Paths;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import utils.CommandInterpreter;
import utils.Logger;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JOptionPane;

public class LoadGamePanel extends JPanel {

    JButton loadGameButton;
    JButton backButton;
    JTextField loadFileField;

    public LoadGamePanel(GameWindow gameWindow, CommandInterpreter commandInterpreter) {

        setBackground(UIStyles.backgroundColor);
        setLayout(new BorderLayout());

        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(UIStyles.backgroundColor);

        loadFileField = UIFactory.createTextField(20);

        loadGameButton = UIFactory.createButton("Betöltés", e -> {
            String savePath = loadFileField.getText();

            if (savePath != null && !savePath.trim().isEmpty()) {
                try (Scanner fileScanner = new Scanner(Paths.get(savePath).toFile())) {

                    while (fileScanner.hasNextLine()) {
                        String line = fileScanner.nextLine().trim();

                        if (line.isBlank() || line.startsWith("#")) {
                            continue;
                        }

                        commandInterpreter.execute(line);
                    }

                    gameWindow.showGamePanel();

                } catch (Exception ex) {
                    Logger.logError("Error loading game: " + ex.getMessage());

                    JOptionPane.showMessageDialog(
                        this,
                        "Nem sikerült betölteni az alábbi játékot: " + ex.getMessage(),
                        "Betöltési hiba",
                        JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });

        backButton = UIFactory.createButton("Vissza", e -> {
            gameWindow.showMainMenu();
        });

        backButton.setPreferredSize(new Dimension(0, 40));

        centerPanel.add(loadFileField);
        centerPanel.add(loadGameButton);

        loadGameButton.setFont(loadGameButton.getFont().deriveFont(20.0f));
        backButton.setFont(backButton.getFont().deriveFont(14.0f));
        loadFileField.setFont(loadFileField.getFont().deriveFont(20.0f));

        add(centerPanel, BorderLayout.CENTER);
        add(backButton, BorderLayout.SOUTH);
    }
}