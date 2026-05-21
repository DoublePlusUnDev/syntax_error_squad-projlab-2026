package ui;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class EndPanel extends JPanel {
    public EndPanel(GameWindow gameWindow) {
        setBackground(UIStyles.backgroundColor);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JLabel endLabel = UIFactory.createLabel("Game Over!", 24);
        endLabel.setAlignmentX(CENTER_ALIGNMENT);
        add(endLabel);
        JButton backToMenuButton = UIFactory.createButton("Back to Menu", e -> gameWindow.showMainMenu());
        backToMenuButton.setAlignmentX(CENTER_ALIGNMENT);
        add(backToMenuButton);

    }
    
}
