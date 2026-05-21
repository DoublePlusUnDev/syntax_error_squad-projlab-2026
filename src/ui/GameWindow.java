package ui;
import gamelogic.GameLogic;
import java.awt.CardLayout;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
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
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        cards = new JPanel(cardLayout);

        MainMenuPanel mainMenuPanel = new MainMenuPanel(this);
        cards.add(MAIN_MENU, mainMenuPanel);

        SettingsPanel settingsPanel = new SettingsPanel(this, settingsManager);
        cards.add(SETTINGS_MENU, settingsPanel);

        MainPanel gamePanel = new MainPanel(this,commandInterpreter, gameLogic);
        cards.add(GAME_PANEL, gamePanel);

        LoadGamePanel loadGamePanel = new LoadGamePanel(this, commandInterpreter);
        cards.add("LoadGamePanel", loadGamePanel);

        EndPanel endPanel = new EndPanel(this);
        cards.add("EndPanel", endPanel);

        WaitPanel waitPanel = new WaitPanel(this, gameLogic);
        cards.add("WaitPanel", waitPanel);

        add(cards);
        setVisible(true);
    }

    public void showWaitPanel() {
        cardLayout.show(cards, "WaitPanel");
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

    public void showLoadGamePanel() {
        cardLayout.show(cards, "LoadGamePanel");
    }

    public void showEndPanel() {
        cardLayout.show(cards, "EndPanel");
    }

    public void startGame() {
        Random random = new Random();
        Long seed = random.nextLong();
        commandInterpreter.execute("/seed -seed " + seed.toString());
        commandInterpreter.execute(settingsManager.getGeneratorCommand());

        //String startLaneId = gameLogic.getRoads().getRoadSegments().get(0).getLane(0).id;
        //commandInterpreter.execute("/addPlayer -id player1 -type snowplow -net net -lane " + startLaneId);
        commandInterpreter.execute(settingsManager.getStartCommand());
        showGamePanel();
    }
}
