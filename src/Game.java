import gamelogic.GameLogic;
import ui.GameUI;
import ui.SettingsManager;
import utils.CommandInterpreter;

public class Game {
    
    public static void main(String[] args) {
        CommandInterpreter commandInterpreter = new CommandInterpreter();
        SettingsManager settingsManager = new SettingsManager();

        GameUI gameUI = new GameUI(commandInterpreter, GameLogic.getInstance(), settingsManager);
    }
}
