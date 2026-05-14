import java.awt.CardLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameUI extends JFrame {

    private CardLayout cardLayout;
    private JPanel cards;

    private static GameUI instance;
    public final static String MAIN_MENU = "MainMenu";
    public final static String SETTINGS_MENU = "SettingsMenu";
    public final static String GAME_PANEL = "GamePanel";
    
    
    public GameUI(CommandInterpreter commandInterpreter) {
        setTitle("Projlab Traffic Game ");
        setSize(1280, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        cards = new JPanel(cardLayout);

        MainMenuPanel mainMenuPanel = new MainMenuPanel(this);
        cards.add(MAIN_MENU, mainMenuPanel);

        SettingsPanel settingsPanel = new SettingsPanel(this);
        cards.add(SETTINGS_MENU, settingsPanel);

        GamePanel gamePanel = new GamePanel(commandInterpreter);
        cards.add(GAME_PANEL, gamePanel);

        add(cards);
        setVisible(true);
    }

    public void showMainMenu() {
        cardLayout.show(cards, MAIN_MENU);
    }

    public void showGamePanel() {
        cardLayout.show(cards, GAME_PANEL);
    }

    public void quitApplication() {
        dispose();
    }

    public void showSettingsMenu() {
        cardLayout.show(cards, SETTINGS_MENU);
    }
}
