package ui;
import gamelogic.GameLogic;
import java.awt.CardLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import utils.CommandInterpreter;

public class GameWindow extends JFrame {

    private final CardLayout cardLayout;
    private final JPanel cards;

    public static final String MAIN_MENU = "MainMenu";
    public static final String SETTINGS_MENU = "SettingsMenu";
    public static final String GAME_PANEL = "GamePanel";

    private final CommandInterpreter commandInterpreter;
    private final GameLogic gameLogic;
    private final SettingsManager settingsManager;

    public GameWindow(CommandInterpreter commandInterpreter, GameLogic gameLogic, SettingsManager settingsManager) {
        this.commandInterpreter = commandInterpreter;
        this.gameLogic = gameLogic;
        this.settingsManager = settingsManager;

        setTitle("Projlab Traffic Game ");
        setSize(1280, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        cards = new JPanel(cardLayout);

        MainMenuPanel mainMenuPanel = new MainMenuPanel(this);
        cards.add(MAIN_MENU, mainMenuPanel);

        SettingsPanel settingsPanel = new SettingsPanel(this, settingsManager);
        cards.add(SETTINGS_MENU, settingsPanel);

        MainPanel gamePanel = new MainPanel(commandInterpreter, gameLogic);
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

    public void startGame() {
        commandInterpreter.execute(settingsManager.getGeneratorCommand());
        
        commandInterpreter.execute("/addPlayer -id player1 -type snowplow -net net -lane Mainroad0.lane1");
        commandInterpreter.execute(settingsManager.getStartCommand());
        showGamePanel();
    }
}
