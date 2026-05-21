package ui;

import gamelogic.GameLogic;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class WaitPanel extends JPanel {
    private transient GameWindow gameWindow;
    private final GameLogic gameLogic;
    private final SettingsManager settingsManager;
    private JLabel topLabel;
    private JButton resumeButton;

    public WaitPanel(GameWindow gameWindow, GameLogic gameLogic, SettingsManager settingsManager) {
        this.gameWindow = gameWindow;
        this.gameLogic = gameLogic;
        this.settingsManager = settingsManager;
        setBackground(UIStyles.backgroundColor);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        topLabel = UIFactory.createLabel("<Player> köre következik!", 24);
        topLabel.setAlignmentX(CENTER_ALIGNMENT);
        add(topLabel);

        resumeButton = UIFactory.createButton("Folytatás", e -> gameWindow.showGamePanel());
        resumeButton.setAlignmentX(CENTER_ALIGNMENT);
        add(resumeButton);
        gameLogic.addTurnEndedListener(this::update);
    }

    private void update(){
        if (!(boolean) settingsManager.getSetting(SettingsManager.TURN_WINDOW_KEY)) {
            gameWindow.showGamePanel();
            return;
        }

        String currentPlayer = gameLogic.getCurrentPlayer().id;
        topLabel.setText(currentPlayer + " köre következik!");
    }
}
