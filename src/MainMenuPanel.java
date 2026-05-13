import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainMenuPanel extends JPanel {
    JLabel titleLabel;
    JLabel creatorLabel;

    JButton newGameButton;
    JButton loadGameButton;
    JButton settingsButton;
    JButton exitButton;

    

    public MainMenuPanel() {
        this.setBackground(UIStyles.backgroundColor);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(Box.createVerticalGlue());

        titleLabel = new JLabel("ZÚZMARAVÁROS");
        titleLabel.setBackground(UIStyles.backgroundColor);
        titleLabel.setForeground(UIStyles.textColor);
        titleLabel.setFont(titleLabel.getFont().deriveFont(44.0f));
        titleLabel.setAlignmentX(CENTER_ALIGNMENT);
        add(titleLabel);

        add(Box.createRigidArea(new Dimension(0, 10)));

        creatorLabel = new JLabel("--- SYNTAX ERROR SQUAD ---");
        creatorLabel.setBackground(UIStyles.backgroundColor);
        creatorLabel.setForeground(UIStyles.buttonBorderColor);
        creatorLabel.setFont(creatorLabel.getFont().deriveFont(18.0f));
        creatorLabel.setAlignmentX(CENTER_ALIGNMENT);
        add(creatorLabel);

        add(Box.createRigidArea(new Dimension(0, 40)));

        newGameButton = new JButton("Új Játék");
        newGameButton.setBackground(UIStyles.buttonBackgroundColor);
        newGameButton.setBorder(BorderFactory.createLineBorder(UIStyles.buttonBorderColor, 2));
        newGameButton.setForeground(UIStyles.textColor);
        newGameButton.setAlignmentX(CENTER_ALIGNMENT);
        newGameButton.setMaximumSize(new Dimension(240, 40));
        newGameButton.setPreferredSize(new Dimension(240, 40));
        newGameButton.setFont(newGameButton.getFont().deriveFont(18.0f));
        add(newGameButton);

        add(Box.createRigidArea(new Dimension(0, 10)));

        loadGameButton = new JButton("Játék Betöltése");
        loadGameButton.setBackground(UIStyles.buttonBackgroundColor);
        loadGameButton.setBorder(BorderFactory.createLineBorder(UIStyles.buttonBorderColor, 2));
        loadGameButton.setForeground(UIStyles.textColor);
        loadGameButton.setAlignmentX(CENTER_ALIGNMENT);
        loadGameButton.setMaximumSize(new Dimension(240, 40));
        loadGameButton.setPreferredSize(new Dimension(240, 40));
        loadGameButton.setFont(loadGameButton.getFont().deriveFont(18.0f));
        add(loadGameButton);

        add(Box.createRigidArea(new Dimension(0, 10)));

        settingsButton = new JButton("Beállítások");
        settingsButton.setBackground(UIStyles.buttonBackgroundColor);
        settingsButton.setBorder(BorderFactory.createLineBorder(UIStyles.buttonBorderColor, 2));
        settingsButton.setForeground(UIStyles.textColor);
        settingsButton.setAlignmentX(CENTER_ALIGNMENT);
        settingsButton.setMaximumSize(new Dimension(240, 40));
        settingsButton.setPreferredSize(new Dimension(240, 40));
        settingsButton.setFont(settingsButton.getFont().deriveFont(18.0f));
        settingsButton.addActionListener(e -> GameUI.getInstance().showSettingsMenu());
        add(settingsButton);

        add(Box.createRigidArea(new Dimension(0, 10)));

        exitButton = new JButton("Kilépés");
        exitButton.setBackground(UIStyles.buttonBackgroundColor);
        exitButton.setBorder(BorderFactory.createLineBorder(UIStyles.buttonBorderColor, 2));
        exitButton.setForeground(UIStyles.textColor);
        exitButton.setAlignmentX(CENTER_ALIGNMENT);
        exitButton.setMaximumSize(new Dimension(240, 40));
        exitButton.setPreferredSize(new Dimension(240, 40));
        exitButton.setFont(exitButton.getFont().deriveFont(18.0f));
        exitButton.addActionListener(e -> GameUI.getInstance().quitApplication());
        add(exitButton);

        add(Box.createVerticalGlue());
    }
}
