import java.awt.CardLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameUI extends JFrame {

    private CardLayout cardLayout;
    private JPanel cards;

    private static GameUI instance;
    public final static String MAIN_MENU = "MainMenu";
    public final static String SETTINGS_MENU = "SettingsMenu";
    
    public static GameUI getInstance() {
        if (instance == null) {
            instance = new GameUI();
        }
        return instance;
    }
    private GameUI() {
        setTitle("Projlab Traffic Game ");
        setSize(1280, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        cards = new JPanel(cardLayout);

        MainMenuPanel mainMenuPanel = new MainMenuPanel();
        cards.add(MAIN_MENU, mainMenuPanel);

        SettingsPanel settingsPanel = new SettingsPanel();
        cards.add(SETTINGS_MENU, settingsPanel);

        add(cards);
        setVisible(true);
    }

    public void showMainMenu() {
        cardLayout.show(cards, MAIN_MENU);
    }
    
    public void quitApplication() {
        dispose();
    }

    public void showSettingsMenu() {
        cardLayout.show(cards, SETTINGS_MENU);
    }
}
