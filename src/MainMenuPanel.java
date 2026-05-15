import java.awt.Dimension;
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

    

    public MainMenuPanel(GameUI gameUI) {
        this.setBackground(UIStyles.backgroundColor);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(Box.createVerticalGlue());

        titleLabel = UIFactory.createLabel("ZÚZMARAVÁROS", 44.0f, UIStyles.textColor, CENTER_ALIGNMENT);
        add(titleLabel);

        add(Box.createRigidArea(new Dimension(0, 10)));

        creatorLabel = UIFactory.createLabel("--- SYNTAX ERROR SQUAD ---", 18.0f, UIStyles.borderColor, CENTER_ALIGNMENT);
        add(creatorLabel);

        add(Box.createRigidArea(new Dimension(0, 40)));

        newGameButton = UIFactory.createButton("Új játék", 18.0f, 240, 40, CENTER_ALIGNMENT, e -> gameUI.startGame());
        add(newGameButton);

        add(Box.createRigidArea(new Dimension(0, 10)));

        loadGameButton = UIFactory.createButton("Játék Betöltése", 18.0f, 240, 40, CENTER_ALIGNMENT, null);
        add(loadGameButton);

        add(Box.createRigidArea(new Dimension(0, 10)));

        settingsButton = UIFactory.createButton("Beállítások", 18.0f, 240, 40, CENTER_ALIGNMENT, e -> gameUI.showSettingsMenu());
        add(settingsButton);

        add(Box.createRigidArea(new Dimension(0, 10)));

        exitButton = UIFactory.createButton("Kilépés", 18.0f, 240, 40, CENTER_ALIGNMENT, e -> gameUI.quitApplication());
        add(exitButton);

        add(Box.createVerticalGlue());
    }
}
